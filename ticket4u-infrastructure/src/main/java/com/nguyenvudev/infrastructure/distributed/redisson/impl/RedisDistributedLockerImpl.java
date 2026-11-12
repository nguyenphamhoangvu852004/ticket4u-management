package com.nguyenvudev.infrastructure.distributed.redisson.impl;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import com.nguyenvudev.infrastructure.distributed.redisson.RedisDistributedLocker;
import com.nguyenvudev.infrastructure.distributed.redisson.RedisDistributedService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RedisDistributedLockerImpl implements RedisDistributedService {

    private final RedissonClient redissonClient;

    public RedisDistributedLockerImpl(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public RedisDistributedLocker getDistributedLock(String lockKey) {

        RLock rLock = this.redissonClient.getLock(lockKey);

        return new RedisDistributedLocker() {
            @Override
            public boolean tryLock(long waitTime, long leaseTime, TimeUnit timeUnit) throws InterruptedException {
                boolean isLockSuccess = rLock.tryLock(waitTime, leaseTime, timeUnit);
                log.info("{} get lock result: {}", lockKey, isLockSuccess);
                return isLockSuccess;
            }

            @Override
            public void lock(long leasetime, TimeUnit timeUnit) {
                rLock.lock(leasetime, timeUnit);
            }

            @Override
            public void unlock() {
                if (isLocked() && isHeldByCurrentThread()) {
                    rLock.unlock();
                }
            }

            @Override
            public boolean isLocked() {
                return rLock.isLocked();
            }

            @Override
            public boolean isHeldByThread(long threadIs) {
                return rLock.isHeldByThread(threadIs);
            }

            @Override
            public boolean isHeldByCurrentThread() {
                return rLock.isHeldByCurrentThread();
            }

        };
    }

}
