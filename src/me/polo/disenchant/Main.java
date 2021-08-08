package me.polo.disenchant;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public void onEnable(){
        getServer().getPluginCommand("disenchant").setExecutor(new DisenchantCmd());
    }
    public void onDisable(){}

}
