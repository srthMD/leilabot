package ro.srth.leila.listener;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SlinkRobuxMention extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String match = "give robux";
        String match2 = "pls robux";
        String match3 = "plz robux";
        String match4 = "please robux";

        String received = event.getMessage().getContentRaw();
        if(event.getAuthor().getId().equals("474329614022606851")){
            if(received.contains(match) || received.contains(match2) || received.contains(match3) || received.contains(match4)/*inneficent but it works*/){
                System.out.println("KurdistanMention Fired");

                String message = "SHAT AP POOR TURK STOP BEGGAR";
                event.getMessage().reply(message).queue();
            }
        } else {
            System.out.println("SlinkRobuxMention id check failed");
        }
    }
}
