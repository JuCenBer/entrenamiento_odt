import { Routes } from '@angular/router';
import { LoginFormComponent } from './login-form/login-form.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { HomeComponent } from './home/home.component';
import { TransactionsComponent } from './transactions/transactions.component';
import { VehiclesComponent } from './vehicles/vehicles.component';
import { RegisterFormComponent } from './register-form/register-form.component';
import { SellComponent } from './sell/sell.component';

export const routes: Routes = [
    { path: '', redirectTo: '/login', pathMatch: 'full'},
    { path: 'login', component: LoginFormComponent},
    { path: 'register', component: RegisterFormComponent},
    { path: 'home', component: HomeComponent},
    { path: 'sell', component: SellComponent},
    { path: 'transactions', component: TransactionsComponent},
    { path: 'vehicles', component: VehiclesComponent},
    { path: '**', component: PageNotFoundComponent },
];