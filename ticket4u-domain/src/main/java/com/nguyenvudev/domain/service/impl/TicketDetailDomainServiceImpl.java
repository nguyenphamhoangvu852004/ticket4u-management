package com.nguyenvudev.domain.service.impl;

import com.nguyenvudev.domain.model.entity.TicketDetail;
import com.nguyenvudev.domain.repository.TicketDetailRepository;
import com.nguyenvudev.domain.service.TicketDetailDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TicketDetailDomainServiceImpl implements TicketDetailDomainService {

    private TicketDetailRepository ticketDetailRepository;

    public TicketDetailDomainServiceImpl(TicketDetailRepository ticketDetailRepository) {
        this.ticketDetailRepository = ticketDetailRepository;
    }


    @Override
    public TicketDetail getTicketDetailById(Long ticketId) {
        log.info("Implement Domain : {}", ticketId);
        return this.ticketDetailRepository.findById(ticketId).orElse(null);
    }
}
