package ro.srth.leila.listener;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class LeilaEmojiMention extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String match = ":Lela:";
        if (event.getMessage().getContentRaw().contains(match)){
            event.getMessage().reply("is thgat me emoji?????????????").queue();
        }
    }
}
