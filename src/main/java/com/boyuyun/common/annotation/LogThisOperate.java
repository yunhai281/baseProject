package com.boyuyun.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.boyuyun.common.autolog.OperateType;
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogThisOperate {
	public String module();
	public OperateType operateType();
}
