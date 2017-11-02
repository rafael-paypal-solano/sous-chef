package com.souschef.json;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * <p>This annotation provides hints for JSON encoders; it must be placed in all public getters that
 * need special treatment from the encoders.</p>
 * @author rsolano
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface JSONPolicy {

	/**
	 * <p>When <code>true</code>, it tells the encoder to ignore the annotated attribute (getter).</p>
	 * @return
	 */
	boolean isExcluded() default true;
	
	/**
	 * <p>When <code>true</code>, it tells the encoder to prefix object definition with their corresponding class</p>
	 * @return
	 */
	boolean isEncodeClass() default false;
	
}
