import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService) {}

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
          console.log(data);
        },
        (err) => {
          console.log(err);
        }
      )
  }
}
