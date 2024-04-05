import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ErrorMessage } from '../models/error-message';
import { UserService } from '../services/user-service/user.service';
import { RechargeData } from '../models/recharge-data';
import { CommonModule, NgIf } from '@angular/common';

@Component({
  selector: 'app-sell',
  standalone: true,
  imports: [ReactiveFormsModule, NgIf, CommonModule],
  templateUrl: './sell.component.html',
  styleUrl: './sell.component.css'
})
export class SellComponent {

  errorMsg: ErrorMessage = {
    message: '',
    status: 0
  }
  status = {
    failed: false,
    success: false
  }
  pepito!: string;
  constructor(private userService: UserService){

  }
  
  sellForm: FormGroup = new FormGroup({
    cellphone: new FormControl('', [Validators.required, Validators.minLength(10)]),
    amount: new FormControl('', [Validators.required])
  })

  onSubmit(): void{
    let rechargeData = new RechargeData(this.sellForm.value.cellphone, this.sellForm.value.amount)
    if(rechargeData.amount < 100){
      this.errorMsg.message = "El monto minimo debe ser de $100"
      this.status.failed = true
      this.status.success = false
    }
    else{
      this.userService.recharge(rechargeData).subscribe({
        error: (e) =>{
          this.errorMsg = e.error;
          this.status.success = false;
          this.status.failed = true;
        },
        next: (data) => {
          console.log(JSON.parse(data))
          this.status.success = true;
          this.status.failed = false;
        }
      });
    }
  }
}
