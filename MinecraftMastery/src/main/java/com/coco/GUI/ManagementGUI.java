package com.coco.GUI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.MenuPagged;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.button.ButtonMenu;
import org.mineacademy.fo.menu.button.StartPosition;
import org.mineacademy.fo.menu.button.annotation.Position;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;
import org.mineacademy.fo.menu.model.SkullCreator;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ManagementGUI extends Menu {

    private final Button openPlayerManagerGUI;
    public ManagementGUI(){
        setSlotNumbersVisible();
        setTitle("&7Management");
        setSize(9 * 3);
        this.openPlayerManagerGUI = new ButtonMenu(new PlayerManagerGUI(), CompMaterial.PLAYER_HEAD,
                "&6&lOpen Player Manager GUI",
                "",
                "Click to open");
    }

    @Override
    public ItemStack getItemAt(int slot) {
        if (slot == 11)
            return openPlayerManagerGUI.getItem();
        return null;
    }
    private class PlayerManagerGUI extends Menu {
        private final Button openPlayerEditGUI;

        public PlayerManagerGUI() {
            super(ManagementGUI.this);
            setTitle("&7Player Manager");
            setSize(9 * 3);

            openPlayerEditGUI = new ButtonMenu(new PlayerEdit(), CompMaterial.PLAYER_HEAD,
                    "&6&lOpen Player Edit GUI",
                    "",
                    "Click to open");
        }

        @Override
        public ItemStack getItemAt(int slot) {
            if (slot == 11)
                return openPlayerEditGUI.getItem();
            return null;
        }
    }

    private class PlayerEdit extends MenuPagged<Player>{
        public PlayerEdit(){
            super(ManagementGUI.this, Bukkit.getOnlinePlayers().stream().filter(player -> player.getType() == EntityType.PLAYER).collect(Collectors.toList()));
            setTitle("&7Player Manager");
            setSize(9 * 3);
        }

        @Override
        protected ItemStack convertToItemStack(Player player) {
            ItemStack playerHead = SkullCreator.itemFromUuid(player.getUniqueId());
            ItemMeta itemMeta = playerHead.getItemMeta();
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6&l" + player.getName()));
            playerHead.setItemMeta(itemMeta);
            return playerHead;
        }



        @Override
        protected void onPageClick(Player player, Player player2, ClickType clickType) {


        }
    }
}
