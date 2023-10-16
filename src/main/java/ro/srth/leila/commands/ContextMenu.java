package ro.srth.leila.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class ContextMenu extends Command{

    public String commandName;
    public boolean register;

    public final List<Permission> permissions;

    public ContextMenu(){
        permissions = new ArrayList<>();
    }

    public abstract void runContextMenu(@NotNull MessageContextInteractionEvent event);

}
