package ro.srth.leila.guild.vars;

public class GuildObject extends GuildVariable<Object>{
    public GuildObject(Object var, String name) {
        super(var, name);
    }

    @Override
    public String toString() {
        return var.toString();
    }
}
