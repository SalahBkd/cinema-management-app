import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CinemaService {

  public host = "http://localhost:8080";

  constructor(private http: HttpClient) { }

  public getVilles(): Observable<any> {
    return this.http.get(this.host + "/villes");
  }

  getCinemas(ville: any): Observable<any> {
    return this.http.get(ville._links.cinemas.href);
  }

  getSalles(cinema: any): Observable<any> {
    return this.http.get(cinema._links.salles.href);
  }

  getProjections(salle: any): Observable<any> {
    let url = salle._links.projections.href.replace("{?projection}", "");
    return this.http.get(url + "?projection=p1");
  }

  getTicketsOfPlaces(projection: any): Observable<any> {
    let url = projection._links.tickets.href.replace("{?projection}", "");
    return this.http.get(url + "?projection=ticketProjection");
  }

  payetTickets(dataForm: any): Observable<any> {
    return this.http.post(this.host + "/payerTickets", dataForm);
  }
}
