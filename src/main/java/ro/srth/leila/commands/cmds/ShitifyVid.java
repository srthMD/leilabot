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

public class ShitifyVid extends Command {
    ShitifyHandler shitifyhandler = new ShitifyHandler();

    public ShitifyVid() {
        this.commandName = "shitifyvid";
        this.description = "Compresses a video a lot.";
        this.type = CommandType.SLASH;
        this.args = new ArrayList<OptionData>();
        args.add(new OptionData(OptionType.ATTACHMENT, "video", "video to compress", true));
        args.add(new OptionData(OptionType.INTEGER, "bitrate", "bitrate of the video (default 2400)", false).setRequiredRange(1000L, 10000L));
        args.add(new OptionData(OptionType.INTEGER, "fps", "fps of the video (default 5)", false).setRequiredRange(3L, 25L));
        args.add(new OptionData(OptionType.INTEGER, "width", "width dimension of the video (default 700)", false).setRequiredRange(200, 1200L));
        args.add(new OptionData(OptionType.INTEGER, "height", "height dimension of the video (default 250)", false).setRequiredRange(200, 1200L));
        args.add(new OptionData(OptionType.INTEGER, "audiobitrate", "audio bit rate (default 16000)", false).setRequiredRange(16000, 25000L));
        args.add(new OptionData(OptionType.INTEGER, "audiosamplingrate", "audio sampling rate (default 16000)", false).setRequiredRange(16000, 25000L));
        this.register = true;
    }
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals(this.commandName) && !event.isAcknowledged()) {
            Bot.log.info(event.getInteraction().getUser().getName() + " Fired shitifyImg");

            OptionMapping image = event.getOption("video");

            Integer bitrate = event.getOption("bitrate", 2400, OptionMapping::getAsInt);
            Integer fps = event.getOption("fps", 5, OptionMapping::getAsInt);
            Integer width = event.getOption("width", 700, OptionMapping::getAsInt);
            Integer height = event.getOption("height", 250, OptionMapping::getAsInt);
            Integer audiobitrate = event.getOption("audiobitrate", 16000, OptionMapping::getAsInt);
            Integer audiosamplingrate = event.getOption("audiosamplingrate", 16000, OptionMapping::getAsInt);


            Message.Attachment attachment = image.getAsAttachment();

            File shitifyFile;

            event.getInteraction().deferReply().queue();

            try {
                if (!attachment.isVideo()) {
                    event.reply("attachment is not a video").setEphemeral(true).queue();
                } else {
                    File imageToCompress = attachment.getProxy().downloadToPath().get().toFile();
                    try {
                        shitifyFile = shitifyhandler.compressVid(imageToCompress, bitrate, fps, width, height, audiobitrate, audiosamplingrate);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    event.getHook().sendFiles(FileUpload.fromData(shitifyFile)).submit(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
                event.getHook().sendMessage("something went wrong").queue();
            }
        }
    }
}