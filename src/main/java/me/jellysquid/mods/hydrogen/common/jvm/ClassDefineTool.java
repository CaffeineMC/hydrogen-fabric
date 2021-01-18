package me.jellysquid.mods.hydrogen.common.jvm;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

public class ClassDefineTool {
    private static final Method DEFINE;
    private static final Logger LOGGER = LogManager.getLogger("Hydrogen-Reflect");
    private static final boolean IS_JAVA_9 = isJava9OrGreater();

    static {
        if (IS_JAVA_9) {
            try {
                DEFINE = MethodHandles.Lookup.class.getMethod("defineClass", byte[].class);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Could not obtain reference to MethodHandles.Lookup.defineClass(byte[])", e);
            }
        } else {
            LOGGER.warn("Trying to reflect-hack into the class-loader so we can define our own classes in an unrestricted loader...");
            LOGGER.warn("** The JVM might warn you with something nasty! **");
            try {
                DEFINE = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, int.class, int.class);
                DEFINE.setAccessible(true);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Could not obtain reference to ClassLoader.defineClass(byte[], int, int)", e);
            } catch (Throwable e) {
                throw new RuntimeException("Could not expose method", e);
            }
        }
    }

    /**
     * Checks for the presence of required Java 9 method {@link java.lang.invoke.MethodHandles#privateLookupIn(Class, MethodHandles.Lookup)}.
     *
     * @return true if the methods exist (on Java 9 or higher), false otherwise
     */
    private static boolean isJava9OrGreater() {
        try {
            Class.forName("java.lang.invoke.MethodHandles").getDeclaredMethod("privateLookupIn", Class.class, MethodHandles.Lookup.class);
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            return false;
        }
        return true;
    }


    public static void defineClass(Class<?> classInTarget, String name) throws ReflectiveOperationException {
        Object target = getTarget(classInTarget);
        String path = "/" + name.replace('.', '/') + ".class";
        URL url = ClassDefineTool.class.getResource(path);

        LOGGER.info("Injecting class '{}' (url: {})", name, url);

        byte[] code;

        try {
            code = IOUtils.toByteArray(url);
        } catch (IOException e) {
            throw new RuntimeException("Could not read class bytes from resources: " + path, e);
        }

        callDefine(target, code);

    }

    @SuppressWarnings({"JavaReflectionInvocation", "PrimitiveArrayArgumentToVarargsMethod"})
    private static void callDefine(Object target, byte[] code) throws InvocationTargetException, IllegalAccessException {
        if (IS_JAVA_9)
            DEFINE.invoke(target, code);
        else
            DEFINE.invoke(target, code, 0, code.length);
    }

    /**
     * Gets the "target" from a class in that target. A target is a {@link ClassLoader} or a {@link MethodHandles.Lookup}, depending on the version of Java used.
     *
     * @param classInTarget the class in the target
     * @return the target
     * @throws RuntimeException if the target lookup fails
     */
    private static Object getTarget(Class<?> classInTarget) {
        if (IS_JAVA_9) {
            try {
                return MethodHandles.privateLookupIn(classInTarget, MethodHandles.lookup());
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Could not access target module from our module", e);
            }
        } else
            return classInTarget.getClassLoader();
    }

}
