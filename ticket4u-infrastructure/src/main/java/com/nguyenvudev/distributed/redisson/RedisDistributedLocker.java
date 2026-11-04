package com.nguyenvudev.distributed.redisson;

import java.util.concurrent.TimeUnit;

public interface RedisDistributedLocker {

    boolean tryLock(long waitTime, long leaseTime, TimeUnit timeUnit) throws InterruptedException;

    void lock(long leasetime, TimeUnit timeUnit);

    void unlock();

    boolean isLocked();

    boolean isHeldByThread(long threadIs);

    boolean isHeldByCurrentThread();

}
