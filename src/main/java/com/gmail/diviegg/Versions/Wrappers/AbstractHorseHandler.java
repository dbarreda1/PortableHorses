package com.gmail.diviegg.Versions.Wrappers;

import com.gmail.diviegg.PortableHorses;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NBTList;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractHorseHandler {

    public abstract ItemStack packageHorse(AbstractHorse horse);

    public abstract boolean unpackageHorse(ItemStack saddle, World world, Location loc, Player player);

    public abstract boolean containsHorse(ItemStack saddle);


    public boolean isColliding(AbstractHorse horse) {
        Location horseLoc = horse.getEyeLocation();
        for (int i = -1; i < 1; i++) {
            for (int j = -1; j < 1; j++) {
                Block checked = horse.getWorld().getBlockAt(horseLoc.getBlockX() + i, horseLoc.getBlockY(),
                        horseLoc.getBlockZ() + j);
                if (checked.getType().isOccluding()) {
                    horse.remove();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkSteerable(Entity entity) {
        return entity instanceof Animals && entity instanceof Vehicle;
    }

    public boolean hasSaddle(Entity e) {
        if (e instanceof AbstractHorse) {
            return ((AbstractHorse) e).getInventory().getSaddle() != null;
        }
        if (e instanceof Pig) {
            return ((Pig) e).hasSaddle();
        } else {
            return false;
        }
    }

    public ItemStack setSaddleNBT(AbstractHorse h, ItemStack saddle) {
        FileConfiguration pm = PortableHorses.getPh().getConfig();
        AbstractGeneralUtility gu = PortableHorses.getVersionHandler().getGeneralUtil();
        NBTItem saddleNBT = new NBTItem(saddle);

        NBTCompound disp = saddleNBT.addCompound("display");

        NBTList<String> l = disp.getStringList("Lore");
        String text = gu.isValidColor(pm.getString("TextColor")) ? pm.getString("TextColor").toLowerCase() : "blue";
        String stats = gu.isValidColor(pm.getString("StatColor")) ? pm.getString("StatColor").toLowerCase() : "dark_green";

        double health = h.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() - h.getHealth();
        int allHearts = ((int) h.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) / 2;
        int emptyHearts = (allHearts) - ((int) h.getHealth() / 2);

        l.add(String.format("{\"text\":\"%s%s%s\",\"italic\":\"false\",\"color\":\"red\"}", new String(new char[allHearts - emptyHearts]).replace("\0", "❤"),
                (health % 2 == 0) ? "" : "♥", new String(new char[emptyHearts]).replace("\0", "♡")));
        if (h.getCustomName() != null) {
            l.add(String.format("{\"translate\":\"mco.configure.world.invite.profile.name\",\"italic\":\"false\",\"color\":\"%s\"}", text));
            l.add(String.format("{\"text\":\"   %s\",\"italic\":\"false\",\"color\":\"%s\"}", h.getCustomName(), stats));
        }
        l.add(String.format("{\"translate\":\"key.jump\",\"italic\":\"false\",\"color\":\"%s\"}", text));

        l.add(String.format("{\"text\":\"   %.2f\",\"italic\":\"false\",\"color\":\"%s\"}", (Math.round((-0.1817584952D * Math.pow(h.getJumpStrength(), 3.0D) + 3.689713992D *
                Math.pow(h.getJumpStrength(), 2.0D) + 2.128599134D * h.getJumpStrength() - 0.343930367D) * 100.0D) / 100.0D), stats));
        l.add(String.format("{\"translate\":\"effect.minecraft.speed\",\"italic\":\"false\",\"color\":\"%s\"}", text));
        l.add(String.format("{\"text\":\"   %.2f\",\"italic\":\"false\",\"color\":\"%s\"}", (Math.round(h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)
                .getValue() * 100.0D) / 10.0D), stats));

        disp.setString("Name", String.format("{\"color\":\"%s\",\"translate\":\"%s\",\"italic\":\"false\"}", "blue", "item.minecraft.saddle"));

        saddle = saddleNBT.getItem();
        return saddle;
    }

    public void setHorseStats(AbstractHorse h, Double jumpStrength, Double maxHealth, Double currentHealth, Double speed) {
        h.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
        h.setHealth(currentHealth);
        h.setJumpStrength(jumpStrength);
        h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(speed);
    }


}
