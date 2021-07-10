import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CinemaComponent } from './cinema/cinema.component';
import {LoginComponent} from "./login/login.component";

const routes: Routes = [
  {path:"login",component:LoginComponent},
  {path:"cinema",component : CinemaComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
})
export class AppRoutingModule { }
