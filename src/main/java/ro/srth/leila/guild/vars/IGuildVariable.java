package ro.srth.leila.guild.vars;

/**
 * Generic interface representing a variable in a {@link ro.srth.leila.guild.GuildConfiguration GuildConfiguration}.
 * @param <T> {@link Object Object}
 */
public interface IGuildVariable<T> {

    T getVar();

    String getName();

    String toString();
}
