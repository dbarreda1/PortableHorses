package com.gmail.diviegg.Versions.Wrappers.Legacy.HorseHandler;

import com.gmail.diviegg.PortableHorses;
import com.gmail.diviegg.Versions.Wrappers.AbstractGeneralUtility;
import com.gmail.diviegg.Versions.Wrappers.AbstractHorseHandler;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.entity.SkeletonHorse;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class AbstractLegacyHorseHandler extends AbstractHorseHandler {
    @Override
    public ItemStack packageHorse(AbstractHorse horse) {
        ItemStack saddlePackage = new ItemStack(Material.SADDLE);
        NBTItem saddleNbt = new NBTItem(saddlePackage);

        if (horse.getCustomName() != null) {
            saddleNbt.setString("hName", horse.getCustomName());
        }
        if (horse.getOwner() != null)
            saddleNbt.setString("hOwner", horse.getOwner().getUniqueId().toString());
        if (horse instanceof Horse) {
            if (((Horse) horse).getInventory().getArmor() != null) {
                String armor = ((Horse) horse).getInventory().getArmor().getType().toString();
                saddleNbt.setString("hArmor", armor);
            }
            saddleNbt.setString("hAppearance", ((Horse) horse).getStyle().toString());
            saddleNbt.setString("hColor", ((Horse) horse).getColor().toString());
            saddleNbt.setString("hType", "Normal");
        } else {
            saddleNbt.setString("hType", "Skeleton");
        }
        saddleNbt.setDouble("hMaxHealth", horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
        saddleNbt.setDouble("hCurrentHealth", horse.getHealth());
        saddleNbt.setDouble("hJumpStrength", horse.getJumpStrength());
        saddleNbt.setDouble("hSpeed", horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue());
        saddlePackage = saddleNbt.getItem();
        saddlePackage = setSaddleNBT(horse, saddlePackage);

        horse.remove();
        if (!horse.isValid())
            return saddlePackage;
        else return null;
    }

    @Override
    public boolean unpackageHorse(ItemStack saddle, World world, Location loc, Player player) {
        if (saddle.getType() == Material.SADDLE) {
            NBTItem saddleNbt = new NBTItem(saddle);
            ItemStack freshSaddle = new ItemStack(Material.SADDLE);
            if (saddleNbt.hasNBTData() && saddleNbt.hasKey("hType")) {
                String type = saddleNbt.getString("hType");
                if (type != null) {
                    Class vehicle;
                    AbstractHorse packaged;
                    vehicle = type.equals("Normal") ? Horse.class : SkeletonHorse.class;
                    packaged = (AbstractHorse) world.spawn(loc, vehicle, horse -> {
                        AbstractHorse builder = (AbstractHorse) horse;
                        ((AbstractHorse) horse).setAdult();
                        (builder).addPotionEffect(PotionEffectType.INVISIBILITY.createEffect(999, 1));
                        if (saddleNbt.hasKey("hName"))
                            horse.setCustomName(saddleNbt.getString("hName"));
                        if (saddleNbt.hasKey("hOwner")) {
                            builder.setOwner(Bukkit.getPlayer(UUID.fromString(saddleNbt.getString("hOwner"))));
                        }
                        if (saddleNbt.hasKey("hArmor")) {
                            ((Horse) builder).getInventory().setArmor(new ItemStack(Material.valueOf(saddleNbt.getString("hArmor"))));
                        }
                        if (saddleNbt.hasKey("hColor")) {
                            ((Horse) builder).setColor(Horse.Color.valueOf(saddleNbt.getString("hColor")));
                        }
                        if (saddleNbt.hasKey("hAppearance")) {
                            ((Horse) builder).setStyle(Horse.Style.valueOf(saddleNbt.getString("hAppearance")));
                        }
                        getStatsFromContainer(builder, saddleNbt);
                        builder.setTamed(true);
                        builder.getInventory().setSaddle(freshSaddle);
                    });
                    if (isColliding(packaged))
                        return false;
                    if (PortableHorses.getPh().getConfig().getBoolean("AutoMount"))
                        packaged.addPassenger(player);
                    packaged.removePotionEffect(PotionEffectType.INVISIBILITY);
                    return true;
                }
            }
            return false;
        }

        return false;
    }

    public ItemStack setSaddleNBT(AbstractHorse h, ItemStack saddle) {

        FileConfiguration pm = PortableHorses.getPh().getConfig();
        AbstractGeneralUtility gu = PortableHorses.getVersionHandler().getGeneralUtil();

        ChatColor text = gu.isValidColor(pm.getString("TextColor")) ? fetchColor(pm.getString("TextColor")) : ChatColor.DARK_AQUA;
        ChatColor stats = gu.isValidColor(pm.getString("StatColor")) ? fetchColor(pm.getString("StatColor")) : ChatColor.GREEN;
        String jumpStrengthText = "Jump Height";
        String speedText = "Speed";
        String colorText = "Color";
        String styleText = "Style";
        String blockText = "Blocks";
        if (pm.getBoolean("OverrideLore")) {
            jumpStrengthText = pm.getString("loreText.jumpHeight");
            speedText = pm.getString("loreText.speed");
            colorText = pm.getString("loreText.color");
            styleText = pm.getString("loreText.style");
            blockText = pm.getString("loreText.blocks");
        }

        List<String> l = new ArrayList<>();
        ItemMeta im = saddle.getItemMeta();
        im.addEnchant(Enchantment.LURE, 0, true);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        double health = h.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() - h.getHealth();
        int allHearts = ((int) h.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) / 2;
        int emptyHearts = (allHearts) - ((int) h.getHealth() / 2);

        l.add(ChatColor.RESET + "" + ChatColor.RED + String.format("%s%s%s", new String(new char[allHearts - emptyHearts]).replace("\0", "❤"),
                (health % 2 == 0) ? "" : "♥", new String(new char[emptyHearts]).replace("\0", "♡")));
        if (h.getCustomName() != null) {
            im.setDisplayName(ChatColor.BLUE + h.getCustomName());
        }
        l.add(ChatColor.RESET + "" + text + String.format("%s: " + stats + "%.2f %s", jumpStrengthText, (Math.round((-0.1817584952D * Math.pow(h.getJumpStrength(), 3.0D) + 3.689713992D *
                Math.pow(h.getJumpStrength(), 2.0D) + 2.128599134D * h.getJumpStrength() - 0.343930367D) * 100.0D) / 100.0D), blockText));
        l.add(ChatColor.RESET + "" + text + String.format("%s: " + stats + "%.2f", speedText, (Math.round(h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)
                .getValue() * 100.0D) / 10.0D)));
        if (!(h instanceof SkeletonHorse) && pm.getBoolean("ShowType")) {
            l.add(ChatColor.RESET + "" + text + colorText + ": " + stats + pm.getString("loreText.HorseColors." + ((Horse) h).getColor()));
            l.add(ChatColor.RESET + "" + text + styleText + ": " + stats + pm.getString("loreText.HorseStyles." + ((Horse) h).getStyle()));
        }

        im.setLore(l);
        saddle.setItemMeta(im);
        return saddle;
    }

    private ChatColor fetchColor(String statColor) {
        for (ChatColor c : ChatColor.values()) {
            if (c.name().equalsIgnoreCase(statColor.toUpperCase()))
                return c;
        }


        return null;
    }

    @Override
    public boolean containsHorse(ItemStack saddle) {
        if (saddle != null && saddle.getType() == Material.SADDLE)
            return new NBTItem(saddle).hasKey("hType");
        return false;
    }

    private void getStatsFromContainer(AbstractHorse h, NBTItem cont) {
        Double jumpStrength = cont.hasKey("hJumpStrength") ? cont.getDouble("hJumpStrength") : 0.0;
        Double maxHealth = cont.hasKey("hMaxHealth") ? cont.getDouble("hMaxHealth") : 0.0;
        Double currentHealth = cont.hasKey("hCurrentHealth") ? cont.getDouble("hCurrentHealth") : 0.0;
        Double speed = cont.hasKey("hSpeed") ? cont.getDouble("hSpeed") : 0.0;
        setHorseStats(h, jumpStrength, maxHealth, currentHealth, speed);
    }
}
