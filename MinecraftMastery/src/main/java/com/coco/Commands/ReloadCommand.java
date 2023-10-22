package com.coco.Commands;

import com.coco.MinecraftMastery;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {
    MinecraftMastery main;

    public ReloadCommand(MinecraftMastery main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("MinecraftMastery.reload")){
            if (sender instanceof Player){
                Player player = (Player) sender;
                main.reloadConfig();
                main.msg.sendMessage(player, "&aReloading MinecraftMastery...");
                main.msg.sendMessage(player, "&aMinecraftMastery reloaded.");
            }
        }
        return true;
    }
}
