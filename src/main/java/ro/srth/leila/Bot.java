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
import ro.srth.leila.annotations.NeedsRevamp;
import ro.srth.leila.commands.CmdMan;
import ro.srth.leila.commands.Command;
import ro.srth.leila.listener.ListenerHandler;

import javax.security.auth.login.LoginException;
import java.util.Set;


@NeedsRevamp(reason = "addEventListener spam")
public class Bot{

    public static final Logger log = LoggerFactory.getLogger(Bot.class);

    public static Dotenv env;

    private static ShardManager sman;

    public static void registerCommand(Command command){
        sman.addEventListener(command);
    }

    public static Set<Class<? extends Command>> classes;

    public Bot() throws LoginException {
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

        sman = builder.build();


        // register listeners
        sman.addEventListener(new CmdMan());
        sman.addEventListener(new ListenerHandler());
    }

    public static ShardManager getSman(){
        return sman;
    }


    public static void main(String[] args){
        try{
           Bot bot = new Bot();
        } catch (LoginException ex){
            Bot.log.warn("something went wrong on login:\n" + ex.getLocalizedMessage());
        }
    }
}