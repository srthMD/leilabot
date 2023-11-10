package ro.srth.leila.command.cmds.slash;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.utils.FileUpload;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.command.SlashCommand;
import ro.srth.leila.listener.listeners.RandomMsg;
import ro.srth.leila.main.Bot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;

public class Reload extends SlashCommand {

    static {
        description = "Refreshes certian parts of the bot.";
        subCmds.add(new SubcommandData("randommsg", "reloads all the random messages from disk"));
        permissions.add(Permission.ADMINISTRATOR);
    }

    public Reload(Guild guild) {
        super(guild);
    }

    @Override
    public void runSlashCommand(@NotNull SlashCommandInteractionEvent event) {
        try {
            reloadRandomMsgs();
        } catch (IOException e) {
            event.reply("something went wrong while reloading").queue();
            Bot.log.error(e.getMessage());
        }
        event.reply("successfully reloaded file from file attached").addFiles(FileUpload.fromData(new File("C:\\Users\\SRTH_\\Desktop\\leilabot\\randommsg.txt"))).queue();
    }


    public static void reloadRandomMsgs() throws IOException {
        String line;
        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\SRTH_\\Desktop\\leilabot\\randommsg.txt"));

        RandomMsg.msgs.clear();

        while((line = reader.readLine()) != null){
            // random messages are separated by CRLF
            RandomMsg.msgs.add(line);
        }

        Collections.shuffle(RandomMsg.msgs);

        reader.close();
    }
}
