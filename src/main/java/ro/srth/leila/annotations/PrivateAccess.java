package ro.srth.leila.annotations;


import java.lang.annotation.*;

/**
 * For commands/listeners that are restricted to srth because im power tripping like that
 * mostly troll commands or for muting because i cant unmute myself :(
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR})
public @interface PrivateAccess {
}
