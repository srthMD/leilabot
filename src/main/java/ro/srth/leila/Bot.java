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
import ro.srth.leila.commands.*;
import ro.srth.leila.commands.ctx.ReactionSpam;
import ro.srth.leila.commands.handler.CmdMan;
import ro.srth.leila.listener.*;

import javax.security.auth.login.LoginException;


@NeedsRevamp(reason = "bad logging and addEventListener spam")
public class Bot{
    private final ShardManager sman;

    public static final Logger log = LoggerFactory.getLogger(Bot.class);

    public static Dotenv env;


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
        sman.addEventListener(new RandomGame());
        sman.addEventListener(new LeilaPicSlashCmd());
        sman.addEventListener(new SaySlashCommand());
        sman.addEventListener(new RandomGmodMode());
        sman.addEventListener(new GetLogSlashCmd());
        sman.addEventListener(new OctaviousPicSlashCmd());
        sman.addEventListener(new BotInfoSlashCmd());
        sman.addEventListener(new RateSlashCmd());
        sman.addEventListener(new SimonPicCmd());
        sman.addEventListener(new RngSlashCmd());
        sman.addEventListener(new SayBanCmdHandler());
        sman.addEventListener(new RandomMsg());
        sman.addEventListener(new ToggleRandomMsg());
        sman.addEventListener(new ForceRandomMsg());
        sman.addEventListener(new ChuckyPicCmd());
        sman.addEventListener(new SearchCopypasta());
        sman.addEventListener(new RandomReaction());
        sman.addEventListener(new ToggleRandomReaction());
        sman.addEventListener(new ForceRandomReaction());
        sman.addEventListener(new CopypastaBanCmdHandler());
        sman.addEventListener(new Shitify());
        sman.addEventListener(new ToggleTextReactions());
        sman.addEventListener(new GenericMentionHandler());
        sman.addEventListener(new GenericUserRandomMessage());
        sman.addEventListener(new ReactionSpam());

        //funny stuff
        sman.addEventListener(new PrivateMute());
        sman.addEventListener(new WebhookTroll());
    }


    public static void main(String[] args){
        try{
           Bot bot = new Bot();
        } catch (LoginException ex){
            Bot.log.warn("somethign went wrong on login:\n" + ex.getLocalizedMessage());
        }
    }
}