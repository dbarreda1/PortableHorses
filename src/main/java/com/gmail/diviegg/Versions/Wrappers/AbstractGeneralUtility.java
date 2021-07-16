package com.gmail.diviegg.Versions.Wrappers;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractGeneralUtility implements IGeneralUtiility {
    private final List<String> validColors = new ArrayList<>(Arrays.asList(
            "dark_red",
            "red",
            "gold",
            "yellow",
            "dark_green",
            "green",
            "aqua",
            "dark_aqua",
            "dark_blue",
            "blue",
            "light_purple",
            "dark_purple",
            "white",
            "gray",
            "dark_gray",
            "black"
    ));

    @Override
    public abstract boolean isInteractable(Material material);

    @Override
    public boolean isValidColor(String string) {
        if (string != null)
            return validColors.contains(string.toLowerCase());
        return false;
    }

}
