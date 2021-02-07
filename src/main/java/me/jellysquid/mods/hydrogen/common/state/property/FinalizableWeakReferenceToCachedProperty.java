package me.jellysquid.mods.hydrogen.common.state.property;

import com.google.common.base.FinalizableReferenceQueue;
import com.google.common.base.FinalizableWeakReference;
import net.minecraft.state.property.Property;

import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * A {@link FinalizableWeakReference} for just {@link Property}s that are being cached. This clears the cache when the referent is finalized.
 *
 * @param <T> the {@linkplain Property} type being cached
 * @implNote This operates on a background thread, so caches must be concurrent or synchronized!
 */
public class FinalizableWeakReferenceToCachedProperty<T extends Property<?>> extends FinalizableWeakReference<T> {
    private static final FinalizableReferenceQueue referenceQueue = new FinalizableReferenceQueue();
    private final String name;
    private final Map<String, WeakReference<T>> cacheMap;

    /**
     * Constructs a new finalizable weak reference.
     *
     * @param referent to weakly reference
     * @param cacheMap the cache to remove the referent from when finalized
     */
    public FinalizableWeakReferenceToCachedProperty(T referent, Map<String, WeakReference<T>> cacheMap) {
        super(referent, referenceQueue);
        this.name = referent.getName();
        this.cacheMap = cacheMap;
    }

    @Override
    public void finalizeReferent() {
        System.err.println("Removing " + this.name + " from the cache!");
        cacheMap.remove(this.name);
    }
}
