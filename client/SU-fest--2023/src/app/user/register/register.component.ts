import { Component, ElementRef, ViewChild } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  errorMessage: string | undefined;
  selectedRole: string | null | undefined = 'client';
  passVisible: boolean = false;
  repassVisible: boolean = false;

  constructor (
    private userService: UserService,
    private formBuilder: FormBuilder,
    private router: Router,
  ) {}

  registerForm = this.formBuilder.group({
    nameSur: ['', [Validators.required]],
    company: ['', [Validators.required]],
    username: ['', [Validators.required]],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(4)]],
    rePassword: ['', [Validators.required, Validators.minLength(4)]],
    role: ['',[Validators.required]]
  })

  onRoleChange(){
    this.selectedRole = this.registerForm.get('role')?.value;   
  }

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

  handleSubmit(){
    if(this.selectedRole == 'client') {
      const userData = {
        name: this.registerForm.get('nameSur')?.value,
        username: this.registerForm.get('username')?.value,
        email: this.registerForm.get('email')?.value,
        password: this.registerForm.get('password')?.value,
        repeatPassword: this.registerForm.get('rePassword')?.value,
        role: 'USER',
      }

      this.userService.registerUser(userData)
        .subscribe(
          (data) => {
            this.router.navigate(['login'])
          },
          (err) => {
            this.errorMessage = err.error;
          }
        )
    } else if(this.selectedRole == 'company') {
      const userData = {
        name: this.registerForm.get('company')?.value,
        username: this.registerForm.get('username')?.value,
        email: this.registerForm.get('email')?.value,
        password: this.registerForm.get('password')?.value,
        repeatPassword: this.registerForm.get('rePassword')?.value,
        role: 'COMPANY',
      }
      
      this.userService.registerUser(userData)
        .subscribe(
          (data) => {
            this.router.navigate(['login'])
          },
          (err) => {
            this.errorMessage = err.error;
          }
        )
    } else {
      this.errorMessage = 'Please select role!'
    }
  }
}
