package me.boukadi.gestioncinemas.entity;

import org.springframework.data.rest.core.config.Projection;

@Projection(name="ticketProjection", types = Ticket.class)
public interface ProjectionTicket {
    Long getId();
    String getNomClient();
    double getPrix();
    Integer getCodePayement();
    boolean getReserve();
    Place getPlace();
}
