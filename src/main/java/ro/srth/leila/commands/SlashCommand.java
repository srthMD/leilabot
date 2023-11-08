package ro.srth.leila.commands;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class SlashCommand extends Command{

    public String description;

    public final List<OptionData> args;
    public final List<SubcommandData> subCmds;


    public SlashCommand(Guild guild){
        super(guild);
        subCmds = new ArrayList<>();
        args = new ArrayList<>();
    }

    public SlashCommand(){
        super();
        subCmds = new ArrayList<>();
        args = new ArrayList<>();
    }


    public abstract void runSlashCommand(@NotNull SlashCommandInteractionEvent event);

}
