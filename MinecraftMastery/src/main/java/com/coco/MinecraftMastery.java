package com.coco;

import com.coco.Commands.GetBlocksPlaced;
import com.coco.Commands.GetPlayerUUID;
import com.coco.Commands.OpenManagementGUI;
import com.coco.Commands.ReloadCommand;
import com.coco.Discord.*;
import com.coco.Events.BlockBreakEventXP;
import com.coco.Events.BlockPlaceEventXP;
import com.coco.Events.PlayerJoin;
import com.coco.Util.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.configuration.file.FileConfiguration;
import org.mineacademy.fo.plugin.SimplePlugin;

public final class MinecraftMastery extends SimplePlugin {
    public PlaySound playSoundToPlayer;
    public PlayerMessages msg;
    public PlayerData playerData;
    public FileConfiguration pluginConfig;
    private GetPlayerUUID getPlayerUUID;
    private GetBlocksPlaced getBlocksPlacedCmd;
    public MiniMessages messages;
    public static JDA jda;
    private OpenManagementGUI managementGUI;
    private BroadCastMessage discordMessage;
    private Chat chat;
    private DiscordChat discordChat;
    private ReloadCommand reloadCommand;
    private PlayerJoin playerJoin;
    public LinkCommand linkCommand;
    private ResetCodes reseter;
    public DiscordConfig discordConfig;
    private UnlinkCommand unlinkCommand;
    private ForceLinkCommand forceLinkCommand;
    @Override
    public void onPluginStart() {
        saveDefaultConfig();
        pluginConfig = getConfig();
        playSoundToPlayer = new PlaySound();
        msg = new PlayerMessages();
        playerData = new PlayerData(this);
        getPlayerUUID = new GetPlayerUUID(this);
        getBlocksPlacedCmd = new GetBlocksPlaced(this);
        messages = new MiniMessages(this);
        reloadCommand = new ReloadCommand(this);
        playerJoin = new PlayerJoin(this);
        managementGUI = new OpenManagementGUI(this);
        linkCommand = new LinkCommand(this);
        reseter = new ResetCodes(this);
        discordConfig = new DiscordConfig(this);
        unlinkCommand = new UnlinkCommand(this);
        forceLinkCommand = new ForceLinkCommand(this);
        getLogger().info("Enabling Discord bot...");
        discordConfig.loadDiscordConfig();
        if (discordConfig.isEnabled()) {
            switch (discordConfig.getStatus()) {
                case "ONLINE":
                    try {
                        getLogger().info("Getting Discord token...");
                        String botToken = discordConfig.getToken();
                        jda = JDABuilder.createDefault(botToken).setStatus(OnlineStatus.ONLINE).setActivity(Activity.playing(discordConfig.getActivity())).enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT).addEventListeners(new DiscordChat(this), new DirectMessagesListener(this)).build().awaitReady();
                    } catch (Exception e) {
                        e.printStackTrace();
                        getLogger().info("Failed to initialize the Discord bot: " + e.getMessage());
                        setEnabled(false);
                    }
                    break;
                case "IDLE":
                    try {
                        getLogger().info("Getting Discord token...");
                        String botToken = discordConfig.getToken();
                        getLogger().info(botToken);
                        jda = JDABuilder.createDefault(botToken).setStatus(OnlineStatus.IDLE).setActivity(Activity.playing(discordConfig.getActivity())).enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT).addEventListeners(new DiscordChat(this), new DirectMessagesListener(this)).build().awaitReady();

                    } catch (Exception e) {
                        e.printStackTrace();
                        getLogger().info("Failed to initialize the Discord bot: " + e.getMessage());
                        setEnabled(false);
                    }
                    break;
                case "DND":
                    try {
                        getLogger().info("Getting Discord token...");
                        String botToken = discordConfig.getToken();
                        getLogger().info(botToken);
                        jda = JDABuilder.createDefault(botToken).setStatus(OnlineStatus.DO_NOT_DISTURB).setActivity(Activity.playing(discordConfig.getActivity())).enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT).addEventListeners(new DiscordChat(this), new DirectMessagesListener(this)).build().awaitReady();

                    } catch (Exception e) {
                        e.printStackTrace();
                        getLogger().info("Failed to initialize the Discord bot: " + e.getMessage());
                        setEnabled(false);
                    }
                    break;
                case "OFFLINE":
                    try {
                        getLogger().info("Getting Discord token...");
                        String botToken = discordConfig.getToken();
                        getLogger().info("Token: " + botToken);
                        jda = JDABuilder.createDefault(botToken).setStatus(OnlineStatus.OFFLINE).setActivity(Activity.playing(discordConfig.getActivity())).enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT).addEventListeners(new DiscordChat(this), new DirectMessagesListener(this)).build().awaitReady();

                    } catch (Exception e) {
                        e.printStackTrace();
                        getLogger().info("Failed to initialize the Discord bot: " + e.getMessage());
                        setEnabled(false);
                    }
                    break;
            }

            discordMessage = new BroadCastMessage(this, jda);
        }
        chat = new Chat(this);
        discordChat = new DiscordChat(this);


        getServer().getPluginManager().registerEvents(new BlockBreakEventXP(this), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceEventXP(this), this);
        getServer().getPluginManager().registerEvents(chat, this);
        getServer().getPluginManager().registerEvents(playerJoin, this);

        getCommand("getplayeruuid").setExecutor(getPlayerUUID);
        getCommand("blocksplaced").setExecutor(getBlocksPlacedCmd);
        getCommand("broadcastdiscord").setExecutor(discordMessage);
        getCommand("minecraftmasteryreload").setExecutor(reloadCommand);
        getCommand("management").setExecutor(managementGUI);
        getCommand("link").setExecutor(linkCommand);
        getCommand("unlink").setExecutor(unlinkCommand);
        getCommand("forcelink").setExecutor(forceLinkCommand);

    }

    @Override
    public void onPluginStop() {
        if (jda != null && jda.getStatus() == JDA.Status.CONNECTED){
            jda.shutdown();
            getLogger().info("Discord bot is now disconnected.");
        }
        reseter.start();
    }
    public static JDA getJda(){
        return jda;
    }
}
