import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginData } from '../../models/login-data';
import { User } from '../../models/user';
import { enviromentUrl } from '../../enviroment/enviroment.component';
import { Router } from '@angular/router';

@Injectable({providedIn: 'root'})
export class AuthService {
  constructor(private router: Router, private http: HttpClient) { }

  loginUser(loginData: LoginData): Observable<any>{
    return this.http.post<User>(enviromentUrl.apiUrl +'/auth/login', loginData)
  }

  logoutUser(): void {
    localStorage.clear();
    this.router.navigate(['login']);
  }
}