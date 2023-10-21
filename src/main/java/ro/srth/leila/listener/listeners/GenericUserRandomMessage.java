package ro.srth.leila.listener.listeners;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.exception.GuildNotFoundException;
import ro.srth.leila.main.Bot;
import ro.srth.leila.commands.cmds.slash.Toggle;
import ro.srth.leila.listener.Listener;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GenericUserRandomMessage extends Listener {

    public GenericUserRandomMessage(){
        this.name = "GenericUserRandomMessage";
    }

    private final long[] ids = {428213747094650882L, 364480181936717855L, 584012160158400573L, 820081420889751603L}; // could have mabye used maps to map an id to a video or vice versa but i dont care
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        boolean toggle;

        try {
            toggle = Toggle.getTextToggle(event.getGuild().getIdLong());
        } catch (GuildNotFoundException e) {
            toggle = true;
        }

        if(!toggle){return;}

        for (long id : ids) {
            if(id == event.getAuthor().getIdLong()){
                Random random = ThreadLocalRandom.current();
                int chance = random.nextInt(0, 100);


                if (chance <= 1){

                    if(id == 820081420889751603L){
                        event.getMessage().reply("https://cdn.discordapp.com/attachments/1064186787007832214/1113174334463291402/3aee08a1d19f11b1.mp4").queue();
                        return;
                    } else if(id == 584012160158400573L){
                        event.getMessage().reply("https://cdn.discordapp.com/attachments/1026077332689145932/1071446664574943253/hurrisika.mp4").queue();
                        return;
                    } else if(id == 364480181936717855L){
                        event.getMessage().reply("https://cdn.discordapp.com/attachments/1064186787007832214/1113172795862548500/ispejimas.mp4").queue();
                        return;
                    } else if(id == 428213747094650882L){
                        event.getMessage().reply("https://cdn.discordapp.com/attachments/1026077332689145932/1062766645535330405/Danskt_svin.mp4").queue();
                        return;
                    } else{
                        Bot.log.warn(id + " is not a valid id for GenericUserRandomMessage");
                    }
                }
            }
        }
    }
}
