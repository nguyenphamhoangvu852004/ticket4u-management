package com.nguyenvudev.infrastructure.persistence.repository;

import com.nguyenvudev.domain.repository.HiDomainRepository;
import org.springframework.stereotype.Service;

@Service
public class HiInfrasRepositoryImpl implements HiDomainRepository {

    @Override
    public String sayHi(String who) {
        return "Hi Infrastructure";
    }
}
