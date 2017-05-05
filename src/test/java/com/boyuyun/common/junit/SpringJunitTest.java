package com.boyuyun.common.junit;

import java.util.Iterator;
import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试共公类
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-context.xml")
public class SpringJunitTest {

	/**
	 * 遍历打印list，将list每个元素输出到控制台
	 * @param list
	 * @author Stone
	 * @date 2017-2-8 上午11:52:35
	 */
	public void printList(List<?> list){
		if(list ==null){
			System.out.println(list+"为null");
			return;
		}
		if(list.isEmpty()){
			System.out.println(list+"为空");
			return;
		}
		Iterator<?> iterator = list.iterator();
		for(;iterator.hasNext();){
			Object next = iterator.next();
			System.out.println(next.toString());
		}
	}
}
