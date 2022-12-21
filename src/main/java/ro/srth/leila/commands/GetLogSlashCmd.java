package ro.srth.leila.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.utils.FileUpload;
import ro.srth.leila.Bot;

import java.nio.file.Paths;

public class GetLogSlashCmd extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equals("getlog")){
            Bot.log.info("GetLogSlashCmd fired by" + event.getInteraction().getUser().getAsTag());
            FileUpload upload = FileUpload.fromData(Paths.get(Bot.fhp));

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
