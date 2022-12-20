package ro.srth.leila.commands;

import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

public class CmdMan extends ListenerAdapter {
    @Override
    public void onGuildReady(GuildReadyEvent event) {
        // register commands here
        List<CommandData> commandData = new ArrayList<>();

        commandData.add(Commands.slash("leilapicture", "Sends a random picture of Leila from what I loaded into the bot."));


        OptionData content = new OptionData(OptionType.STRING, "content", "What you want the bot to say.", true);
        OptionData channel = new OptionData(OptionType.CHANNEL, "channel", "What channel you want the bot to say the message in.", false);

        commandData.add(Commands.slash("say", "Makes the bot say a message in a specified channel as long as it can speak in that channel.").addOptions(content, channel));


        commandData.add(Commands.slash("test", "Makes the bot send a message as a test to see if it works."));


        event.getGuild().updateCommands().addCommands(commandData).queue();
    }
}
