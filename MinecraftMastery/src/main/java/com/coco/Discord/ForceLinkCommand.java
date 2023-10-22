package com.coco.Discord;

import com.coco.MinecraftMastery;
import net.dv8tion.jda.api.entities.User;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ForceLinkCommand implements CommandExecutor {
    MinecraftMastery main;
    FileConfiguration config;
    public ForceLinkCommand(MinecraftMastery main) {
        this.main = main;
        config = main.getConfig();
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("DiscordLink.link.force")) {
            player.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }

        if (args.length == 0) {
            main.msg.sendMessage(player, "&cUsage /forcelink (Player) (Discord ID)");
            return true;
        }
        if (args.length == 1) {
            main.msg.sendMessage(player, "&cUsage /forcelink " + args[0] + " (Discord ID)");
            return true;
        }
        if (args.length == 2){
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null){
                main.msg.sendMessage(player, "&cThere is no player with that UUID");
                return true;
            }
            UUID targetUUID = target.getUniqueId();
            for (Player i : Bukkit.getOnlinePlayers()) {
                String discordID = args[1];
                String playerName = i.getName();

                if (discordID.equalsIgnoreCase(playerName)) {
                    main.msg.sendMessage(player, "&cPlease use a Discord ID instead of a player name. /forceunlink (player) &e(Discord ID)");
                    return true;
                }
            }

            if (main.discordConfig.linkPlayer(targetUUID, args[1])) {
                main.msg.sendMessage(player, config.getString("Messages.forceunlink_to_sender")
                        .replace("{target_name}", target.getName())
                        .replace("{target_discord}", args[1]));
                main.msg.sendMessage(target, config.getString("Messages.").replace("{target_discord}", args[1]));
                return true;
            } else {
                if (main.discordConfig.isPlayerLinked(targetUUID)) {
                    main.msg.sendMessage(player, "&cThat player is already linked to a discord account.");
                    main.msg.sendMessage(player, "&cUse &e/forceunlink (player) &cto unlink that player!");
                    return true;
                }
                main.msg.sendMessage(player, "&cThere was an error while linking that player!");
                return true;
            }
        }
        if (args.length >= 3){
            main.msg.sendMessage(player, "&cUsage /forcelink (Player) (Discord ID)");
            return true;
        }

        return true;
    }

}
