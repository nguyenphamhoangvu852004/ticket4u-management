package com.nguyenvudev.domain.service.impl;

import com.nguyenvudev.domain.repository.HiDomainRepository;
import com.nguyenvudev.domain.service.HiDomainService;
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
