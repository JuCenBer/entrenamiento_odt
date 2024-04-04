import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { LoginData } from '../../models/login-data';
import { User } from '../../models/user';
import { enviromentUrl } from '../../enviroment/enviroment.component';
import { Router } from '@angular/router';
import { RegisterData } from '../../models/register-data';

@Injectable({providedIn: 'root'})
export class AuthService {
  constructor(private router: Router, private http: HttpClient) { }

  public isLoggedIn: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  loginUser(loginData: LoginData): Observable<any>{
    return this.http.post<User>(enviromentUrl.apiUrl +'/auth/login', loginData)
  }

  logoutUser(): void {
    localStorage.clear();
    this.router.navigate(['login']);
  }

  registerUser(registerData: RegisterData):Observable<any>{
    return this.http.post<User>(enviromentUrl.apiUrl+'/auth/register', registerData);
  }
}