package ro.srth.leila.listener;

import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ro.srth.leila.Bot;
import ro.srth.leila.api.KeywordMentionHandler;

public class TutorialMention extends ListenerAdapter {
    public static Channel channel;
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String match = "tutorial";
        if(event.getMessage().getContentRaw().toLowerCase().contains(match) && !event.getMessage().getAuthor().getId().equals("1054544562841997363")){
            Bot.log.info("TutorialMention fired by" + event.getAuthor().getAsTag()); // log in case of abuse


            channel = event.getChannel();

            String message = "https://cdn.discordapp.com/attachments/1020345638405292043/1083081737304801372/tuterial.mp4";
            int handler = KeywordMentionHandler.handler;

            //if(handler == 2){
                event.getMessage().reply(message).queue();
                //   channel = null;
            //}
        }
    }

    public static Channel returnChannel(){
        if (channel != null){
            return channel;
        } else {
            return null;
        }
    }
}
