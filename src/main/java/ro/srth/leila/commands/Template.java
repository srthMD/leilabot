//easy to just copy and paste

package ro.srth.leila.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jetbrains.annotations.NotNull;

public class Template extends Command {

    public Template() {
        super();
        this.commandName = "foo";
        this.description = "bar";
        this.type = CommandType.SLASH;
        this.register = false;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals(this.commandName) && !event.isAcknowledged()) {

        }
    }
}
