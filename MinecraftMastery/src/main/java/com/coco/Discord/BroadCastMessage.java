package com.coco.Discord;

import com.coco.MinecraftMastery;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class BroadCastMessage implements CommandExecutor {
    private final JDA jda;
    private final MinecraftMastery main;

    public BroadCastMessage(MinecraftMastery main, JDA jda) {
        this.main = main;
        this.jda = jda;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String channelID = args[0];
            String message = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

            try {
                TextChannel discordChannel = jda.getTextChannelById(channelID);
                if (discordChannel != null) {
                    discordChannel.sendMessage(message).queue();
                    main.msg.sendMessage(player, "&aMessage sent on discord: &7" + message);
                } else {
                    main.msg.sendMessage(player, "&cDiscord channel not found.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                main.msg.sendMessage(player, "&cFailed to send message. Check the console for more information.");
            }
            return true;
        }
        return false;
    }
}
