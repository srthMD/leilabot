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

public class OctaviousPicSlashCmd extends Command {
    final File dir = new File("C:\\Users\\SRTH_\\Desktop\\leilabot\\octavious");

    public OctaviousPicSlashCmd() {
        this.commandName = "octaviouspicture";
        this.description = "Sends a picture of Octavious that I loaded into the bot.";
        this.type = Command.CommandType.SLASH;
        this.args = new ArrayList<OptionData>();
        this.register = true;
    }
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals(this.commandName) && !event.isAcknowledged()) {
            Bot.log.info(event.getInteraction().getUser().getName() + " Fired OctaviousPicSlashCmd");

            Random rand = ThreadLocalRandom.current();

            File[] files = dir.listFiles();

            File file = files[rand.nextInt(files.length)];

            FileUpload upload = FileUpload.fromData(file);

            Bot.log.info("filepath:" + file);

            event.replyFiles(upload).queue();
        }
    }
    static int getNumberOfOctaviousPictures(){
        final File dir = new File("C:\\Users\\SRTH_\\Desktop\\leilabot\\octavious");
        File[] files = dir.listFiles();
        return files.length;
    }
}
