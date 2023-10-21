package ro.srth.leila.guild;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import ro.srth.leila.annotations.Local;
import ro.srth.leila.guild.vars.GuildObject;
import ro.srth.leila.guild.vars.GuildVariable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Links discord guilds to guild-specific variables used by leilabot.
 * @see GuildVariable
 */
public class GuildConfiguration {
   public static final GuildConfiguration NULLCFG = new GuildConfiguration(0);

    private Map<String, GuildVariable<?>> vars;

    private long guildId;

    GuildConfiguration(long id){
        vars = new HashMap<>();
        guildId = id;
    }

    GuildConfiguration(long id, List<GuildVariable<?>> vars){
        vars = List.copyOf(vars);
        guildId = id;
    }

    GuildConfiguration(GuildConfiguration gc, long id){
        vars = gc.getVars();
        guildId = id;
    }

    public static GuildConfiguration getDefaultGuildConfiguration(){
        Set<Field> defaults = new Reflections("ro.srth", Scanners.FieldsAnnotated).getFieldsAnnotatedWith(Local.class);


        GuildConfiguration guildConfiguration = new GuildConfiguration(0);
        defaults.forEach((field -> {
            try {
                var gv = new GuildObject(field.get(null), field.getName());
                guildConfiguration.vars.put(field.getName(), gv);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }));
        return guildConfiguration;
    }

    public Map<String, GuildVariable<?>> getVars() {return vars;}

    public long getGuildId() {return guildId;}

    public void setGuildid(long id) {this.guildId = id;}
}
