package com.coco.Commands;

import com.coco.MinecraftMastery;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class GetBlocksBroke implements CommandExecutor {
    MinecraftMastery main;
    public GetBlocksBroke(MinecraftMastery main){
        this.main = main;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            if (player.hasPermission("MinecraftMastery.GetBlocksBroke")) {
                if (args.length == 1) {
                    String playerName = args[0];
                    Player target = Bukkit.getPlayer(playerName);
                    if (target != null) {
                        UUID playerUUID = target.getUniqueId();
                        main.msg.sendMessage(player, main.getConfig().getString("Messages.GetBlocksBroke")
                                .replace("{target}", target.getName())
                                .replace("{blocks_broke}", String.valueOf(main.getConfig().getInt("data.players." + playerUUID + ".Blocksbroke"))));
                        return true;
                    }
                    main.msg.sendMessage(player, "Messages.PlayerNotFound");
                    return true;
                } else if (args.length == 0){
                    UUID playerUUID = player.getUniqueId();
                    main.msg.sendMessage(player, main.getConfig().getString("Messages.GetBlocksPlacedSender")
                            .replace("{blocks_broke}", String.valueOf(main.getConfig().getInt("data.players." + playerUUID + ".Blocksbroke"))));
                    return true;
                }
            } else {
                main.msg.sendMessage(player, main.getConfig().getString("Messages.NoPermission"));
                return true;
            }
        }
        return false;
    }
}
