package ro.srth.leila.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.FileUpload;
import ro.srth.leila.Bot;

import java.io.File;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class LeilaPicSlashCmd extends ListenerAdapter {
    final File dir = new File("C:\\Users\\SRTH_\\Desktop\\leilabot\\leila");

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        String command = event.getName();
        if (command.equals("leilapicture")) {
            Bot.log.info(event.getInteraction().getUser().getAsTag() + "Fired LeilaPicSlashCmd");

            File[] files = dir.listFiles();

            Random rand = ThreadLocalRandom.current();

            File file = files[rand.nextInt(files.length)]; // chooses a random file

            FileUpload upload = FileUpload.fromData(file); // converts file????? idk it works

            Bot.log.info("filepath:" + file.toString());

            event.replyFiles(upload).queue(); // send
        }
    }
    static int getNumberOfLeilaPictures(){
        final File dir = new File("C:\\Users\\SRTH_\\Desktop\\leilabot\\leila");
        File[] files = dir.listFiles();;
        return files.length;
    }
}
