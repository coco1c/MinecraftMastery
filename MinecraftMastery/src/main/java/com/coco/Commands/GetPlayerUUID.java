package com.coco.Commands;

import com.coco.MinecraftMastery;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetPlayerUUID implements CommandExecutor {
    MinecraftMastery main;
    public GetPlayerUUID(MinecraftMastery main){
        this.main = main;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("MinecraftMastery.getuuid")) {
            main.msg.sendMessage(player, main.messages.noPermission());
        }
        if (args.length != 1) {
            main.msg.sendMessage(player, "&cUsage: /getplayeruuid <playername>");
            return true;
        }
        String playerName = args[0];
        Player target = player.getServer().getPlayer(playerName);

        if (target == null) {
            main.msg.sendMessage(player, "&cPlayer not found.");
            return true;
        }
        String uuid = target.getUniqueId().toString();
        TextComponent message = createClickableMessage(uuid);
        player.spigot().sendMessage(ChatMessageType.CHAT, message);
        return true;
    }
    private TextComponent createClickableMessage(String uuid) {
        TextComponent message = new TextComponent(uuid);
        message.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, uuid));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to copy UUID").create()));
        return message;
    }
}
