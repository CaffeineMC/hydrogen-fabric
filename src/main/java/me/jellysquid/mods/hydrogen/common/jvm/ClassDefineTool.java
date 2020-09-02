package me.jellysquid.mods.hydrogen.common.jvm;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;

public class ClassDefineTool {
    private static final Method DEFINE;
    private static final Logger LOGGER = LogManager.getLogger("Hydrogen-Reflect");

    static {
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

    public static void defineClass(ClassLoader loader, String name) throws ReflectiveOperationException {
        String path = "/" + name.replace('.', '/') + ".class";
        URL url = ClassDefineTool.class.getResource(path);

        LOGGER.info("Injecting class '{}' (url: {})", name, url);

        byte[] code;

        try {
            code = IOUtils.toByteArray(url);
        } catch (IOException e) {
            throw new RuntimeException("Could not read class bytes from resources: " + path, e);
        }

        DEFINE.invoke(loader, code, 0, code.length);
    }

}
