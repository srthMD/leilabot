package ro.srth.leila.commands.cmds;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.*;
import ro.srth.leila.commands.Command;
import ro.srth.leila.util.CopypastaBan;

import java.util.ArrayList;

public class CopypastaBanCmdHandler extends Command {

    public String user1;

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

                String json = String.valueOf(handler.readJson());

                user1 = user.getAsUser().getId();
                if (json.contains(user1)){
                    event.reply("User is already banned.").setEphemeral(true).queue();
                    return;
                }

                event.reply("Banning " + user.getAsUser().getName() + " from using /searchcopypasta.").setEphemeral(true).queue();

                Bot.log.info("Writing " + user1);
                handler.jArray.add(user1);

                handler.writeJson();
            } else if(!event.getInteraction().getUser().getId().equals("780805916743565312") || !event.getInteraction().getUser().getId().equals("584834083943874581")){
                event.reply("You cant fire this command").setEphemeral(true).queue();
            }
        }
    }
}
