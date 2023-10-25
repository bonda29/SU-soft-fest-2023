import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  loginUser(userData: any) {
    const { appUrl } = environment;

    return this.http.post(`${appUrl}/auth/login`, userData);
  }

  registerUser(userData: any) {
    const { appUrl } = environment;

    return this.http.post(`${appUrl}/auth/register`, userData);
  }
}
