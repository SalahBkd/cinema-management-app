package me.boukadi.gestioncinemas.service;

import lombok.NoArgsConstructor;
import me.boukadi.gestioncinemas.dao.*;
import me.boukadi.gestioncinemas.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Service
@Transactional
public class CinemaInitService implements ICinemaInitService {
    @Autowired
    private VilleRepository villeRepository;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private SeanceRepository seanceRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private ProjectionRepository projectionRepository;
    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private TicketRepository ticketRepository;


    @Override
    public void initVilles() {
        Stream.of("Casabalnca", "Marrakech", "Rabat", "Khenifra").forEach(villeName -> {
            Ville ville = new Ville();
            ville.setName(villeName);
            villeRepository.save(ville);
        });
    }

    @Override
    public void initCinemas() {
        villeRepository.findAll().forEach(ville -> {
            Stream.of("Megaram", "IMAX", "Founoun", "Shahrazad", "Daouliz").forEach(cinemaName -> {
                Cinema cinema = new Cinema();
                cinema.setName(cinemaName);
                cinema.setVille(ville);
                cinema.setNumOfrooms(3+(int) (Math.random()*7));
                cinemaRepository.save(cinema);
            });
        });

    }

    @Override
    public void initSalles() {
        cinemaRepository.findAll().forEach(cinema -> {
            for (int i = 0; i < cinema.getNumOfrooms(); i++) {
                Salle salle = new Salle();
                salle.setName("Salle " + i + 1);
                salle.setCinema(cinema);
                salle.setNombrePlace(15 + (int)(Math.random() * 20));
                salleRepository.save(salle);
            }
        });
    }

    @Override
    public void initPlaces() {
        salleRepository.findAll().forEach(salle -> {
            for (int i = 0; i < salle.getNombrePlace(); i++) {
                Place place = new Place();
                place.setNumero(i+1);
                place.setSalle(salle);
                placeRepository.save(place);
            }
        });
    }

    @Override
    public void initSeances() {
        Stream.of("12:00", "15:00", "17:00", "19:00", "21:00").forEach(seanceStartTime -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            Seance seance = new Seance();
            try {
                seance.setHeureDebut(dateFormat.parse(seanceStartTime));
                seanceRepository.save(seance);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void initCategories() {
        Stream.of("Histoire", "Action", "Drama", "War", "Fiction").forEach(categorieName -> {
            Categorie categorie = new Categorie();
            categorie.setName(categorieName);
            categorieRepository.save(categorie);
        });
    }

    @Override
    public void initFilms() {
        double[] durees = new double[] {1, 1.5, 2, 2.5, 3};
        List<Categorie> categorieList = categorieRepository.findAll();

        Stream.of("Pulp Fiction", "Green Book", "The Shawshank Redemption", "Se7en", "Forrest Gump").forEach(filmName -> {
            Film film = new Film();
            film.setTitre(filmName);
            film.setDuree(durees[new Random().nextInt(durees.length)]);
            film.setPhoto(filmName.replaceAll(" ", ""));
            film.setCategorie(categorieList.get(new Random().nextInt(categorieList.size())));
            filmRepository.save(film);
        });
    }

    @Override
    public void initProjections() {
        double[] prix = new double[] {30, 50, 60, 75, 90, 100};
        List<Film> films = filmRepository.findAll();
        villeRepository.findAll().forEach(ville -> {
            ville.getCinemas().forEach(cinema -> {
                cinema.getSalles().forEach(salle -> {
                    int index = new Random().nextInt(films.size());
                    System.out.println("HERE==="+ index);
                    Film film = films.get(index);

                    seanceRepository.findAll().forEach(seance -> {
                        Projection projection = new Projection();
                        projection.setDateProjection(new Date());
                        projection.setFilm(film);
                        projection.setPrix(prix[new Random().nextInt(prix.length)]);
                        projection.setSalle(salle);
                        projection.setSeance(seance);
                        projectionRepository.save(projection);
                    });
                });
            });
        });
    }

    @Override
    public void initTickets() {
        projectionRepository.findAll().forEach(projection -> {
            projection.getSalle().getPlaces().forEach(place -> {
                Ticket ticket = new Ticket();
                ticket.setPlace(place);
                ticket.setPrix(projection.getPrix());
                ticket.setProjection(projection);
                ticket.setReserve(false);
                ticketRepository.save(ticket);
            });
        });
    }
}
