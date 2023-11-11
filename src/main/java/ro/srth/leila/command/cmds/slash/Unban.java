package ro.srth.leila.command.cmds.slash;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.command.SlashCommand;
import ro.srth.leila.command.util.SayBan;

public class Unban extends SlashCommand {

    static {
        description = "unbans a user from say or copypastasearch";
        subCmds.add(new SubcommandData("say", "Unbans a user from /say").addOption(OptionType.USER, "user", "The user you want to unban from /say", true));
        permissions.add(Permission.MESSAGE_MANAGE);
    }

    public Unban(Guild guild) {
        super(guild);
    }

    @Override
    public void runSlashCommand(@NotNull SlashCommandInteractionEvent event) {
        switch (event.getSubcommandName()){
            case("say"):

                if(!SayBan.isBanned(event.getOption("user", OptionMapping::getAsUser).getIdLong())){
                    event.reply("user isnt banned").setEphemeral(true).queue();
                    return;
                }

                boolean suc = SayBan.unbanId(event.getOption("user", OptionMapping::getAsUser).getIdLong());
                if(!suc){
                    event.reply("something went wrong while unbanning").setEphemeral(true).queue();
                }

                event.reply("success").setEphemeral(true).queue();


                break;
            default:
                event.reply("something went wrong").setEphemeral(true).queue();
        }
    }
}