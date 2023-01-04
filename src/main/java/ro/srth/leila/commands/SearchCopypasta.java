package ro.srth.leila.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import ro.srth.leila.Bot;
import ro.srth.leila.api.GetCopypasta;

public class SearchCopypasta extends ListenerAdapter {
    GetCopypasta copypastahandler = new GetCopypasta();
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equals("searchcopypasta")) {

            OptionMapping query = event.getOption("query");
            String query1 = query.getAsString();

            Bot.log.info(event.getInteraction().getUser().getAsTag() + " Fired SearchCopypasta with query " + query1);
            try {
                event.getInteraction().reply(copypastahandler.getCopypasta(query1)).queue();
            } catch (Exception e) {
                Bot.log.info(e.toString());
                event.reply("something went wrong while executing the command").setEphemeral(true).queue();
            }
        }
    }
}
