import { NgFor } from '@angular/common';
import { Component } from '@angular/core';
import { User } from '../models/user';
import { UserService } from '../services/user-service/user.service';
import { ErrorMessage } from '../models/error-message';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [NgFor],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  
  vehiculos?: string[];
  errorMsg!: ErrorMessage;
  
  constructor(private userService: UserService){
  }

  ngOnInit():void{
    this.getVehiculos();
  }

  getVehiculos():void{
    this.userService.getVehiculos().subscribe({
      error: (e) => {
          this.errorMsg.msg = e.error.message
          this.errorMsg.status = 401
        },
        next: (data) =>{
          this.vehiculos = data;
        }
      });
  }
}
