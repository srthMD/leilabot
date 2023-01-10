package ro.srth.leila.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ro.srth.leila.Bot;

public class ForceRandomReaction extends ListenerAdapter {
    public static boolean forced;
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        String forcedreply;
        if (command.equals("forcerandomreaction")){
            if (event.getInteraction().getUser().getId().equals("780805916743565312") || event.getInteraction().getUser().getId().equals("584834083943874581")){
                Bot.log.info(event.getInteraction().getUser().getAsTag() + " Fired ForceRandomReaction");
                if(!forced){
                    forced = true;
                    forcedreply = "true";
                    event.reply("setting force reaction toggle to " + forcedreply).setEphemeral(true).queue();
                } else if(forced){
                    forced = false;
                    forcedreply = "false";
                    event.reply("setting force reaction toggle to " + forcedreply).setEphemeral(true).queue();
                }
            } else{
                event.reply("you dont have permission for this command Lahhhh").setEphemeral(true).queue();
            }
        }
    }
}
