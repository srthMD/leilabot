package ro.srth.leila.listener;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ro.srth.leila.Bot;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GenericUserRandomMessage extends ListenerAdapter {

    private final long[] ids = {428213747094650882L, 364480181936717855L, 584012160158400573L, 820081420889751603L}; // could have mabye used maps to map an id to a video or vice versa but i dont care
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        for (long id : ids) {
            if(id == event.getAuthor().getIdLong()){
                Random random = ThreadLocalRandom.current();
                int chance = random.nextInt(0, 100);


                if (chance == 57){
                   // thanks java for not supporting longs in switch

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
                        Bot.log.warning(id + " is not a valid id for GenericUserRandomMessage");
                    }
                }
            }
        }
    }
}
