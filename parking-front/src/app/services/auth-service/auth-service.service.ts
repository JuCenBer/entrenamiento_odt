import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginData } from '../../models/login-data';
import { User } from '../../models/user';
import { enviromentUrl } from '../../enviroment/enviroment.component';

@Injectable({providedIn: 'root'})
export class AuthService {
  constructor(private http: HttpClient) { }

  loginUser(loginData: LoginData): Observable<any>{
    return this.http.post<User>(enviromentUrl.apiUrl +'/auth/login', loginData)
  }
}