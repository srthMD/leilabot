package ro.srth.leila;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.srth.leila.commands.CmdMan;
import ro.srth.leila.listener.ListenerHandler;

public class Bot{

    public static final Logger log = LoggerFactory.getLogger(Bot.class);

    public static Dotenv env;

    private static ShardManager sman;

    public Bot() {
        CmdMan.initMaps();

        env = Dotenv.configure().directory("C:\\Users\\SRTH_\\Desktop\\leilabot").load(); //load .env

        String token = env.get("TOKEN");

        //builder stuff
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createLight(token);

        String status = "KICK ME IF ANIMAL ABUSER";

        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.watching(status));
        builder.enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_VOICE_STATES);
        builder.enableCache(CacheFlag.EMOJI, CacheFlag.VOICE_STATE);
        builder.setMemberCachePolicy(MemberCachePolicy.ALL);

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
    }

    public static ShardManager getSman(){
        return sman;
    }


    public static void main(String[] args){
        Bot bot = new Bot();
    }
}