package ro.srth.leila.commands;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
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
        if (command.equals("shitify")) {
            Bot.log.info(event.getInteraction().getUser().getAsTag() + " Fired ShitifyCmd");

            OptionMapping image = event.getOption("image");

            Message.Attachment attachment = image.getAsAttachment();

            MessageChannelUnion channel = event.getInteraction().getChannel();

            File shitifyFile = null;

            event.reply("bot will send file in the channel when compression is done").setEphemeral(true).queue();

            try {
                if (!attachment.isImage() && !attachment.isVideo()) {
                    event.reply("attachment is not an image or video").setEphemeral(true).queue();
                } else if (attachment.isImage() || attachment.isVideo()) {
                    shitifyFile = null;
                    if (attachment.isImage()) {
                        File imageToCompress = attachment.getProxy().downloadToPath().get().toFile();
                        try {
                            shitifyFile = shitifyhandler.compressImg(imageToCompress);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        channel.sendFiles(FileUpload.fromData(shitifyFile)).submit(true);
                    } else if (attachment.isVideo()) {
                        File videoToCompress = attachment.getProxy().downloadToPath().get().toFile();
                        try {
                            shitifyFile = shitifyhandler.compressVid(videoToCompress);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        channel.sendFiles(FileUpload.fromData(shitifyFile)).submit(true);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            shitifyFile.deleteOnExit();
        }
    }
}
