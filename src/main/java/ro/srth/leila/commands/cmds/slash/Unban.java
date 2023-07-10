package ro.srth.leila.commands.cmds.slash;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.commands.Command;
import ro.srth.leila.util.CopypastaBan;
import ro.srth.leila.util.SayBan;

import java.io.IOException;

public class Unban extends Command {
    public Unban() {
        super();
        this.commandName = "unban";
        this.description = "unbans a user from say or copypastasearch";
        this.type = CommandType.SLASH;
        subCmds.add(new SubcommandData("say", "Unbans a user from /say").addOption(OptionType.USER, "user", "The user you want to unban from /say", true));
        subCmds.add(new SubcommandData("searchcopypasta", "Unbans a user from /searchcopypasta").addOption(OptionType.USER, "user", "The user you want to unban from /searchcopypasta", true));
        this.register = true;
    }
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals(this.commandName) && !event.isAcknowledged()) {
            switch (event.getSubcommandName()){
                case("say"):
                    SayBan shandler = new SayBan();

                    try {
                        if(!shandler.isBanned(event.getOption("user", OptionMapping::getAsUser).getIdLong())){
                            event.reply("user isnt banned").setEphemeral(true).queue();
                            return;
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    try {
                        shandler.unbanId(event.getOption("user", OptionMapping::getAsUser).getIdLong());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case("searchcopypasta"):
                    CopypastaBan handler = new CopypastaBan();

                    try {
                        if(!handler.isBanned(event.getOption("user", OptionMapping::getAsUser).getIdLong())){
                            event.reply("user isnt already banned").setEphemeral(true).queue();
                            return;
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    try {
                        handler.unbanId(event.getOption("user", OptionMapping::getAsUser).getIdLong());
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
