package ro.srth.leila.commands;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.utils.FileUpload;

import java.io.File;
import java.util.*;

public class LeilaPicSlashCmd extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equals("leilapicture")){
            File dir = new File("PUT PATH HERE");
            File[] files = dir.listFiles();

            Random rand = new Random();

            assert files != null;

            File file = files[rand.nextInt(files.length)];

            System.out.println(file.toString());


            FileUpload upload = FileUpload.fromData(file);

            event.replyFiles(upload).queue();
        }
    }
    @Override
    public void onGuildReady(GuildReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();
        commandData.add(Commands.slash("leilapicture", "Sends a random picture of Leila from what I loaded into the bot."));
        event.getGuild().updateCommands().addCommands(commandData).queue();
    }

}
