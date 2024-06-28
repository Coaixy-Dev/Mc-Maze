package ren.lawliet.mc.mcalg;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.DragonFireball;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

import static ren.lawliet.mc.mcalg.Spawn.circleAroundPlayer;

public final class Mc_alg extends JavaPlugin {

    @Override
    public void onLoad() {
        Bukkit.getServer().getLogger().info("Loading McAlg");

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (command.getName().equalsIgnoreCase("alg")) {
                if (args[0].equalsIgnoreCase("maze")) {
                    Maze.startMaze(player, args);
                }
                if (args[0].equalsIgnoreCase("pop")) {
                    int size = Integer.parseInt(args[1]);
                    Block block = Objects.requireNonNull(player.getTargetBlockExact(10));
                    Location location = block.getLocation();

                    Bukkit.getScheduler().runTask(this, () -> {
                        try {
                            PopSort.sort(this, player.getWorld(), location, size);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    });

                }
                if (args[0].equalsIgnoreCase("spawn")) {
//                    DragonFireball dragonFireball = (DragonFireball) player.getWorld().spawnEntity(player.getLocation(), EntityType.DRAGON_FIREBALL);
//                    double radius = Double.parseDouble(args[1]); // 半径
//                    double speed = Double.parseDouble(args[2]); // 转圈速度
//                    circleAroundPlayer(this, dragonFireball, player, radius, speed);

                    new BukkitRunnable() {
                        public void run() {
                            LightningStrike lightningStrike = (LightningStrike) player.getWorld().spawnEntity(player.getLocation().add(0, 10, 0), EntityType.LIGHTNING);
                            lightningStrike.setFlashes(5);
                            lightningStrike.setCausingPlayer(player);
                            if (player.isSneaking()) {
                                this.cancel();
                            }
                        }
                    }.runTaskTimer(this, 0, 20);

                }
                return true;
            }
        }
        return false;
    }
}
