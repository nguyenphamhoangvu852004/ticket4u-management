package com.nguyenvudev.infrastructure.persistence.repository;

import com.nguyenvudev.domain.model.entity.TicketDetail;
import com.nguyenvudev.domain.repository.TicketDetailRepository;
import com.nguyenvudev.infrastructure.persistence.mapper.TicketDetailJPAMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class TicketDetailRepositoryImpl implements TicketDetailRepository {

    private final TicketDetailJPAMapper ticketDetailJPAMapper;

    public TicketDetailRepositoryImpl(TicketDetailJPAMapper ticketDetailJPAMapper) {
        this.ticketDetailJPAMapper = ticketDetailJPAMapper;
    }

    @Override
    public Optional<TicketDetail> findById(Long id) {
        return Optional.of(this.ticketDetailJPAMapper.findById(id)).orElse(null);
    }
}
