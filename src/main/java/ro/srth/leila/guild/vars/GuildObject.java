package ro.srth.leila.guild.vars;

import net.dv8tion.jda.api.entities.Guild;

import java.util.Objects;

public class GuildObject extends AbstractGuildVariableImpl<Object> {
    public GuildObject(String var, String name, Guild guild) {
        super(var, name, guild);
    }

    public GuildObject(Object var, String name, Guild guild) {
        super(var, name, guild);
    }

    @Override
    public String toString() {
        return var.toString();
    }

    @Override
    public boolean equals(Object other) {
        return Objects.equals(var, other);
    }
}
