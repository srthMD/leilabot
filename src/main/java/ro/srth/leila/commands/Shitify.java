package ro.srth.leila.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.utils.FileUpload;
import ro.srth.leila.Bot;
import ro.srth.leila.api.ShitifyHandler;

import java.io.File;
import java.io.IOException;

public class Shitify extends ListenerAdapter {
    ShitifyHandler shitifyhandler = new ShitifyHandler();
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equals("shitify")){
            Bot.log.info(event.getInteraction().getUser().getAsTag() + " Fired ShitifyCmd");

            OptionMapping image = event.getOption("image");

            try{
                if(!image.getAsAttachment().isImage()){
                    event.reply("attachment is not an image").setEphemeral(true).queue();
                } else if (image.getAsAttachment().isImage()){
                    if(image.getAsAttachment().getFileExtension().equals(".webp")){
                        event.reply("SORRY NO QUIERES WEBP PICTURE").setEphemeral(true).queue();
                    }
                    File imageToCompress = image.getAsAttachment().downloadToFile().join();
                    File shitifyFile;

                    try {
                        shitifyFile = shitifyhandler.compress(imageToCompress);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    if(shitifyFile.getTotalSpace() == 0){
                        event.reply("something broke while compressing file just try the command again").setEphemeral(true).queue();
                    } else{
                        event.replyFiles(FileUpload.fromData(shitifyFile)).queue();
                    }
                }
            } catch (Exception ignored){
            }
        }
    }
}
