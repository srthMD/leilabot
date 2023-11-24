package ro.srth.leila.command;

import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import org.jetbrains.annotations.NotNull;

public abstract class LBContextMenu extends LBCommand {
    public static String formalName;

    public abstract void runContextMenu(@NotNull MessageContextInteractionEvent event);
}
