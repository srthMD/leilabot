package ro.srth.leila.command;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class SlashCommand extends Command{

    public static String description;
    public static List<OptionData> args = new ArrayList<>();
    public static List<SubcommandData> subCmds = new ArrayList<>();

    public SlashCommand(Guild guild){
        super(guild);
    }

    public abstract void runSlashCommand(@NotNull SlashCommandInteractionEvent event);
}
