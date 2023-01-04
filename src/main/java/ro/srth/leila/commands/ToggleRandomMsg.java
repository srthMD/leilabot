package ro.srth.leila.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ro.srth.leila.Bot;

public class ToggleRandomMsg extends ListenerAdapter {
    public static boolean toggled = true;
    String toggledreply;
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equals("togglerandommsg")){
            Bot.log.info(event.getInteraction().getUser().getAsTag() + " Fired ToggleRandomMessage");
            if(!toggled){
                toggled = true;
                toggledreply = "true";
                event.reply("setting random message toggle to " + toggledreply).queue();
            } else if(toggled){
                toggled = false;
                toggledreply = "false";
                event.reply("setting random message toggle to " + toggledreply).queue();
            }
        }
    }
    public static boolean getToggledStatus(){
        return toggled;
    }
}
