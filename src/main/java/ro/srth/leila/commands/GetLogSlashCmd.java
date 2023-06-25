package ro.srth.leila.commands;

import ch.qos.logback.classic.LoggerContext;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.utils.FileUpload;
import org.slf4j.LoggerFactory;
import ro.srth.leila.Bot;
import ro.srth.leila.annotations.NeedsRevamp;

import java.nio.file.Paths;

@NeedsRevamp(reason = "logger rewrite would probably break this")
public class GetLogSlashCmd extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equals("getlog")){
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
