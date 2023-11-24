package ro.srth.leila.command.cmds.slash;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.command.LBSlashCommand;

import java.util.concurrent.ThreadLocalRandom;

public class RNG extends LBSlashCommand {

    static final ThreadLocalRandom rand = ThreadLocalRandom.current();

    static {
        description = "Generates a random number with given paramaters";
        args.add(new OptionData(OptionType.INTEGER, "minimum", "Minimum number.", true));
        args.add(new OptionData(OptionType.INTEGER, "maximum", "Maximum number.", true));
    }


    @Override
    public void runSlashCommand(@NotNull SlashCommandInteractionEvent event) {
        OptionMapping minmapping = event.getOption("minimum");
        OptionMapping maxmapping = event.getOption("maximum");

        int min = minmapping.getAsInt();
        int max = maxmapping.getAsInt();

        int rating = rand.nextInt(min, max);

        event.reply(rating + " (min: " + min + " max: " + max + ")").queue();
    }
}