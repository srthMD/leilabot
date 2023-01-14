package ro.srth.leila.listener;

import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ro.srth.leila.Bot;
import ro.srth.leila.api.KeywordMentionHandler;

import java.util.concurrent.TimeUnit;

public class KurdistanMention extends ListenerAdapter {
    public static Channel channel;
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String match = "kurdistan";
        if(event.getMessage().getContentRaw().contains(match) && !event.getMessage().getAuthor().getId().equals("1054544562841997363")){
            Bot.log.info("KurdistanMention fired by" + event.getAuthor().getAsTag()); // log in case of abuse

            channel = event.getChannel();

            String message = "\uD83C\uDDF9\uD83C\uDDF7\uD83C\uDDF9\uD83C\uDDF7\uD83C\uDDF9\uD83C\uDDF7\uD83C\uDDF9\uD83C\uDDF7\uD83C\uDDF9\uD83C\uDDF7\uD83C\uDDF9\uD83C\uDDF7Mustafa Kemal ATATURK\uD83C\uDDF9\uD83C\uDDF7\uD83C\uDDF9\uD83C\uDDF7 KEBAB STRONG \uD83C\uDDF9\uD83C\uDDF7\uD83D\uDCAA\uD83D\uDCAA\uD83D\uDCAAOTTOMAN STRONG\uD83C\uDDF9\uD83C\uDDF7\uD83D\uDCAA\uD83C\uDDF9\uD83C\uDDF7 GO CRY ARMENIA\uD83E\uDD2E\uD83E\uDD22\uD83C\uDDE6\uD83C\uDDF2\uD83E\uDD22\uD83E\uDD2E ARMENIA GENOCIDE NOT HAPPEN BUT IF DID THEY DESERVE\uD83D\uDD95\uD83D\uDD95 ARMNIA KILL TURKS FIRST!! GAYREEK SWIM 1923\uD83C\uDDEC\uD83C\uDDF7\uD83E\uDD22\uD83E\uDD2E\uD83E\uDD23\uD83E\uDD23 TURKEY VS WHOLE WORLD 1919-1923\uD83C\uDDF9\uD83C\uDDF7\uD83C\uDDF9\uD83C\uDDF7 ISTANBUL>>>>CONSTANTINOPLE 1453 BEST YEAR OTTOMAN\uD83C\uDDF9\uD83C\uDDF7\uD83C\uDDF9\uD83C\uDDF7\uD83D\uDCAA\uD83D\uDCAA\uD83D\uDC4A\uD83D\uDC4A TURAN POWER\uD83C\uDDF9\uD83C\uDDF7\uD83C\uDDF9\uD83C\uDDF7\uD83C\uDDE6\uD83C\uDDFF\uD83C\uDDE6\uD83C\uDDFF GAYMNIA=EAST TURKEY, WEST AZERBAIJAN\uD83C\uDDE6\uD83C\uDDFF\uD83C\uDDF9\uD83C\uDDF7\uD83C\uDDE6\uD83C\uDDFF\uD83C\uDDF9\uD83C\uDDF7 OTTOMAN DEFEAT BRITISH GAY in GALIPOLI\uD83E\uDD2E\uD83E\uDD22\uD83C\uDDEC\uD83C\uDDE7 TURKEY\uD83C\uDDF9\uD83C\uDDF7\uD83C\uDDF9\uD83C\uDDF7 BETTER THAN EUROSHIT\uD83D\uDCA9\uD83D\uDCA9\uD83C\uDDEA\uD83C\uDDFA\uD83E\uDD2E\uD83D\uDD95\uD83D\uDD95\uD83D\uDD95";

            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if(KeywordMentionHandler.returnInt() == 2){
                event.getChannel().sendMessage(message).queue();
                channel = null;
            }
        }
    }

    public static Channel returnChannel(){
        if (channel == null){
            return null;
        } else {
            return channel;
        }
    }
}
