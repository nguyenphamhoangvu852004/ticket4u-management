package com.nguyenvudev.controller.resource;

import com.nguyenvudev.application.model.TicketDetailDTO;
import com.nguyenvudev.application.service.ticket.TicketDetailApplicationService;
import com.nguyenvudev.domain.model.entity.TicketDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(("/tickets"))
public class TicketDetailController {

    private final TicketDetailApplicationService ticketDetailApplicationService;


    public TicketDetailController(TicketDetailApplicationService ticketDetailApplicationService) {
        this.ticketDetailApplicationService = ticketDetailApplicationService;
    }

    @GetMapping("/{ticketId}")
    public TicketDetailDTO getTicketDetail(@PathVariable("ticketId") Long ticketId, @RequestParam(name = "version",required = false) Long version){
        return this.ticketDetailApplicationService.getTicketDetailById(ticketId,version);
    }

    @GetMapping("/{ticketId}/order")
    public boolean orderTicketByUser(@PathVariable("ticketId") Long ticketId){

        return this.ticketDetailApplicationService.orderTicketByUser(ticketId);
    }
}
