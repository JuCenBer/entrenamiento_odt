import { Component } from '@angular/core';


export const enviromentUrl = {
  production: false,
  apiUrl: "http://localhost:8080/parking-back"
} 

@Component({
  selector: 'app-enviroment',
  standalone: true,
  imports: [],
  templateUrl: './enviroment.component.html',
  styleUrl: './enviroment.component.css'
})
export class EnviromentComponent {
  
}
