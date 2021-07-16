package com.gmail.diviegg.Commands;

import com.gmail.diviegg.PortableHorses;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class TabComplete implements TabCompleter {
    CommandBase commandBase = PortableHorses.getCommandBase();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        Set<String> commandNames = commandBase.getCommands().keySet();
        final List<String> completions = new ArrayList<>();
        StringUtil.copyPartialMatches(args[0], commandNames, completions);
        Collections.sort(completions);
        return completions;
    }

}
