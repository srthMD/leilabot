package ro.srth.leila.commands.cmds;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.FileUpload;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.*;
import ro.srth.leila.commands.Command;
import ro.srth.leila.util.ShitifyHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Shitify extends Command {
    ShitifyHandler shitifyhandler = new ShitifyHandler();

    public Shitify() {
        this.commandName = "shitify";
        this.description = "Compresses a video/image a lot.";
        this.type = CommandType.SLASH;
        this.args = new ArrayList<OptionData>();
        args.add(new OptionData(OptionType.ATTACHMENT, "file", "video/image to compress", true));
        this.register = true;
    }
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals(this.commandName) && !event.isAcknowledged()) {
            Bot.log.info(event.getInteraction().getUser().getAsTag() + " Fired ShitifyCmd");

            OptionMapping image = event.getOption("image");

            Message.Attachment attachment = image.getAsAttachment();

            File shitifyFile = null;

            event.getInteraction().deferReply().queue();

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
                        event.getHook().sendFiles(FileUpload.fromData(shitifyFile)).submit(true);
                    } else if (attachment.isVideo()) {
                        File videoToCompress = attachment.getProxy().downloadToPath().get().toFile();
                        try {
                            shitifyFile = shitifyhandler.compressVid(videoToCompress);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        event.getHook().sendFiles(FileUpload.fromData(shitifyFile)).submit(true);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            shitifyFile.delete();
        }
    }
}