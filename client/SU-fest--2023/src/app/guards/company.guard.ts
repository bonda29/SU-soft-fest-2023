import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root',
})

export class CompanyGuard implements CanActivate {
    constructor(private router: Router) {}
  
    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
      if (localStorage.getItem('role') == "COMPANY") {
        return true;
      } else {
        this.router.navigate(['/login']);
        return false;
      }
    }
  }