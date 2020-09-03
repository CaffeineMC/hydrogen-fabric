package me.jellysquid.mods.hydrogen.common.state.any;

import me.jellysquid.mods.hydrogen.common.state.single.SingleMatchAny;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Property;
import org.apache.commons.lang3.ArrayUtils;

import java.util.List;
import java.util.function.Predicate;

public class AllMatchAnyObject implements Predicate<BlockState> {
    private final Property<?>[] properties;
    private final Object[][] values;

    public AllMatchAnyObject(List<Predicate<BlockState>> list) {
        int size = list.size();

        this.properties = new Property[size];
        this.values = new Object[size][];

        for (int i = 0; i < size; i++) {
            SingleMatchAny predicate = (SingleMatchAny) list.get(i);

            this.properties[i] = predicate.property;
            this.values[i] = predicate.values;
        }
    }

    @Override
    public boolean test(BlockState blockState) {
        for (int i = 0; i < this.properties.length; i++) {
            if (!ArrayUtils.contains(this.values[i], blockState.get(this.properties[i]))) {
                return false;
            }
        }

        return true;
    }
}
