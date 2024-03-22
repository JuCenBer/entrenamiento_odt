import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { enviromentUrl } from '../../enviroment/enviroment.component';
import { Parking } from '../../models/parking';
import { Transaction } from '../../models/transaction';
const httpOptions = {

  headers: new HttpHeaders({'Content-Type': 'application/json'})
}

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient, private router: Router) { }

  
  startParking(parkingData: any):Observable<any>{
    return this.http.post<any>(enviromentUrl.apiUrl+"/users/start_parking", parkingData, httpOptions)
  }
  
  endParking():Observable<any>{
    return this.http.post<any>(enviromentUrl.apiUrl+"/users/end_parking", httpOptions);
  }
  
  isParked():Observable<any>{
    return this.http.get<any>(enviromentUrl.apiUrl+"/users/parking_status");
  }
  
  getVehiculos(): Observable<string[]>{
    return this.http.get<string[]>(enviromentUrl.apiUrl+"/users/vehicles");
  }

  getTransactions(): Observable<any>{
    return this.http.get<Transaction[]>(enviromentUrl.apiUrl+"/users/transactions");
  }
}
