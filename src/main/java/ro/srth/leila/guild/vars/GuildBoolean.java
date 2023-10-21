package ro.srth.leila.guild.vars;

/**
 * Extension of {@link GuildVariable} to be able to operate specificly with booleans.
 */
public class GuildBoolean extends GuildVariable<Boolean> {
    public GuildBoolean(boolean var, String name) {
        super(var, name);
    }

    @Override
    public String toString() {
        return var.toString();
    }

    public boolean equals(boolean other){
        return var.equals(other);
    }
}
