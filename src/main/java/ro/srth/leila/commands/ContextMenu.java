package ro.srth.leila.commands;

import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import org.jetbrains.annotations.NotNull;

public abstract class ContextMenu extends Command{

    public String commandName;
    public boolean register;


    public ContextMenu(){
        super();
    }

    public abstract void runContextMenu(@NotNull MessageContextInteractionEvent event);

}
