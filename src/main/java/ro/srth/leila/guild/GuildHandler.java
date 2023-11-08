package ro.srth.leila.guild;

import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import ro.srth.leila.exception.UnsuccessfulWriteException;
import ro.srth.leila.exception.UnsucessfulReadException;
import ro.srth.leila.main.Bot;

public class GuildHandler extends ListenerAdapter {

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        long id = event.getGuild().getIdLong();

        if(!GuildReader.exists(id)){
            try {
                GuildWriter.write(new GuildConfiguration(GuildConfiguration.getDefaultGuildConfiguration(id), id));
            } catch (UnsuccessfulWriteException e) {
                Bot.log.error(e.getMessage());
            }
        }else{
            try {
                GuildConfiguration cfg = GuildReader.getFromDisk(id);
                Bot.instance().getGuildCache().put(id, cfg);
            } catch (UnsucessfulReadException ignored) {/*this should never run*/}
        }
    }
}
