import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  passVisible: boolean = false;
  errorMessage: string | undefined;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private router: Router,
    private authService: AuthService) {}

  loginForm = this.formBuilder.group({
    username: ['', [Validators.required]],
    password: ['', [Validators.required, Validators.minLength(4)]]
  })

  changeVisibility(pass: any) {
    this.passVisible = !this.passVisible;
    if(pass.type == "password"){
      pass.type = "text";
    } else {
      pass.type = "password";
    }
  }

  handleSubmit() {
    const userData = {
      username: this.loginForm.get('username')?.value,
      password: this.loginForm.get('password')?.value,
    }

    this.userService.loginUser(userData)
      .subscribe(
        (data) => {
          console.log(data);
          
          localStorage.setItem('id', data.id);
          localStorage.setItem('email', data.email);
          localStorage.setItem('username', data.username);
          localStorage.setItem('role', data.role);
          localStorage.setItem('token', data.token);

          this.authService.isLoggedIn = true;

          if(localStorage.getItem('role') == "USER"){
            this.router.navigate(["customer-home"]);
          } else {
            this.router.navigate(["business-home"]);
          }
        },
        (err) => {
          this.errorMessage = err.error;          
        }
      )
  }
}
