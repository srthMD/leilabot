package ro.srth.leila.commands.cmds.slash;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.utils.FileUpload;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.commands.SlashCommand;
import ro.srth.leila.main.Bot;
import ro.srth.leila.commands.util.MediaHandler;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class Shitify extends SlashCommand {

    private static final Set<String> AUDIO_FORMATS = new HashSet<String>(Arrays.asList("mp3", "m4a", "wav", "ogg"));

    private static final List<Integer> VALID_MP3_SAMPLE_RATES = new ArrayList<Integer>(Arrays.asList(8000, 11025, 12000, 16000, 22050, 32000, 44100, 48000));

    public Shitify() {
        super();
        this.commandName = "shitify";
        this.description = "compresses media to extreme levels";
        subCmds.add(new SubcommandData("image", "Shitifies an image")
                .addOption(OptionType.ATTACHMENT, "image", "The image you want to compress", true)
                .addOptions(new OptionData(OptionType.INTEGER, "resizebefore", "resolution to scale the image down to (default 40)", false).setRequiredRange(10L, 150L),
                        new OptionData(OptionType.INTEGER, "resizeafter", "resolution to scale the image back up to lower quality (default 400)", false).setRequiredRange(200L, 600L),
                        new OptionData(OptionType.INTEGER, "quality", "number between 1-50 determining quality (default 7)", false).setRequiredRange(1L, 50L)));

        subCmds.add(new SubcommandData("video", "Shitifies a video")
                .addOption(OptionType.ATTACHMENT, "video", "The video you want to compress", true)
                .addOptions(
                        new OptionData(OptionType.INTEGER, "bitrate", "bitrate of the video (default 5000)", false).setRequiredRange(1000L, 30000L),
                        new OptionData(OptionType.INTEGER, "fps", "fps of the video (default 5)", false).setRequiredRange(3L, 25L),
                        new OptionData(OptionType.INTEGER, "audiobitrate", "audio bit rate (default 16000)", false).setRequiredRange(8000L, 48000L),
                        new OptionData(OptionType.INTEGER, "audiosamplingrate", "audio sampling rate (default 16000)", false).setRequiredRange(8000L, 48000L),
                        new OptionData(OptionType.INTEGER, "volume", "sets volume of the video", false).setRequiredRange(1L, 25L),
                        new OptionData(OptionType.STRING, "filter", "extra cool filters", false).addChoices(
                                new Command.Choice("Mirror", "mirror"),
                                new Command.Choice("Color Bake", "colorbake"),
                                new Command.Choice("Audio Distort", "extremedistort")
                        )));

        subCmds.add(new SubcommandData("audio", "Shitifies an audio")
                .addOption(OptionType.ATTACHMENT, "audio", "the audio file to shitify", true)
                .addOptions(
                        new OptionData(OptionType.INTEGER, "audiobitrate", "audio bit rate (default 16000)", false).setRequiredRange(8000L, 48000L),
                        new OptionData(OptionType.INTEGER, "audiosamplingrate", "audio sampling rate (default 16000)", false).setRequiredRange(8000L, 48000L),
                        new OptionData(OptionType.INTEGER, "volume", "sets volume of the video", false).setRequiredRange(1L, 25L),
                        new OptionData(OptionType.NUMBER, "speed", "sets speed of the audio", false).setRequiredRange(0.1, 10.0),
                        new OptionData(OptionType.STRING, "filter", "extra filters to apply", false).addChoices(
                                new Command.Choice("Funny Flanger", "flanger"),
                                new Command.Choice("Funny Equalizer", "funnymic"),
                                new Command.Choice("Extreme High Pass", "highpass"),
                                new Command.Choice("Audio Distort", "extremedistort"))));

        this.register = true;
    }

    @Override
    public void runSlashCommand(@NotNull SlashCommandInteractionEvent event) {
        switch (event.getSubcommandName()){
            case("image"):
                OptionMapping image = event.getOption("image");

                Integer resizebefore = event.getOption("resizebefore", 40, OptionMapping::getAsInt);
                Integer resizeafter = event.getOption("resizeafter", 400, OptionMapping::getAsInt);
                Integer quality = event.getOption("quality", 7, OptionMapping::getAsInt);

                Message.Attachment attachment = image.getAsAttachment();

                File shitifyFile;

                event.deferReply().queue();

                try {
                    if (!attachment.isImage()) {
                        event.getHook().sendMessage("attachment is not an image").setEphemeral(true).queue();
                    } else {
                        File imageToCompress = attachment.getProxy().downloadToPath().get().toFile();
                        try {
                            shitifyFile = MediaHandler.compressImg(imageToCompress, resizebefore, resizeafter, quality);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        CompletableFuture<Message> future = event.getHook().sendFiles(FileUpload.fromData(shitifyFile)).submit(true);

                        future.whenComplete((_unused, _unused1) -> shitifyFile.delete());


                    }
                } catch (Exception e) {
                    Bot.log.error(e.getMessage());
                    event.getHook().sendMessage("something went wrong").queue();
                }

                break;

            case("video"):
                OptionMapping video = event.getOption("video");

                Integer bitrate = event.getOption("bitrate", 5000, OptionMapping::getAsInt);
                Integer fps = event.getOption("fps", 5, OptionMapping::getAsInt);
                Integer audioBitrate = event.getOption("audiobitrate", 16000, OptionMapping::getAsInt);
                Integer audioSamplingRate = event.getOption("audiosamplingrate", 16000, OptionMapping::getAsInt);
                Integer volume = event.getOption("volume", 1, OptionMapping::getAsInt);
                String preset = event.getOption("filter", "", OptionMapping::getAsString);

                Message.Attachment v_attachment = video.getAsAttachment();

                File v_shitifyFile;

                event.getInteraction().deferReply().queue();

                try {
                    if (!v_attachment.isVideo()) {
                        event.getHook().sendMessage("attachment is not a video").setEphemeral(true).queue();
                    } else {
                        File imageToCompress = v_attachment.getProxy().downloadToPath().get().toFile();
                        try {
                            v_shitifyFile = MediaHandler.compressVideo(imageToCompress, bitrate, fps, audioBitrate, audioSamplingRate, volume, preset);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        CompletableFuture<Message> future = event.getHook().sendFiles(FileUpload.fromData(v_shitifyFile)).submit(true);

                        future.whenComplete((_unused, _unused1) -> v_shitifyFile.delete());
                    }
                } catch (Exception e) {
                    Bot.log.error(e.getMessage());
                    event.getHook().sendMessage("something went wrong").queue();
                }

                break;

            case("audio"):
                OptionMapping audio = event.getOption("audio");

                Integer a_BitRate = event.getOption("audiobitrate", 16000, OptionMapping::getAsInt);
                Integer a_SamplingRate = event.getOption("audiosamplingrate", 16000, OptionMapping::getAsInt);
                Integer a_volume = event.getOption("volume", 2, OptionMapping::getAsInt);
                Double a_speed = event.getOption("speed", 1.0, OptionMapping::getAsDouble);
                String a_preset = event.getOption("filter", "", OptionMapping::getAsString);

                Message.Attachment a_attachment = audio.getAsAttachment();

                File a_shitifyFile;

                event.getInteraction().deferReply().queue();

                try {
                    if (!isAudio(a_attachment)) {
                        event.getHook().sendMessage("invalid audio file").setEphemeral(true).queue();
                    } else {
                        if(a_attachment.getFileExtension().equals("mp3")){
                            a_SamplingRate = roundMp3SamplingRate(a_SamplingRate);
                        }

                        File audioToCompress = a_attachment.getProxy().downloadToPath().get().toFile();
                        try {
                            a_shitifyFile = MediaHandler.compressAudio(audioToCompress ,a_BitRate, a_SamplingRate, a_volume, a_speed, a_preset);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        CompletableFuture<Message> future = event.getHook().sendFiles(FileUpload.fromData(a_shitifyFile)).submit(true);

                        future.whenComplete((_unused, _unused1) -> a_shitifyFile.delete());
                    }
                } catch (Exception e) {
                    Bot.log.error(e.getMessage());
                    event.getHook().sendMessage("something went wrong").queue();
                }

                break;


            default:
                event.reply("something went wrong").setEphemeral(true).queue();
        }
    }

    public static boolean isAudio(Message.Attachment a){
        return a.getFileExtension() != null && AUDIO_FORMATS.contains(a.getFileExtension().toLowerCase());
    }


    private static int roundMp3SamplingRate(int currentRate){
        if(VALID_MP3_SAMPLE_RATES.contains(currentRate)) return currentRate;


        Comparator<Integer> c = Comparator
                .comparing((Integer value) -> Math.abs(value - currentRate))
                .thenComparing(Comparator.naturalOrder());

        return VALID_MP3_SAMPLE_RATES.stream().min(c).orElseThrow();
    }
}
