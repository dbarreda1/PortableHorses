package com.gmail.diviegg.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class SubCommand {
    public abstract void onCommand(CommandSender sender, Command command, String[] args);

    abstract public String getPermission();
}
