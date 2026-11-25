package com.nguyenvudev.application.service.ticket.impl;

import com.nguyenvudev.application.mapping.TicketDetailMapper;
import com.nguyenvudev.application.model.TicketDetailDTO;
import com.nguyenvudev.application.model.cache.TicketDetailCache;
import com.nguyenvudev.application.service.ticket.cache.TicketDetailCacheService;
import com.nguyenvudev.application.service.ticket.cache.TicketDetailCacheServiceRefactor;
import com.nguyenvudev.domain.model.entity.TicketDetail;
import com.nguyenvudev.application.service.ticket.TicketDetailApplicationService;
import com.nguyenvudev.domain.service.TicketDetailDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TicketDetailApplicationServiceImpl implements TicketDetailApplicationService {
    private final TicketDetailCacheService ticketDetailCacheService;
    private final TicketDetailCacheServiceRefactor ticketDetailCacheServiceRefactor;

    public TicketDetailApplicationServiceImpl(TicketDetailCacheService ticketDetailCacheService,TicketDetailCacheServiceRefactor ticketDetailCacheServiceRefactor) {
        this.ticketDetailCacheService = ticketDetailCacheService;
        this.ticketDetailCacheServiceRefactor = ticketDetailCacheServiceRefactor;
    }

    @Override
    public TicketDetailDTO getTicketDetailById(Long ticketId, Long version) {
        log.info("Implement Application: {}, {}: ",ticketId,version);
        TicketDetailCache ticketDetailCache =this.ticketDetailCacheServiceRefactor.getTicketDetail(ticketId,version);
        // mapper
        TicketDetailDTO ticketDetailDTO =  TicketDetailMapper.mapperToTicketDetailDTO(ticketDetailCache.getTicketDetail());
        ticketDetailDTO.setVersion(ticketDetailCache.getVersion());
        return ticketDetailDTO;
    }

    @Override
    public boolean orderTicketByUser(Long ticketId) {
        return this.ticketDetailCacheService.orderTicketByUser(ticketId);
    }
    }
