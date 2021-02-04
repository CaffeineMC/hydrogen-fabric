package me.jellysquid.mods.hydrogen.common.jvm;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.lang.invoke.WrongMethodTypeException;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;

public class ClassDefineTool {
    // Don't know how bad this works with vm intrinsics.
    private static final Tool TOOL;
    private static final Logger LOGGER = LogManager.getLogger("Hydrogen-Reflect");

    static {
        LOGGER.warn("Trying to reflect-hack into the class-loader so we can define our own classes in an unrestricted loader...");
        LOGGER.warn("** The JVM might warn you with something nasty! **");

        TOOL = createTool();
    }

    private static Tool createTool() {
        Lookup lookup = MethodHandles.lookup();
        try {
            MethodHandle privateLookupIn = lookup.findStatic(MethodHandles.class, "privateLookupIn", MethodType.methodType(Lookup.class, Class.class, Lookup.class));
            MethodHandle defineClass = lookup.findVirtual(Lookup.class, "defineClass", MethodType.methodType(Class.class, byte[].class));
            return (context, bytes) -> (Class<?>) defineClass.invokeExact((Lookup) privateLookupIn.invokeExact((Class<?>) context, (Lookup) lookup), (byte[]) bytes);
        } catch (NoSuchMethodException | IllegalAccessException expected) {
            LOGGER.info("Couldn't find modern Java's way of defining class, falling back to reflecting ClassLoader");
            // fall through. Should we check illegal access exceptions though?
        }

        try {
            Method define = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, int.class, int.class);
            define.setAccessible(true);
            MethodHandle handle = lookup.unreflect(define);
            return (context, bytes) -> (Class<?>) handle.invokeExact((ClassLoader) context.getClassLoader(), (byte[]) bytes, (int) 0, (int) bytes.length);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Could not obtain reference to ClassLoader.defineClass(byte[], int, int)", e);
        } catch (Throwable e) {
            throw new RuntimeException("Could not expose method", e);
        }
    }

    public static Class<?> defineClass(Class<?> context, String name) throws WrongMethodTypeException {
        String path = "/" + name.replace('.', '/') + ".class";
        URL url = ClassDefineTool.class.getResource(path);

        LOGGER.info("Injecting class '{}' (url: {})", name, url);

        byte[] code;

        try {
            code = IOUtils.toByteArray(url);
        } catch (IOException e) {
            throw new RuntimeException("Could not read class bytes from resources: " + path, e);
        }

        try {
            return TOOL.define(context, code);
        } catch (Throwable propagated) {
            // includes propagating WrongMethodTypeException
            throw propagate(propagated);
        }
    }

    /**
     * Rethrows a throwable like an unchecked exception or error. This is prohibited by javac
     * but acceptable by the JVM.
     *
     * @param e the exception to throw
     * @param <E> the generic to trick the compiler
     * @return a stub so you can write {@code throw propagate(ex);} to end control flow
     * if you return in the try block above
     * @throws E always
     */
    @SuppressWarnings("unchecked")
    static <E extends Throwable> E propagate(Throwable e) throws E {
        throw (E) e;
    }

    interface Tool {
        Class<?> define(Class<?> context, byte[] bytes) throws Throwable;
    }

}
