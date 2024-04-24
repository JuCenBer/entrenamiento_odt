import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {
  
  private currencySymbol: String = $localize`$`
  private configUrl = 'assets/config.json';
  
  constructor(private http: HttpClient) {
  }
  
  public loadConfig(): Observable<any> {
    return this.http.get<any>(this.configUrl);
  }

  public getCurrencySymbol(): String{
    return this.currencySymbol;
  }
}
