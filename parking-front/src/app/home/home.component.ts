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

  cellphone: string = localStorage.getItem("username")!;
  balance!: number;
  
  constructor(private userService: UserService){
  }

  ngOnInit():void{
    this.isParked();
    this.getUserInfo();
  }

  public parkingForm = new FormGroup({
    vehicle: new FormControl('', [Validators.required,Validators.nullValidator]),
  });

  getUserInfo():void{
    this.userService.getUserInformation().subscribe({
      error: (e) =>{
        this.errorMsg = e.error;
      },
      next: (data) =>{
        localStorage.setItem("balance", data.balance);
        this.balance = data.balance;
        console.log(data.vehiculos);
        this.vehiculos = data.vehiculos;
      }
    })
  }

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
      }
     })
  }

  endParking():void{
    this.userService.endParking().subscribe({
      error: (e)=>{
        this.errorMsg = e.error
      },
      next: (data)=>{
        localStorage.setItem("balance", data.balance);
        this.parkingInfo.parked = false;
        this.parkingInfo.parkedCarLicensePlate = "";
        this.parkingForm.get("vehicle")?.setValue(null)
        this.parkingForm.get("vehicle")?.enable()
        this.getUserInfo();
      }
    })
  }

}
