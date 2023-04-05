package com.blipay.merchant.demo.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MerchantJob {

    @Scheduled(cron = "0 1 0 * * ?")
    public void exe() {
        log.info("sssss");
    }
}
