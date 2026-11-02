package com.nguyenvudev.service.event.impl;

import com.nguyenvudev.service.HiDomainService;
import com.nguyenvudev.service.event.EventApplicationService;
import org.springframework.stereotype.Service;

@Service
public class EventApplicationServiceImpl implements EventApplicationService {
    // Call Domain Service
    private final HiDomainService hiDomainService;

    public EventApplicationServiceImpl(HiDomainService hiDomainService) {
        this.hiDomainService = hiDomainService;
    }

    @Override
    public String sayHi(String who) {
        return this.hiDomainService.sayHi(who);
    }
}
