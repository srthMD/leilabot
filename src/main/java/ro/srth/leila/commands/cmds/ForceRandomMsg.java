package ro.srth.leila.commands.cmds;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.commands.Command;
import ro.srth.leila.*;

import java.util.ArrayList;

public class ForceRandomMsg extends Command {

    public ForceRandomMsg() {
        this.commandName = "forcerandommsg";
        this.description = "Forces the random message event to be fired every message";
        this.type = Command.CommandType.SLASH;
        this.args = new ArrayList<OptionData>();
        this.register = true;
    }

    public static boolean forced;
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals(this.commandName) && !event.isAcknowledged()) {
            if(event.getUser().getIdLong() == (584834083943874581L | 780805916743565312L)){
                Bot.log.info(event.getInteraction().getUser().getAsTag() + " Fired ForceRandomMessage");
                if(!forced){
                    forced = true;
                    event.reply("setting force message toggle to %s".formatted(forced)).setEphemeral(true).queue();
                } else if(forced){
                    forced = false;
                    event.reply("setting force message toggle to %s".formatted(forced)).setEphemeral(true).queue();
                }
            }else{
                event.reply("you dont have permission for this command").setEphemeral(true).queue();
            }
        }
    }
}
