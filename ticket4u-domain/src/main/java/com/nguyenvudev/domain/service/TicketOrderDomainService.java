package com.nguyenvudev.domain.service;

public interface TicketOrderDomainService {
    boolean decreaseStockLevel1(Long ticketId, int quantity);
    boolean decreaseStockLevel2(Long ticketId, int quantity);
    boolean decreaseStockLevel3(Long ticketId, int oldStockAvailable, int quantity);
    int getStockAvailable(Long ticketId);
}
