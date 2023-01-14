package ro.srth.leila.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import ro.srth.leila.Bot;
import ro.srth.leila.api.CopypastaBan;
import ro.srth.leila.api.SayBan;

public class CopypastaBanCmdHandler extends ListenerAdapter {
    public String user1;

    CopypastaBan handler = new CopypastaBan();
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equals("copypastaban")){
            if(event.getInteraction().getUser().getId().equals("780805916743565312") || event.getInteraction().getUser().getId().equals("584834083943874581")){
                Bot.log.info(event.getInteraction().getUser().getAsTag() + " Fired Copypastaban");

                OptionMapping user = event.getOption("copypastabanuser");

                String json = String.valueOf(handler.readJson());

                user1 = user.getAsUser().getId();
                if (json.contains(user1)){
                    event.reply("User is already banned.").setEphemeral(true).queue();
                    return;
                }

                event.reply("Banning " + user.getAsUser().getAsTag() + " from using /searchcopypasta.").setEphemeral(true).queue();

                Bot.log.info("Writing " + user1);
                handler.jArray.add(user1);

                handler.writeJson();
            } else if(!event.getInteraction().getUser().getId().equals("780805916743565312") || !event.getInteraction().getUser().getId().equals("584834083943874581")){
                event.reply("You cant fire this command").setEphemeral(true).queue();
            }
        }
    }
}
