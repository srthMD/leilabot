package ro.srth.leila.listener;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ro.srth.leila.Bot;

import java.util.Random;

public class RandomGame extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent /* fuck you intents */ event) {
        String match = "what do i play";

        if (event.getMessage().getContentRaw().contains(match) && !event.getMessage().getContentRaw().contains("gmod")) {
            Bot.log.info("RandomGame Fired by " + event.getAuthor().getAsTag());

            String games[] = {
                    "Item Asylum",
                    "Minecraft",
                    "Dont Starve Together",
                    "CSGO(Hide And Seek)",
                    "CSGO(Unranked)",
                    "Gmod",
                    "Payday 2",
                    "Crab Game",
                    "Parkour",
                    "Phasmaphobia",
                    "Nothing You Stupid Cunt",
                    "Arsenal",
                    "Build A Boat For Treasure",
                    "Fortnite",
                    "Jailbreak",
                    "World Of Tanks",
                    "World Of Tanks Blitz",
                    "War Thunder",
                    "Gang Beasts",
                    "Hollow Knight",
                    "MW2",
                    "People Playground",
                    "Ready or Not",
                    "Hotline Miami 1",
                    "Hotline Miami 2",
                    "Slime Rancher",
                    "Slime Rancher 2",
                    "BONEWORKS",
                    "Blade & Sorcery",
                    "Gta V",
                    "SUPERHOT",
                    "SUPERHOT:MCD",
                    "Rainbow 6 Seige",
                    "Hand Simulator",
                    "Trove",
                    "The Wild West",
                    "State Of Anarchy",
                    "Doom Eternal",
                    "Make A Wish",
                    "Pls Donate",
                    "Ultrakill",
                    "Terraria(unmodded)",
                    "Terraria(modded)",
                    "Fall Guys",
                    "Oxygen Not Included",
                    "With Your Penis Hahahahahahahahhahahahahahhahahahhahahahahhahhahahahahahhhahahahahahahahahahahahahahahahahahahahahahhahahahahhahahahahahahahahahah",
                    "Play baldis basics  educational and learning classic remastered demo that's me",
                    "Blood And Bacon",
                    "Redbox",
                    "Jackbox",
                    ""
            };

            Random random = new Random();
            int index = random.nextInt(games.length);

            String reply = games[index];
            Bot.log.info(reply);

            event.getMessage().reply(reply).queue();
        }
    }
}
