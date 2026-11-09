package com.nguyenvudev.controller.resource;

import com.nguyenvudev.application.service.ticket.TicketDetailApplicationService;
import com.nguyenvudev.domain.model.entity.TicketDetail;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(("/tickets"))
public class TicketDetailController {

    private final TicketDetailApplicationService ticketDetailApplicationService;


    public TicketDetailController(TicketDetailApplicationService ticketDetailApplicationService) {
        this.ticketDetailApplicationService = ticketDetailApplicationService;
    }

    @GetMapping("/{ticketId}")
    public TicketDetail getTicketDetail(@PathVariable("ticketId") Long ticketId){
        return this.ticketDetailApplicationService.getTicketDetailById(ticketId);
    }
}
