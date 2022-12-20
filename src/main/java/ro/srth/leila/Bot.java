package ro.srth.leila;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import ro.srth.leila.commands.CmdMan;
import ro.srth.leila.commands.LeilaPicSlashCmd;
import ro.srth.leila.commands.SaySlashCommand;
import ro.srth.leila.commands.TestSlashCmd;
import ro.srth.leila.listener.ArmeniaMention;
import ro.srth.leila.listener.KurdistanMention;
import ro.srth.leila.listener.MsgOnKick;
import ro.srth.leila.listener.RandomGame;

import javax.security.auth.login.LoginException;

public class Bot{
    private final ShardManager sman;

    private final Dotenv env;

    public Bot() throws LoginException {
        env = Dotenv.configure().directory("PUT PATH HERE").load(); //load .env

        String token = env.get("TOKEN");

        //builder stuff
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createLight(token);

        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.playing("Eating The So"));
        builder.enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS);

        sman = builder.build();

        // register listeners
        sman.addEventListener(new MsgOnKick());
        sman.addEventListener(new RandomGame());
        sman.addEventListener(new KurdistanMention());
        sman.addEventListener(new ArmeniaMention());
        sman.addEventListener(new LeilaPicSlashCmd());
        sman.addEventListener(new TestSlashCmd());
        sman.addEventListener(new SaySlashCommand());
        sman.addEventListener(new CmdMan());
    }


    public ShardManager getsman(){return sman;}

    public Dotenv getEnv(){return env;}

    public static void main(String[] args){
        try{
            Bot bot = new Bot();
        } catch (LoginException hmar){
            System.out.println("somethign went wrong on login");
        }
    }
}