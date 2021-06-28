package me.boukadi.gestioncinemas.entity;

import org.springframework.data.rest.core.config.Projection;

import java.util.Collection;
import java.util.Date;

@Projection(name = "p1", types = {me.boukadi.gestioncinemas.entity.Projection.class})
public interface ProjectionProj {
    Long getId();
    double getPrix();
    Date getDateProjection();
    Salle getSalle();
    Film getFilm();
    Seance getSeance();
    Collection<Ticket> getTickets();
}
