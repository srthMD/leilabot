package ro.srth.leila.listener;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ro.srth.leila.Bot;
import ro.srth.leila.commands.ForceRandomMsg;
import ro.srth.leila.commands.ToggleRandomMsg;

import java.util.Random;

public class RandomMsg extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent  event) {
        Random randommsgchance = new Random();

        int num1;
        int num2;
        int chance;

        if(ForceRandomMsg.forced){
            chance = 26;
        } else{
            num1 = 0;
            num2 = 75;
            chance = randommsgchance.nextInt(num1, num2);
        }


        if (chance == 26 && ToggleRandomMsg.toggled && !event.getMessage().getAuthor().getId().equals(event.getJDA().getSelfUser().getId())) {
            Bot.log.info("RandomMsg Fired");

            String msgs[] = {
                    "guys pleas like ans subscrib as only 2 percent of me vi ewers are subscribed",
                    "6 LIKES AND I WILL PLAY FORTNITE",
                    "erdoganchik",
                    "yebat blyat blyat shat ap man",
                    "https://gyazo.com/413ff061fa56cad3a06dd940ef2ed14c.gif",
                    "https://tenor.com/view/mike-taylor-why-why-me-me-slide-gif-15024288",
                    "https://imgur.com/a/BHR2uHG",
                    "https://tenor.com/view/when-the-drip-is-respectful-when-the-camel-arab-camel-arab-funny-gif-22131745",
                    "Lah",
                    "Ya Hmar",
                    "Play baldis basics  educational and learning classic remastered demo that's me",
                    "https://media.discordapp.net/attachments/995644703796117524/998489849470402641/this_will_be_sonic_exe_in_2013.gif",
                    "https://cdn.discordapp.com/attachments/1025198806859526154/1030505471901179945/PXL_20221014_154000023.TS.mp4",
                    "SAHT AP MAN I AM NOT DICKCORD BOT!!!! I AM REAL LELEA",
                    "yuor keyboart",
                    "https://cdn.discordapp.com/attachments/1021759229452898356/1042758070360617011/redditsave.com_patched_it_2022-wm0bpgw2a4x91.mp4",
                    "https://gyazo.com/6ecd578d231aaeba22c4cf67d8bbbad4.gif",
                    "https://media.discordapp.net/attachments/776456526638219274/893170620425195581/image0-1-1.gif",
                    "https://tenor.com/view/ragedose-criminality-roblox-criminality-katana-katana-sword-gif-25383013",
                    "Scrubby Number One Polish Femboy",
                    "https://tenor.com/view/send-horny-not-sussy-gif-21191779",
                    "https://cdn.discordapp.com/attachments/1021759229452898356/1057731695006204064/thugpredator.mp4",
                    "https://tenor.com/view/arabic-spongebob-car-gif-24735304",
                    "Pray To The Neighbors Dog", //funny rasis scrubby refrence
                    "I Have Your Ip",
                    "https://tenor.com/view/cars-guido-we-do-a-little-trolling-gif-20839544",
                    "Stupid Ass Simon https://cdn.discordapp.com/attachments/1045328191369256990/1058852518475595826/Bxlx4WvvXaZGAAAAAElFTkSuQmCC.png",
                    "moldovan superpower",
                    "Cheddar Is A Stupid Cunt",
                    "BUNA ZIUA EU SUNT LEILA ȘI EU PLACE COCAINE"
            };

            Random random = new Random();
            int index = random.nextInt(msgs.length);

            String reply = msgs[index];

            if(!event.getMessage().getChannel().getId().equals("1046576871330037830") || !event.getMessage().getChannel().getId().equals("1022265777371480124")){
                event.getMessage().getChannel().sendMessage(reply).queue();
                Bot.log.info("sending" + reply + "from random message");
            }
        }
    }
}
