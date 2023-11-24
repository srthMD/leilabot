package ro.srth.leila.command.cmds.slash;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.command.LBSlashCommand;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;


public class Rate extends LBSlashCommand {
    static final ThreadLocalRandom rand = ThreadLocalRandom.current();

    static {
        description = "Rates a user 0-100.";
        args.add(new OptionData(OptionType.USER, "user_to_rate", "Select the user to rate.", true));
    }


    @Override
    public void runSlashCommand(@NotNull SlashCommandInteractionEvent event) {
        String usr;
        try{
            usr = Objects.requireNonNull(event.getOption("user_to_rate")).getAsString();
        } catch (NullPointerException ignored){
            event.reply("something went wrong during execution").setEphemeral(true).queue();
            return;
        }

        int rating = rand.nextInt(0, 100);

        event.reply(usr + " is a " + rating + "/100").queue();

    }

}