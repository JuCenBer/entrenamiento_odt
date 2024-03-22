import { NgFor, NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { UserService } from '../services/user-service/user.service';
import { ErrorMessage } from '../models/error-message';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Parking } from '../models/parking';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [NgFor, NgIf, ReactiveFormsModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  
  parkingInfo: Parking = {
    parked: false,
    parkedCarLicensePlate:""
  };

  vehiculos?: string[];
  errorMsg: ErrorMessage={
    message: "",
    status:0
  };
  
  constructor(private userService: UserService){
  }

  ngOnInit():void{
    this.getVehiculos();
    this.isParked();
    //preguntar si la persona esta estacionada
  }

  public parkingForm = new FormGroup({
    vehicle: new FormControl('', [Validators.required,Validators.nullValidator]),
  });


  getVehiculos():void{
    this.userService.getVehiculos().subscribe({
      error: (e) => {
          this.errorMsg = e.error
          console.log(this.errorMsg.message +" "+ this.errorMsg.status)
        },
        next: (data) =>{
          this.vehiculos = data;
        }
      });
  }

  isParked():void{
    this.userService.isParked().subscribe({
      error: (e)=> {
        this.errorMsg = e.error;
      },
      next: (data) =>{
        this.parkingInfo = data;
      }
    })
  }

  startParking(): void{
    const parkingData = this.parkingForm.value.vehicle?.trim()
    const parkingDataJson = JSON.stringify(parkingData).trim()
    console.log(parkingDataJson)
    this.userService.startParking(parkingDataJson).subscribe({
      error:(e) =>{
        this.errorMsg = e.error;
      },
      complete: () =>{
        this.errorMsg.message = "";
        this.parkingInfo.parked = true;
        this.parkingInfo.parkedCarLicensePlate = this.parkingForm.value.vehicle!.trim();
        this.parkingForm.get("vehicles")?.disable()
        //bloquear el selector de vehiculos y cambiar de 
        //color el boton de iniciar estacionamiento
      }
     })
  }

  endParking():void{
    this.userService.endParking().subscribe({
      error: (e)=>{
        this.errorMsg = e.error
      },
      next: (data)=>{
        this.parkingInfo.parked = false;
        this.parkingInfo.parkedCarLicensePlate = "";
        
      }
    })
  }
}
