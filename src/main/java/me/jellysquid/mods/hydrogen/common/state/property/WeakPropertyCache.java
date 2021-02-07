package me.jellysquid.mods.hydrogen.common.state.property;

import me.jellysquid.mods.hydrogen.common.collections.MapKeyGetter;
import net.minecraft.state.property.Property;

import java.util.WeakHashMap;

public class WeakPropertyCache {

    private static final WeakHashMap<Property<?>, Void> CACHE = new WeakHashMap<>();

    public static <T extends Property<?>> T tryCacheProperty(T newProperty) {
        // synchronized to prevent interleaving of separated contains-put pattern
        synchronized (CACHE) {
            // Checked by the property's equals method in MapKeyGetter#equals
            @SuppressWarnings("unchecked")
            T originalKey = (T) MapKeyGetter.getOriginalKey(CACHE, newProperty);
            if (originalKey != null) {
                System.err.println("Got \"" + originalKey + "\" from cache!");
                return originalKey;
            } else {
                System.err.println("Caching \"" + newProperty + "\"");
                CACHE.put(newProperty, null);
                return newProperty;
            }
        }
    }
}
