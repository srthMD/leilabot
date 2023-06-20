package ro.srth.leila.listener;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ro.srth.leila.Bot;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomGame extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String match = "what do i play";

        for (String trigger : GenericMentionHandler.triggers) {
            if(event.getMessage().getContentRaw().toLowerCase().contains(trigger)){return;}
        }

        if (event.getMessage().getContentRaw().toLowerCase().contains(match) && !event.getMessage().getContentRaw().contains("gmod") && !event.getMessage().getAuthor().getId().equals("1054544562841997363")) {
            Bot.log.info("RandomGame Fired by " + event.getAuthor().getAsTag());

            String[] games = {
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
                    "World Of Tanks Blitz BECAUS I LAV BELARUS!!*&@@!!@@!##@!#@!",
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
                    "Modern Warfare 2"
            };

            Random random = ThreadLocalRandom.current();
            int index = random.nextInt(games.length);

            String reply = games[index];
            Bot.log.info(reply);

            event.getMessage().reply(reply).queue();
        }
    }
}
