package ro.srth.leila.listener;

import net.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ro.srth.leila.Bot;
import ro.srth.leila.annotations.NeedsRevamp;
import ro.srth.leila.commands.ForceRandomReaction;
import ro.srth.leila.commands.ToggleRandomReaction;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@NeedsRevamp(reason = "old")
public class RandomReaction extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent  event) {
        Random randomchance = ThreadLocalRandom.current();

        int num1;
        int num2;
        int chance = 0;

        if(!ForceRandomReaction.forced){
            num1 = 1;
            num2 = 55;
            chance = randomchance.nextInt(num1, num2);
        } else if (ForceRandomReaction.forced){
            chance = 14;
        }


        if (chance == 14 && ToggleRandomReaction.toggled && !event.getMessage().getAuthor().getId().equals(event.getJDA().getSelfUser().getId()) && !event.getChannel().getId().equals("1046576871330037830")){
            Bot.log.info("RandomReaction Fired");

            List<RichCustomEmoji> emojis = event.getGuild().getEmojis();

            int index = randomchance.nextInt(emojis.size());

            RichCustomEmoji reaction = emojis.get(index);
            event.getMessage().addReaction(reaction).queue();
        }
    }
}
