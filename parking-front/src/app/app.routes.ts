import { Routes } from '@angular/router';
import { LoginFormComponent } from './login-form/login-form.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { HomeComponent } from './home/home.component';
import { TransactionsComponent } from './transactions/transactions.component';

export const routes: Routes = [
    { path: '', redirectTo: 'login', pathMatch: 'full'},
    { path: 'login', component: LoginFormComponent},
    { path: 'home', component: HomeComponent},
    { path: 'transactions', component: TransactionsComponent},
    { path: '**', component: PageNotFoundComponent },
];