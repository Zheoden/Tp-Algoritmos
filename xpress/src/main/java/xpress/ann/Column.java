package xpress.ann;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column
{
	int LAZY=1;
	int EAGER=2;
	
	String name() default "";
	int fetchType() default EAGER; 
}
