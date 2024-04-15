import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from './services/auth-service/auth-service.service';
import { UserService } from './services/user-service/user.service';

export const authGuard: CanActivateFn = (route, state) => {
  let router = inject(Router);
  let token = localStorage.getItem("token")
  if(!token){
      router.navigate(["login"])
      return false;
    }
    else return true;
};

export const sellerGuard: CanActivateFn = (route, state) => {
  let router = inject(Router);
  let user = inject(UserService)
  let permissions = JSON.parse(localStorage.getItem("permissions")!)
  if(!user.hasPermission("Sell", permissions)){
      router.navigate([router.url]);
      return false;
    }
    else return true;
};
export const automovilistaGuard: CanActivateFn = (route, state) => {
  let router = inject(Router);
  let user = inject(UserService)
  let permissions = JSON.parse(localStorage.getItem("permissions")!)
  if(!user.hasPermission("Park", permissions)){
    router.navigate([router.url]);
    return false;
    }
    else return true;
};