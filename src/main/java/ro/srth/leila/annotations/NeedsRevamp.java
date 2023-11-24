package ro.srth.leila.annotations;


import java.lang.annotation.*;


/**
 * Annotation for old parts of the bot that need rewrites.
 */

@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR})
public @interface NeedsRevamp {
    String reason();
}
