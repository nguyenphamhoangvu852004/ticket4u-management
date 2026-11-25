package com.nguyenvudev.application.conjob;

import com.nguyenvudev.application.service.order.cache.StockOrderCacheService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WarmupDataBeforeEvent {

    private StockOrderCacheService stockOrderCacheService;

    public WarmupDataBeforeEvent(StockOrderCacheService stockOrderCacheService) {
        this.stockOrderCacheService = stockOrderCacheService;
    }

    @PostConstruct
    public void addDatabaseToCache(){
        log.info("Load ticket once from db to cache...  warmup...| {}", System.currentTimeMillis());
        this.stockOrderCacheService.addStockAvailableToCache(4L);
    }

}
