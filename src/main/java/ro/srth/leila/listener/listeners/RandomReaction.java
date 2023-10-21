package ro.srth.leila.listener.listeners;

import net.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.commands.cmds.slash.Force;
import ro.srth.leila.commands.cmds.slash.Toggle;
import ro.srth.leila.exception.GuildNotFoundException;
import ro.srth.leila.listener.Listener;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomReaction extends Listener {

    public RandomReaction(){
        this.name = "RandomReaction";
    }

    final Random rand = ThreadLocalRandom.current();

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        boolean toggle, force;

        try {
            toggle = Toggle.getReactionToggle(event.getGuild().getIdLong());
        } catch (GuildNotFoundException e) {
            toggle = true;
        }

        try {
            force = Force.getReactionForced(event.getGuild().getIdLong());
        } catch (GuildNotFoundException e) {
            force = false;
        }

        if(event.getAuthor().isBot() || event.getAuthor().isSystem() || !toggle){return;}

        if (rand.nextInt(1, 101) <= 2 && !force){

            List<RichCustomEmoji> emojis = event.getGuild().getEmojis();

            if(emojis.isEmpty()){return;}

            int index = rand.nextInt(emojis.size());

            RichCustomEmoji reaction = emojis.get(index);
            event.getMessage().addReaction(reaction).queue();
        }else if(force){
            List<RichCustomEmoji> emojis = event.getGuild().getEmojis();

            int index = rand.nextInt(emojis.size());

            RichCustomEmoji reaction = emojis.get(index);
            event.getMessage().addReaction(reaction).queue();
        }
    }
}
