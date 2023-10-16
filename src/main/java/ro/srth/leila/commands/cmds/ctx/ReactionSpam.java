package ro.srth.leila.commands.cmds.ctx;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.commands.ContextMenu;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class ReactionSpam extends ContextMenu {
    private static final int MAX_REACTIONS = 20;

    public ReactionSpam() {
        this.commandName = "Reaction Spam";
        this.register = true;
    }

    @Override
    public void runContextMenu(@NotNull MessageContextInteractionEvent event) {
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
