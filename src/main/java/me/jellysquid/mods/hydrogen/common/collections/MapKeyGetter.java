package me.jellysquid.mods.hydrogen.common.collections;

import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * This uses some spooky code to, given an element {@code element} find the element {@code original} stored in a set such that {@code new.equals(original) == true}.
 * <p>
 * This class is based on a <a href="https://stackoverflow.com/a/37603105/9073728" target="_top">StackOverflow answer</a>.
 */
public class MapKeyGetter<T> {

    private final T element;
    @Nullable
    private Object original = null;

    public MapKeyGetter(T element) {
        this.element = element;
    }

    @SuppressWarnings({"ResultOfMethodCallIgnored", "SuspiciousMethodCalls", "unchecked"})
    public static <K> K getOriginalKey(Map<K, ?> map, K newElem) {
        MapKeyGetter<K> getter = new MapKeyGetter<>(newElem);
        map.containsKey(getter);
        return (K) getter.get();
    }


    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(final Object o) {
        boolean eq = this.element.equals(o);
        if (eq)
            this.original = o;
        return eq;
    }

    @Override
    public int hashCode() {
        return this.element.hashCode();
    }

    /**
     * This should only be called after this class has been used!
     *
     * @return the last object that tested equal to {@link MapKeyGetter#element}
     */
    private Object get() {
        return this.original;
    }
}
