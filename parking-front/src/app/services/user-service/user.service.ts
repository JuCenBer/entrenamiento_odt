import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { enviromentUrl } from '../../enviroment/enviroment.component';
import { Parking } from '../../models/parking';
import { Transaction } from '../../models/transaction';
import { User } from '../../models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient, private router: Router) { }

  public gotTransactions: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);


  startParking(parkingData: any):Observable<any>{
    return this.http.post<any>(enviromentUrl.apiUrl+"/users/start_parking", parkingData)
  }
  
  endParking():Observable<any>{
    return this.http.post<any>(enviromentUrl.apiUrl+"/users/end_parking", null);
  }
  
  isParked():Observable<any>{
    return this.http.get<any>(enviromentUrl.apiUrl+"/users/parking_status");
  }
  
  getVehiculos(): Observable<string[]>{
    return this.http.get<string[]>(enviromentUrl.apiUrl+"/users/vehicles");
  }

  addVehicle(vehicleData: any): Observable<any>{
    return this.http.put<string[]>(enviromentUrl.apiUrl+"/users/add_vehicle", vehicleData)
  }

  getTransactions(): Observable<any>{
    return this.http.get<Transaction[]>(enviromentUrl.apiUrl+"/users/transactions");
  }

  recharge(rechargeData: any): Observable<any>{
    return this.http.post<Transaction[]>(enviromentUrl.apiUrl+"/seller/recharge", rechargeData);
  }

  getUserInformation(): Observable<any>{
    return this.http.get<User>(enviromentUrl.apiUrl + "/users/user");
  }

  hasPermission(permission: String, permissions: String[]): boolean{
    return permissions.includes(permission);
  }
}
