import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CinemaService {

  public host = "http://localhost:8080";

  constructor(private http: HttpClient) { }

  public getVilles(): Observable<any> {
    let username='user1';
    let password='123';
    const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(username + ':' + password) });
    return this.http.get(this.host + "/villes", {headers});
  }

  getCinemas(ville: any): Observable<any> {
    let username='user1';
    let password='123';
    const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(username + ':' + password) });
    return this.http.get(ville._links.cinemas.href, {headers});
  }

  getSalles(cinema: any): Observable<any> {
    let username='user1';
    let password='123';
    const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(username + ':' + password) });
    return this.http.get(cinema._links.salles.href, {headers});
  }

  getProjections(salle: any): Observable<any> {
    let username='user1';
    let password='123';
    const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(username + ':' + password) });
    let url = salle._links.projections.href.replace("{?projection}", "");
    return this.http.get(url + "?projection=p1", {headers});
  }

  getTicketsOfPlaces(projection: any): Observable<any> {
    let username='user1';
    let password='123';
    const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(username + ':' + password) });
    let url = projection._links.tickets.href.replace("{?projection}", "");
    return this.http.get(url + "?projection=ticketProjection", {headers});
  }

  payetTickets(dataForm: any): Observable<any> {
    let username='user1';
    let password='123';
    const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(username + ':' + password) });
    return this.http.post(this.host + "/payerTickets", dataForm, {headers});
  }
}
