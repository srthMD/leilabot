package ro.srth.leila.listener;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.FileUpload;
import ro.srth.leila.Bot;
import ro.srth.leila.commands.ToggleTextReactions;

import java.io.File;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GenericMentionHandler extends ListenerAdapter {

    public final static String[] triggers = {
            "armenia",
            "happy birthday",
            "kurdistan",
            ":Lela:",
            "https://tenor.com/view/poland-polish-flag-gif-11055231",
            ":simon:",
            "robux",
            "tutorial"
    };

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(!ToggleTextReactions.getToggledStatus() || event.getAuthor().isBot()){return;}

        for (String trigger: triggers) {
            if(event.getMessage().getContentRaw().toLowerCase().contains(trigger)){
                sendResult(trigger, event);
                return;
            }
        }
    }


    private void sendResult(String trigger, MessageReceivedEvent event) {
        Bot.log.info(event.getAuthor().getAsTag() + " fired GenericMentionHandler with trigger " + trigger);

        switch(trigger){
            case("armenia"):
                event.getMessage().reply("\uD83C\uDDF9\uD83C\uDDF7\uD83C\uDDF9\uD83C\uDDF7\uD83C\uDDF9\uD83C\uDDF7\uD83C\uDDF9\uD83C\uDDF7\uD83C\uDDF9\uD83C\uDDF7\uD83C\uDDF9\uD83C\uDDF7Mustafa Kemal ATATURK\uD83C\uDDF9\uD83C\uDDF7\uD83C\uDDF9\uD83C\uDDF7 KEBAB STRONG \uD83C\uDDF9\uD83C\uDDF7\uD83D\uDCAA\uD83D\uDCAA\uD83D\uDCAAOTTOMAN STRONG\uD83C\uDDF9\uD83C\uDDF7\uD83D\uDCAA\uD83C\uDDF9\uD83C\uDDF7 GO CRY ARMENIA\uD83E\uDD2E\uD83E\uDD22\uD83C\uDDE6\uD83C\uDDF2\uD83E\uDD22\uD83E\uDD2E ARMENIA GENOCIDE NOT HAPPEN BUT IF DID THEY DESERVE\uD83D\uDD95\uD83D\uDD95 ARMNIA KILL TURKS FIRST!! GAYREEK SWIM 1923\uD83C\uDDEC\uD83C\uDDF7\uD83E\uDD22\uD83E\uDD2E\uD83E\uDD23\uD83E\uDD23 TURKEY VS WHOLE WORLD 1919-1923\uD83C\uDDF9\uD83C\uDDF7\uD83C\uDDF9\uD83C\uDDF7 ISTANBUL>>>>CONSTANTINOPLE 1453 BEST YEAR OTTOMAN\uD83C\uDDF9\uD83C\uDDF7\uD83C\uDDF9\uD83C\uDDF7\uD83D\uDCAA\uD83D\uDCAA\uD83D\uDC4A\uD83D\uDC4A TURAN POWER\uD83C\uDDF9\uD83C\uDDF7\uD83C\uDDF9\uD83C\uDDF7\uD83C\uDDE6\uD83C\uDDFF\uD83C\uDDE6\uD83C\uDDFF GAYMNIA=EAST TURKEY, WEST AZERBAIJAN\uD83C\uDDE6\uD83C\uDDFF\uD83C\uDDF9\uD83C\uDDF7\uD83C\uDDE6\uD83C\uDDFF\uD83C\uDDF9\uD83C\uDDF7 OTTOMAN DEFEAT BRITISH GAY in GALIPOLI\uD83E\uDD2E\uD83E\uDD22\uD83C\uDDEC\uD83C\uDDE7 TURKEY\uD83C\uDDF9\uD83C\uDDF7\uD83C\uDDF9\uD83C\uDDF7 BETTER THAN EUROSHIT\uD83D\uDCA9\uD83D\uDCA9\uD83C\uDDEA\uD83C\uDDFA\uD83E\uDD2E\uD83D\uDD95\uD83D\uDD95\uD83D\uDD95").queue();
                break;
            case("happy birthday"):
                final File dir = new File("C:\\Users\\SRTH_\\Desktop\\leilabot\\bday");

                Random rand = ThreadLocalRandom.current();

                File[] files = dir.listFiles();
                File file = files[rand.nextInt(files.length)]; // chooses a random file

                FileUpload upload = FileUpload.fromData(file);

                Bot.log.info("filepath:" + file);

                event.getMessage().replyFiles(upload).queue();
                break;
            case("kurdistan"):
                event.getMessage().reply("\uD83C\uDDF9\uD83C\uDDF7\uD83C\uDDF9\uD83C\uDDF7\uD83C\uDDF9\uD83C\uDDF7\uD83C\uDDF9\uD83C\uDDF7\uD83C\uDDF9\uD83C\uDDF7\uD83C\uDDF9\uD83C\uDDF7Mustafa Kemal ATATURK\uD83C\uDDF9\uD83C\uDDF7\uD83C\uDDF9\uD83C\uDDF7 KEBAB STRONG \uD83C\uDDF9\uD83C\uDDF7\uD83D\uDCAA\uD83D\uDCAA\uD83D\uDCAAOTTOMAN STRONG\uD83C\uDDF9\uD83C\uDDF7\uD83D\uDCAA\uD83C\uDDF9\uD83C\uDDF7 GO CRY ARMENIA\uD83E\uDD2E\uD83E\uDD22\uD83C\uDDE6\uD83C\uDDF2\uD83E\uDD22\uD83E\uDD2E ARMENIA GENOCIDE NOT HAPPEN BUT IF DID THEY DESERVE\uD83D\uDD95\uD83D\uDD95 ARMNIA KILL TURKS FIRST!! GAYREEK SWIM 1923\uD83C\uDDEC\uD83C\uDDF7\uD83E\uDD22\uD83E\uDD2E\uD83E\uDD23\uD83E\uDD23 TURKEY VS WHOLE WORLD 1919-1923\uD83C\uDDF9\uD83C\uDDF7\uD83C\uDDF9\uD83C\uDDF7 ISTANBUL>>>>CONSTANTINOPLE 1453 BEST YEAR OTTOMAN\uD83C\uDDF9\uD83C\uDDF7\uD83C\uDDF9\uD83C\uDDF7\uD83D\uDCAA\uD83D\uDCAA\uD83D\uDC4A\uD83D\uDC4A TURAN POWER\uD83C\uDDF9\uD83C\uDDF7\uD83C\uDDF9\uD83C\uDDF7\uD83C\uDDE6\uD83C\uDDFF\uD83C\uDDE6\uD83C\uDDFF GAYMNIA=EAST TURKEY, WEST AZERBAIJAN\uD83C\uDDE6\uD83C\uDDFF\uD83C\uDDF9\uD83C\uDDF7\uD83C\uDDE6\uD83C\uDDFF\uD83C\uDDF9\uD83C\uDDF7 OTTOMAN DEFEAT BRITISH GAY in GALIPOLI\uD83E\uDD2E\uD83E\uDD22\uD83C\uDDEC\uD83C\uDDE7 TURKEY\uD83C\uDDF9\uD83C\uDDF7\uD83C\uDDF9\uD83C\uDDF7 BETTER THAN EUROSHIT\uD83D\uDCA9\uD83D\uDCA9\uD83C\uDDEA\uD83C\uDDFA\uD83E\uDD2E\uD83D\uDD95\uD83D\uDD95\uD83D\uDD95").queue();
                break;
            case(":Lela:"):
                event.getMessage().reply("Wowwe That Is Me Emoji So Cooli").queue();
                break;
            case("https://tenor.com/view/poland-polish-flag-gif-11055231"):
                event.getMessage().reply("POLSKA \uD83C\uDDF5\uD83C\uDDF1  \uD83C\uDDF5\uD83C\uDDF1  \uD83C\uDDF5\uD83C\uDDF1  \uD83C\uDDF5\uD83C\uDDF1  \uD83C\uDDF5\uD83C\uDDF1  \uD83C\uDDF5\uD83C\uDDF1  \uD83C\uDDF5\uD83C\uDDF1  BEST KRAJ OF THEM ALL \uD83E\uDEE1\uD83E\uDEE1\uD83E\uDEE1\uD83E\uDEE1\uD83E\uDEE1\uD83E\uDEE1\uD83E\uDEE1\uD83E\uDEE1\uD83E\uDEE1 I WAS DRIVING IN MY CAR ON THE ROAD ITS NOT SO FAR \uD83C\uDDF5\uD83C\uDDF1\n" + "BERLIN\uD83C\uDDE9\uD83C\uDDEA  LONDON\uD83C\uDFF4\uDB40\uDC67\uDB40\uDC62\uDB40\uDC65\uDB40\uDC6E\uDB40\uDC67\uDB40\uDC7F   MOSCOW TOO\uD83C\uDDF7\uD83C\uDDFA  ITS MY \uD83D\uDC96  I TELL YOU TRUE \uD83D\uDE4F  WHEN I CAME TO POLSKA \uD83C\uDDF5\uD83C\uDDF1 I SAW MY \uD83D\uDE97\uD83D\uDE97 WAS STOLEN \uD83D\uDE2D  IT WAS MY FAVORITE TRUCK \uD83D\uDEFB\uD83D\uDEFB\uD83D\uDEFB I SAID FUCKING KURWA MAC!! \uD83D\uDE21\uD83D\uDCA2 i lovE POLAND \uD83C\uDDF5\uD83C\uDDF1\uD83C\uDDF5\uD83C\uDDF1\uD83C\uDDF5\uD83C\uDDF1 I LOVE POLAND \uD83C\uDDF5\uD83C\uDDF1\uD83C\uDDF5\uD83C\uDDF1\uD83C\uDDF5\uD83C\uDDF1 I HAD A REALLY PRETTY CAR âœ¨âœ¨\uD83C\uDF1F BABY YOU HAVE ASS SO FAR \uD83C\uDF51\uD83C\uDDF5\uD83C\uDDF1  DONT BE SHY COME WITH US\uD83D\uDE98 WE WILL SHOW AMOROUS THEN I STAY IN POLAND \uD83C\uDDF5\uD83C\uDDF1\uD83C\uDDF5\uD83C\uDDF1\uD83C\uDDF5\uD83C\uDDF1 HAVE NOT CAR BUT I DONT MIND \uD83E\uDD37\u200Dâ™‚  CHICKS IN POLAND ARE SO HOT \uD83D\uDC6F\u200Dâ™€ \uD83C\uDDF5\uD83C\uDDF1\uD83C\uDDF5\uD83C\uDDF1 I LOVE KURWA MAC! PPPPPPPPPPPPPOOOOOLLLLLLAND \uD83C\uDDF5\uD83C\uDDF1\uD83C\uDDF5\uD83C\uDDF1\uD83C\uDDF5\uD83C\uDDF1 KOCHAMY POLSKÄ˜ KOCHAMY POLSKÄ˜ KOCHAMY POLSKÄ˜ KOCHAMY POLSKÄ˜ KOCHAMY POLSKÄ˜ \uD83D\uDC96\uD83D\uDC96\uD83D\uDC96\uD83D\uDC96\uD83C\uDDF5\uD83C\uDDF1\uD83C\uDDF5\uD83C\uDDF1\uD83C\uDDF5\uD83C\uDDF1\uD83C\uDDF5\uD83C\uDDF1\uD83C\uDDF5\uD83C\uDDF1\uD83D\uDC96\uD83D\uDC96\uD83D\uDC96\uD83D\uDC96\uD83D\uDC96\uD83C\uDDF5\uD83C\uDDF1\uD83C\uDDF5\uD83C\uDDF1\uD83C\uDDF5\uD83C\uDDF1\uD83C\uDDF5\uD83C\uDDF1\uD83C\uDDF5\uD83C\uDDF1").queue();
            return;
            case(":simon:"):
                event.getMessage().reply("Wowe That Simoen Emoji I Know That Cat").queue();
                break;
            case("robux"):
                if (event.getAuthor().getIdLong() == 474329614022606851L){
                    event.getMessage().reply("SHAT AP POOR TURK STOP BEGGAR").queue();
                }
                break;
            case("tutorial"):
                event.getMessage().reply("https://cdn.discordapp.com/attachments/1020345638405292043/1083081737304801372/tuterial.mp4").queue();
                break;

            default:
                Bot.log.warn(trigger + " is not a known trigger in switch statement");
        }
    }

    public static int getNumberOfBdayArtPctures(){
        final File dir = new File("C:\\Users\\SRTH_\\Desktop\\leilabot\\bday");
        File[] files = dir.listFiles();
        return files.length;
    }
}
