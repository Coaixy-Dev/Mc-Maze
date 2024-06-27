package ren.lawliet.mc.mcalg;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Mc_alg extends JavaPlugin {

    @Override
    public void onLoad() {
        Bukkit.getServer().getLogger().info("Loading McAlg");

    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && command.getName().equalsIgnoreCase("maze")) {
            Player player = (Player) sender;
            player.sendMessage("[Maze] Create Maze");
            Block block = Objects.requireNonNull(player.getTargetBlockExact(10));
            Location location = block.getLocation();
            player.sendMessage(block.getType().name() + " " + location.getX() + " " + location.getY() + " " + location.getZ());

            int row = Integer.parseInt(args[0]);
            int col = Integer.parseInt(args[1]);
            double ob  = Double.parseDouble(args[2]);
            player.sendMessage("The Size of " + row + " " + col);
            int[][] mazeArray = maze.generateMaze(row, col, ob);
            // x is row z is col
            int startX = (int) location.getX();
            int startZ = (int) location.getZ();
            int Y = (int) location.getY();
            World world = player.getWorld();


            for (int i = 0; i < mazeArray.length; i++) {
                for (int j = 0; j < mazeArray[i].length; j++) {
                    int x = startX + j;
                    int z = startZ + i;
                    Block newBlock = world.getBlockAt(x, Y, z);
                    if (mazeArray[i][j] == 1) {
                        newBlock.setType(Material.STONE);
                    } else if (mazeArray[i][j] == 0) {
                        newBlock.setType(Material.AIR);
                    }
                    if (mazeArray[i][j] == 2 || mazeArray[i][j] == 3) {
                        newBlock.setType(block.getType());
                    }
                }
            }
            return true;
        }
        return false;
    }
}
