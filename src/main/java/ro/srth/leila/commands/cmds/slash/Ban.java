package ro.srth.leila.commands.cmds.slash;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.commands.Command;
import ro.srth.leila.util.CopypastaBan;
import ro.srth.leila.util.SayBan;

import java.io.IOException;

public class Ban extends Command {

    SayBan shandler;
    CopypastaBan handler;

    public Ban() {
        super();
        this.commandName = "ban";
        this.description = "bans a user from say or copypastasearch";
        this.type = CommandType.SLASH;
        subCmds.add(new SubcommandData("say", "Bans a user from /say").addOption(OptionType.USER, "user", "The user you want to ban from /say", true));
        permissions.add(Permission.MESSAGE_MANAGE);
        this.register = true;

        shandler = new SayBan();
        handler = new CopypastaBan();
    }
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals(this.commandName) && !event.isAcknowledged()) {
            switch (event.getSubcommandName()){
                case("say"):
                    try {
                        if(shandler.isBanned(event.getOption("user", OptionMapping::getAsUser).getIdLong())){
                            event.reply("user is already banned").setEphemeral(true).queue();
                            return;
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    try {
                        shandler.banId(event.getOption("user", OptionMapping::getAsUser).getIdLong());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                default:
                    event.reply("something went wrong").setEphemeral(true).queue();
            }
        }
    }
}
