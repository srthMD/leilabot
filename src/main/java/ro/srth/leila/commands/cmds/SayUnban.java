package ro.srth.leila.commands.cmds;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.commands.Command;
import ro.srth.leila.util.SayBan;
import ro.srth.leila.Bot;

import java.io.IOException;
import java.util.ArrayList;

public class SayUnban extends Command{

    SayBan handler = new SayBan();
    public SayUnban() {
        this.commandName = "sayunban";
        this.description = "Unbans a user from /say if they are already banned";
        this.type = Command.CommandType.SLASH;
        this.args = new ArrayList<OptionData>();
        args.add(new OptionData(OptionType.USER, "user", "The user to unban", true));
        this.register = true;
    }
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals(this.commandName) && !event.isAcknowledged()) {
            User user = event.getOption("user").getAsUser();
            try {
                if(handler.isBanned(user.getIdLong())){
                   handler.unbanId(user.getIdLong());

                   Bot.log.info(user.getName() + " was successfully unbanned from /say");
                   event.reply("attempting to unban").setEphemeral(true).queue();

                }else{
                    event.reply("user is not banned").setEphemeral(true).queue();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
