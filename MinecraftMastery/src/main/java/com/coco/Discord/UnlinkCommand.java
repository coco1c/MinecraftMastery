package com.coco.Discord;

import com.coco.MinecraftMastery;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

public class UnlinkCommand implements CommandExecutor {
    private MinecraftMastery main;
    private FileConfiguration config;

    public UnlinkCommand(MinecraftMastery main) {
        this.main = main;
        config = main.getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can run this command!");
            return false;
        }
        Player player = (Player) sender;

        if (!player.hasPermission("DiscordLinking.unlink")) {
            player.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }
        UUID playerUUID = player.getUniqueId();
        if (main.discordConfig.isPlayerLinked(playerUUID)){
            main.discordConfig.unLinkPlayer(playerUUID);
            main.msg.sendMessage(player, config.getString("Messages.unlink"));
            return true;
        }
        return true;
    }
}
