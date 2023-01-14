package ro.srth.leila.listener;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ro.srth.leila.Bot;
import ro.srth.leila.commands.ForceRandomMsg;
import ro.srth.leila.commands.ToggleRandomMsg;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomMsg extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent  event) {
        Random randommsgchance = ThreadLocalRandom.current();

        int num1;
        int num2;
        int chance = 0;

        if(!ForceRandomMsg.forced){
            num1 = 1;
            num2 = 55;
            chance = randommsgchance.nextInt(num1, num2);
        } else if (ForceRandomMsg.forced){
            chance = 26;
        }


        if (chance == 26 && ToggleRandomMsg.toggled && !event.getMessage().getAuthor().getId().equals(event.getJDA().getSelfUser().getId()) && !event.getChannel().getId().equals("1046576871330037830")){
            Bot.log.info("RandomMsg Fired");

            String[] msgs = {
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
                    "BUNA ZIUA EU SUNT LEILA SI EU PLACE COCAINE",
                    "https://tenor.com/view/sharkblox-sharkblox-server-stupid59-gif-19277040",
                    "https://tenor.com/view/ccp-gif-20520582",
                    "Kaka land :steamhappy:",
                    "green waht is yum problem in bren men",
                    "i lov bananass",
                    "https://tenor.com/view/baldi-wave-happy-basics-education-gif-12160854",
                    "whas ap rob lox gang",
                    "https://tenor.com/view/haram-gif-25942073",
                    "are yuo haram",
                    "Marhaba",
                    "https://tenor.com/view/agamatsu-the-voices-deepwoken-gif-26200177",
                    "https://media.discordapp.net/attachments/992420210093084767/1036771831752949770/16639618653756539506196417252122.gif",
                    "https://media.discordapp.net/attachments/956290218946863134/991485412495343616/speed-3.gif",
                    "MIY AKKKKKK!@#!#!$(*@#*(&#@&*^(&^*!!@",
                    "Itnoways pleas play Baldis Basics To Get All Achievements Because you love Achievements",
                    "I Hate Mex",
                    "Leroy Has Problem In Bren",
                    "https://cdn.discordapp.com/attachments/1020345638405292043/1059629248782008450/Circel.mp4",
                    "Senin ananin amina koyarim orospu cocugu seni pic",
                    "https://cdn.discordapp.com/attachments/781885169375903756/1060791156490899526/v09044g40000ceo4vc3c77ubb9vpk620.mp4",
                    "https://tenor.com/view/henners786-henla-hennerse-henners-henry-gif-25599177"
            };

            int index = randommsgchance.nextInt(msgs.length);

            String reply = msgs[index];
            event.getMessage().getChannel().sendMessage(reply).queue();
        }
    }
}
