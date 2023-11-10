package ro.srth.leila.listener.listeners;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.command.cmds.slash.Force;
import ro.srth.leila.command.cmds.slash.Reload;
import ro.srth.leila.command.cmds.slash.Toggle;
import ro.srth.leila.exception.GuildNotFoundException;
import ro.srth.leila.listener.Listener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class RandomMsg extends Listener {

    public RandomMsg(){
        this.name = "RandomMsg";
        try {
            Reload.reloadRandomMsgs();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    final Random rand = ThreadLocalRandom.current();


    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        boolean toggle, force;

        try {
            toggle = Toggle.getMessageToggle(event.getGuild().getIdLong());
        } catch (GuildNotFoundException e) {
           toggle = true;
        }

        try {
            force = Force.getMessageForced(event.getGuild().getIdLong());
        } catch (GuildNotFoundException e) {
            force = false;
        }


        if(event.getAuthor().isBot() || event.getAuthor().isSystem() || !toggle){return;}

        if(force){
            int index = rand.nextInt(msgs.size());

            String reply = msgs.get(index);
            event.getMessage().getChannel().sendMessage(reply).queue();
        } else{
            int rng = rand.nextInt(1, 251);
            if(rng <= 3){
                int index = rand.nextInt(msgs.size());

                String reply = msgs.get(index);
                event.getMessage().getChannel().sendMessage(reply).queue();
            }
        }
    }

    public static final ArrayList<String> msgs = new ArrayList<>();
}
