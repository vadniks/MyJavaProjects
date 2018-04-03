import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Vad Nik.
 * @version dated Apr 1, 2018.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface NotUsed {
    double sinceVersion() default 1.0;
    String cause() default "useless";
    boolean forFuture() default true;
    String additionalInfo() default "";
    boolean example() default true;
}