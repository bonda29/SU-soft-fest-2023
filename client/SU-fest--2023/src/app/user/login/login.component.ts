import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private router: Router) {}

  loginForm = this.formBuilder.group({
    username: ['', [Validators.required]],
    password: ['', [Validators.required, Validators.minLength(4)]]
  })

  handleSubmit() {
    const userData = {
      username: this.loginForm.get('username')?.value,
      password: this.loginForm.get('password')?.value,
    }

    this.userService.loginUser(userData)
      .subscribe(
        (data) => {
          const entries = Object.entries(data);

          localStorage.setItem('id', entries[0][1]);
          localStorage.setItem('email', entries[1][1]);
          localStorage.setItem('username', entries[2][1]);
          localStorage.setItem('role', entries[3][1]);
          localStorage.setItem('token', entries[4][1]);

          if(localStorage.getItem('role') == "USER"){
            this.router.navigate(["user-home"]);
          } else {
            this.router.navigate(["business-home"]);
          }
        },
        (err) => {
          console.log(err);
        }
      )
  }
}
