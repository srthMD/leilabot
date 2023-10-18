package ro.srth.leila.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class SlashCommand extends Command{

    public String commandName;
    public String description;
    public boolean register;

    public final List<OptionData> args;
    public final List<SubcommandData> subCmds;
    public final List<Permission> permissions;


    public SlashCommand(){
        permissions = new ArrayList<>();
        subCmds = new ArrayList<>();
        args = new ArrayList<>();
    }


    public abstract void runSlashCommand(@NotNull SlashCommandInteractionEvent event);

}