package me.boukadi.gestioncinemas;

import me.boukadi.gestioncinemas.entity.Film;
import me.boukadi.gestioncinemas.entity.Salle;
import me.boukadi.gestioncinemas.entity.Ticket;
import me.boukadi.gestioncinemas.service.CinemaInitService;
import me.boukadi.gestioncinemas.service.ICinemaInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class GestioncinemasApplication implements CommandLineRunner {
    @Autowired
    private ICinemaInitService cinemaInitService;
    @Autowired
    private RepositoryRestConfiguration restConfiguration;

    public static void main(String[] args) {
        SpringApplication.run(GestioncinemasApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        restConfiguration.exposeIdsFor(Film.class, Salle.class, Ticket.class);
        cinemaInitService.initVilles();
        cinemaInitService.initCinemas();
        cinemaInitService.initSalles();
        cinemaInitService.initPlaces();
        cinemaInitService.initSeances();
        cinemaInitService.initCategories();
        cinemaInitService.initFilms();
        cinemaInitService.initProjections();
        cinemaInitService.initTickets();
    }
}
