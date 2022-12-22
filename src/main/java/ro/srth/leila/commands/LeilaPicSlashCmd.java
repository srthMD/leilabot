package ro.srth.leila.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.FileUpload;
import ro.srth.leila.Bot;

import java.io.File;
import java.util.Random;

public class LeilaPicSlashCmd extends ListenerAdapter {
    static File dir = new File("C:\\Users\\SRTH_\\Desktop\\leilabot\\leila");
    static File[] files = dir.listFiles();;
    static int amt = files.length;
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        String command = event.getName();
        if (command.equals("leilapicture")) {
            Bot.log.info(event.getInteraction().getUser().getAsTag() + "Fired LeilaPicSlashCmd");

            Random rand = new Random();

            File file = files[rand.nextInt(files.length)]; // chooses a random file

            FileUpload upload = FileUpload.fromData(file); // converts file????? idk it works

            Bot.log.info("filepath:" + file.toString());

            event.replyFiles(upload).queue(); // send
        }
    }
    static int getNumberOfLeilaPictures(){
        File dir = new File("C:\\Users\\SRTH_\\Desktop\\leilabot\\leila");
        File[] files = dir.listFiles();;
        int amt = files.length;
        return amt;
    }
}
