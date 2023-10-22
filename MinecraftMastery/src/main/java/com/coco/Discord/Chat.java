package com.coco.Discord;

import com.coco.MinecraftMastery;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.checkerframework.checker.units.qual.C;

import java.awt.*;
import java.util.Random;

public class Chat implements Listener {
    private MinecraftMastery main;
    private Random random = new Random();
    public Chat(MinecraftMastery main){
        this.main = main;
    }
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player player = e.getPlayer();
        float r = random.nextFloat();
        float g = random.nextFloat();
        float b = random.nextFloat();
        Color randomColor = new Color(r, g, b);
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(randomColor);
        embed.setAuthor(player.getName(), null, "https://crafatar.com/avatars/" + player.getUniqueId());
        embed.setDescription(e.getMessage());

        TextChannel channel = main.getJda().getTextChannelById(main.discordConfig.getChannelIDMinecraftToDiscord());
        channel.sendMessageEmbeds(embed.build()).queue();

    }
}
