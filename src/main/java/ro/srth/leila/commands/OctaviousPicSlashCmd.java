package ro.srth.leila.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.FileUpload;
import ro.srth.leila.Bot;

import java.io.File;
import java.util.Random;

public class OctaviousPicSlashCmd extends ListenerAdapter {
    static File dir = new File("C:\\Users\\SRTH_\\Desktop\\leilabot\\octavious");
    static File[] files = dir.listFiles();;
    static int amt = files.length;
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        String command = event.getName();
        if (command.equals("octaviouspicture")){
            Bot.log.info(event.getInteraction().getUser().getAsTag() + " Fired OctaviousPicSlashCmd");

            Random rand = new Random();

            File file = files[rand.nextInt(files.length)]; // chooses a random file

            FileUpload upload = FileUpload.fromData(file); // converts file????? idk it works

            Bot.log.info("filepath:" + file.toString());

            event.replyFiles(upload).queue(); // send
        }
    }
    static int getNumberOfOctaviousPictures(){
        File dir = new File("C:\\Users\\SRTH_\\Desktop\\leilabot\\octavious");
        File[] files = dir.listFiles();;
        int amt = files.length;
        return amt;
    }
}
