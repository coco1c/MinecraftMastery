package com.coco.Util;

import com.coco.MinecraftMastery;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerData {
    private MinecraftMastery main;
    private FileConfiguration playerDataConfig;

    public PlayerData(MinecraftMastery main) {
        this.main = main;
        this.playerDataConfig = main.getConfig();
    }

    private String getPlayerDataPath(Player player) {
        UUID playerUUID = player.getUniqueId();
        return "data.players." + playerUUID + ".";
    }

    private void savePlayerConfig() {
        main.saveConfig();
    }

    public int getBlocksBrokeForPlayer(Player player) {
        String path = getPlayerDataPath(player);
        return playerDataConfig.getInt(path + "BlocksBroke", 0);
    }

    public void setBlocksBrokeForPlayer(Player player, int blocksBroke) {
        String path = getPlayerDataPath(player);
        playerDataConfig.set(path + "BlocksBroke", blocksBroke);
        savePlayerConfig();
    }

    public void addBlocksBrokeForPlayer(Player player, int blocksToAdd) {
        String path = getPlayerDataPath(player);
        int oldBlocks = getBlocksBrokeForPlayer(player);
        int newBlocks = oldBlocks + blocksToAdd;
        setBlocksBrokeForPlayer(player, newBlocks);
    }

    public void setPlayerLevel(Player player, int level) {
        String path = getPlayerDataPath(player);
        playerDataConfig.set(path + "level", level);
        savePlayerConfig();
    }

    public int getPlayerLevel(Player player) {
        String path = getPlayerDataPath(player);
        return playerDataConfig.getInt(path + "level", 1);
    }

    public void setBlocksPlacedForPlayer(Player player, int blocksPlaced) {
        String path = getPlayerDataPath(player);
        playerDataConfig.set(path + "BlocksPlaced", blocksPlaced);
        savePlayerConfig();
    }

    public void addBlocksPlacedForPlayer(Player player, int blocksToAdd) {
        String path = getPlayerDataPath(player);
        int oldBlocks = getBlocksPlaced(player);
        int newBlocks = oldBlocks + blocksToAdd;
        setBlocksPlacedForPlayer(player, newBlocks);
    }

    public int getBlocksPlaced(Player player) {
        String path = getPlayerDataPath(player);
        return playerDataConfig.getInt(path + "BlocksPlaced");
    }
}
