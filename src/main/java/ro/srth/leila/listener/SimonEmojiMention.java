package ro.srth.leila.listener;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SimonEmojiMention extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String match = ":simon:";
        if (event.getMessage().getContentRaw().contains(match) && !event.getMessage().getContentRaw().contains(":Lela:")){
            System.out.println("SimonEmojiMention Fired");
            event.getMessage().reply("Wowe That Simoen Emoji I Know That Cat").queue();
        }
    }
}
