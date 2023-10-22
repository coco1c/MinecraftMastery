package com.coco.Events;

import com.coco.MinecraftMastery;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerJoin implements Listener {
    private MinecraftMastery main;
    private FileConfiguration config;

    public PlayerJoin(MinecraftMastery main) {
        this.main = main;
        config = main.getConfig();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        String path = "data.players." + playerUUID + ".";

        if (!config.contains(path)) {
            config.set(path + "BlocksPlaced", 0);
            config.set(path + "BlocksBroke", 0);
            config.set(path + "level", 1);

            main.getLogger().info("Player data initialized for " + player.getName());
            main.saveConfig();
        }
    }
}
