package ro.srth.leila.annotations;

import java.lang.annotation.*;

/**
 * Annotation to tell the bot that a command or listener is specific to one guild.
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface GuildSpecific {
    long guildIdLong();
}
