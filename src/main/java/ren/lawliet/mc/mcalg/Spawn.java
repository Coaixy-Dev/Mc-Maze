package ren.lawliet.mc.mcalg;

import org.bukkit.Location;
import org.bukkit.entity.DragonFireball;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Coaixy
 * @createTime 2024-06-28
 * @packageName ren.lawliet.mc.mcalg
 */
public class Spawn {

    public static void circleAroundPlayer(JavaPlugin plugin, final Entity entity, final Player player, final double radius, final double speed) {
        new BukkitRunnable() {
            double angle = 0;
            double increment = speed / radius; // 控制转圈速度

            @Override
            public void run() {
                if (!entity.isValid() || !player.isOnline()) {
                    this.cancel();
                    return;
                }
                if (player.isSneaking()) {
                    this.cancel();
                    return;
                }
                // 计算下一个位置
                double x = player.getLocation().getX() + radius * Math.cos(angle);
                double z = player.getLocation().getZ() + radius * Math.sin(angle);
                Location newLoc = new Location(player.getWorld(), x, player.getLocation().getY(), z);


                // 设置实体的位置和方向
                entity.teleport(newLoc);

                entity.getLocation().setDirection(player.getLocation().toVector().subtract(entity.getLocation().toVector()));

                // 增加角度
                angle += increment;

                // 如果角度超过360度，重置为0
                if (angle >= 2 * Math.PI) {
                    angle = 0;
                }
            }

        }.runTaskTimer(plugin, 0, 1); // 1tick执行一次
    }
}
