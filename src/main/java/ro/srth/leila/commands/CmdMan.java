package ro.srth.leila.commands;

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

        } catch (Exception e){
            Bot.log.warning("exeption while registering slash commands");
            Bot.log.warning(e.toString());
        }

        event.getGuild().updateCommands().addCommands(commandData).queue();
    }
}
