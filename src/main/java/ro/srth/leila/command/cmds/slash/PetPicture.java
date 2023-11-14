package ro.srth.leila.command.cmds.slash;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.utils.FileUpload;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.command.SlashCommand;
import ro.srth.leila.main.Config;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class PetPicture extends SlashCommand {

    ThreadLocalRandom rand;

    static {
        description = "command to house sub commands for pet pictures";
        subCmds.add(new SubcommandData("simon", "Sends a random picture of simon"));
        subCmds.add(new SubcommandData("leila", "Sends a random picture of leila"));
        subCmds.add(new SubcommandData("octavious", "Sends a random picture of octavious"));
        subCmds.add(new SubcommandData("chucky", "Sends a random picture of chucky"));
    }

    public PetPicture(Guild guild) {
        super(guild);
        rand = ThreadLocalRandom.current();
    }

    @Override
    public void runSlashCommand(@NotNull SlashCommandInteractionEvent event) {
        File dir;

        switch (event.getSubcommandName()) {
            case ("simon") -> dir = new File(Config.ROOT + "\\simon");
            case ("leila") -> dir = new File(Config.ROOT + "\\leila");
            case ("octavious") -> dir = new File(Config.ROOT + "\\octavious");
            case ("chucky") -> dir = new File(Config.ROOT + "\\chucky");
            default -> {
                event.reply("something went wrong").setEphemeral(true).queue();
                return;
            }
        }

        File[] files = dir.listFiles();

        assert files != null;
        File file = files[rand.nextInt(files.length)];

        FileUpload upload = FileUpload.fromData(file);

        event.replyFiles(upload).queue();
    }

    static int getNumberOfChuckyPictures(){
        final File dir = new File(Config.ROOT + "\\chucky");
        return Objects.requireNonNull(dir.listFiles()).length;
    }

    static int getNumberOfLeilaPictures(){
        final File dir = new File(Config.ROOT + "\\leila");
        return Objects.requireNonNull(dir.listFiles()).length;
    }

    static int getNumberOfOctaviousPictures(){
        final File dir = new File(Config.ROOT + "\\octavious");
        return Objects.requireNonNull(dir.listFiles()).length;
    }

    static int getNumberOfSimonPictures(){
        final File dir = new File(Config.ROOT + "\\simon");
        return Objects.requireNonNull(dir.listFiles()).length;
    }
}
