package ro.srth.leila.commands.cmds;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.FileUpload;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.*;
import ro.srth.leila.commands.Command;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SimonPicCmd extends Command {
    final File dir = new File("C:\\Users\\SRTH_\\Desktop\\leilabot\\simon");

    public SimonPicCmd() {
        this.commandName = "simonpicture";
        this.description = "Sends a random picture of Simon from what I loaded into the bot";
        this.type = CommandType.SLASH;
        this.args = new ArrayList<OptionData>();
        this.register = true;
    }
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals(this.commandName) && !event.isAcknowledged()) {
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