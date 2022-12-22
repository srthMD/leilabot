package ro.srth.leila.listener;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ro.srth.leila.Bot;

public class SimonEmojiMention extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String match = ":simon:";
        if (event.getMessage().getContentRaw().contains(match) && !event.getMessage().getContentRaw().contains(":Lela:") && !event.getMessage().getAuthor().getId().equals("1054544562841997363")){
            Bot.log.info("SimonEmojiMention Fired by" + event.getAuthor().getAsTag());
            event.getMessage().reply("Wowe That Simoen Emoji I Know That Cat").queue();
        }
    }
}
