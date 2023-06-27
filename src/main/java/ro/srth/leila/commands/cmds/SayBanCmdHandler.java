package ro.srth.leila.commands.cmds;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.*;
import ro.srth.leila.commands.Command;
import ro.srth.leila.util.SayBan;

import java.util.ArrayList;

public class SayBanCmdHandler extends Command {

    public String user1;

    SayBan handler = new SayBan();

    public SayBanCmdHandler() {
        this.commandName = "sayban";
        this.description = "Bans a user from using /say.";
        this.type = CommandType.SLASH;
        this.args = new ArrayList<OptionData>();
        args.add(new OptionData(OptionType.USER, "saybanuser", "The user you want to ban from /say.", true));
        this.register = false;
    }
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals(this.commandName) && !event.isAcknowledged()) {
            if(event.getInteraction().getUser().getId().equals("780805916743565312") || event.getInteraction().getUser().getId().equals("584834083943874581")){
                Bot.log.info(event.getInteraction().getUser().getAsTag() + " Fired SayBan");

                OptionMapping user = event.getOption("saybanuser");

                String json = String.valueOf(handler.readJson());

                user1 = user.getAsUser().getId();
                if (json.contains(user1)){
                    event.reply("User is already banned.").setEphemeral(true).queue();
                    return;
                }

                event.reply("Banning " + user.getAsUser().getAsTag() + " from using /say.").setEphemeral(true).queue();

                Bot.log.info("Writing " + user1);
                handler.jArray.add(user1);

                handler.writeJson();
            } else{
                event.reply("You cant fire this command").setEphemeral(true).queue();
            }
        }
    }
}