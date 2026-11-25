package com.nguyenvudev.controller.resource;

import com.nguyenvudev.application.service.order.TicketOrderApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/orders")
public class TicketOrderController {

    private TicketOrderApplicationService ticketOrderApplicationService;

    public TicketOrderController(TicketOrderApplicationService ticketOrderApplicationService) {
        this.ticketOrderApplicationService = ticketOrderApplicationService;
    }

    @GetMapping("/{ticketId}/{quantity}")
    public boolean placeOrderV1(@PathVariable("ticketId") Long ticketId, @PathVariable("quantity") int quantity){
        return this.ticketOrderApplicationService.decreaseStockLevel3CAS(ticketId, quantity);
    }

}
