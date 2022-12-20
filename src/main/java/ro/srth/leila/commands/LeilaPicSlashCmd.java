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
            File dir = new File("PUT PATH HERE"); // gets dir
            File[] files = dir.listFiles(); // lists the files in the dir

            Random rand = new Random();

            assert files != null; // idk

            File file = files[rand.nextInt(files.length)]; // chooses a random file

            System.out.println(file.toString()); // debug

            FileUpload upload = FileUpload.fromData(file); // converts file????? idk it works

            event.replyFiles(upload).queue(); // send
        }
    }
}
