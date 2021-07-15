package me.jellysquid.mods.hydrogen.common.jvm;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.URL;

public class ClassDefineTool {
    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();

    private static final Logger LOGGER = LogManager.getLogger("Hydrogen");

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
            // The context class need to be in a module that exports and opens to ClassDefineTool
            // Example: guava's automatic module exports and opens to everything
            MethodHandles.Lookup privateLookup = MethodHandles.privateLookupIn(context, LOOKUP);
            return privateLookup.defineClass(code);
        } catch (Throwable throwable) {
            throw new RuntimeException("Failed to define class", throwable);
        }
    }

}
