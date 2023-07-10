package ro.srth.leila.listener.listeners;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.commands.cmds.slash.Force;
import ro.srth.leila.commands.cmds.slash.Toggle;
import ro.srth.leila.listener.Listener;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class RandomMsg extends Listener {

    public RandomMsg(){
        this.name = "RandomMsg";
    }

    Random rand = ThreadLocalRandom.current();


    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        if(event.getAuthor().isBot() || event.getAuthor().isSystem() || !Toggle.getMessageToggle()){return;}

        if(Force.getMessageForced()){
            int index = rand.nextInt(msgs.length);

            String reply = msgs[index];
            event.getMessage().getChannel().sendMessage(reply).queue();
        } else{
            int rng = rand.nextInt(1, 251);
            if(rng <= 3){
                int index = rand.nextInt(msgs.length);

                String reply = msgs[index];
                event.getMessage().getChannel().sendMessage(reply).queue();
            }
        }
    }

    String[] msgs = {
            "guys pleas like ans subscrib as only 2 percent of me vi ewers are subscribed",
            "6 LIKES AND I WILL PLAY FORTNITE",
            "https://gyazo.com/413ff061fa56cad3a06dd940ef2ed14c.gif",
            "https://tenor.com/view/mike-taylor-why-why-me-me-slide-gif-15024288",
            "https://imgur.com/a/BHR2uHG",
            "https://tenor.com/view/when-the-drip-is-respectful-when-the-camel-arab-camel-arab-funny-gif-22131745",
            "Play baldis basics  educational and learning classic remastered demo that's me",
            "https://media.discordapp.net/attachments/995644703796117524/998489849470402641/this_will_be_sonic_exe_in_2013.gif",
            "https://cdn.discordapp.com/attachments/1025198806859526154/1030505471901179945/PXL_20221014_154000023.TS.mp4",
            "SAHT AP MAN I AM NOT DICKCORD BOT!!!! I AM REAL LELEA",
            "my keyboart",
            "https://cdn.discordapp.com/attachments/1021759229452898356/1042758070360617011/redditsave.com_patched_it_2022-wm0bpgw2a4x91.mp4",
            "https://gyazo.com/6ecd578d231aaeba22c4cf67d8bbbad4.gif",
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
            "https://tenor.com/view/sharkblox-sharkblox-server-stupid59-gif-19277040",
            "https://tenor.com/view/ccp-gif-20520582",
            "green waht is yum problem in bren men",
            "i lov bananass",
            "https://tenor.com/view/baldi-wave-happy-basics-education-gif-12160854",
            "https://tenor.com/view/haram-gif-25942073",
            "Marhaba",
            "https://tenor.com/view/agamatsu-the-voices-deepwoken-gif-26200177",
            "https://media.discordapp.net/attachments/992420210093084767/1036771831752949770/16639618653756539506196417252122.gif",
            "https://media.discordapp.net/attachments/956290218946863134/991485412495343616/speed-3.gif",
            "MIY AKKKKKK!@#!#!$(*@#*(&#@&*^(&^*!!@",
            "Itnoways pleas play Baldis Basics To Get All Achievements Because you love Achievements",
            "https://cdn.discordapp.com/attachments/1020345638405292043/1059629248782008450/Circel.mp4",
            "Senin ananin amina koyarim orospu cocugu seni pic",
            "https://cdn.discordapp.com/attachments/781885169375903756/1060791156490899526/v09044g40000ceo4vc3c77ubb9vpk620.mp4",
            "https://tenor.com/view/henners786-henla-hennerse-henners-henry-gif-25599177",
            "https://gyazo.com/43ecdc500efcef7dd1cd43526a859d5f.gif",
            "https://media.discordapp.net/attachments/734110004172423178/1082719987271221328/F12A3953-F102-4088-BEB1-F68E57F88ECD.gif",
            "https://cdn.discordapp.com/attachments/1082731961879101602/1083066016088719410/burger.mp4",
            "https://cdn.discordapp.com/attachments/1064186787007832214/1082772833970700358/alex_2.mp4",
            "https://cdn.discordapp.com/attachments/1064186787007832214/1082767424274829402/ssstwitter.com_1676181663415.mp4",
            "e Storia Underteeeeeele",
            "Lalala Skeleto Skeleto",
            "https://cdn.discordapp.com/attachments/1064186787007832214/1083089634868863046/image.png",
            "https://cdn.discordapp.com/attachments/1064186787007832214/1083089055614505021/xxckAYZKFUpSz1dS.mp4",
            "Hello Its Me RomaniaAWesomegamer274 And Today I Am Gona Be Playing War Thunder",
            "https://cdn.discordapp.com/attachments/1064186787007832214/1083088157777612851/de.mp4",
            "Alex Stop Using Equalizer You Stupid Cunt",
            "Intoways Why Did You Send This To Me https://cdn.discordapp.com/attachments/1064186787007832214/1083068248800628838/Screenshot_10.png",
            "https://cdn.discordapp.com/attachments/1064186787007832214/1083088476897030215/image.png",
            "HI GUYS IM HERE TO REMIND YUO THAT /getlog EXISTS YOU IDIOTS BYE!!#!@!1212!!11!",
            "https://cdn.discordapp.com/attachments/1064186787007832214/1126614368168198255/send15928494677673185786.mp4"
    };
}
