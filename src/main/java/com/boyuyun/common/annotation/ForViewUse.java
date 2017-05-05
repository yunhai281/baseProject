package com.boyuyun.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识该属性并不在数据表中直接存储，而是通过关联表查询获得
 * 需要修改mapper中的查询语句获取
 * 暂时无程序处理该注解标识的属性
 * @author zhy
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ForViewUse {

}
