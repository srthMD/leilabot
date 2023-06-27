package ro.srth.leila.commands.cmds;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.*;
import ro.srth.leila.commands.Command;
import ro.srth.leila.util.CopypastaBan;
import ro.srth.leila.util.GetCopypasta;

import java.util.ArrayList;

public class SearchCopypasta extends Command {

    private final GetCopypasta copypastahandler = new GetCopypasta();
    private final CopypastaBan banhandler = new CopypastaBan();

    public SearchCopypasta() {
        this.commandName = "searchcopypasta";
        this.description = "Replies with a copypasta from r/copypasta given a search query using randomized sorts";
        this.type = CommandType.SLASH;
        this.args = new ArrayList<OptionData>();
        args.add(new OptionData(OptionType.STRING, "query", "Searches r/copypasta with the query provided.", true));
        this.register = true;
    }
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals(this.commandName) && !event.isAcknowledged()) {
            if(banhandler.readJson().toString().contains(event.getInteraction().getUser().getId())){
                event.reply("you cant use this command").setEphemeral(true).queue();
                Bot.log.info(event.getInteraction().getUser().getAsTag() + " tried to fire searchcopypasta but was banned");
            } else{
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
}