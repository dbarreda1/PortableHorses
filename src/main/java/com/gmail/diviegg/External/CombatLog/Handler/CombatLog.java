package com.gmail.diviegg.External.CombatLog.Handler;

import com.gmail.diviegg.PortableHorses;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class CombatLog {
    private static final int combatTime = PortableHorses.getPh().getConfig().getInt("CombatCooldown") ;
    HashMap<UUID, Long> inCombat = new HashMap<>();
    HashMap<UUID, Long> warnings = new HashMap<>();

    public void setInCombat(Player player) {
        inCombat.put(player.getUniqueId(), new Date().getTime());
    }

    public void setLastWarning(Player player) {
        warnings.put(player.getUniqueId(), new Date().getTime());
    }

    public boolean isInCombat(Player player) {
        if (inCombat.containsKey(player.getUniqueId())) {
            boolean result = new Date().getTime() - inCombat.get(player.getUniqueId()) < combatTime * 1000L;
            if (!result)
                inCombat.remove(player.getUniqueId());
            return result;
        } else
            return false;
    }

    public int timeLeftInCombat(Player player) {
        if (isInCombat(player)) {
            return combatTime - (int) (Math.round((new Date().getTime() - inCombat.get(player.getUniqueId())) / 1000d));
        }
        return 0;
    }

    public boolean canWarn(Player player) {
        if (warnings.containsKey(player.getUniqueId())) {
            return (int) (Math.round((new Date().getTime() - warnings.get(player.getUniqueId())) / 1000d)) > 1;
        }
        return true;
    }
}
