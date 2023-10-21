package ro.srth.leila.commands.cmds.slash;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.commands.SlashCommand;

import java.util.concurrent.ThreadLocalRandom;

public class RngSlashCmd extends SlashCommand {

    public RngSlashCmd() {
        super();
        this.commandName = "rng";
        this.description = "Generates a random number with given paramaters";
        args.add(new OptionData(OptionType.INTEGER, "minimum", "Minimum number.", true));
        args.add(new OptionData(OptionType.INTEGER, "maximum", "Maximum number.", true));
        this.register = true;
    }

    @Override
    public void runSlashCommand(@NotNull SlashCommandInteractionEvent event) {
        OptionMapping minmapping = event.getOption("minimum");
        OptionMapping maxmapping = event.getOption("maximum");


        int min = minmapping.getAsInt();
        int max = maxmapping.getAsInt();

        if(min < 0 || max < 0 || min > Integer.MAX_VALUE || max > Integer.MAX_VALUE || min == max){
            event.reply("None of the options can be negative, equal to each other or be more than the maximum value of " + Integer.MAX_VALUE + ".").setEphemeral(true).queue();
        } else{
            ThreadLocalRandom random = ThreadLocalRandom.current();
            int rating = random.nextInt(min, max);

            event.reply(rating + " (min: " + min + " max: " + max + ")").queue();
        }
    }
}