package ren.lawliet.mc.mcalg;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Coaixy
 * @createTime 2024-06-27
 * @packageName ren.lawliet.mc.mcalg
 */

public class PopSort {

    private static int[] generateArray(int size) {
        Random rand = new Random();
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = rand.nextInt(size);
        }
        return result;
    }

    private static ArrayList<Material> generateMaterial(int size) {
        Material[] allMaterials = Material.values();
        ArrayList<Material> materials = new ArrayList<>();
        ArrayList<Material> result = new ArrayList<>();
        for (Material material : allMaterials) {
            if (material.name().contains("WOOL")) {
                materials.add(material);
            }
        }
        for (int i = 0; i < size; i++) {
            int index = i;
            if (index > materials.size() - 1) {
                index = i % (materials.size() - 1);
            }
            result.add(materials.get(index));
        }
        return result;
    }

    public static void sort(JavaPlugin plugin, World world, Location location, int size) throws InterruptedException {
        int[] array = generateArray(size);
        ArrayList<Material> materials = generateMaterial(array.length);
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        HashMap<Double, Integer> sizeMap = updateSize(x, array);
        int index = 0;
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    sizeMap = updateSize(x, array);
                    index++;
                    HashMap<Double, Integer> finalSizeMap = sizeMap;
                    new BukkitRunnable() {
                        public void run() {
                            updateBlock(world, materials, (int) y, (int) z, finalSizeMap, size);
                        }
                    }.runTaskLater(plugin, 3L * index);

                }
            }
        }

    }

    private static void updateBlock(World world, ArrayList<Material> materials, int y, int z, HashMap<Double, Integer> sizeMap, int max) {
        AtomicInteger index = new AtomicInteger();
        sizeMap.forEach((x, size) -> {
                    for (int k = 0; k < max; k++) {
                        world.getBlockAt(x.intValue(), y + k, z).setType(Material.AIR);
                    }
                    Material material = materials.get(index.get());
                    for (int k = 0; k < size; k++) {
                        world.getBlockAt(x.intValue(), y + k, z).setType(materials.get(size));
                    }
                    index.getAndIncrement();
                }
        );
    }

    private static HashMap<Double, Integer> updateSize(double startX, int[] array) {
        HashMap<Double, Integer> numbers = new HashMap<>();
        double newX = startX;
        /**
         * 1 : x坐标 2 : size
         */
        for (int i = 0; i < array.length; i++) {
            numbers.put(newX, array[i]);
            newX++;
        }
        return numbers;
    }
}
