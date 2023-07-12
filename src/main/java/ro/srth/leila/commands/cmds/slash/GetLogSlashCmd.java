package ro.srth.leila.commands.cmds.slash;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.FileUpload;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.Bot;
import ro.srth.leila.commands.Command;

import java.nio.file.Paths;

//i was right
//@NeedsRevamp(reason = "logger rewrite would probably break this")

public class GetLogSlashCmd extends Command {

    public GetLogSlashCmd() {
        super();
        this.commandName = "getlog";
        this.description = "Sends the current log of the bot.";
        this.type = Command.CommandType.SLASH;
        args.add(new OptionData(OptionType.BOOLEAN, "isephemeral", "Makes the reply visible to others (false = visible to others).", true));
        this.register = true;
    }
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals(this.commandName) && !event.isAcknowledged()) {
            Bot.log.info(event.getInteraction().getUser().getName() + " Fired GetLogSlashCmd");

            FileUpload upload = FileUpload.fromData(Paths.get("C:\\Users\\SRTH_\\AppData\\Local\\Temp\\bot.log"));

            OptionMapping isEphemeral = event.getOption("isephemeral");

            if (isEphemeral.getAsBoolean()){
                event.replyFiles(upload).setEphemeral(true).queue();
                Bot.log.info("sending log as ephemeral message");
            }
            else{
                event.replyFiles(upload).queue();
                Bot.log.info("sending log as public message");
            }
        }
    }
}

