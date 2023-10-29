import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent{
  constructor(
    public authService: AuthService,
    private router: Router) {}

  home() {
    if(localStorage.getItem('role') == "USER"){
      this.router.navigate(["customer-home"]);
    } else {
      this.router.navigate(["business-home"]);
    }
  }

  logout() {
    localStorage.clear();
    this.authService.isLoggedIn = false;
  }
}
