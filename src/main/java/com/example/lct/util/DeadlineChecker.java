package com.example.lct.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableAsync
@Component
@Slf4j
public class DeadlineChecker {

    @Async
    @Scheduled(cron = "0 0 8,14,20 * * *") //В 8:00, 14:00 и 20:00 каждого дня
    public void scheduleFixedRateTaskAsync() {
        log.info("Fixed rate task async");
        //Thread.sleep(2000);
    }
}
