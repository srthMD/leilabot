package ro.srth.leila.listener;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ro.srth.leila.Bot;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CupRandomMessage extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent  event) {
        Random random = ThreadLocalRandom.current();
        int chance = random.nextInt(0, 100);

        if (chance == 57 && event.getMessage().getAuthor().getId().equals("428213747094650882")){
            Bot.log.info("AggeRandomMsg Fired");
            event.getMessage().reply("https://cdn.discordapp.com/attachments/1026077332689145932/1062766645535330405/Danskt_svin.mp4").queue();
        }
    }
}
