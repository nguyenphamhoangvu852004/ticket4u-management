package com.nguyenvudev.domain.service.impl;

import com.nguyenvudev.domain.repository.TicketOrderRepository;
import com.nguyenvudev.domain.service.TicketOrderDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TicketOrderDomainServiceImpl implements TicketOrderDomainService {

    public TicketOrderDomainServiceImpl(TicketOrderRepository ticketOrderRepository) {
        this.ticketOrderRepository = ticketOrderRepository;
    }

    private TicketOrderRepository ticketOrderRepository;

    @Override
    public boolean decreaseStockLevel1(Long ticketId, int quantity) {
        return this.ticketOrderRepository.decreaseStockLevel1(ticketId,quantity);
    }

    @Override
    public boolean decreaseStockLevel2(Long ticketId, int quantity) {
        return false;
    }

    @Override
    public boolean decreaseStockLevel3(Long ticketId, int oldStockAvailable, int quantity) {
        return this.ticketOrderRepository.decreaseStockLevel3CAS(ticketId,oldStockAvailable,quantity);
    }

    @Override
    public int getStockAvailable(Long ticketId) {
        return this.ticketOrderRepository.getStockAvaiable(ticketId);
    }
}
