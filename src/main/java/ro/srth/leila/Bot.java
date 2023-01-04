package ro.srth.leila;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import ro.srth.leila.commands.*;
import ro.srth.leila.commands.handler.CmdMan;
import ro.srth.leila.listener.*;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Bot{
    public static FileHandler fh;
    public static String fhp;
    private final ShardManager sman;

    public static Logger log = Logger.getLogger(Bot.class.getName());
    public static Dotenv env;

    public Bot() throws LoginException {
        env = Dotenv.configure().directory("C:\\Users\\SRTH_\\Desktop\\leilabot").load(); //load .env

        String token = env.get("TOKEN");

        //builder stuff
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createLight(token);

        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.playing("Eating The So"));
        builder.enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS);

        sman = builder.build();


        // logger code from stackoverflow because i don't know how to use logs
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yy-hh.mm.ss");
            LocalDateTime now = LocalDateTime.now();
            fhp = "C:\\temp\\" + dtf.format(now) +  ".log";
            fh = new FileHandler(fhp);
            log.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

        } catch (SecurityException | IOException e) {
            log.warning(e.toString());
        }

        // register listeners
        sman.addEventListener(new CmdMan());
        sman.addEventListener(new MsgOnKick());
        sman.addEventListener(new RandomGame());
        sman.addEventListener(new KurdistanMention());
        sman.addEventListener(new ArmeniaMention());
        sman.addEventListener(new LeilaPicSlashCmd());
        sman.addEventListener(new TestSlashCmd());
        sman.addEventListener(new SaySlashCommand());
        sman.addEventListener(new RandomGmodMode());
        sman.addEventListener(new SlinkRobuxMention());
        sman.addEventListener(new LeilaEmojiMention());
        sman.addEventListener(new SimonEmojiMention());
        sman.addEventListener(new GetLogSlashCmd());
        sman.addEventListener(new OctaviousPicSlashCmd());
        sman.addEventListener(new BotInfoSlashCmd());
        sman.addEventListener(new RateSlashCmd());
        sman.addEventListener(new SimonPicCmd());
        sman.addEventListener(new RngSlashCmd());
        sman.addEventListener(new PolandGifMention());
        sman.addEventListener(new SayBanCmdHandler());
        sman.addEventListener(new RandomMsg());
        sman.addEventListener(new ToggleRandomMsg());
        sman.addEventListener(new ForceRandomMsg());
        sman.addEventListener(new ChuckyPicCmd());
        sman.addEventListener(new SearchCopypasta());
    }

    public ShardManager getsman(){return sman;}

    public Dotenv getEnv(){return env;}

    public static void main(String[] args){
        try{
            Bot bot = new Bot();
        } catch (LoginException hmar){
            Bot.log.warning("somethign went wrong on login");
        }
    }
}