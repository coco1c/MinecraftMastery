package com.coco.Commands;

import com.coco.GUI.ManagementGUI;
import com.coco.MinecraftMastery;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OpenManagementGUI implements CommandExecutor {
    MinecraftMastery main;
    public OpenManagementGUI(MinecraftMastery main){
        this.main = main;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("MinecraftMastery.managementgui.open")) {
            main.msg.sendMessage(player, main.messages.noPermission());
            return true;
        }
        new ManagementGUI().displayTo(player);
        return true;

    }
}
