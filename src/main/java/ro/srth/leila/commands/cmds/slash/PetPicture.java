package ro.srth.leila.commands.cmds.slash;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.utils.FileUpload;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.commands.Command;

import java.io.File;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class PetPicture extends Command{

    public PetPicture() {
        super();
        this.commandName = "petpicture";
        this.description = "command to house sub commands for pet pictures";
        this.type = CommandType.SLASH;
        subCmds.add(new SubcommandData("simon", "Sends a random picture of simon"));
        subCmds.add(new SubcommandData("leila", "Sends a random picture of leila"));
        subCmds.add(new SubcommandData("octavious", "Sends a random picture of octavious"));
        subCmds.add(new SubcommandData("chucky", "Sends a random picture of chucky"));
        this.register = true;
    }
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals(this.commandName) && !event.isAcknowledged()) {
            File dir;

            switch (event.getSubcommandName()) {
                case ("simon") -> dir = new File("C:\\Users\\SRTH_\\Desktop\\leilabot\\simon");
                case ("leila") -> dir = new File("C:\\Users\\SRTH_\\Desktop\\leilabot\\leila");
                case ("octavious") -> dir = new File("C:\\Users\\SRTH_\\Desktop\\leilabot\\octavious");
                case ("chucky") -> dir = new File("C:\\Users\\SRTH_\\Desktop\\leilabot\\chucky");
                default -> {
                    event.reply("something went wrong").setEphemeral(true).queue();
                    return;
                }
            }

            File[] files = dir.listFiles();

            Random rand = ThreadLocalRandom.current();

            File file = files[rand.nextInt(files.length)];

            FileUpload upload = FileUpload.fromData(file);

            event.replyFiles(upload).queue();
        }
    }

    static int getNumberOfChuckyPictures(){
        final File dir = new File("C:\\Users\\SRTH_\\Desktop\\leilabot\\chucky");
        File[] files = dir.listFiles();
        return files.length;
    }

    static int getNumberOfLeilaPictures(){
        final File dir = new File("C:\\Users\\SRTH_\\Desktop\\leilabot\\leila");
        File[] files = dir.listFiles();
        return files.length;
    }

    static int getNumberOfOctaviousPictures(){
        final File dir = new File("C:\\Users\\SRTH_\\Desktop\\leilabot\\octavious");
        File[] files = dir.listFiles();
        return files.length;
    }

    static int getNumberOfSimonPictures(){
        final File dir = new File("C:\\Users\\SRTH_\\Desktop\\leilabot\\simon");
        File[] files = dir.listFiles();
        return files.length;
    }
}
