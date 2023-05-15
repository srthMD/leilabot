package ro.srth.leila.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.FileUpload;
import ro.srth.leila.Bot;

import java.io.File;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SimonPicCmd extends ListenerAdapter {
    final File dir = new File("C:\\Users\\SRTH_\\Desktop\\leilabot\\simon");

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        String command = event.getName();
        if (command.equals("simonpicture")){
            Bot.log.info(event.getInteraction().getUser().getAsTag() + " Fired SimonPicCmd");

            Random rand = ThreadLocalRandom.current();

            final File[] files = dir.listFiles();

            File file = files[rand.nextInt(files.length)]; // chooses a random file

            FileUpload upload = FileUpload.fromData(file); // converts file????? idk it works

            Bot.log.info("filepath:" + file.toString());

            event.replyFiles(upload).queue(); // send
        }
    }
    static int getNumberOfSimonPictures(){
        final File dir = new File("C:\\Users\\SRTH_\\Desktop\\leilabot\\simon");
        File[] files = dir.listFiles();;
        return files.length;
    }
}
