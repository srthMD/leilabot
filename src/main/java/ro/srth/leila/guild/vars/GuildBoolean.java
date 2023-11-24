package ro.srth.leila.guild.vars;

import net.dv8tion.jda.api.entities.Guild;

/**
 * Extension of {@link AbstractGuildVariableImpl AbstractGuildVariableImpl} to be able to operate specificly with booleans.
 */

public class GuildBoolean extends AbstractGuildVariableImpl<Boolean> {

    public GuildBoolean(String var, String name, Guild guild) {
        super(Boolean.parseBoolean(var), name, guild);
    }

    public GuildBoolean(boolean var, String name, Guild guild) {
        super(var, name, guild);
    }

    @Override
    public String toString() {
        return var.toString();
    }

    public boolean equals(boolean other){
        return var.equals(other);
    }
}
