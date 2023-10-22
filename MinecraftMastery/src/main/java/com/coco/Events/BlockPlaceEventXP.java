package com.coco.Events;

import com.coco.MinecraftMastery;
import com.coco.Util.PlayerData;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.HashMap;
import java.util.Map;

public class BlockPlaceEventXP implements Listener {
    MinecraftMastery main;
    private Map<Player, PlayerData> playerDataMap = new HashMap();
    private int blocksToPlaceLevelUp;
    private int blocksPerLevelIncreasePlace;
    public BlockPlaceEventXP(MinecraftMastery main){
        this.main = main;
        this.blocksToPlaceLevelUp = main.getConfig().getInt("leveling.blocksToPlaceLevelUp", 10);
        this.blocksPerLevelIncreasePlace = main.getConfig().getInt("leveling.blocksPerLevelIncreasePlace", 10);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (main.getConfig().getBoolean("Events_With_XP.BlockPlace")) {
            Player player = e.getPlayer();
            PlayerData playerData = new PlayerData(main);

            int blocksPlaced = playerData.getBlocksPlaced(player);
            int playerLevel = playerData.getPlayerLevel(player);

            Sound sound = Sound.ENTITY_PLAYER_LEVELUP;

            if (blocksPlaced < playerLevel * blocksPerLevelIncreasePlace) {
                playerData.setBlocksPlacedForPlayer(player, blocksPlaced + 1);
            }

            if (blocksPlaced == playerLevel * blocksPerLevelIncreasePlace - 1) {
                player.giveExp(1);
                main.playSoundToPlayer.playSound(player, sound);
                playerData.setBlocksPlacedForPlayer(player, 0);
                playerData.setPlayerLevel(player, playerLevel + 1);
            }
        }
    }

}
