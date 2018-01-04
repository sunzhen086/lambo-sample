package com.soecode.lyf.scheduler;

import com.soecode.lyf.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TestScheduler {

    @Autowired
    private BookService bookService;

    @Scheduled(cron="0/5 * * * * ?")//每5秒钟执行一次
    public void testCron(){
        System.out.println(bookService.getList());
    }
}
