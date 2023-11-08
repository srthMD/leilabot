package ro.srth.leila.commands.cmds.slash;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.commands.SlashCommand;

import java.util.concurrent.ThreadLocalRandom;

public class RNG extends SlashCommand {

    ThreadLocalRandom random;

    public RNG(Guild guild) {
        super(guild);
        this.commandName = "rng";
        this.description = "Generates a random number with given paramaters";
        args.add(new OptionData(OptionType.INTEGER, "minimum", "Minimum number.", true));
        args.add(new OptionData(OptionType.INTEGER, "maximum", "Maximum number.", true));

        random = ThreadLocalRandom.current();
    }

    public RNG() {
        super();
        this.commandName = "rng";
        this.description = "Generates a random number with given paramaters";
    }

    @Override
    public void runSlashCommand(@NotNull SlashCommandInteractionEvent event) {
        OptionMapping minmapping = event.getOption("minimum");
        OptionMapping maxmapping = event.getOption("maximum");


        int min = minmapping.getAsInt();
        int max = maxmapping.getAsInt();


        int rating = random.nextInt(min, max);

        event.reply(rating + " (min: " + min + " max: " + max + ")").queue();
    }
}