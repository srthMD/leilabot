package ro.srth.leila.guild.vars;

import net.dv8tion.jda.api.entities.Guild;

public abstract class AbstractGuildVariableImpl<T> implements GuildVariable<T> {

    final String name;
    T var;

    Guild guild;

    public AbstractGuildVariableImpl(T var, String name, Guild guild) {
        this.name = name;
        this.var = var;
        this.guild = guild;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public T getVar() {
        return var;
    }

    @Override
    public Guild getGuild(){
        return guild;
    }

    public void setVar(T var) {
        this.var = var;
    }

    public abstract String toString();


}
