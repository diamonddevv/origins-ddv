package net.diamonddev.ddvorigins.util;

import net.minecraft.util.math.random.Random;

import java.util.ArrayList;

public class WeightedList<T> extends ArrayList<WeightedList.Entry<T>> {

    public void add(T obj, int weight) {
        add(new Entry<>(obj, weight));
    }

    public T getRandom(Random random) {
        ArrayList<T> list = new ArrayList<>();
        for (Entry<T> entry : this) {
            for (int i = 0; i < entry.weight; i++) {
                list.add(entry.obj);
            }
        }
        return list.get(random.nextBetween(0, list.size()));
    }

    public record Entry<T>(T obj, int weight) {}
}
