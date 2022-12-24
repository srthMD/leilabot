package ro.srth.leila.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import ro.srth.leila.Bot;

import java.util.Random;

public class RngSlashCmd extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        if(command.equals("rng")){
            OptionMapping minmapping = event.getOption("minimum");
            OptionMapping maxmapping = event.getOption("maximum");

            Bot.log.info(event.getInteraction().getUser().getAsTag() + " fired RngSlashCmd");

            int min = minmapping.getAsInt();
            int max = maxmapping.getAsInt();

            if(min < 0 || max < 0 || min > Integer.MAX_VALUE || max > Integer.MAX_VALUE || min == max){
                event.reply("None of the options can be negative, equal to each other or be more than the maximum value of " + Integer.MAX_VALUE + ".").setEphemeral(true).queue();
            } else{
                Random random = new Random();
                int rating = random.nextInt(minmapping.getAsInt(), maxmapping.getAsInt());

                event.reply(String.valueOf(rating)).queue();
            }

        }
    }
}
