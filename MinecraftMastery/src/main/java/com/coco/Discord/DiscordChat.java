package com.coco.Discord;

import com.coco.MinecraftMastery;
import net.dv8tion.jda.api.entities.MessageType;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.sticker.Sticker;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DiscordChat extends ListenerAdapter {
    private MinecraftMastery main;
    public DiscordChat(MinecraftMastery main){
        this.main = main;
    }
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event){
        if (event.getChannel().equals(main.getJda().getTextChannelById(main.discordConfig.getChannelIDDiscordToMinecraft()))) {
            if (!event.getAuthor().isBot()) {
                List<Role> userRoles = event.getMember().getRoles();
                Role highestRole = userRoles.isEmpty() ? null : userRoles.get(0);
                String msg = event.getMessage().getContentRaw();
                String formatMessage = main.getConfig().getString("Discord.settings.format_message_discord_to_minecraft")
                        .replace("<name>", event.getMember().getEffectiveName());

                if (highestRole != null) {
                    String highestRoleName = highestRole.getName();
                    formatMessage = formatMessage.replace("<role>", highestRoleName).replace("<message>", msg);
                } else {
                    formatMessage = formatMessage.replace("<role>", "Member").replace("<message>", msg);
                }
                if (msg.isEmpty()) return;
                if (event.getMessage().getContentRaw().startsWith("https://") || event.getMessage().getContentRaw().startsWith("http://") || event.getMessage().getContentRaw().endsWith(".com") || event.getMessage().getContentRaw().endsWith(".net") || event.getMessage().getContentRaw().endsWith(".gg")) return;
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', formatMessage));

            }
        }
    }
}
