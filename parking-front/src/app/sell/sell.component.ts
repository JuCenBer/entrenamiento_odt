import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ErrorMessage } from '../models/error-message';

@Component({
  selector: 'app-sell',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './sell.component.html',
  styleUrl: './sell.component.css'
})
export class SellComponent {

  errorMsg: ErrorMessage = {
    message: '',
    status: 0
  }
  
  sellForm: FormGroup = new FormGroup({
    cellphone: new FormControl('', [Validators.nullValidator, Validators.minLength(10)]),
    amount: new FormControl('', [Validators.nullValidator])
  })

  onSubmit():void{

  }
}
