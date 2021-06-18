package me.jellysquid.mods.hydrogen.common.jvm;

import com.google.common.collect.ImmutableMap;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.util.Map;

@SuppressWarnings("unchecked")
public class ClassConstructors {
    private static MethodHandle FAST_IMMUTABLE_REFERENCE_HASH_MAP_CONSTRUCTOR;

    public static void init() {
        initGuavaExtensions();
    }

    private static void initGuavaExtensions() {
        // define classes in their reverse link order to prevent duplicate definition issues
        ClassDefineTool.defineClass(ImmutableMap.class, "com.google.common.collect.HydrogenImmutableMapEntry");
        ClassDefineTool.defineClass(ImmutableMap.class, "com.google.common.collect.HydrogenEntrySetIterator");
        ClassDefineTool.defineClass(ImmutableMap.class, "com.google.common.collect.HydrogenEntrySet");

        Class<?> immutableRefHashMapClass = ClassDefineTool.defineClass(ImmutableMap.class, "com.google.common.collect.HydrogenImmutableReferenceHashMap");

        try {
            FAST_IMMUTABLE_REFERENCE_HASH_MAP_CONSTRUCTOR = MethodHandles.lookup()
                    .findConstructor(immutableRefHashMapClass, MethodType.methodType(Void.TYPE, Map.class))
                    // compiler can only generate a desc returning ImmutableMap below
                    .asType(MethodType.methodType(ImmutableMap.class, Map.class));
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to find constructor", e);
        }
    }

    public static <K, V> ImmutableMap<K, V> createFastImmutableMap(Map<K, V> orig) {
        try {
            return (ImmutableMap<K, V>) FAST_IMMUTABLE_REFERENCE_HASH_MAP_CONSTRUCTOR.invokeExact((Map<K, V>) orig);
        } catch (Throwable e) {
            throw new RuntimeException("Could not instantiate collection", e);
        }
    }
}
