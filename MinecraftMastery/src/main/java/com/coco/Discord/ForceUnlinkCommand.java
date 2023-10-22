package com.coco.Discord;

import com.coco.MinecraftMastery;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ForceUnlinkCommand implements CommandExecutor {
    private MinecraftMastery main;
    private FileConfiguration config;
    public ForceUnlinkCommand(MinecraftMastery main) {
        this.main = main;
        config = main.getConfig();
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can run this command!");
            return false;
        }
        Player player = (Player) sender;

        if (!player.hasPermission("DiscordLinking.unlink")) {
            player.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }
        if (args.length == 0) {
            main.msg.sendMessage(player, "&cUsage /forceunlink (Player)");
            return true;
        }
        if (args.length == 1){
            Player target = main.getServer().getPlayer(args[0]);
            if (target == null){
                main.msg.sendMessage(player, "&cThere is no player with that UUID");
                return true;
            }
            main.discordConfig.unLinkPlayer(target.getUniqueId());
            main.msg.sendMessage(player, "&aSuccessfully unlinked &7" + target.getName());
            return true;
        }
        return false;
    }
}
