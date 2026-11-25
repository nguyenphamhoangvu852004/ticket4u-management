package com.nguyenvudev.infrastructure.persistence.repository;

import com.nguyenvudev.domain.repository.TicketOrderRepository;
import com.nguyenvudev.infrastructure.persistence.mapper.TicketOrderJPAMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TicketOrderRepositoryImpl implements TicketOrderRepository {
    private TicketOrderJPAMapper ticketOrderJPAMapper;

    public TicketOrderRepositoryImpl(TicketOrderJPAMapper ticketOrderJPAMapper) {
        this.ticketOrderJPAMapper = ticketOrderJPAMapper;
    }

    @Override
    public boolean decreaseStockLevel1(Long ticketId, int quantity) {
        log.info("Run test: decreaseStockLevel1 with: {}, {}",ticketId,quantity);
        return this.ticketOrderJPAMapper.decreaseStockLevel1(ticketId,quantity) > 0;
    }

    @Override
    public boolean decreaseStockLevel2(Long ticketId, int quantity) {
        return false;
    }

    @Override
    public boolean decreaseStockLevel3CAS(Long ticketId, int oldStockAvailable, int quantity) {
        return false;
    }

    @Override
    public int getStockAvaiable(Long ticketId) {
        return this.ticketOrderJPAMapper.getStockAvailable(ticketId);
    }
}
