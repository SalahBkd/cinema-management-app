package me.boukadi.gestioncinemas.dao;

import me.boukadi.gestioncinemas.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin("http://localhost:4200")
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
