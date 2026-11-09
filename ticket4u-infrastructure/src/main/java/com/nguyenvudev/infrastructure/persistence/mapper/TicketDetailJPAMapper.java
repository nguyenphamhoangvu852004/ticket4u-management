package com.nguyenvudev.infrastructure.persistence.mapper;

import com.nguyenvudev.domain.model.entity.TicketDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketDetailJPAMapper extends JpaRepository<TicketDetail, Long> {

}
