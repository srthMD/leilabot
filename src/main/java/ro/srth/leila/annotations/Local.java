package ro.srth.leila.annotations;

import java.lang.annotation.*;

/**
 * Marks a variable as a local guild variable.
 * <p>
 * Variables must still be initalized for default values.
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Local {
}
