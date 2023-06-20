package ro.srth.leila.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import ro.srth.leila.Bot;
import ro.srth.leila.util.SayBan;

public class SayBanCmdHandler extends ListenerAdapter {
    public String user1;

    SayBan handler = new SayBan();
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equals("sayban")){
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
