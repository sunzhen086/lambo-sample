package com.inspur.irtda.service;

import java.util.List;
import java.util.Map;

/**
 * 业务接口：站在"使用者"角度设计接口 三个方面：方法定义粒度，参数，返回类型（return 类型/异常）
 */
public interface GodsService {
	List<Map<String, Object>> getDayGoodShot(Map<String, String> paramMap);
	List<Map<String, Object>> getMonthGoodShot(Map<String, String> paramMap);
	List<Map<String, Object>> getYearGoodShot(Map<String, String> paramMap);
	int updateGoodShot(List<Map<String, Object>> list);
}
