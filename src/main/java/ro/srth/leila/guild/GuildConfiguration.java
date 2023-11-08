package ro.srth.leila.guild;

import net.dv8tion.jda.api.entities.Guild;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import ro.srth.leila.annotations.Local;
import ro.srth.leila.guild.vars.GuildVariable;
import ro.srth.leila.main.Bot;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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

    public static GuildConfiguration getDefaultGuildConfiguration(long guildId){
        Reflections ref = new Reflections("ro.srth", Scanners.FieldsAnnotated);

        Set<Field> defaults = ref.getFieldsAnnotatedWith(Local.class);

        GuildConfiguration guildConfiguration = new GuildConfiguration(0);
        defaults.forEach((field -> {
            try {
                Class<? extends GuildVariable<?>> clazz = field.getAnnotation(Local.class).clazz();

                Object instance = clazz.getDeclaredConstructor(field.getType(), String.class, Guild.class).newInstance(field.get(null), field.getName(), Bot.getSman().getGuildById(guildId));


                guildConfiguration.vars.put(field.getName(), (GuildVariable<?>) instance);
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }));
        return guildConfiguration;
    }

    public Map<String, GuildVariable<?>> getVars() {return vars;}

    public long getGuildId() {return guildId;}

    public void setGuildid(long id) {this.guildId = id;}
}
