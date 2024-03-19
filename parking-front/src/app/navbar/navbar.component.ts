import { NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { AuthService } from '../services/auth-service/auth-service.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [NgIf],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {

  constructor(private auth: AuthService){

  }

  isLogged(): boolean{
    if(localStorage.getItem("token") == null || localStorage.getItem("token") == "")
      return false;
    else
      return true;
  }

  logout(){
    this.auth.logoutUser();
  }
}
