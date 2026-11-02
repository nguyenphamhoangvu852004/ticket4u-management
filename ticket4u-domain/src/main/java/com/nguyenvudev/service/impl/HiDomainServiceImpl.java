package com.nguyenvudev.service.impl;

import com.nguyenvudev.repository.HiDomainRepository;
import com.nguyenvudev.service.HiDomainService;
import org.springframework.stereotype.Service;

@Service
public class HiDomainServiceImpl implements HiDomainService {
    private final HiDomainRepository hiDomainRepository;

    public HiDomainServiceImpl(HiDomainRepository hiDomainRepository) {
        this.hiDomainRepository = hiDomainRepository;
    }

    @Override
    public String sayHi(String who) {
        return this.hiDomainRepository.sayHi(who);
    }
}
