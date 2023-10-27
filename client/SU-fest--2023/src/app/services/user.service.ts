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

    const json = JSON.stringify(userData);
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    })

    return this.http.post(`${appUrl}/auth/login`, json, {headers});
  }

  registerUser(userData: any) {
    const { appUrl } = environment;

    const json = JSON.stringify(userData);
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    })

    return this.http.post(`${appUrl}/auth/register`, json, {headers});
  }
}
