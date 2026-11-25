package com.nguyenvudev.application.service.order.cache;

import com.nguyenvudev.application.model.cache.TicketDetailCache;
import com.nguyenvudev.application.service.ticket.cache.TicketDetailCacheService;
import com.nguyenvudev.application.service.ticket.cache.TicketDetailCacheServiceRefactor;
import com.nguyenvudev.domain.service.TicketDetailDomainService;
import com.nguyenvudev.infrastructure.cache.redis.RedisInfraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
public class StockOrderCacheService {

    private RedisInfraService redisInfraService;
//    private TicketDetailDomainService ticketDetailDomainService;
    private TicketDetailCacheServiceRefactor ticketDetailCacheServiceRefactor;

    public StockOrderCacheService(TicketDetailCacheServiceRefactor ticketDetailCacheServiceRefactor, RedisInfraService redisInfraService) {
        this.ticketDetailCacheServiceRefactor = ticketDetailCacheServiceRefactor;
        this.redisInfraService = redisInfraService;
    }

    public int decreaseStockCache(Long ticketId, Integer quantity){
        // 1. get stock available
        String keyStockItem = getKeyStockItemCache(ticketId);
        int stockAvailable = this.redisInfraService.getInt(keyStockItem);
        log.info("stockAvailable keyStockItem: {} {} {}",keyStockItem, stockAvailable, String.valueOf(stockAvailable));

        // 2.Decrease stock
        if (stockAvailable>=quantity){
            this.redisInfraService.setInt(keyStockItem,stockAvailable-quantity);
            log.info("stockAvailable racing...: {}",stockAvailable-quantity);
            return 1;
        }
        return 0;
    }

    public int decreaseStockCacheByLUA(Long ticketId, Integer quantity){

        String keyStockItem = getKeyStockItemCache(ticketId);

        DefaultRedisScript<Long> defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setLocation(new ClassPathResource("decreaseStock.lua"));
        defaultRedisScript.setResultType(Long.class);
        Long result =(Long) redisInfraService.getRedisTemplate().execute(defaultRedisScript, Collections.singletonList(keyStockItem),quantity);
        log.info("Lua result: {}",result.intValue());
        return  result.intValue();
    }

    private String getKeyStockItemCache(Long ticketId){
        return "TICKET:" + ticketId + ":STOCK";
    }
    public void addStockAvailableToCache(Long ticketId){
        if (ticketId == null){
            return;
        }
        // get from mysql
        TicketDetailCache ticketDetailCache = this.ticketDetailCacheServiceRefactor.getTicketDetailDatabase(ticketId);
        if (ticketDetailCache == null){
            return;
        }
        // set to cache
        String keyStockItem = this.getKeyStockItemCache(ticketId);
        this.redisInfraService.setInt(keyStockItem,ticketDetailCache.getTicketDetail().getStockAvailable());
        log.info("Set database -> Cache : {} {}", keyStockItem, ticketDetailCache.getTicketDetail().getStockAvailable());
    }
}
