package ro.srth.leila.listener;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Random;

public class RandomGame extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent /* fuck you intents */ event) {
        String match = "what do i play";

        if (event.getMessage().getContentRaw().contains(match)) {
            System.out.println("RandomGame Fired");

            String games[] = {
                    "Item Asylum",
                    "Minecraft",
                    "Dont Starve Together",
                    "CSGO(Hide And Seek)",
                    "CSGO(Unranked)",
                    "Gmod(TTT)",
                    "Gmod(Sandbox)",
                    "Payday 2",
                    "Crab Game",
                    "Parkour",
                    "Phasmaphobia",
                    "Nothing You Stupid Cunt",
                    "Arsenal",
                    "Build A Boat For Treasure",
            };

            Random random = new Random();
            int index = random.nextInt(games.length);

            String reply = games[index];
            System.out.println(reply);

            event.getMessage().reply(reply).queue();
        }
    }
}
