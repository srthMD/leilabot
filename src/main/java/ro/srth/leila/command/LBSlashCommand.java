package ro.srth.leila.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class LBSlashCommand extends LBCommand {
    public static String description;
    public static final List<OptionData> args = new ArrayList<>();
    public static final List<SubcommandData> subCmds = new ArrayList<>();

    public abstract void runSlashCommand(@NotNull SlashCommandInteractionEvent event);
}
