package ro.srth.leila.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Template extends Command {

    public Template() {
        this.commandName = "foo";
        this.description = "bar";
        this.type = CommandType.SLASH;
        this.args = new ArrayList<OptionData>();
        this.register = false;
    }
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals(this.commandName) && !event.isAcknowledged()) {

        }
    }
}
