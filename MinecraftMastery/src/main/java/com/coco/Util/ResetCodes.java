package com.coco.Util;

import com.coco.MinecraftMastery;
import org.bukkit.configuration.file.FileConfiguration;

public class ResetCodes {
    private MinecraftMastery main;
    private FileConfiguration config;

    public ResetCodes(MinecraftMastery main) {
        this.main = main;
        config = main.getConfig();
    }

    public void start() {
        try {
            main.getLogger().info("Starting to clear the codes...");
            String pathCodes = "codes";
            config.set(pathCodes, "");
            main.saveConfig();
            main.getLogger().info("Codes have been successfully reset.");
        } catch (Exception e) {
            e.printStackTrace();
            main.getLogger().info("Failed to clear the codes.");
        }
    }
}
