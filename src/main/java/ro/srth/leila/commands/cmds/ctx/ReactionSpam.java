package ro.srth.leila.commands.cmds.ctx;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.commands.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class ReactionSpam extends Command {
    private final int MAX_REACTIONS = 20;

    @Override
    public void onMessageContextInteraction(@NotNull MessageContextInteractionEvent event) {
        if(event.getName().equals(this.commandName) && !event.isAcknowledged()) {
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

    public ReactionSpam() {
        this.commandName = "Reaction Spam";
        this.description = null;
        this.type = CommandType.CONTEXT_MENU;
        this.args = new ArrayList<>();
        this.register = true;
    }
}
