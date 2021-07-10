import { Component } from '@angular/core';
import {CinemaService} from "./services/cinema.service";
import {AuthService} from "./services/auth.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'frontend-cinema';

  constructor(public authService: AuthService) { }

  isAuth() {
    return this.authService.isAuthenticated();
  }
}
