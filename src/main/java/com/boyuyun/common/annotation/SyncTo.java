package com.boyuyun.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.boyuyun.common.datasync.SyncDataType;
import com.boyuyun.common.datasync.SyncOperateType;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SyncTo {
	public String [] system();//该方法需要同步的系统
	public SyncDataType dataType();//该方法操作的数据类型
	public SyncOperateType operateType();//该方法的操作类型
}
