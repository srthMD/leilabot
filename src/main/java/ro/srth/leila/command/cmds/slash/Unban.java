package ro.srth.leila.command.cmds.slash;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.command.LBSlashCommand;
import ro.srth.leila.command.util.BanHandler;

import java.util.Set;

public class Unban extends LBSlashCommand {

    static {
        description = "unbans a user from say or copypastasearch";
        subCmds.addAll(Set.of(
                new SubcommandData("say", "Unbans a user from /say").addOption(OptionType.USER, "user", "The user you want to unban from /say", true),
                new SubcommandData("stream", "Unbans a user from /stream").addOption(OptionType.USER, "user", "The user you want to unban")));
        permissions.add(Permission.MESSAGE_MANAGE);
    }


    @Override
    public void runSlashCommand(@NotNull SlashCommandInteractionEvent event) {
        switch (event.getSubcommandName()){
            case("say"): {
                unban(BanHandler.Command.SAY, event);
                break;
            }
            case("stream"):{
                unban(BanHandler.Command.STREAM, event);
                break;
            }
            default:
                event.reply("something went wrong").setEphemeral(true).queue();
        }
    }

    private void unban(BanHandler.Command cmd, SlashCommandInteractionEvent event){
        if(!BanHandler.isBanned(event.getOption("user", OptionMapping::getAsUser).getIdLong(), cmd)){
            event.reply("user isnt banned").setEphemeral(true).queue();
            return;
        }

        boolean suc = BanHandler.unbanId(event.getOption("user", OptionMapping::getAsUser).getIdLong(), cmd);
        if(!suc){
            event.reply("something went wrong while unbanning").setEphemeral(true).queue();
        }

        event.reply("success").setEphemeral(true).queue();

    }
}
