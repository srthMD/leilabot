package ro.srth.leila.commands.cmds.slash;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.utils.FileUpload;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.annotations.GuildSpecific;
import ro.srth.leila.commands.Command;
import ro.srth.leila.listener.listeners.RandomMsg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@GuildSpecific(guildIdLong = 696053797755027537L)
public class Reload extends Command {

    public Reload() {
        super();
        this.commandName = "reload";
        this.description = "Refreshes certian parts of the bot.";
        this.type = CommandType.SLASH;
        subCmds.add(new SubcommandData("randommsg", "reloads all the random messages from disk"));
        this.register = true;
    }
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals(this.commandName) && !event.isAcknowledged()) {
            if(event.getUser().getIdLong() == 584834083943874581L){
                try {
                    reloadRandomMsgs();
                } catch (IOException e) {
                    event.reply("something went wrong while reloading").queue();
                }
                event.reply("successfully reloaded file from file attached").addFiles(FileUpload.fromData(new File("C:\\Users\\SRTH_\\Desktop\\leilabot\\randommsg.txt"))).queue();
            }
        }
    }

    public static void reloadRandomMsgs() throws IOException {

        String line = null;

        BufferedReader reader;

        reader = new BufferedReader(new FileReader("C:\\Users\\SRTH_\\Desktop\\leilabot\\randommsg.txt"));

        RandomMsg.msgs.clear();

        while((line = reader.readLine()) != null){
            RandomMsg.msgs.add(line);
        }
        reader.close();
    }
}
