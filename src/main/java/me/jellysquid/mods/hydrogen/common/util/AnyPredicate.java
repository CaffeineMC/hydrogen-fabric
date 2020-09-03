package me.jellysquid.mods.hydrogen.common.util;

import java.util.function.Predicate;

public class AnyPredicate<T> implements Predicate<T> {
    private final Predicate<T>[] predicates;

    public AnyPredicate(Predicate<T>[] predicates) {
        this.predicates = predicates;
    }

    @Override
    public boolean test(T t) {
        for (Predicate<T> predicate : this.predicates) {
            if (predicate.test(t)) {
                return true;
            }
        }

        return false;
    }
}
