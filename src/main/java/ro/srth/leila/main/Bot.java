package ro.srth.leila.main;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.srth.leila.command.CmdMan;
import ro.srth.leila.command.util.stream.GuildMusicManager;
import ro.srth.leila.guild.GuildConfiguration;
import ro.srth.leila.guild.GuildHandler;
import ro.srth.leila.listener.ListenerHandler;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


/**
 * Main class of the bot.
 */
public class Bot{

    public static final Logger log = LoggerFactory.getLogger(Bot.class);

    public static Dotenv env;

    private static ShardManager sman;

    private static Bot instance;

    private final Cache<Long, GuildConfiguration> guildCache;

    public Cache<Long, GuildConfiguration> getGuildCache() {return guildCache;}

    private final Map<Long, GuildMusicManager> streamManagers = new HashMap<>();

    private final AudioPlayerManager playerManager = new DefaultAudioPlayerManager();

    //preforms login and starts all event listeners
    public Bot() {
        guildCache = Caffeine.newBuilder().build();

        env = Dotenv.configure().directory("C:\\Users\\SRTH_\\Desktop\\leilabot").load(); //load .env

        String token = env.get("TOKEN");

        //builder stuff
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createLight(token);

        builder.setStatus(Config.STATUS);
        builder.setActivity(Config.ACTIVITY_STATUS);
        builder.enableIntents(Config.GATEWAY_INTENTS);
        builder.enableCache(Config.CACHE_FLAGS);
        builder.setMemberCachePolicy(Config.MEMBER_CACHE_POLICY);

        try{
            sman = builder.build();
        } catch (Exception e) {
            Bot.log.warn("sman build() failed, most likely no internet connection");
            Bot.log.info(e.getMessage());

            for (int i = 1; i < 10; i++) {
                boolean suc = false;
                try{
                    sman = builder.build();
                    suc = true;
                } catch (Exception e2){
                    Bot.log.warn("reconnect attempt failed, attempt " + i);

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                if(suc){break;}
            }
        }

        //register command and listener handlers
        sman.addEventListener(new CmdMan());
        sman.addEventListener(new ListenerHandler());
        sman.addEventListener(new GuildHandler());
    }

    public static ShardManager getSman(){
        return sman;
    }

    public static Bot instance(){return instance;}

    public Map<Long, GuildMusicManager> getStreamManagers(){return streamManagers;}

    public AudioPlayerManager getPlayerManager(){return playerManager;}

    public static int getNumberOfBdayArtPctures(){
        final File dir = new File(Config.ROOT + "\\bday");
        File[] files = dir.listFiles();
        return files.length;
    }

    public static void main(String[] args){
        instance = new Bot();
        AudioSourceManagers.registerRemoteSources(instance.playerManager);
        AudioSourceManagers.registerLocalSource(instance.playerManager);
    }
}