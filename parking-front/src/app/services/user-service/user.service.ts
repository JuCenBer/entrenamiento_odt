import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { enviromentUrl } from '../../enviroment/enviroment.component';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient, private router: Router) { }

  getVehiculos(): Observable<any>{
    return this.http.get(enviromentUrl+"/users/vehicles");
  }
}
