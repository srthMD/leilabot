package ro.srth.leila.listener;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Random;

public class RandomGmodMode extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent  event) {
        String match = "what do we play in gmod";
        String match1 = "what do i play in gmod";

        if (event.getMessage().getContentRaw().contains(match) || event.getMessage().getContentRaw().contains(match1)) {
            System.out.println("RandomGmodMode Fired");

            String games[] = {
                    "sandbox",
                    "ttt",
                    "murder",
                    "deathrun",
                    "prop hunt",
                    "melon bomber",
                    "flood"
            };

            Random random = new Random();
            int index = random.nextInt(games.length);

            String reply = games[index];
            System.out.println(reply);

            event.getMessage().reply(reply).queue();
        }
    }
}
