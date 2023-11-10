package ro.srth.leila.command.cmds.slash;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.FileUpload;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.main.Bot;
import ro.srth.leila.command.SlashCommand;

import java.nio.file.Paths;

public class GetLog extends SlashCommand {

    static {
        description = "Sends the current log of the bot.";
        args.add(new OptionData(OptionType.BOOLEAN, "isephemeral", "Makes the reply visible to others (false = visible to others).", true));
    }

    public GetLog(Guild guild) {
        super(guild);
    }

    @Override
    public void runSlashCommand(@NotNull SlashCommandInteractionEvent event) {
        FileUpload upload = FileUpload.fromData(Paths.get("C:\\Users\\SRTH_\\AppData\\Local\\Temp\\bot.log"));

        OptionMapping isEphemeral = event.getOption("isephemeral");

        if (isEphemeral.getAsBoolean()){
            event.replyFiles(upload).setEphemeral(true).queue();
            Bot.log.info("sending log as ephemeral message");
        } else {
            event.replyFiles(upload).queue();
            Bot.log.info("sending log as public message");
        }
    }
}

