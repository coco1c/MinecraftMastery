package com.coco.Discord;

import com.coco.MinecraftMastery;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class DiscordConfig {
    private final JavaPlugin plugin;
    private File discordFile;
    private FileConfiguration discordConfig;
    private String pathID = "Linked_Players_ID.";
    private String pathUUID = "Linked_Players.";

    public DiscordConfig(JavaPlugin plugin) {
        this.plugin = plugin;
        loadDiscordConfig();
    }

    public void loadDiscordConfig() {
        discordFile = new File(plugin.getDataFolder(), "discord.yml");

        if (!discordFile.exists()) {
            plugin.saveResource("discord.yml", false);
        }

        discordConfig = YamlConfiguration.loadConfiguration(discordFile);
    }

    public FileConfiguration getDiscordConfig() {
        return discordConfig;
    }

    public void saveDiscordConfig() {
        try {
            discordConfig.save(discordFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isEnabled() {
        return discordConfig.getBoolean("Discord.settings.enabled");
    }

    public void setEnabled(boolean enabled) {
        discordConfig.set("Discord.settings.enabled", enabled);
        saveDiscordConfig();
    }

    public String getToken() {
        return discordConfig.getString("Discord.settings.bot_token");
    }

    public void setToken(String token) {
        discordConfig.set("Discord.settings.bot_token", token);
        saveDiscordConfig();
    }
    public String getStatus(){
        return discordConfig.getString("Discord.settings.status");
    }
    public String getActivity(){
        return discordConfig.getString("Discord.settings.activity");
    }
    public boolean isPlayerLinked(UUID uuid){
        return discordConfig.contains("Linked_Players." + uuid);
    }
    public boolean isPlayerLinked(String id){
        return discordConfig.contains("Linked_Players_ID." + id);
    }
    public UUID getMinecraftUUID(String discordID) {
        if (isPlayerLinked(discordID)) {
            String uuidStr = discordConfig.getString("Linked_Players_ID." + discordID);
            return UUID.fromString(uuidStr);
        }
        return null;
    }
    public String getDiscordID(UUID uuid){
        if (isPlayerLinked(uuid)){
            return discordConfig.getString("Linked_Players." + uuid);
        }
        return null;
    }
    public boolean linkPlayer(UUID uuid, String id){
        if (isPlayerLinked(uuid)) return false;
        discordConfig.set(pathUUID + uuid, id);
        String uuidstr = uuid.toString();
        discordConfig.set(pathID + id, uuidstr);
        saveDiscordConfig();
        return true;
    }
    public boolean unLinkPlayer(UUID uuid){
        if (!isPlayerLinked(uuid)) return false;
        String uuidstr = uuid.toString();
        String id = discordConfig.getString(pathUUID + uuidstr);
        discordConfig.set(pathUUID + uuidstr, null);
        discordConfig.set(pathID + id, null);
        saveDiscordConfig();
        return true;
    }
    public String getChannelIDMinecraftToDiscord(){
        return discordConfig.getString("Discord.channels.channel_ID_minecraft_to_discord_on_chat");
    }
    public String getChannelIDDiscordToMinecraft(){
        return discordConfig.getString("Discord.channels.channel_ID_discord_to_minecraft_on_chat");
    }
    public void syncDiscordToMinecraftRoles(){
        discordConfig.set("Discord.roles.sync_discord_to_minecraft", false);
        saveDiscordConfig();
    }
    public void syncMinecraftToDiscordRoles(){
        discordConfig.set("Discord.roles.sync_minecraft_to_discord", true);
        saveDiscordConfig();
    }
    public List<String> getDiscordRoles() {
        return discordConfig.getStringList("Discord.roles.discord_roles");
    }
    public List<String> getMinecraftRoles() {
        return discordConfig.getStringList("Discord.roles.minecraft_roles");
    }
    public void addDiscordRole(String role) {
        List<String> roles = getDiscordRoles();
        roles.add(role);
        discordConfig.set("Discord.roles.discord_roles", roles);
        saveDiscordConfig();
    }
    public void addMinecraftRole(String role) {
        List<String> roles = getMinecraftRoles();
        roles.add(role);
        discordConfig.set("Discord.roles.minecraft_roles", roles);
        saveDiscordConfig();
    }
    public void removeDiscordRole(String role) {
        List<String> roles = getDiscordRoles();
        roles.remove(role);
        discordConfig.set("Discord.roles.discord_roles", roles);
        saveDiscordConfig();
    }
    public void removeMinecraftRole(String role) {
        List<String> roles = getMinecraftRoles();
        roles.remove(role);
        discordConfig.set("Discord.roles.minecraft_roles", roles);
        saveDiscordConfig();
    }
    public boolean isSyncDiscordToMinecraftRoles() {
        return discordConfig.getBoolean("Discord.roles.sync_discord_to_minecraft");
    }
    public boolean isSyncMinecraftToDiscordRoles() {
        return discordConfig.getBoolean("Discord.roles.sync_minecraft_to_discord");
    }

}
