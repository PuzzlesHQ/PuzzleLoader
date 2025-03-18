package com.github.puzzle.game.common;

import com.github.puzzle.game.commands.ClientCommandManager;
import com.github.puzzle.game.commands.ClientCommandSource;
import com.github.puzzle.game.commands.CommandManager;

public class Commands {

    public static void register() {
        ClientCommandManager.DISPATCHER.register(CommandManager.createHelp(ClientCommandSource.class, "?", '?', ClientCommandManager.DISPATCHER));
        ClientCommandManager.DISPATCHER.register(CommandManager.createHelp(ClientCommandSource.class, "help", '?', ClientCommandManager.DISPATCHER));
    }

}
