package ren.lawliet.mc.mcalg;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;


public final class Mc_alg extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getServer().getLogger().info("Loading McAlg");
        Config.pluginInstance = this;
        Objects.requireNonNull(this.getCommand("alg")).setExecutor(new Command());
        this.getServer().getPluginManager().registerEvents(this, this);
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent event) {
        // 获取玩家指向的实体
        Entity entity = event.getRightClicked();

        // 判断实体是否为生物实体
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.addPassenger(event.getPlayer());
        }
    }


}
