package ro.srth.leila.commands.handler;

import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import ro.srth.leila.Bot;

import java.util.ArrayList;
import java.util.List;

public class CmdMan extends ListenerAdapter {
    @Override
    public void onGuildReady(GuildReadyEvent event) {
        // register commands here
        List<CommandData> commandData = new ArrayList<>();
        try{
            commandData.add(Commands.slash("leilapicture", "Sends a random picture of Leila from what I loaded into the bot."));
            Bot.log.info("attempting to add leilapicture command to command data");

            commandData.add(Commands.slash("octaviouspicture", "Sends a random picture of Octavious from what I loaded into the bot."));
            Bot.log.info("attempting to add octaviouspicture command to command data");

            commandData.add(Commands.slash("simonpicture", "Sends a random picture of Simon from what I loaded into the bot."));
            Bot.log.info("attempting to add simonpicture command to command data");

            OptionData content = new OptionData(OptionType.STRING, "content", "What you want the bot to say.", true);
            OptionData channel = new OptionData(OptionType.CHANNEL, "channel", "What channel you want the bot to say the message in.", false);
            commandData.add(Commands.slash("say", "Makes the bot say a message in a specified channel as long as it can speak in that channel.").addOptions(content, channel));
            Bot.log.info("attempting to add say command to command data");

            commandData.add(Commands.slash("test", "Makes the bot send a message as a test to see if it works."));
            Bot.log.info("attempting to add test command to command data");

            OptionData isEphemeral = new OptionData(OptionType.BOOLEAN, "isephemeral", "Makes the reply visible to others (false = visible to others).", true);
            commandData.add(Commands.slash("getlog", "Sends the current log of the bot.").addOptions(isEphemeral));
            Bot.log.info("attempting to add getlog command to command data");

            commandData.add(Commands.slash("botinfo", "Sends information about the bot."));
            Bot.log.info("attempting to add botinfo command to command data");

            OptionData user_to_rate = new OptionData(OptionType.USER, "user_to_rate", "Select the user to rate.", true);
            commandData.add(Commands.slash("rate", "Rates a user 0-100.").addOptions(user_to_rate));
            Bot.log.info("attempting to add rate command to command data");


            OptionData minimum = new OptionData(OptionType.INTEGER, "minimum", "Minimum number.", true);
            OptionData maximum = new OptionData(OptionType.INTEGER, "maximum", "Minimum number.", true);
            commandData.add(Commands.slash("rng", "Generates a random number with given paramaters.").addOptions(minimum, maximum));
            Bot.log.info("attempting to add rng command to command data");


            OptionData saybanuser = new OptionData(OptionType.USER, "saybanuser", "The user you want to ban from /say.", true);
            commandData.add(Commands.slash("sayban", "Bans a user from using /say.").addOptions(saybanuser));
            Bot.log.info("attempting to add sayban command to command data");

            commandData.add(Commands.slash("togglerandommsg", "Toggles random messages"));

            commandData.add(Commands.slash("forcerandommsg", "Forces the random message event to be fired every message"));

            Bot.log.info("\n--------------------------END COMMAND REGISTERING--------------------------\n\n\n"); // idk why it logs twice but i dont care


        } catch (Exception e){
            Bot.log.warning("exeption while registering slash commands");
            Bot.log.warning(e.toString());
        }
        event.getGuild().updateCommands().addCommands(commandData).queue();
    }
}
