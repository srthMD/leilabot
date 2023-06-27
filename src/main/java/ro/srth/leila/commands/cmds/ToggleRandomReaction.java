package ro.srth.leila.commands.cmds;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.*;
import ro.srth.leila.commands.Command;

import java.util.ArrayList;

public class ToggleRandomReaction extends Command {

    public static boolean toggled = true;

    public ToggleRandomReaction() {
        this.commandName = "togglerandomreaction";
        this.description = "Toggles random messages";
        this.type = CommandType.SLASH;
        this.args = new ArrayList<OptionData>();
        this.register = true;
    }
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals(this.commandName) && !event.isAcknowledged()) {
            Bot.log.info(event.getInteraction().getUser().getAsTag() + " Fired ToggleRandomReaction");
            if(!toggled){
                toggled = true;

                event.reply("setting random reaction toggle to " + toggled).queue();
            } else if(toggled){
                toggled = false;

                event.reply("setting random reaction toggle to " + toggled).queue();
            }
        }
    }
    public static boolean getToggledStatus(){
        return toggled;
    }
}



