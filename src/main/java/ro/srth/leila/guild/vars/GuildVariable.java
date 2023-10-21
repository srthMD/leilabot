package ro.srth.leila.guild.vars;

public abstract class GuildVariable<T> implements IGuildVariable<T>{
    final String name;
    T var;

    public GuildVariable(T var, String name) {
        this.name = name;
        this.var = var;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public T getVar() {
        return var;
    }

    public void setVar(T var) {
        this.var = var;
    }

    public abstract String toString();
}
