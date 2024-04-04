import { NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { AuthService } from '../services/auth-service/auth-service.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [NgIf, RouterLink],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  
  permissions: string[] = [];
  isLoggedIn: boolean = false;

  constructor(private auth: AuthService){
    this.auth.isLoggedIn.subscribe( value => {
      this.isLoggedIn = value;
      this.getPermissions();
    });
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

  getPermissions():void{
    this.permissions = JSON.parse(localStorage.getItem("permissions")!);
  }
}
