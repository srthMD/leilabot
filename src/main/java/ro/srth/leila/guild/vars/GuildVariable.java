package ro.srth.leila.guild.vars;

import net.dv8tion.jda.api.entities.Guild;

/**
 * Generic interface representing a variable in a {@link ro.srth.leila.guild.GuildConfiguration GuildConfiguration}.
 * @param <T> {@link Object Object}
 */
public interface GuildVariable<T> {

    T getVar();

    Guild getGuild();

    String getName();

    String toString();
}
