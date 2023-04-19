package ro.srth.leila.listener;

import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ro.srth.leila.Bot;
import ro.srth.leila.api.KeywordMentionHandler;
import ro.srth.leila.commands.ToggleTextReactions;

public class SimonEmojiMention extends ListenerAdapter {
    public static Channel channel;
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(!ToggleTextReactions.getToggledStatus()){return;}
        String match = ":simon:";
        if (event.getMessage().getContentRaw().contains(match) && !event.getMessage().getAuthor().getId().equals("1054544562841997363")){
            Bot.log.info("SimonEmojiMention Fired by" + event.getAuthor().getAsTag());

            String message = "Wowe That Simoen Emoji I Know That Cat";

            channel = event.getChannel();

            int handler = KeywordMentionHandler.handler;

            //if(handler == 5){
                event.getChannel().sendMessage(message).queue();
           //    channel = null;
           // }
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
