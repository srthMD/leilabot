package ro.srth.leila.commands.cmds;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.*;
import ro.srth.leila.commands.Command;

import java.util.ArrayList;

public class ToggleTextReactions extends Command {
    public static boolean ReactionsToggled = true;
    public ToggleTextReactions() {
        this.commandName = "toggletextreactions";
        this.description = "Bot wont react to keywords like tutorial, listeners like random game still work.";
        this.type = CommandType.SLASH;
        this.args = new ArrayList<OptionData>();
        this.register = true;
    }
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals(this.commandName) && !event.isAcknowledged()) {
            Bot.log.info(event.getInteraction().getUser().getName() + " Fired ToggleTextReactions");
            if(!ReactionsToggled){
                ReactionsToggled = true;
                event.getInteraction().reply("Setting reaction toggle to " + ReactionsToggled).queue();
            } else {
                ReactionsToggled = false;
                event.getInteraction().reply("Setting reaction toggle to " + ReactionsToggled).queue();
            }
        }
    }
    public static boolean getToggledStatus(){
        return ReactionsToggled;
    }
}

