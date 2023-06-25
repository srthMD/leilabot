package ro.srth.leila.commands.ctx;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class ReactionSpam extends ListenerAdapter {

    private final int MAX_REACTIONS = 20;
    @Override
    public void onMessageContextInteraction(MessageContextInteractionEvent event) {
        if(event.getName().equals("Reaction Spam")){

            int currentreactions = event.getTarget().getReactions().size();
            if(currentreactions == 20){return;}
            int iteratorcount = MAX_REACTIONS-currentreactions;

            List<RichCustomEmoji> emojis = event.getJDA().getEmojis();

            Message msg = event.getTarget();

            ThreadLocalRandom rand = ThreadLocalRandom.current();

            IntStream.rangeClosed(0, iteratorcount).forEachOrdered(i -> msg.addReaction(emojis.get(rand.nextInt(emojis.size()))).queue());

            event.reply("done").setEphemeral(true).queue();
        }
    }
}
