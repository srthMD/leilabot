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

public class Ban extends LBSlashCommand {

    static {
        description = "bans a user from say or copypastasearch";
        subCmds.addAll(Set.of(
                new SubcommandData("say", "Bans a user from /say").addOption(OptionType.USER, "user", "The user you want to ban from /say", true),
                new SubcommandData("stream", "Bans a user from /stream").addOption(OptionType.USER, "user", "The user you want to ban from /stream")));
        permissions.add(Permission.MESSAGE_MANAGE);
    }

    @Override
    public void runSlashCommand(@NotNull SlashCommandInteractionEvent event) {
        //currently switch for ease of expansion
        switch (event.getSubcommandName()){
            case("say"): {
                ban(BanHandler.Command.SAY, event);
                break;
            }
            case("stream"):{
                ban(BanHandler.Command.STREAM, event);
                break;
            }
            default:
                event.reply("something went wrong").setEphemeral(true).queue();
        }
    }

    private void ban(BanHandler.Command cmd, SlashCommandInteractionEvent event){
        if (BanHandler.isBanned(event.getOption("user", OptionMapping::getAsUser).getIdLong(), cmd)) {
            event.reply("user is already banned").setEphemeral(true).queue();
            return;
        }

        boolean suc = BanHandler.banId(event.getOption("user", OptionMapping::getAsUser).getIdLong(), cmd);

        if (!suc) {
            event.reply("something went wrong trying to ban").setEphemeral(true).queue();
        }

        event.reply("success").setEphemeral(true).queue();
    }
}
