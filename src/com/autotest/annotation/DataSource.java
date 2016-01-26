package com.autotest.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import com.autotest.enums.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.METHOD})
public @interface DataSource
{
  public abstract DriverType Drive();
  public abstract DataType minmax() default DataType.min;//0��������С��excel����������0�����excel������
  public abstract int count() default 1;
}
