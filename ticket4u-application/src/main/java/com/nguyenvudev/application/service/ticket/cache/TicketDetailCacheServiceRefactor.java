package com.nguyenvudev.application.service.ticket.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.nguyenvudev.application.model.cache.TicketDetailCache;
import com.nguyenvudev.domain.model.entity.TicketDetail;
import com.nguyenvudev.domain.service.TicketDetailDomainService;
import com.nguyenvudev.infrastructure.cache.redis.RedisInfraService;
import com.nguyenvudev.infrastructure.distributed.redisson.RedisDistributedLocker;
import com.nguyenvudev.infrastructure.distributed.redisson.RedisDistributedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class TicketDetailCacheServiceRefactor {

    private RedisInfraService redisInfraService;
    private RedisDistributedService redisDistributedService;
    private TicketDetailDomainService ticketDetailDomainService;
    private final static Cache<Long, TicketDetailCache> ticketDetailLocalCache = CacheBuilder.newBuilder()
            .initialCapacity(10)
            .concurrencyLevel(12)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build();

    public TicketDetailCacheServiceRefactor(RedisInfraService redisInfraService, RedisDistributedService redisDistributedService, TicketDetailDomainService ticketDetailDomainService) {
        this.redisInfraService = redisInfraService;
        this.redisDistributedService = redisDistributedService;
        this.ticketDetailDomainService = ticketDetailDomainService;
    }

    public TicketDetailCache getTicketDetail(Long ticketId, Long version) {
        TicketDetailCache ticketDetailCache = getTicketDetailLocalCache(ticketId);
        if (ticketDetailCache != null) {

            if (version == null) {
                log.info("01. GET TICKET FROM LOCAL CACHE: versionUser: {} versionLocal: {}", version, ticketDetailCache.getVersion());
                return ticketDetailCache;
            }

            if (version.equals(ticketDetailCache.getVersion())) {
                log.info("02. GET TICKET FROM LOCAL CACHE: versionUser: {} versionLocal: {}", version, ticketDetailCache.getVersion());
                return ticketDetailCache;
            }

            if (version <ticketDetailCache.getVersion()) {
                log.info("03. GET TICKET FROM LOCAL CACHE: versionUser: {} versionLocal: {}", version, ticketDetailCache.getVersion());
                return ticketDetailCache;
            }

            if (version >ticketDetailCache.getVersion()) {
                log.info("03. GET TICKET FROM LOCAL CACHE: versionUser: {} versionLocal: {}", version, ticketDetailCache.getVersion());
                return getTicketDetailFromDistributedCache(ticketId, version);
            }

            return ticketDetailCache;
        }
        // mapping
        return getTicketDetailFromDistributedCache(ticketId, version);
    }

    public TicketDetailCache getTicketDetailDatabase(Long ticketId) {
        RedisDistributedLocker redisDistributedLocker = this.redisDistributedService.getDistributedLock(genEventItemKey(ticketId));
        try {
            // tao lock
            boolean isLock = redisDistributedLocker.tryLock(1, 5, TimeUnit.SECONDS);
            if (!isLock) {
                return null;
            }
            // get cache
            TicketDetailCache ticketDetailCache = redisInfraService.getObject(genEventItemKey(ticketId), TicketDetailCache.class);
            if (ticketDetailCache != null) {
                return ticketDetailCache;
            }
            TicketDetail ticketDetail = this.ticketDetailDomainService.getTicketDetailById(ticketId);
            ticketDetailCache = new TicketDetailCache().withClone(ticketDetail).withVersion(System.currentTimeMillis());

            // set data to distributed cache
            redisInfraService.setObject(genEventItemKey(ticketId), ticketDetailCache);
            return ticketDetailCache;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            redisDistributedLocker.unlock();
        }
    }

    public TicketDetailCache getTicketDetailFromDistributedCache(Long ticketId, Long version) {
        TicketDetailCache ticketDetailCache = redisInfraService.getObject(genEventItemKey(ticketId), TicketDetailCache.class);
        if (ticketDetailCache == null) {
            log.info("GET TICKET FROM DISTRIBUTED LOCK");
            ticketDetailCache = this.getTicketDetailDatabase(ticketId);
        }
        ticketDetailLocalCache.put(ticketId, ticketDetailCache);
        log.info("GET TICKET FROM DISTRIBUTED CACHE");
        return ticketDetailCache;
    }

    private TicketDetailCache getTicketDetailLocalCache(Long id) {
        try {
            return this.ticketDetailLocalCache.getIfPresent(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String genEventItemKey(Long itemId) {
        return "PRO_TICKET:ITEM:" + itemId;
    }
}
