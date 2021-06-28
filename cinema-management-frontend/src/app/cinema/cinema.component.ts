import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {CinemaService} from "../services/cinema.service";
import {tick} from "@angular/core/testing";

@Component({
  selector: 'app-cinema',
  templateUrl: './cinema.component.html',
  styleUrls: ['./cinema.component.css']
})
export class CinemaComponent implements OnInit {
  public villes: any;
  public cinemas: any;
  public currentVille: any;
  public currentCinema: any;
  public salles: any;
  public currentProjection: any;
  public selectedTickets: any;

  constructor(public cinemaService: CinemaService) { }

  ngOnInit(): void {
    this.cinemaService.getVilles().subscribe(data => {
      this.villes = data;
    }, error => {
      console.log(error);
    });
  }

  onGetCinemas(ville: any) {
    this.currentVille = ville;
    this.salles = undefined;
    this.cinemaService.getCinemas(ville).subscribe(data => {
      this.cinemas = data;
    }, error => {
      console.log(error);
    });
  }

  onGetSalles(cinema: any) {
    this.currentCinema = cinema;
    this.cinemaService.getSalles(cinema).subscribe(data => {
      this.salles = data;
      this.salles._embedded.salles.forEach((salle: any) => {
        this.cinemaService.getProjections(salle).subscribe(data => {
          salle.projections = data;
        }, error => {
          console.log(error);
        });
      })
    }, error => {
      console.log(error);
    });
  }

  onGetTicketsOfPlaces(projection: any) {
    this.currentProjection = projection;
    this.selectedTickets = [];
    this.cinemaService.getTicketsOfPlaces(projection).subscribe(data => {
      this.currentProjection.tickets = data;
    }, error => {
      console.log(error);
    });
  }

  onSelectTicket(ticket: any) {
    if(!ticket.selected) {
      ticket.selected = !ticket.selected;
      this.selectedTickets.push(ticket);
    } else {
      ticket.selected = false;
      this.selectedTickets.splice(this.selectedTickets.indexOf(ticket), 1);
    }
  }

  getTicketClass(ticket: any) {
    let str = "btn ticket ";
    if(ticket.reserve) {
      str += "btn-danger"
    } else if(ticket.selected) {
      str += "btn-warning";
    } else {
      str += "btn-success"
    }
    return str;
  }

  onPayTickets(dataForm: any) {
    let tickets: any = [];
    this.selectedTickets.forEach((ticket: any) => {
      tickets.push(ticket.id);
    });
    dataForm.tickets = tickets;
    this.cinemaService.payetTickets(dataForm).subscribe(data => {
      alert("Tickets Réservés avec succès");
      this.onGetTicketsOfPlaces(this.currentProjection);
    }, error => {
      console.log(error);
    });
  }
}
