package com.gmail.diviegg.Listeners;

import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace implements Listener {
    @EventHandler
    public void phOnBlockPlace(BlockPlaceEvent e) {
        for (Entity en : e.getPlayer().getWorld().getNearbyEntities(e.getBlock().getLocation(), 2, 2, 2)) {
            if (en instanceof AbstractHorse && en.getTicksLived() < 10)
                e.setCancelled(true);
        }
    }
}
