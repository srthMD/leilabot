package ro.srth.leila.commands.cmds;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.*;
import ro.srth.leila.commands.Command;
import ro.srth.leila.util.CopypastaBan;

import java.io.IOException;
import java.util.ArrayList;

public class CopypastaBanCmdHandler extends Command {

    public long user1;

    CopypastaBan handler = new CopypastaBan();

    public CopypastaBanCmdHandler() {
        this.commandName = "copypastaban";
        this.description = "Bans a user from using /searchcopypasta.";
        this.type = Command.CommandType.SLASH;
        this.args = new ArrayList<OptionData>();
        args.add(new OptionData(OptionType.USER, "copypastabanuser", "The user you want to ban from /searchcopypasta.", true));
        this.register = true;
    }
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals(this.commandName) && !event.isAcknowledged()) {
            if(event.getInteraction().getUser().getId().equals("780805916743565312") || event.getInteraction().getUser().getId().equals("584834083943874581")){
                Bot.log.info(event.getInteraction().getUser().getName() + " Fired Copypastaban");

                OptionMapping user = event.getOption("copypastabanuser");


                user1 = user.getAsUser().getIdLong();
                try {
                    if (handler.isBanned(user1)){
                        event.reply("User is already banned.").setEphemeral(true).queue();
                        return;
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                event.reply("Banning " + user.getAsUser().getName() + " from using /searchcopypasta.").setEphemeral(true).queue();

                Bot.log.info("Writing " + user1);
                try {
                    handler.banId(user1);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            } else if(!event.getInteraction().getUser().getId().equals("780805916743565312") || !event.getInteraction().getUser().getId().equals("584834083943874581")){
                event.reply("You cant fire this command").setEphemeral(true).queue();
            }
        }
    }
}
