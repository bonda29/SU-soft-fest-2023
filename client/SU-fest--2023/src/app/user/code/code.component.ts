import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-code',
  templateUrl: './code.component.html',
  styleUrls: ['./code.component.css']
})
export class CodeComponent {
  errorMessage: string| undefined;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private router: Router) {}

  forgotPasswordForm = this.formBuilder.group({
    email: ['', [Validators.required, Validators.email]]
  })

  handleSubmit() {
    const userData = {
      email: this.forgotPasswordForm.get('email')?.value,
    }

    this.userService.sendCode(userData)
      .subscribe(
        (data) => {
          if(userData.email){
            localStorage.setItem('email', userData.email)
          }
          this.router.navigate(['newPassword']);
        },
        (err) => {
          this.errorMessage = err.error;
        }
      )
  }
}
