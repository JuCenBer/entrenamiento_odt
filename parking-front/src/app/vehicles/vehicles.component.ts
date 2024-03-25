import { Component } from '@angular/core';
import { UserService } from '../services/user-service/user.service';
import { ErrorMessage } from '../models/error-message';
import { NgFor, NgIf } from '@angular/common';
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-vehicles',
  standalone: true,
  imports: [NgFor, NgIf, ReactiveFormsModule],
  templateUrl: './vehicles.component.html',
  styleUrl: './vehicles.component.css'
})
export class VehiclesComponent {

  vehicles:String[]=[];
  errorMsg: ErrorMessage={
    message:'',
    status:0,
  }

  constructor(private userService: UserService){

  }

  vehicleForm = new FormGroup({
    licensePlate: new FormControl('', [Validators.required, Validators.nullValidator, Validators.min(6)]),
  })

  ngOnInit():void{
    this.getVehicles();
  }

  getVehicles():void{
    this.userService.getVehiculos().subscribe({
      error: (e) => {
        this.errorMsg = e.error;
      },
      next: (data) =>{
        this.vehicles = data;
      }
    });
  }

  addVehicle():void{
    let vehicle: string = this.vehicleForm.value.licensePlate!.trim()
    let vehicleData = JSON.stringify(vehicle).trim()
    this.userService.addVehicle(vehicleData).subscribe({
      error: (e) =>{
        this.errorMsg = e.error;
      },
      next: (data) => {
        this.vehicles = data;
        this.vehicleForm.get("licensePlate")?.setValue(null)
        this.errorMsg.message = ""
        this.errorMsg.status = 0
      }
    })
  }
}
