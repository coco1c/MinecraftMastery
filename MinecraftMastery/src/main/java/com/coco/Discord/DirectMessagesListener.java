package com.coco.Discord;

import com.coco.MinecraftMastery;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

public class DirectMessagesListener extends ListenerAdapter {
    private MinecraftMastery main;
    private FileConfiguration config;

    public DirectMessagesListener(MinecraftMastery main) {
        this.main = main;
        config = main.getConfig();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User user = event.getAuthor();
        JDA jda = main.getJda();

        if (jda == null) {
            return;
        }

        if (event.isFromType(ChannelType.PRIVATE) && !event.getAuthor().isBot()) {
            user.openPrivateChannel().queue(privateChannel -> {
                privateChannel.sendMessage("Received your code: " + event.getMessage().getContentRaw()).queue();
                privateChannel.sendMessage("Checking the code...").queue();
                String id = event.getAuthor().getId();
                if (main.discordConfig.isPlayerLinked(id)){
                    Player playerUUID = Bukkit.getPlayer(main.discordConfig.getMinecraftUUID(id));
                    if (!(playerUUID != null)) {
                        String name = playerUUID.getName();
                        privateChannel.sendMessage("You are already linked to " + name + " (" + playerUUID.getUniqueId() + ")").queue();
                        return;
                    } else {
                        privateChannel.sendMessage("You are already linked to (" + main.discordConfig.getMinecraftUUID(id) + ")").queue();
                        return;
                    }
                }
                try {
                    String messageContent = event.getMessage().getContentRaw();
                    int code = Integer.parseInt(messageContent);
                } catch (NumberFormatException e){
                    privateChannel.sendMessage("Sorry, the code is not a valid number :confused:").queue();
                    return;
                }

                if (main.linkCommand.codes.contains(event.getMessage().getContentRaw())) {
                    String code = event.getMessage().getContentRaw();
                    String playerUUID = config.getString("codes." + code);
                    main.getLogger().info(playerUUID);
                    UUID uuidd = UUID.fromString(playerUUID);
                    //String playerUUID = config.getString("codes." + code);
                    main.getLogger().info(String.valueOf(uuidd));
                    Player player = Bukkit.getPlayer(uuidd);
                    String playerID = event.getAuthor().getId();

                    if (player != null) {
                        main.msg.sendMessage(player, config.getString("Messages.Linked_Done")
                                .replace("{discord_name}", event.getAuthor().getName()));
                        if (main.discordConfig.linkPlayer(uuidd, playerID)) {
                            privateChannel.sendMessage("Code found, you are now linked to " + uuidd).queue();
                        }
                    } else {
                        privateChannel.sendMessage("Sorry! There was a problem finding the player with the UUID from that code :confused: ");
                    }
                } else {
                    privateChannel.sendMessage("Sorry! I don't know such a code :confused: ").queue();
                }
            });
        }
    }
}
