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
  selectedRole: string | null | undefined = 'none';

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

  handleSubmit(){
    if(this.selectedRole == 'client') {
      const userData = {
        name: this.registerForm.get('nameSur')?.value,
        username: this.registerForm.get('username')?.value,
        email: this.registerForm.get('email')?.value,
        password: this.registerForm.get('password')?.value,
        role: 'USER',
      }

      this.userService.registerUser(userData)
        .subscribe(
          (data) => {
            console.log(data);
          },
          (err) => {
            console.log(err);
          }
        )
    } else if(this.selectedRole == 'company') {
      const userData = {
        company: this.registerForm.get('compnay')?.value,
        username: this.registerForm.get('username')?.value,
        email: this.registerForm.get('email')?.value,
        password: this.registerForm.get('password')?.value,
        role: 'COMPANY',
      }

      this.userService.registerUser(userData)
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
    } else {
      console.log('Please select role first!');
    }
  }
}
