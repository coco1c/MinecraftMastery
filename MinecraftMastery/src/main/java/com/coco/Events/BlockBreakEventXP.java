package com.coco.Events;

import com.coco.MinecraftMastery;
import com.coco.Util.PlayerData;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;
import java.util.Map;

public class BlockBreakEventXP implements Listener {
    private MinecraftMastery main;
    private Map<Player, PlayerData> playerDataMap = new HashMap();
    private int blocksToLevelUp;
    private int blocksPerLevelIncrease;

    public BlockBreakEventXP(MinecraftMastery main) {
        this.main = main;
        this.blocksToLevelUp = main.getConfig().getInt("leveling.blocksToBreakLevelUp", 10);
        this.blocksPerLevelIncrease = main.getConfig().getInt("leveling.blocksPerLevelIncreaseBreak", 10);
    }

    @EventHandler
    public void BlockBreakEvent(BlockBreakEvent e) {
        if (main.getConfig().getBoolean("Events_With_XP.BlockBreak")) {
            Player player = e.getPlayer();
            PlayerData playerData = new PlayerData(main);

            int blocksBroke = playerData.getBlocksBrokeForPlayer(player);
            int playerLevel = playerData.getPlayerLevel(player);

            Sound sound = Sound.ENTITY_PLAYER_LEVELUP;

            if (blocksBroke < playerLevel * blocksPerLevelIncrease) {
                playerData.setBlocksBrokeForPlayer(player, blocksBroke + 1);
            }

            if (blocksBroke == playerLevel * blocksPerLevelIncrease - 1) {
                player.giveExp(1);
                main.playSoundToPlayer.playSound(player, sound);
                playerData.setBlocksBrokeForPlayer(player, 0);
                playerData.setPlayerLevel(player, playerLevel + 1);
            }
        }
    }

}
