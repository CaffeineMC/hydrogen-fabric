package me.jellysquid.mods.hydrogen.common.state.property;

import me.jellysquid.mods.hydrogen.common.collections.MapKeyGetter;
import net.minecraft.state.property.Property;

import java.util.WeakHashMap;

public class WeakPropertyCache {

    /**
     * Values are type Void to prevent anything other than {@code null} from being in them since it's being used as a {@link java.util.Set}.
     */
    private static final WeakHashMap<Property<?>, Void> CACHE = new WeakHashMap<>();

    /**
     * Caches properties passed into it in a {@link WeakHashMap}.
     *
     * @param newProperty the property to cache
     * @param <T>         the property type (checked by the property's equals method)
     * @return the cached property
     */
    @SuppressWarnings("unchecked")
    public static <T extends Property<?>> T tryCacheProperty(T newProperty) {
        // synchronized to prevent interleaving of separated contains-put pattern
        synchronized (CACHE) {
            // Checked by the property's equals method in MapKeyGetter#equals
            T originalKey = (T) MapKeyGetter.getOriginalKey(CACHE, newProperty);
            if (originalKey != null) {
                return originalKey;
            } else {
                CACHE.put(newProperty, null);
                return newProperty;
            }
        }
    }
}
