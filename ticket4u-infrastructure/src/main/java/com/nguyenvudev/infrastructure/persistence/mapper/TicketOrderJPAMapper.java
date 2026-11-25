package com.nguyenvudev.infrastructure.persistence.mapper;

import com.nguyenvudev.domain.model.entity.TicketDetail;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TicketOrderJPAMapper extends JpaRepository<TicketDetail, Long> {

    @Query("select t.stockAvailable from TicketDetail t where t.id = :ticketId")
    int getStockAvailable(@Param("ticketId")Long ticketId);

    @Modifying
    @Transactional
    @Query("update TicketDetail t " +
            "set t.stockAvailable = t.stockAvailable - :quantity,t.updatedAt = CURRENT_TIMESTAMP " +
            "where t.id = :ticketId and t.stockAvailable >= :quantity")
    int decreaseStockLevel1(@Param("ticketId") Long ticketId, @Param("quantity") int quantity);


    @Modifying
    @Transactional
    @Query("update TicketDetail t " +
            "set t.stockAvailable = t.stockAvailable - :quantity,t.updatedAt = CURRENT_TIMESTAMP " +
            "where t.id = :ticketId")
    int decreaseStockLevel0(@Param("ticketId") Long ticketId, @Param("quantity") int quantity);


    @Modifying
    @Transactional
    @Query("  update TicketDetail t"  +
            "    set t.stockAvailable = t.stockAvailable - :quantity, t.updatedAt = CURRENT_TIMESTAMP" +
            "    where t.id = :ticketId and t.stockAvailable = :oldStockAvailable")
    int decreaseStockLevel3CAS(@Param("ticketId") Long ticketId, @Param("oldStockAvailable") int oldStockAvaible,@Param("quantity") int quantity);
}
