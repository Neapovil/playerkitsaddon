package com.github.neapovil.playerkitsaddon;

import org.bukkit.block.data.type.Sign;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.kyori.adventure.text.TextComponent;

public final class PlayerKitsAddon extends JavaPlugin implements Listener
{
    private static PlayerKitsAddon instance;

    @Override
    public void onEnable()
    {
        instance = this;

        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable()
    {
    }

    public static PlayerKitsAddon getInstance()
    {
        return instance;
    }

    @EventHandler
    public void playerInteract(PlayerInteractEvent event)
    {
        if (event.getClickedBlock() == null)
        {
            return;
        }

        if (!(event.getClickedBlock().getBlockData() instanceof Sign || event.getClickedBlock().getBlockData() instanceof WallSign))
        {
            return;
        }

        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
        {
            return;
        }

        final org.bukkit.block.Sign sign = (org.bukkit.block.Sign) event.getClickedBlock().getState();

        if (!((TextComponent) sign.line(0)).content().startsWith("kit"))
        {
            return;
        }

        final String kitname = ((TextComponent) sign.line(1)).content();

        if (kitname.isBlank())
        {
            return;
        }

        event.getPlayer().getInventory().clear();

        this.getServer().dispatchCommand(this.getServer().getConsoleSender(), "kit give " + kitname + " " + event.getPlayer().getName());
    }
}
