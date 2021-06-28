package me.boukadi.gestioncinemas.controller;


import lombok.Data;
import me.boukadi.gestioncinemas.dao.FilmRepository;
import me.boukadi.gestioncinemas.dao.TicketRepository;
import me.boukadi.gestioncinemas.entity.Film;
import me.boukadi.gestioncinemas.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
public class CinemaRestController {
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping(path = "/filmImage/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable(name="id") Long id) throws Exception {
        Film film = filmRepository.findById(id).get();
        String imageName = film.getPhoto();
        File file = new File(System.getProperty("user.home") + "/cinema/images/" + imageName + ".jpg");
        Path path = Paths.get(file.toURI());
        return Files.readAllBytes(path);
    }

    @PostMapping("/payerTickets")
    @Transactional
    public List<Ticket> payerTickets(@RequestBody TicketForm ticketForm) {
        List<Ticket> paidTickets = new ArrayList<>();
        ticketForm.getTickets().forEach(ticketId -> {
            Ticket ticket = ticketRepository.findById(ticketId).get();
            ticket.setNomClient(ticketForm.getClientName());
            ticket.setCodePayement(ticketForm.getCodePayement());
            ticket.setReserve(true);
            ticketRepository.save(ticket);
            paidTickets.add(ticket);
        });
        return paidTickets;
    }
}

@Data
class TicketForm {
    private String clientName;
    private int codePayement;
    private List<Long> tickets = new ArrayList<>();
}
