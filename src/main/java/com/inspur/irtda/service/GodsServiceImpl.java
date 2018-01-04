package com.inspur.irtda.service;

import com.inspur.irtda.dao.GodsDao;
import com.inspur.lambo.framework.aspect.annotation.DataSource;
import com.inspur.lambo.framework.aspect.annotation.LogAround;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GodsServiceImpl implements GodsService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

	@DataSource("test2")
	@Transactional
	public List<Map<String, Object>> getDayGoodShot(Map<String, String> paramMap) {
		godsDao.getDayGoodShot(paramMap);
		List<Map<String, Object>> goodShot = godsDao.getGoodShot();
		godsDao.dropGoodShotView();
		return goodShot;
	}

	@LogAround("getMonthGoodShot")
	@DataSource("test2")
	@Transactional
	public List<Map<String, Object>> getMonthGoodShot(Map<String, String> paramMap) {
		godsDao.getMonthGoodShot(paramMap);
		if(log.isInfoEnabled())
			log.info("创建视图完成");
		List<Map<String, Object>> goodShot = godsDao.getGoodShot();
		godsDao.dropGoodShotView();
		return goodShot;
	}

	@DataSource("test2")
	@Transactional
	public List<Map<String, Object>> getYearGoodShot(Map<String, String> paramMap) {
		godsDao.getYearGoodShot(paramMap);
		List<Map<String, Object>> goodShot = godsDao.getGoodShot();
		godsDao.dropGoodShotView();
		return goodShot;
	}

	@DataSource("test2")
	@Transactional
	public int updateGoodShot(List<Map<String, Object>> list) {
		int i = 0;
		if (list != null && list.size() > 0){
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("PERIOD", (String) list.get(0).get("PERIOD"));
			paramMap.put("TYPE", (String) list.get(0).get("TYPE"));
			godsDao.deleteGoodShot(paramMap);
			i = godsDao.insertGoodShot(list);
		}
		return i;
	}

	// 注入Service依赖
	@Autowired
	private GodsDao godsDao;
}
