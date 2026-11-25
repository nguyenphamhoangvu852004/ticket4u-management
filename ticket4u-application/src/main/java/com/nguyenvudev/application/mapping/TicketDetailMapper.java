package com.nguyenvudev.application.mapping;

import com.nguyenvudev.application.model.TicketDetailDTO;
import com.nguyenvudev.domain.model.entity.TicketDetail;
import org.springframework.beans.BeanUtils;

public class TicketDetailMapper {
    public static TicketDetailDTO mapperToTicketDetailDTO(TicketDetail ticketDetail) {
        if (ticketDetail == null) return null;
        TicketDetailDTO ticketDetailDTO = new TicketDetailDTO();
        BeanUtils.copyProperties(ticketDetail, ticketDetailDTO);
        return ticketDetailDTO;
    }

}
