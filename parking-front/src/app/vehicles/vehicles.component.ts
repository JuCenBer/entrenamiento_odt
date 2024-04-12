import { Component } from '@angular/core';
import { UserService } from '../services/user-service/user.service';
import { ErrorMessage } from '../models/error-message';
import { NgFor, NgIf } from '@angular/common';
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '../services/auth-service/auth-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-vehicles',
  standalone: true,
  imports: [NgFor, NgIf, ReactiveFormsModule],
  templateUrl: './vehicles.component.html',
  styleUrl: './vehicles.component.css'
})
export class VehiclesComponent {

  failed: boolean = false;
  success: boolean = false;
  vehicles:String[]= JSON.parse(localStorage.getItem("vehiculos")!);
  errorMsg: ErrorMessage={
    message:'',
    status:0,
  }

  constructor(private userService: UserService, private auth: AuthService, private router: Router){
  }

  vehicleForm = new FormGroup({
    licensePlate: new FormControl('', [Validators.required, Validators.nullValidator, Validators.min(6)]),
  })

  addVehicle():void{
    let vehicle: string = this.vehicleForm.value.licensePlate!.trim()
    let vehicleData = JSON.stringify(vehicle).trim()
    this.userService.addVehicle(vehicleData).subscribe({
      error: (e) =>{
        this.failed = true
        this.success = false;
        this.errorMsg = e.error;
      },
      next: (data) => {
        this.vehicles = data;
        this.failed = false
        this.success = true;
        localStorage.setItem("vehiculos", JSON.stringify(data));
        this.vehicleForm.get("licensePlate")?.setValue(null)
        this.errorMsg.message = ""
        this.errorMsg.status = 0
      }
    })
  }
}
