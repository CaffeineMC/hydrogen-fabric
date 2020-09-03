package me.jellysquid.mods.hydrogen.common.state.single;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Property;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class SingleMatchAny implements Predicate<BlockState> {
    public static final ObjectOpenHashSet<SingleMatchAny> PREDICATES = new ObjectOpenHashSet<>();

    public final Property<?> property;
    public final Object[] values;

    private SingleMatchAny(Property<?> property, List<Object> values) {
        this.property = property;
        this.values = values.toArray();
    }

    public static SingleMatchAny create(Property<?> property, List<Object> values) {
        return PREDICATES.addOrGet(new SingleMatchAny(property, values));
    }

    public static boolean areOfType(List<Predicate<BlockState>> predicates) {
        return predicates.stream()
                .allMatch(p -> {
                    return p instanceof SingleMatchAny;
                });
    }

    public static boolean valuesMatchType(List<Predicate<BlockState>> predicates, Class<?> type) {
        return predicates.stream()
                .allMatch(p -> {
                    return p instanceof SingleMatchAny &&
                            Arrays.stream(((SingleMatchAny) p).values).allMatch(t -> type.isInstance(p));
                });
    }

    @Override
    public boolean test(BlockState blockState) {
        return ArrayUtils.contains(this.values, blockState.get(this.property));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SingleMatchAny that = (SingleMatchAny) o;
        return Objects.equals(property, that.property) &&
                Arrays.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(property);
        result = 31 * result + Arrays.hashCode(values);
        return result;
    }
}
