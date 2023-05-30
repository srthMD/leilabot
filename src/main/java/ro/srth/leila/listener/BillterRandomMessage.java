package ro.srth.leila.listener;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ro.srth.leila.Bot;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class BillterRandomMessage extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Random random = ThreadLocalRandom.current();
        int chance = random.nextInt(0, 100);

        if (chance == 57 && event.getMessage().getAuthor().getId().equals("364480181936717855")){
            Bot.log.info("BillterRandomMsg Fired");
            event.getMessage().reply("https://cdn.discordapp.com/attachments/1064186787007832214/1113172795862548500/ispejimas.mp4").queue();
        }
    }
}
