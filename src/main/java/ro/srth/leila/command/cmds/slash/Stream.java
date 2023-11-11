package ro.srth.leila.command.cmds.slash;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.command.SlashCommand;
import ro.srth.leila.command.util.stream.GuildMusicManager;
import ro.srth.leila.main.Bot;

import java.awt.*;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class Stream extends SlashCommand {

    Set<String> VALIDFORMATS = Set.of("mp4","mp3","ogg","wav");

    Bot inst;

    static{
        description = "Streams audio from a supported link or file";
        subCmds.addAll(Set.of(
                new SubcommandData("link", "Stream audio from a youtube, soundcloud or twitch stream link")
                        .addOption(OptionType.STRING, "link", "The link you want to stream from", true),
                new SubcommandData("file", "Stream audio from a supported file format")
                        .addOption(OptionType.ATTACHMENT, "file", "the supported file type to stream from", true),
                new SubcommandData("skip", "Skips the current track"),
                new SubcommandData("pause", "Stops the stream"),
                new SubcommandData("resume", "resumes the current stream"),
                new SubcommandData("abort", "Disconnects the bot and clears the queue"),
                new SubcommandData("queue", "displays the current queue"),
                new SubcommandData("current", "Displays the current track if there is one playing")
        ));

        permissions.add(Permission.VOICE_USE_SOUNDBOARD);
    }

    public Stream(Guild guild) {
        super(guild);
        inst = Bot.instance();
    }

    @Override
    public void runSlashCommand(@NotNull SlashCommandInteractionEvent event) {

        GuildVoiceState userVcState = Objects.requireNonNull(event.getMember()).getVoiceState();

        if(!(userVcState != null && userVcState.inAudioChannel())){
            event.reply("you must be in a voice channel to use the stream command").setEphemeral(true).queue();
            return;
        }


        System.out.println(event.getSubcommandName());
        switch (event.getSubcommandName()){
            case("link"): {
                connect(guild.getAudioManager(), userVcState.getChannel().asVoiceChannel());
                String url = event.getOption("link", OptionMapping::getAsString);
                loadAndPlay(event, url);
                break;
            }
            case("file"):{
                connect(guild.getAudioManager(), userVcState.getChannel().asVoiceChannel());
                Message.Attachment attachment = event.getOption("file", OptionMapping::getAsAttachment);

                if(VALIDFORMATS.contains(attachment.getFileExtension())){
                    loadAndPlayFile(event, attachment);
                }else{
                    event.reply("format is not valid").queue();
                }
                break;
            }
            case("skip"): {
                if(!event.getGuild().getAudioManager().isConnected()){
                    event.reply("Bot is not in a voice channel").queue();
                    return;
                }

                if(canUse(userVcState, guild.getAudioManager())){
                    skipTrack(event);
                } else{
                    event.reply("you can only use this command when you are in the same vc as the bot").setEphemeral(true).queue();
                }
                break;
            }
            case("queue"):{
                if(!event.getGuild().getAudioManager().isConnected()){
                    event.reply("Bot is not in a voice channel").queue();
                    return;
                }

                if(canUse(userVcState, guild.getAudioManager())){
                    GuildMusicManager man = getGuildAudioPlayer(event.getGuild());

                    if(man.scheduler.getQueue().isEmpty()){
                        event.reply("Queue is empty").queue();
                        return;
                    }

                    EmbedBuilder eb = new EmbedBuilder();

                    eb.setTitle("Current Queue");

                    AudioTrack current = man.plr.getPlayingTrack();
                    AudioTrackInfo info = current.getInfo();

                    eb.setThumbnail(current.getInfo().artworkUrl);
                    eb.addField("Current song:", info.title + " - " + info.author, false);
                    eb.setFooter("Written in Java by srth ", "https://avatars.githubusercontent.com/u/94727593?v=4");
                    eb.setColor(Color.white);


                    AtomicInteger indx = new AtomicInteger(1);
                    man.scheduler.getQueue().forEach((audioTrack -> {
                        var currentInfo = audioTrack.getInfo();

                        eb.addField(indx + ".", currentInfo.title + " - " + currentInfo.author, false);
                        indx.getAndIncrement();
                    }));

                    event.replyEmbeds(eb.build()).queue();
                } else{
                    event.reply("you can only use this command when you are in the same vc as the bot").setEphemeral(true).queue();
                }
                break;
            }
            case("abort"): {
                if(canUse(userVcState, guild.getAudioManager())){
                    abort(event);
                    event.reply("Clearing queue and leaving voice channel").queue();
                } else{
                    event.reply("you can only use this command when you are in the same vc as the bot").setEphemeral(true).queue();
                }
                break;
            }
            case("pause"): {
                if(!event.getGuild().getAudioManager().isConnected()){
                    event.reply("Bot is not in a voice channel").queue();
                    return;
                }

                if(canUse(userVcState, guild.getAudioManager())){
                    GuildMusicManager man = getGuildAudioPlayer(event.getGuild());

                    if(man.plr.isPaused()){
                        event.reply("player is already paused").queue();
                    } else{
                        man.plr.setPaused(true);
                        event.reply("paused track").queue();
                    }

                } else{
                    event.reply("you can only use this command when you are in the same vc as the bot").setEphemeral(true).queue();
                }
                break;
            }
            case("resume"): {
                if(!event.getGuild().getAudioManager().isConnected()){
                    event.reply("Bot is not in a voice channel").queue();
                    return;
                }

                if(canUse(userVcState, guild.getAudioManager())){
                    GuildMusicManager man = getGuildAudioPlayer(event.getGuild());

                    if(man.plr.isPaused()){
                        man.plr.setPaused(false);
                        event.reply("resumed track").queue();
                    } else{
                        event.reply("player is already playing").queue();
                    }

                } else{
                    event.reply("you can only use this command when you are in the same vc as the bot").setEphemeral(true).queue();
                }
                break;
            }
        }
    }

    @NotNull
    private EmbedBuilder getEmbedBuilder(AudioTrack track, GuildMusicManager man) {
        EmbedBuilder eb = new EmbedBuilder();

        AudioTrackInfo info = track.getInfo();

        eb.setTitle("Current Track");

        if(info.artworkUrl != null){
            eb.setThumbnail(info.artworkUrl);
        }

        eb.setFooter("Written in Java by srth ", "https://avatars.githubusercontent.com/u/94727593?v=4");
        eb.setColor(Color.white);

        if(!info.title.equalsIgnoreCase("Unknown Title")){
            eb.addField("Track Name: ", info.title + " - " + info.author, false);
        }

        eb.addField("Time Remaining: ", toTime(man.plr.getPlayingTrack().getPosition()) + " out of " + toTime(info.length) ,false);
        return eb;
    }

    private void connect(AudioManager man, VoiceChannel channel){
        if(!man.isConnected()){
            man.openAudioConnection(channel);
            man.setSelfDeafened(true);
        }
    }

    //https://github.com/lavalink-devs/lavaplayer/blob/main/demo-jda/src/main/java/com/sedmelluq/discord/lavaplayer/demo/jda/Main.java
    private synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
        long guildId = guild.getIdLong();
        GuildMusicManager musicManager = inst.getStreamManagers().get(guildId);

        if (musicManager == null) {
            musicManager = new GuildMusicManager(inst.getPlayerManager());
            inst.getStreamManagers().put(guildId, musicManager);
        }

        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;
    }

    private void loadAndPlay(final SlashCommandInteractionEvent event, final String trackUrl) {
        GuildMusicManager musicManager = getGuildAudioPlayer(event.getGuild());

        inst.getPlayerManager().loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                event.reply("Adding to queue: " + track.getInfo().title + " - " + track.getInfo().author).queue();

                play(event.getGuild(), musicManager, track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                AudioTrack firstTrack = playlist.getSelectedTrack();

                if (firstTrack == null) {
                    firstTrack = playlist.getTracks().get(0);
                }

                event.reply("Adding to queue:  " + firstTrack.getInfo().title + " - " + firstTrack.getInfo().author + " (first track of playlist " + playlist.getName() + ")").queue();

                play(event.getGuild(), musicManager, firstTrack);
            }

            @Override
            public void noMatches() {
                event.reply("Nothing found by " + trackUrl).queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                event.reply("Could not play: " + exception.getMessage()).queue();
            }
        });
    }

    private void loadAndPlayFile(SlashCommandInteractionEvent event, Message.Attachment attachment) {
        GuildMusicManager musicManager = getGuildAudioPlayer(event.getGuild());

        inst.getPlayerManager().loadItemOrdered(musicManager, attachment.getProxyUrl(), new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                event.reply("Adding file to queue: " + attachment.getFileName()).queue();

                play(event.getGuild(), musicManager, track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {}

            @Override
            public void noMatches() {}

            @Override
            public void loadFailed(FriendlyException exception) {
                event.reply("Could not play: " + exception.getMessage()).queue();
            }
        });
    }

    private void play(Guild guild, GuildMusicManager musicManager, AudioTrack track) {
        musicManager.scheduler.queue(track);
    }

    private void skipTrack(SlashCommandInteractionEvent event) {
        GuildMusicManager musicManager = getGuildAudioPlayer(event.getGuild());
        AudioTrack track = musicManager.scheduler.nextTrack();

        event.reply("Skipped to next track: " +  track.getInfo().title + " - " + track.getInfo().author).queue();
    }

    private void abort(SlashCommandInteractionEvent event){
        GuildMusicManager musicManager = getGuildAudioPlayer(event.getGuild());

        musicManager.scheduler.abort();
        musicManager.plr.destroy();

        event.getGuild().getAudioManager().closeAudioConnection();
    }


    private boolean canUse(GuildVoiceState userState, AudioManager man){
        if(userState.inAudioChannel()){
            AudioChannel usrChannel = userState.getChannel().asVoiceChannel();
            AudioChannel botChannel = man.getConnectedChannel().asVoiceChannel();

            return usrChannel.getIdLong() == botChannel.getIdLong();
        }
        return false;
    }


    private String toTime(long time){
        long seconds = time/1000;
        long minutes = 0;

        while (seconds < 60){
            minutes++;
            seconds -= 60;
        }

        return minutes + ":" + seconds;
    }
}
