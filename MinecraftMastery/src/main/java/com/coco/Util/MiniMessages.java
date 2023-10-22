package com.coco.Util;

import com.coco.MinecraftMastery;

public class MiniMessages {
    MinecraftMastery main;
    public MiniMessages(MinecraftMastery main){
        this.main = main;
    }
    public String noPermission(){
        return main.getConfig().getString("Messages.NoPermission");
    }
    public String playerNotFound(){
        return main.getConfig().getString("Messages.PlayerNotFound");
    }
}
