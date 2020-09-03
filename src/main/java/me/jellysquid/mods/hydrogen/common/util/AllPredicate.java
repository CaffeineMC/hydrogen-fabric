package me.jellysquid.mods.hydrogen.common.util;

import java.util.function.Predicate;

public class AllPredicate<T> implements Predicate<T> {
    private final Predicate<T>[] predicates;

    public AllPredicate(Predicate<T>[] predicates) {
        this.predicates = predicates;
    }

    @Override
    public boolean test(T t) {
        for (Predicate<T> predicate : this.predicates) {
            if (!predicate.test(t)) {
                return false;
            }
        }

        return true;
    }
}
