package ro.srth.leila.listener;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ro.srth.leila.Bot;

public class LeilaEmojiMention extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String match = ":Lela:";
        if (event.getMessage().getContentRaw().contains(match)){
            Bot.log.info("LeilaEmojiMention fired by" + event.getAuthor().getAsTag());
            event.getMessage().reply("is thgat me emoji?????????????").queue();
        }
    }
}
