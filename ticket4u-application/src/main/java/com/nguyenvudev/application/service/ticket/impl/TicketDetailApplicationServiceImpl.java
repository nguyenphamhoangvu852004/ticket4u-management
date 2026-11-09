package com.nguyenvudev.application.service.ticket.impl;

import com.nguyenvudev.application.service.ticket.cache.TicketDetailCacheService;
import com.nguyenvudev.domain.model.entity.TicketDetail;
import com.nguyenvudev.application.service.ticket.TicketDetailApplicationService;
import com.nguyenvudev.domain.service.TicketDetailDomainService;
import org.springframework.stereotype.Service;

@Service
public class TicketDetailApplicationServiceImpl implements TicketDetailApplicationService {
    private final TicketDetailCacheService ticketDetailCacheService;

    public TicketDetailApplicationServiceImpl(TicketDetailCacheService ticketDetailCacheService) {
        this.ticketDetailCacheService = ticketDetailCacheService;
    }

    @Override
    public TicketDetail getTicketDetailById(Long ticketId) {
        return this.ticketDetailCacheService.getTicketDefaultCacheVip(ticketId,System.currentTimeMillis());
    }
}
