package ro.srth.leila.listener;

import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ro.srth.leila.Bot;
import ro.srth.leila.api.KeywordMentionHandler;

import java.util.concurrent.TimeUnit;

public class LeilaEmojiMention extends ListenerAdapter {
    public static Channel channel;
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String match = ":Lela:";
        if (event.getMessage().getContentRaw().contains(match) && !event.getMessage().getAuthor().getId().equals("1054544562841997363")){
            Bot.log.info("LeilaEmojiMention fired by" + event.getAuthor().getAsTag());

            channel = event.getChannel();

            String message = "Wowwe That Is Me Emoji So Cooli";

            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if(KeywordMentionHandler.returnInt() == 3){
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
