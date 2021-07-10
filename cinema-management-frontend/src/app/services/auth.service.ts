import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private username: string = '';
  private password: string = '';

  constructor(private http:HttpClient) { }

  login(username:string,password:string){
    const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(username + ':' + password) });
    this.username = username;
    this.password = password;
    return this.http.get("http://localhost:8080/",{headers,responseType: 'text' as 'json'});
  }

  isAuthenticated(): boolean {
    if(this.username && this.password) {
      return true;
    } else {
      return false;
    }
}
}
