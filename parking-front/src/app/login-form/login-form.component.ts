import { Component } from '@angular/core';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../services/auth-service/auth-service.service';
import { LoginData } from '../models/login-data';
import { Router, RouterLink } from '@angular/router';
import { ErrorMessage } from '../models/error-message';
import { AppComponent } from '../app.component';

@Component({
  standalone: true,
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrl: './login-form.component.css',
  imports: [ ReactiveFormsModule, RouterLink]})

export class LoginFormComponent {

  errorMsg: ErrorMessage = {
    message: "",
    status: 0
  }
  constructor(private authService: AuthService, private router: Router){}

  public loginForm = new FormGroup({
    cellphone: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required)
  });

  public onSubmit(){
    const cellphoneTrim = this.loginForm.value.cellphone!.trim()
    const passwordTrim = this.loginForm.value.password!.trim().toLowerCase()
    if(this.loginForm.valid){
      this.authService.loginUser(new LoginData(cellphoneTrim, passwordTrim)).subscribe({
        error: (e) => {
          this.errorMsg = e.error;
        },
        next: (data) =>{
          localStorage.setItem("token", data.token);
          localStorage.setItem("permissions", JSON.stringify(data.permissions));
          localStorage.setItem("username", cellphoneTrim);
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
