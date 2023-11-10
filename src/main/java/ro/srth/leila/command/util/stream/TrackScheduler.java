package ro.srth.leila.command.util.stream;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TrackScheduler extends AudioEventAdapter {
    private final AudioPlayer plr;

    private final BlockingQueue<AudioTrack> queue;

    public TrackScheduler(AudioPlayer plr){
        this.plr = plr;
        this.queue = new LinkedBlockingQueue<>();
    }

    public void queue(AudioTrack track){
        if (!plr.startTrack(track, true)){
            queue.offer(track);
        }
    }

    public AudioTrack nextTrack() {
        AudioTrack track = queue.poll();
        plr.startTrack(track, false);
        return track;
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext) {
            nextTrack();
        }
    }

    public List<AudioTrack> getQueue(){
        return new ArrayList<>(queue);
    }

    public void abort(){
        queue.clear();
    }
}
