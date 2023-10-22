package com.coco.Util;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class PlaySound {

    public void playSound(Player player, Sound sound) {

        player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
    }
}
