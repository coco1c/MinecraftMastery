package com.coco.Discord;

import com.coco.MinecraftMastery;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class LinkCommand implements CommandExecutor {
    private MinecraftMastery main;
    private FileConfiguration config;
    private Random random = new Random();
    public static List<String> codes = new ArrayList<>();

    public LinkCommand(MinecraftMastery main) {
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

        if (!player.hasPermission("DiscordLink.link")) {
            player.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }

        if (args.length == 0) {
            int code = random.nextInt(1000000000) + 1000;
            String message = config.getString("Messages.LinkMessage");
            message = message.replace("{code}", String.valueOf(code));

            TextComponent textComponent = new TextComponent(ChatColor.translateAlternateColorCodes('&', message));
            textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, String.valueOf(code)));

            player.spigot().sendMessage(ChatMessageType.CHAT, textComponent);

            String path = "codes." + code;
            config.set(path, player.getUniqueId().toString());
            codes.add(String.valueOf(code));
            main.saveConfig();
            return true;
        }

        return true;
    }

}