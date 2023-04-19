package ro.srth.leila.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ro.srth.leila.Bot;

public class ToggleTextReactions extends ListenerAdapter {
    public static boolean ReactionsToggled = true;
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equals("toggletextreactions")){
           Bot.log.info(event.getInteraction().getUser().getAsTag() + " Fired ToggleTextReactions");
           if(!ReactionsToggled){
               ReactionsToggled = true;
               event.getInteraction().reply("Setting reaction toggle to " + ReactionsToggled).queue();
           } else {
               ReactionsToggled = false;
               event.getInteraction().reply("Setting reaction toggle to " + ReactionsToggled).queue();
           }
        }
    }
    public static boolean getToggledStatus(){
        return ReactionsToggled;
    }
}
