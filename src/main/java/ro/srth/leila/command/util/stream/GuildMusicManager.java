package ro.srth.leila.command.util.stream;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

public class GuildMusicManager {
    public final AudioPlayer plr;

    public final TrackScheduler scheduler;

    public GuildMusicManager(AudioPlayerManager man){
        plr = man.createPlayer();
        scheduler = new TrackScheduler(plr);
        plr.addListener(scheduler);
    }

    public AudioPlayerSendHandler getSendHandler(){
        return new AudioPlayerSendHandler(plr);
    }
}
