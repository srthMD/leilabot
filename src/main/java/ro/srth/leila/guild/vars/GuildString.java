package ro.srth.leila.guild.vars;

import net.dv8tion.jda.api.entities.Guild;

public class GuildString extends GuildVariable<String>{
    public GuildString(String var, String name, Guild guild) {
        super(var, name, guild);
    }

    @Override
    public String toString() {
        return var;
    }
}
