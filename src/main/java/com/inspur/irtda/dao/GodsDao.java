package com.inspur.irtda.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface GodsDao {

	/**
	 * 生成昨日榜单视图
	 *
	 * @param paramMap 查询条件
	 */
	void getDayGoodShot(@Param("paramMap") Map<String, String> paramMap);

	/**
	 * 生成上月榜单视图
	 *
	 * @param paramMap 查询条件
	 */
	void getMonthGoodShot(@Param("paramMap") Map<String, String> paramMap);

	/**
	 * 生成今年榜单视图
	 *
	 * @param paramMap 查询条件
	 */
	void getYearGoodShot(@Param("paramMap") Map<String, String> paramMap);

	/**
	 * 删除视图
	 */
	void dropGoodShotView();

	/**
	 * 查询榜单
	 *
	 * @return
	 */
	List<Map<String, Object>> getGoodShot();

	/**
	 * 删除榜单
	 *
	 * @param paramMap 时间、商品类型
	 *
	 * @return
	 */
	int deleteGoodShot(@Param("paramMap") Map<String, String> paramMap);

	/**
	 * 插入榜单
	 *
	 * @param list 榜单内容
	 *
	 * @return
	 */
	int insertGoodShot(@Param("list") List<Map<String, Object>> list);

}
