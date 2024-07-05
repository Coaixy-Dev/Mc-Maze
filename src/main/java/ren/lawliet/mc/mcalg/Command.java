package ren.lawliet.mc.mcalg;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Locale;
import java.util.Objects;

import static ren.lawliet.mc.mcalg.Spawn.circleAroundPlayer;

/**
 * @author Coaixy
 * @createTime 2024-06-29
 * @packageName ren.lawliet.mc.mcalg
 */

public class Command implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (command.getName().equalsIgnoreCase("alg")) {
                if (args[0].equalsIgnoreCase("maze")) {
                    Maze.startMaze(player, args);
                }
                if (args[0].equalsIgnoreCase("sort")) {
                    String type = args[1];
                    int size = Integer.parseInt(args[2]);
                    if (type.equalsIgnoreCase("pop")) {
                        Block block = player.getTargetBlockExact(10);
                        Location location;
                        if (block != null) {
                            location = block.getLocation();
                        } else {
                            location = player.getLocation();
                        }

                        Bukkit.getScheduler().runTask(Config.pluginInstance, () -> {
                            try {
                                Sort.popSort(Config.pluginInstance, player.getWorld(), location, size);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }

                }
                if (args[0].equalsIgnoreCase("spawn")) {
                    DragonFireball dragonFireball = (DragonFireball) player.getWorld().spawnEntity(player.getLocation(), EntityType.DRAGON_FIREBALL);
                    double radius = Double.parseDouble(args[1]); // 半径
                    double speed = Double.parseDouble(args[2]); // 转圈速度
                    circleAroundPlayer(Config.pluginInstance, dragonFireball, player, radius, speed);
                }

                if (args[0].equalsIgnoreCase("vehicle")) {
                    int speed = Integer.parseInt(args[1]);
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            Entity vehicle = player.getVehicle();
                            if (vehicle == null) {
                                this.cancel();
                            }
                            if (player.isSneaking()) {
                                this.cancel();
                            }
                            Vector velocity = player.getLocation().getDirection().multiply(speed);
                            if (vehicle instanceof Minecart) {
                                ((Minecart) vehicle).setMaxSpeed(speed);
                            } else if (vehicle instanceof LivingEntity) {
                                ((LivingEntity) vehicle).setHealth(10);
                            }
                            vehicle.setVelocity(velocity);
                        }
                    }.runTaskTimer(Config.pluginInstance, 0, 5);
                }
                return true;
            }

        }
        return false;
    }
}
