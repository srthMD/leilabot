package ro.srth.leila.commands.cmds;

import ch.qos.logback.classic.LoggerContext;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.FileUpload;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;
import ro.srth.leila.*;
import ro.srth.leila.commands.Command;

import java.nio.file.Paths;
import java.util.ArrayList;

//i was right
//@NeedsRevamp(reason = "logger rewrite would probably break this")

public class GetLogSlashCmd extends Command {

    public GetLogSlashCmd() {
        this.commandName = "getlog";
        this.description = "Sends the current log of the bot.";
        this.type = Command.CommandType.SLASH;
        this.args = new ArrayList<OptionData>();
        args.add(new OptionData(OptionType.BOOLEAN, "isephemeral", "Makes the reply visible to others (false = visible to others).", true));
        this.register = true;
    }
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals(this.commandName) && !event.isAcknowledged()) {
            Bot.log.info(event.getInteraction().getUser().getAsTag() + " Fired GetLogSlashCmd");

            LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

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

