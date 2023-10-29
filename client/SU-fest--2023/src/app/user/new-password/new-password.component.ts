import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-new-password',
  templateUrl: './new-password.component.html',
  styleUrls: ['./new-password.component.css']
})
export class NewPasswordComponent {
  passVisible: boolean = false;
  repassVisible: boolean = false;
  errorMessage: string | undefined;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private router: Router) {}

  newPasswordForm = this.formBuilder.group({
    code: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(6)]],
    newPassword: ['', [Validators.required, Validators.minLength(4)]],
    repeatPassword: ['', [Validators.required, Validators.minLength(4)]],
  })

  changeVisibilityPass(pass:any) {
    this.passVisible = !this.passVisible;
    if(pass.type == "password"){
      pass.type = "text";
    } else {
      pass.type = "password";
    }
  }

  changeVisibilityRepass(repass:any) {
    this.repassVisible = !this.repassVisible;
    if(repass.type == "password"){
      repass.type = "text";
    } else {
      repass.type = "password";
    }
  }

  handleSubmit() {
    const userData = {
      passwordSendToEmail: this.newPasswordForm.get('code')?.value,
      newPassword: this.newPasswordForm.get('newPassword')?.value,
      repeatPassword: this.newPasswordForm.get('repeatPassword')?.value,
      email: localStorage.getItem('email'),
    }        

    this.userService.sendNewPassword(userData)
      .subscribe(
        (data) => {
          console.log(data);
          this.router.navigate(['login']);
        },
        (err) => {
          this.errorMessage = err.error;
        }
      )
  }
}
