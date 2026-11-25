package com.nguyenvudev.application.service.ticket;

import com.nguyenvudev.application.model.TicketDetailDTO;
import com.nguyenvudev.application.model.cache.TicketDetailCache;
import com.nguyenvudev.domain.model.entity.TicketDetail;

public interface TicketDetailApplicationService {

    TicketDetailDTO getTicketDetailById(Long ticketId, Long version);

    boolean orderTicketByUser(Long ticketId);
}
