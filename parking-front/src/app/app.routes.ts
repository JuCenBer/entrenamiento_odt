import { Routes } from '@angular/router';
import { LoginFormComponent } from './login-form/login-form.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { HomeComponent } from './home/home.component';
import { TransactionsComponent } from './transactions/transactions.component';
import { VehiclesComponent } from './vehicles/vehicles.component';
import { RegisterFormComponent } from './register-form/register-form.component';
import { SellComponent } from './sell/sell.component';
import { authGuard, automovilistaGuard, sellerGuard } from './auth.guard';

export const routes: Routes = [
    { path: '', redirectTo: '/login', pathMatch: 'full'},
    { path: 'login', component: LoginFormComponent},
    { path: 'register', component: RegisterFormComponent},
    { path: 'home', component: HomeComponent, canActivate: [authGuard, automovilistaGuard]},
    { path: 'sell', component: SellComponent, canActivate: [authGuard, sellerGuard]},
    { path: 'transactions', component: TransactionsComponent, canActivate: [authGuard]},
    { path: 'vehicles', component: VehiclesComponent, canActivate: [authGuard, automovilistaGuard]},
    { path: '**', component: PageNotFoundComponent },
];