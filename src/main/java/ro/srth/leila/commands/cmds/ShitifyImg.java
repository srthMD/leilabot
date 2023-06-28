package ro.srth.leila.commands.cmds;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.FileUpload;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.Bot;
import ro.srth.leila.commands.Command;
import ro.srth.leila.util.ShitifyHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ShitifyImg extends Command {
    ShitifyHandler shitifyhandler = new ShitifyHandler();

    public ShitifyImg() {
        this.commandName = "shitifyimg";
        this.description = "Compresses an image a lot.";
        this.type = CommandType.SLASH;
        this.args = new ArrayList<OptionData>();
        args.add(new OptionData(OptionType.ATTACHMENT, "image", "image to compress", true));
        args.add(new OptionData(OptionType.INTEGER, "resizebefore", "resolution to scale the image down to (default 40)", false).setRequiredRange(10L, 150L));
        args.add(new OptionData(OptionType.INTEGER, "resizeafter", "resolution to scale the image back up to lower quality (default 400)", false).setRequiredRange(200L, 600L));
        args.add(new OptionData(OptionType.INTEGER, "quality", "number between 1-50 determining quality (default 7)", false).setRequiredRange(1L, 50L));
        this.register = true;
    }
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals(this.commandName) && !event.isAcknowledged()) {
            Bot.log.info(event.getInteraction().getUser().getAsTag() + " Fired shitifyImg");

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
        }
    }
}