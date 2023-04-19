package ro.srth.leila.listener;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ro.srth.leila.Bot;
import ro.srth.leila.commands.ToggleTextReactions;

public class SlinkRobuxMention extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(!ToggleTextReactions.getToggledStatus()){return;}
        String match = "robux";

        String received = event.getMessage().getContentRaw().toLowerCase();
        if(received.contains(match)){
            if(event.getAuthor().getId().equals("474329614022606851")){
                Bot.log.info("SlinkRobuxMention Fired by " + event.getAuthor().getAsTag() /* in case of false positives */);

                String message = "SHAT AP POOR TURK STOP BEGGAR";
                event.getMessage().reply(message).queue();
            } else {
                Bot.log.warning("SlinkRobuxMention id check fail");
            }
        }
    }
}
