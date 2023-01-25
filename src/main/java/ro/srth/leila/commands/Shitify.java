package ro.srth.leila.commands;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.utils.FileUpload;
import ro.srth.leila.Bot;
import ro.srth.leila.api.ShitifyHandler;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Shitify extends ListenerAdapter {
    ShitifyHandler shitifyhandler = new ShitifyHandler();
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equals("shitify")){
            Bot.log.info(event.getInteraction().getUser().getAsTag() + " Fired ShitifyCmd");

            OptionMapping image = event.getOption("image");

            Message.Attachment attachment = image.getAsAttachment();

            try{
                if(!attachment.isImage() && !attachment.isVideo()){
                    event.reply("attachment is not an image or video").setEphemeral(true).queue();
                } else if (attachment.isImage() || attachment.isVideo()) {
                    File shitifyFile = null;
                    if (attachment.isImage()) {
                        File imageToCompress = attachment.downloadToFile().join();
                        try {
                            shitifyFile = shitifyhandler.compressImg(imageToCompress);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (attachment.isVideo()) {
                        File videoToCompress = attachment.downloadToFile().join();
                        try {
                            shitifyFile = shitifyhandler.compressVid(videoToCompress);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    event.replyFiles(FileUpload.fromData(shitifyFile)).queue();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
