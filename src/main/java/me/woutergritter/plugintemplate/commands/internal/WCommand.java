package me.woutergritter.plugintemplate.commands.internal;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Field;

public abstract class WCommand extends Command {
    protected final String command;

    public WCommand(String command) {
        super(command);

        this.command = command;
    }

    public abstract void execute(CommandContext ctx);

    @Override
    public final boolean execute(CommandSender sender, String label, String[] args) {
        CommandContext ctx = new CommandContext(this, sender, args);

        try{
            this.execute(ctx);
        }catch(CommandInterrupt interrupt) {
            if(interrupt.isAbsolutePath()) {
                ctx.sendAbsolute(interrupt.getPath(), interrupt.getArgs());
            }else{
                ctx.send(interrupt.getPath(), interrupt.getArgs());
            }
        }

        return true;
    }

    public void register() {
        CommandMap commandMap = getCommandMap();
        if(commandMap != null) {
            commandMap.register(command, this);
        }
    }

    public String getCommand() {
        return command;
    }

    private static CommandMap getCommandMap() {
        try{
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);

            return (CommandMap) commandMapField.get(Bukkit.getServer());
        }catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
