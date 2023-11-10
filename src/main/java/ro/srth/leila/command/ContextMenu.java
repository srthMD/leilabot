package ro.srth.leila.command;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import org.jetbrains.annotations.NotNull;

public abstract class ContextMenu extends Command{

    public static String formalName;

    public ContextMenu(Guild guild){
        super(guild);
    }

    public abstract void runContextMenu(@NotNull MessageContextInteractionEvent event);
}
