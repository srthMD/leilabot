package ro.srth.leila.annotations;

import ro.srth.leila.guild.vars.GuildVariable;

import java.lang.annotation.*;

/**
 * Marks a variable as a local guild variable.
 * <p>
 * Variables must still be initalized for default values.
 */

// idk it's just really convenient

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Local {
    Class<? extends GuildVariable<?>> clazz();
}
