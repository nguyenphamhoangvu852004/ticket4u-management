package com.nguyenvudev.domain.repository;

import com.nguyenvudev.domain.model.entity.TicketDetail;

import java.util.Optional;

public interface TicketDetailRepository {
    Optional<TicketDetail> findById(Long id);
}
