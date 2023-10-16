package ro.srth.leila.commands.cmds.slash;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.commands.SlashCommand;
import ro.srth.leila.util.SayBan;

public class Ban extends SlashCommand {



    public Ban() {
        super();
        this.commandName = "ban";
        this.description = "bans a user from say or copypastasearch";
        subCmds.add(new SubcommandData("say", "Bans a user from /say").addOption(OptionType.USER, "user", "The user you want to ban from /say", true));
        permissions.add(Permission.MESSAGE_MANAGE);
        this.register = true;
    }

    @Override
    public void runSlashCommand(@NotNull SlashCommandInteractionEvent event) {
        switch (event.getSubcommandName()){
            case("say"):
                if(SayBan.isBanned(event.getOption("user", OptionMapping::getAsUser).getIdLong())){
                    event.reply("user is already banned").setEphemeral(true).queue();
                    return;
                }

                boolean suc = SayBan.banId(event.getOption("user", OptionMapping::getAsUser).getIdLong());

                if(!suc){
                    event.reply("something went wrong trying to ban").setEphemeral(true).queue();
                }

                event.reply("success").setEphemeral(true).queue();
                break;
            default:
                event.reply("something went wrong").setEphemeral(true).queue();
        }
    }
}
