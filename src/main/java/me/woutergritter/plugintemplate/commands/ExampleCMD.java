package me.woutergritter.plugintemplate.commands;

import me.woutergritter.plugintemplate.commands.internal.CommandContext;
import me.woutergritter.plugintemplate.commands.internal.WCommand;

public class ExampleCMD extends WCommand {
    public ExampleCMD() {
        super("example");
    }

    @Override
    public void execute(CommandContext ctx) {
        ctx.send("output");
    }
}
