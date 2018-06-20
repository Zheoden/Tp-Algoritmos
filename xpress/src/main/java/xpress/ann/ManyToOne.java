package xpress.ann;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ManyToOne
{
	int LAZY=1;
	int EAGER=2;
	
	String columnName() default "";
	int fetchType() default EAGER; 
}
