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

public class ChuckyPicCmd extends Command {
    final File dir = new File("C:\\Users\\SRTH_\\Desktop\\leilabot\\chucky");

    public ChuckyPicCmd() {
        this.commandName = "chuckypicture";
        this.description = "Sends a random picture of Chucky from what I loaded into the bot.";
        this.type = Command.CommandType.SLASH;
        this.args = new ArrayList<OptionData>();
        this.register = true;
    }
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals(this.commandName) && !event.isAcknowledged()) {
            Bot.log.info(event.getInteraction().getUser().getAsTag() + " Fired ChuckyPicture");

            File[] files = dir.listFiles();

            Random rand = ThreadLocalRandom.current();

            File file = files[rand.nextInt(files.length)]; // chooses a random file

            FileUpload upload = FileUpload.fromData(file); // converts file????? idk it works

            Bot.log.info("filepath:" + file.toString());

            event.replyFiles(upload).queue(); // send
        }
    }
    static int getNumberOfChuckyPictures(){
        final File dir = new File("C:\\Users\\SRTH_\\Desktop\\leilabot\\chucky");
        File[] files = dir.listFiles();;
        return files.length;
    }
}
