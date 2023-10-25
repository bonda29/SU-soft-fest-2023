import { Component } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  constructor (
    private userService: UserService,
    private formBuilder: FormBuilder,
  ) {}

  registerForm = this.formBuilder.group({
    username: ['', [Validators.required]],
    password: ['', [Validators.required, Validators.minLength(4)]],
    rePassword: ['', [Validators.required, Validators.minLength(4)]],
  })


  handleSubmit(){
    const userData = {
      username: this.registerForm
    }
  }
}
