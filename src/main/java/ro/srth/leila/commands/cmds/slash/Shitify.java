package ro.srth.leila.commands.cmds.slash;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.utils.FileUpload;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.commands.Command;
import ro.srth.leila.util.ShitifyHandler;

import java.io.File;
import java.io.IOException;

public class Shitify extends Command {

    ShitifyHandler shitifyhandler = new ShitifyHandler();
    public Shitify() {
        super();
        this.commandName = "shitify";
        this.description = "compresses media to extreme levels";
        this.type = CommandType.SLASH;
        //i just copy paste this took me like only one minute
        subCmds.add(new SubcommandData("image", "Shitifies an image").addOption(OptionType.ATTACHMENT, "image", "The image you want to compress", true).addOptions(new OptionData(OptionType.INTEGER, "resizebefore", "resolution to scale the image down to (default 40)", false).setRequiredRange(10L, 150L), new OptionData(OptionType.INTEGER, "resizeafter", "resolution to scale the image back up to lower quality (default 400)", false).setRequiredRange(200L, 600L), new OptionData(OptionType.INTEGER, "quality", "number between 1-50 determining quality (default 7)", false).setRequiredRange(1L, 50L)));
        subCmds.add(new SubcommandData("video", "Shitifies a video").addOption(OptionType.ATTACHMENT, "video", "The video you want to compress", true).addOptions(new OptionData(OptionType.INTEGER, "bitrate", "bitrate of the video (default 5000)", false).setRequiredRange(1000L, 30000L), new OptionData(OptionType.INTEGER, "fps", "fps of the video (default 5)", false).setRequiredRange(3L, 25L), new OptionData(OptionType.INTEGER, "width", "width dimension of the video (default 700)", false).setRequiredRange(200, 1200L), new OptionData(OptionType.INTEGER, "height", "height dimension of the video (default 250)", false).setRequiredRange(200, 1200L), new OptionData(OptionType.INTEGER, "audiobitrate", "audio bit rate (default 16000)", false).setRequiredRange(15000L, 25000L), new OptionData(OptionType.INTEGER, "audiosamplingrate", "audio sampling rate (default 16000)", false).setRequiredRange(15000L, 25000L)));
        this.register = true;
    }
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals(this.commandName) && !event.isAcknowledged()) {
            switch (event.getSubcommandName()){
                case("image"):
                    OptionMapping image = event.getOption("image");

                    Integer resizebefore = event.getOption("resizebefore", 40, OptionMapping::getAsInt);
                    Integer resizeafter = event.getOption("resizeafter", 400, OptionMapping::getAsInt);
                    Integer quality = event.getOption("quality", 7, OptionMapping::getAsInt);

                    Message.Attachment attachment = image.getAsAttachment();

                    File shitifyFile;

                    event.getInteraction().deferReply().queue();

                    try {
                        if (!attachment.isImage()) {
                            event.reply("attachment is not an image").setEphemeral(true).queue();
                        } else {
                            File imageToCompress = attachment.getProxy().downloadToPath().get().toFile();
                            try {
                                shitifyFile = shitifyhandler.compressImg(imageToCompress, resizebefore, resizeafter, quality);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            event.getHook().sendFiles(FileUpload.fromData(shitifyFile)).submit(true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        event.getHook().sendMessage("something went wrong").queue();
                    }

                    break;

                case("video"):
                    OptionMapping video = event.getOption("video");

                    Integer bitrate = event.getOption("bitrate", 5000, OptionMapping::getAsInt);
                    Integer fps = event.getOption("fps", 5, OptionMapping::getAsInt);
                    Integer width = event.getOption("width", 700, OptionMapping::getAsInt);
                    Integer height = event.getOption("height", 250, OptionMapping::getAsInt);
                    Integer audiobitrate = event.getOption("audiobitrate", 16000, OptionMapping::getAsInt);
                    Integer audiosamplingrate = event.getOption("audiosamplingrate", 16000, OptionMapping::getAsInt);


                    Message.Attachment v_attachment = video.getAsAttachment();

                    File v_shitifyFile;

                    event.getInteraction().deferReply().queue();

                    try {
                        if (!v_attachment.isVideo()) {
                            event.reply("attachment is not a video").setEphemeral(true).queue();
                        } else {
                            File imageToCompress = v_attachment.getProxy().downloadToPath().get().toFile();
                            try {
                                v_shitifyFile = shitifyhandler.compressVid(imageToCompress, bitrate, fps, width, height, audiobitrate, audiosamplingrate);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            event.getHook().sendFiles(FileUpload.fromData(v_shitifyFile)).submit(true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        event.getHook().sendMessage("something went wrong").queue();
                    }

                    break;

                default:
                    event.reply("something went wrong").setEphemeral(true).queue();
            }
        }
    }
}
