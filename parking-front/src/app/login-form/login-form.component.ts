import { Component } from '@angular/core';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  standalone: true,
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrl: './login-form.component.css',
  imports: [ ReactiveFormsModule ]
})

export class LoginFormComponent {
  public loginForm = new FormGroup({
    cellphone: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required)
  });

  public onSubmit(){

  }
}
