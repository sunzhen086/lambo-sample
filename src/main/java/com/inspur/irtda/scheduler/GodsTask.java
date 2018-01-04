package com.inspur.irtda.scheduler;

import com.inspur.irtda.pub.DateTool;
import com.inspur.irtda.service.GodsService;
import com.inspur.lambo.framework.aspect.annotation.LogAround;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class GodsTask {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    // @Scheduled(cron="0 0/5 * * * ?")//每天凌晨执行一次
    public void dailyTask(){
        System.out.println("商品日定时器开始");
        String date = DateTool.getNextDay(DateTool.getToday(), -1);
        calGoodShotDaily(date);
        System.out.println("商品日定时器结束");
    }

    @Scheduled(fixedRate=500000, initialDelay=1000)//每月1号凌晨执行一次 fixedRate=5000,
    public void monthTask() {
        System.out.println("商品月定时器开始");
        String date = DateTool.getNextMonth(DateTool.getToday().substring(0, 6), -1);
        calGoodShotMonth(date);
        System.out.println("商品月定时器结束");
    }

    /**
     * 计算日榜单内容
     *
     * @param date 定时执行日期
     * @return
     */
    public int calGoodShotDaily(String date) {
        int i = 0;
        Map<String, String> paramMap = new HashMap<>();
        Set<String> godsTypeSet = godsTypes.keySet();
        for (String godsType : godsTypeSet) {
            paramMap.putAll(godsTypes.get(godsType));

            // 日榜
            paramMap.put("DATE", date); // 昨日
            List<Map<String, Object>> goodShots = godsService.getDayGoodShot(paramMap);
            for (Map<String, Object> goodShot : goodShots){
                goodShot.put("PERIOD" , "1");
                goodShot.put("TYPE" , godsType);
            }
            i += godsService.updateGoodShot(goodShots);
        }
        return i;
    }

    /**
     * 计算年、月榜单内容
     *
     * @param date 定时执行日期
     * @return
     */
    @LogAround("calGoodShotMonth")
    public int calGoodShotMonth(String date) {
        int i = 0;
        Map<String, String> paramMap = new HashMap<>();
        Set<String> godsTypeSet = godsTypes.keySet();
        for (String godsType : godsTypeSet) {
            if(log.isInfoEnabled())
                log.info("计算godsType=" + godsType);
            paramMap.putAll(godsTypes.get(godsType));
            // 月榜
            paramMap.put("DATE", date); // 上个月榜
            List<Map<String, Object>> goodShots = godsService.getMonthGoodShot(paramMap);
            if(log.isInfoEnabled())
                log.info("查询完成");
            for (Map<String, Object> goodShot : goodShots){
                goodShot.put("PERIOD" , "2");
                goodShot.put("TYPE" , godsType);
            }
            i += godsService.updateGoodShot(goodShots);
            if(log.isInfoEnabled())
                log.info("插入完成");

            // 年榜
            paramMap.put("DATE", date.substring(0, 4) + "01"); // 上个月所在年榜
            goodShots = godsService.getYearGoodShot(paramMap);
            for (Map<String, Object> goodShot : goodShots){
                goodShot.put("PERIOD" , "3");
                goodShot.put("TYPE" , godsType);
            }
            i += godsService.updateGoodShot(goodShots);
        }
        return i;
    }

    /**
     * 商品分类
     */
    private static Map<String, Map<String, String>> godsTypes = getGodsType();
    private static Map<String, Map<String, String>> getGodsType(){
        Map<String, Map<String, String>> map = new HashMap<>();
        Map<String, String> tobacco = new HashMap<>();
        tobacco.put("SORT_ID_START", "03000601");
        tobacco.put("SORT_ID_END", "03001199");
        map.put("01", tobacco); // 卷烟
        Map<String, String> wine = new HashMap<>();
        wine.put("SORT_ID_START", "03000101");
        wine.put("SORT_ID_END", "03000199");
        map.put("02", wine); // 酒类
        Map<String, String> food = new HashMap<>();
        food.put("SORT_ID_START", "03000001");
        food.put("SORT_ID_END", "03000099");
        map.put("03", food); // 食品饮料
        Map<String, String> house = new HashMap<>();
        house.put("SORT_ID_START", "03000301");
        house.put("SORT_ID_END", "03000399");
        map.put("04", house); // 家居用品
        Map<String, String> fresh = new HashMap<>();
        fresh.put("SORT_ID_START", "03000201");
        fresh.put("SORT_ID_END", "03000299");
        map.put("05", fresh); // 生鲜冷品
        Map<String, String> stationery = new HashMap<>();
        stationery.put("SORT_ID_START", "03000501");
        stationery.put("SORT_ID_END", "03000599");
        map.put("06", stationery); // 文具用品
        Map<String, String> baby = new HashMap<>();
        baby.put("SORT_ID_START", "03000401");
        baby.put("SORT_ID_END", "03000499");
        map.put("07", baby); // 母婴玩具
        return map;
    }

    @Autowired
    private GodsService godsService;
}
