package com.gmail.diviegg.Versions.Wrappers.Modern.HorseHandler;

import com.gmail.diviegg.Handlers.ItemStackHandler;
import com.gmail.diviegg.PortableHorses;
import com.gmail.diviegg.Versions.Wrappers.AbstractHorseHandler;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public abstract class AbstractModernHorseHandler extends AbstractHorseHandler {
    private final Plugin ph = PortableHorses.getPlugin(PortableHorses.class);

    private final NamespacedKey hType = new NamespacedKey(ph, "hType");

    private final NamespacedKey hName = new NamespacedKey(ph, "hName");

    private final NamespacedKey hOwnerID = new NamespacedKey(ph, "hOwner");

    private final NamespacedKey hArmor = new NamespacedKey(ph, "hArmor");

    private final NamespacedKey hAppearance = new NamespacedKey(ph, "hAppearance");

    private final NamespacedKey hColor = new NamespacedKey(ph, "hColor");

    private final NamespacedKey hMaxHealth = new NamespacedKey(ph, "hMaxHealth");

    private final NamespacedKey hCurrentHealth = new NamespacedKey(ph, "hCurrentHealth");

    private final NamespacedKey hJumpStrength = new NamespacedKey(ph, "hJumpStrength");

    private final NamespacedKey hSpeed = new NamespacedKey(ph, "hSpeed");

    public ItemStack packageHorse(AbstractHorse horse) {
        ItemStack saddlePackage = new ItemStack(Material.SADDLE);
        ItemMeta saddleMeta = saddlePackage.getItemMeta();

        PersistentDataContainer cont = saddleMeta.getPersistentDataContainer();
        if (horse.getCustomName() != null) {
            cont.set(hName, PersistentDataType.STRING, horse.getCustomName());
        }
        if (horse.getOwner() != null)
            cont.set(hOwnerID, PersistentDataType.STRING, horse.getOwner().getUniqueId().toString());
        if (horse instanceof Horse) {
            HorseInventory hInv = ((Horse) horse).getInventory();
            if (hInv.getArmor() != null) {
                cont.set(hArmor, PersistentDataType.STRING, ItemStackHandler.ItemStackToString(hInv.getArmor()));

            }
            cont.set(hAppearance, PersistentDataType.STRING, ((Horse) horse).getStyle().toString());
            cont.set(hColor, PersistentDataType.STRING, ((Horse) horse).getColor().toString());
            cont.set(hType, PersistentDataType.STRING, "Normal");
        } else {
            cont.set(hType, PersistentDataType.STRING, "Skeleton");
        }
        cont.set(hMaxHealth, PersistentDataType.DOUBLE, horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
        cont.set(hCurrentHealth, PersistentDataType.DOUBLE, horse.getHealth());
        cont.set(hJumpStrength, PersistentDataType.DOUBLE, horse.getJumpStrength());

        cont.set(hSpeed, PersistentDataType.DOUBLE, horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue());


        saddlePackage.setItemMeta(saddleMeta);
        saddlePackage = setSaddleNBT(horse, saddlePackage);
        horse.remove();
        if (!horse.isValid())
            return saddlePackage;
        else return null;
    }


    public boolean unpackageHorse(ItemStack saddle, World world, Location loc, Player player) {
        if (saddle.getType() == Material.SADDLE) {
            ItemMeta saddleMeta = saddle.getItemMeta();
            PersistentDataContainer cont = null;
            if (saddleMeta != null) {
                cont = saddleMeta.getPersistentDataContainer();
            }
            ItemStack freshSaddle = new ItemStack(Material.SADDLE);
            if (cont != null && cont.has(hType, PersistentDataType.STRING)) {
                String type = cont.get(hType, PersistentDataType.STRING);
                if (type != null) {
                    Class vehicle;
                    AbstractHorse packaged;
                    vehicle = type.equals("Normal") ? Horse.class : SkeletonHorse.class;

                    PersistentDataContainer finalCont = cont;
                    packaged = (AbstractHorse) world.spawn(loc, vehicle, horse -> {
                        AbstractHorse builder = (AbstractHorse) horse;
                        (builder).addPotionEffect(PotionEffectType.INVISIBILITY.createEffect(999, 1));
                        if (finalCont.has(hName, PersistentDataType.STRING))
                            horse.setCustomName(finalCont.get(hName, PersistentDataType.STRING));
                        if (finalCont.has(hOwnerID, PersistentDataType.STRING)) {
                            builder.setOwner(Bukkit.getPlayer(UUID.fromString(finalCont.get(hOwnerID, PersistentDataType.STRING))));
                        }
                        if (finalCont.has(this.hArmor, PersistentDataType.STRING)) {
                            try {
                                ItemStack armor = ItemStackHandler.ItemStackFromString(finalCont.get(this.hArmor, PersistentDataType.STRING));
                                ((Horse) builder).getInventory().setArmor(armor);
                            } catch (Exception e) {
                                ((Horse) horse).getInventory().setArmor(new ItemStack(Material.valueOf(finalCont.get(this.hArmor, PersistentDataType.STRING))));
                            }

                        }
                        if (finalCont.has(this.hColor, PersistentDataType.STRING)) {
                            ((Horse) builder).setColor(Horse.Color.valueOf(finalCont.get(this.hColor, PersistentDataType.STRING)));
                        }
                        if (finalCont.has(this.hAppearance, PersistentDataType.STRING)) {
                            ((Horse) builder).setStyle(Horse.Style.valueOf(finalCont.get(this.hAppearance, PersistentDataType.STRING)));
                        }
                        getStatsFromContainer(builder, finalCont);
                        builder.setTamed(true);
                        builder.getInventory().setSaddle(freshSaddle);
                    });
                    if (isColliding(packaged))
                        return false;
                    if (ph.getConfig().getBoolean("AutoMount"))
                        packaged.addPassenger(player);
                    packaged.removePotionEffect(PotionEffectType.INVISIBILITY);
                    return true;
                }
            }
            return false;
        }

        return false;
    }

    public boolean containsHorse(ItemStack saddle) {
        if (saddle != null) {
            if (saddle.getItemMeta() != null) {
                return saddle.getItemMeta().getPersistentDataContainer().has(hType, PersistentDataType.STRING);
            }
        }
        return false;
    }

    @Override
    public boolean checkSteerable(Entity e) {
        return false;
    }

    @Override
    public boolean hasSaddle(Entity e) {
        return false;
    }

    public void getStatsFromContainer(AbstractHorse h, PersistentDataContainer cont) {
        Double jumpStrength = cont.has(hJumpStrength, PersistentDataType.DOUBLE) ? cont.get(hJumpStrength, PersistentDataType.DOUBLE) : 0.0;
        Double maxHealth = cont.has(hMaxHealth, PersistentDataType.DOUBLE) ? cont.get(hMaxHealth, PersistentDataType.DOUBLE) : 0.0;
        Double currentHealth = cont.has(hCurrentHealth, PersistentDataType.DOUBLE) ? cont.get(hCurrentHealth, PersistentDataType.DOUBLE) : 0.0;
        Double speed = cont.has(hSpeed, PersistentDataType.DOUBLE) ? cont.get(hSpeed, PersistentDataType.DOUBLE) : 0.0;
        setHorseStats(h, jumpStrength, maxHealth, currentHealth, speed);
    }


}
