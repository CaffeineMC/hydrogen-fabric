package me.jellysquid.mods.hydrogen.common.state.all;

import me.jellysquid.mods.hydrogen.common.state.single.SingleMatchOne;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Property;

import java.util.List;
import java.util.function.Predicate;

public class AllMatchOneBoolean implements Predicate<BlockState> {
    private final Property<?>[] properties;
    private final boolean[] values;

    public AllMatchOneBoolean(List<Predicate<BlockState>> list) {
        int size = list.size();

        this.properties = new Property[size];
        this.values = new boolean[size];

        for (int i = 0; i < size; i++) {
            SingleMatchOne predicate = (SingleMatchOne) list.get(i);

            this.properties[i] = predicate.property;
            this.values[i] = (boolean) predicate.value;
        }
    }

    public static boolean canReplace(List<Predicate<BlockState>> list) {
        return list.stream()
                .allMatch(p -> {
                    return p instanceof SingleMatchOne && ((SingleMatchOne) p).value instanceof Boolean;
                });
    }

    @Override
    public boolean test(BlockState blockState) {
        for (int i = 0; i < this.properties.length; i++) {
            Boolean value = (Boolean) blockState.get(this.properties[i]);

            if (value != this.values[i]) {
                return false;
            }
        }

        return true;
    }
}
