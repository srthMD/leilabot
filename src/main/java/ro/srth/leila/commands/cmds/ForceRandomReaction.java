package ro.srth.leila.commands.cmds;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.*;
import ro.srth.leila.commands.Command;

import java.util.ArrayList;

public class ForceRandomReaction extends Command {

    public ForceRandomReaction() {
        this.commandName = "forcerandomreaction";
        this.description = "Forces random reactions";
        this.type = Command.CommandType.SLASH;
        this.args = new ArrayList<OptionData>();
        this.register = true;
    }

    public static boolean forced;
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals(this.commandName) && !event.isAcknowledged()) {
            if (event.getInteraction().getUser().getId().equals("780805916743565312") || event.getInteraction().getUser().getId().equals("584834083943874581")){
                Bot.log.info(event.getInteraction().getUser().getAsTag() + " Fired ForceRandomReaction");
                if(!forced){
                    forced = true;
                    event.reply("setting force reaction toggle to " + forced).setEphemeral(true).queue();
                } else if(forced){
                    forced = false;
                    event.reply("setting force reaction toggle to " + forced).setEphemeral(true).queue();
                }
            } else{
                event.reply("you dont have permission for this command").setEphemeral(true).queue();
            }
        }
    }
}
