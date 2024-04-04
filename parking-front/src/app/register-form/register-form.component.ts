import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { UserService } from '../services/user-service/user.service';
import { ErrorMessage } from '../models/error-message';
import { Router, RouterLink } from '@angular/router';
import { NgIf } from '@angular/common';
import { AuthService } from '../services/auth-service/auth-service.service';
import { RegisterData } from '../models/register-data';

@Component({
  selector: 'app-register-form',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink, NgIf],
  templateUrl: './register-form.component.html',
  styleUrl: './register-form.component.css'
})
export class RegisterFormComponent {

  errorMsg: ErrorMessage={
    message: "",
    status: 0
  }

  constructor(private authService: AuthService, private router: Router){

  }

  registerForm = new FormGroup({
    cellphone: new FormControl('', [Validators.nullValidator, Validators.minLength(10)]),
    password: new FormControl('', [Validators.nullValidator, Validators.minLength(5)]),
    repeatPassword: new FormControl('', [Validators.nullValidator, Validators.minLength(5)]),
  })

  onSubmit(){
    let cellphoneTrim = this.registerForm.value.cellphone!.trim()
    let password1 = this.registerForm.value.password
    let password2 = this.registerForm.value.repeatPassword
    if(password1 == password2){
      let registerData = new RegisterData(this.registerForm.value.cellphone!, this.registerForm.value.password!)
      this.authService.registerUser(registerData).subscribe({
        error: (e) =>{
          this.errorMsg = e.error
        },
        next: (data) =>{
          localStorage.setItem("token", data.token);
          localStorage.setItem("permissions", JSON.stringify(data.permissions));
          localStorage.setItem("username", cellphoneTrim);
          this.authService.isLoggedIn.next(true);
          if (localStorage.getItem("permissions")?.includes("Park")){
            this.router.navigate(["home"]);
          }
          else if(localStorage.getItem("permissions")?.includes("Sell")){
            this.router.navigate(["sell"]);
          }
        }
      })
    }
  }
}
