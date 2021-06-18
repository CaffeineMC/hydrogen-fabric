package me.jellysquid.mods.hydrogen.common.jvm;

import jdk.dynalink.linker.support.Lookup;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.net.URL;

public class ClassDefineTool {
    private static final MethodHandles.Lookup LOOKUP;

    private static final MethodHandle DEFINE_CLASS_METHOD;
    private static final MethodHandle PRIVATE_LOOKUP_IN_METHOD;

    private static final Logger LOGGER = LogManager.getLogger("Hydrogen");

    static {
        LOOKUP = MethodHandles.lookup();

        try {
            PRIVATE_LOOKUP_IN_METHOD = LOOKUP.findStatic(MethodHandles.class, "privateLookupIn",
                    MethodType.methodType(MethodHandles.Lookup.class, Class.class, MethodHandles.Lookup.class));

            DEFINE_CLASS_METHOD = LOOKUP.findVirtual(MethodHandles.Lookup.class, "defineClass",
                    MethodType.methodType(Class.class, byte[].class));
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't access method", e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Couldn't locate method", e);
        }
    }

    public static Class<?> defineClass(Class<?> context, String name) {
        String path = "/" + name.replace('.', '/') + ".class";
        URL url = ClassDefineTool.class.getResource(path);

        if (url == null) {
            throw new RuntimeException("Couldn't find resource: " + path);
        }

        LOGGER.info("Injecting class '{}' (url: {})", name, url);

        byte[] code;

        try {
            code = IOUtils.toByteArray(url);
        } catch (IOException e) {
            throw new RuntimeException("Could not read class bytes from resources: " + path, e);
        }

        try {
            MethodHandles.Lookup privateLookup = (MethodHandles.Lookup) PRIVATE_LOOKUP_IN_METHOD.invokeExact((Class<?>) context, (MethodHandles.Lookup) LOOKUP);

            return (Class<?>) DEFINE_CLASS_METHOD.invokeExact(privateLookup, (byte[]) code);
        } catch (Throwable throwable) {
            throw new RuntimeException("Failed to define class", throwable);
        }
    }

}
