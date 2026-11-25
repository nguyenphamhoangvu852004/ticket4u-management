package com.nguyenvudev.application.service.order.impl;

import com.nguyenvudev.application.service.order.TicketOrderApplicationService;
import com.nguyenvudev.application.service.order.cache.StockOrderCacheService;
import com.nguyenvudev.domain.service.TicketOrderDomainService;
import jakarta.persistence.LockTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.PessimisticLockException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TicketOrderApplicationServiceImpl implements TicketOrderApplicationService {

    private TicketOrderDomainService ticketOrderDomainService;
    private StockOrderCacheService stockOrderCacheService;

    public TicketOrderApplicationServiceImpl(TicketOrderDomainService ticketOrderDomainService, StockOrderCacheService stockOrderCacheService) {
        this.ticketOrderDomainService = ticketOrderDomainService;
        this.stockOrderCacheService = stockOrderCacheService;
    }

    @Override
    public boolean decreaseStockLevel1(Long ticketId, int quantity) {
        try {
            int stockAvailable = this.ticketOrderDomainService.getStockAvailable(ticketId);
            if (stockAvailable<quantity){
                log.info("Case: stockAvailable < quantity | {}, {}", stockAvailable,quantity);
                return false;
            }
            return this.ticketOrderDomainService.decreaseStockLevel1(ticketId,quantity);
        } catch (PessimisticLockException e) {
            log.warn("Pessmimistic Locking failed for ticket {}", ticketId);
            return false;
        }catch (LockTimeoutException e){
            log.warn("Lock timeout exceed for ticket {}", ticketId);
            return false;
        } catch (Exception e) {
            log.warn("Unexpected error while decrease stock for ticketId = {}", ticketId);
            return false;
        }
    }

    @Override
    public boolean decreaseStockLevel3CAS(Long ticketId, int quantity) {
        try {
        /*    int stockAvailable = this.ticketOrderDomainService.getStockAvailable(ticketId);
            if (stockAvailable<quantity){
                log.info("Case: stockAvailable < quantity | {}, {}", stockAvailable,quantity);
                return false;
            }*/
//            int success =  this.stockOrderCacheService.decreaseStockCache(ticketId,quantity);
            int success =  this.stockOrderCacheService.decreaseStockCacheByLUA(ticketId,quantity);
            if (success == 0) {
                log.info("Case: decreaseStockCache failed: ticketId: {}",ticketId);
                return false;
            }
            return this.ticketOrderDomainService.decreaseStockLevel1(ticketId,quantity);
        } catch (PessimisticLockException e) {
            log.warn("Pessmimistic Locking failed for ticket {}", ticketId);
            return false;
        }catch (LockTimeoutException e){
            log.warn("Lock timeout exceed for ticket {}", ticketId);
            return false;
        } catch (Exception e) {
            log.warn("Unexpected error while decrease stock for ticketId = {}, {}", ticketId,e.getMessage());
            return false;
        }
    }

}
