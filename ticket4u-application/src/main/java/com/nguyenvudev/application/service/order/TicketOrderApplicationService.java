package com.nguyenvudev.application.service.order;

public interface TicketOrderApplicationService {
    boolean decreaseStockLevel1(Long ticketId,int quantity);
    boolean decreaseStockLevel3CAS(Long ticketId,int quantity);
}
