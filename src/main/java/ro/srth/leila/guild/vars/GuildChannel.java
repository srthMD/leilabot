package ro.srth.leila.guild.vars;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.Channel;


public class GuildChannel extends AbstractGuildVariableImpl<Long> {

    public GuildChannel(String var, String name, Guild guild) {
        super(Long.valueOf(var), name, guild);
    }

    public GuildChannel(long var, String name, Guild guild) {
        super(var, name, guild);
    }

    public GuildChannel(Channel var, String name, Guild guild) {
        super(var.getIdLong(), name, guild);
    }

    @Override
    public String toString() {
        return String.valueOf(var);
    }


    public Channel toChannel(){
        return guild.getChannelById(Channel.class, var);
    }


    public boolean equals(Channel other) {
        return other.getIdLong() == var;
    }
}
