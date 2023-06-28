package ro.srth.leila.listener.listeners;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import ro.srth.leila.Bot;
import ro.srth.leila.annotations.NeedsRevamp;
import ro.srth.leila.listener.Listener;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@NeedsRevamp(reason = "possible conflicting reactions with GenericMentionHandler")
public class RandomGmodMode extends Listener {

    public RandomGmodMode(){
        this.name = "RandomGmodMode";
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent  event) {
        String match = "what do we play in gmod";
        String match1 = "what do i play in gmod";
        String match2 = "what do we play on gmod";
        String match3 = "what do i play on gmod";

        if (event.getMessage().getContentRaw().toLowerCase().contains(match) || event.getMessage().getContentRaw().contains(match1) || event.getMessage().getContentRaw().contains(match2) || event.getMessage().getContentRaw().contains(match3)) {
            Bot.log.info("RandomGmodMode Fired by " + event.getAuthor().getName());

            String[] games = {
                    "sandbox",
                    "ttt",
                    "murder",
                    "deathrun",
                    "prop hunt",
                    "melon bomber",
                    "flood"
            };

            Random random = ThreadLocalRandom.current();
            int index = random.nextInt(games.length);

            String reply = games[index];
            Bot.log.info(reply);

            event.getMessage().reply(reply).queue();
        }
    }
}
