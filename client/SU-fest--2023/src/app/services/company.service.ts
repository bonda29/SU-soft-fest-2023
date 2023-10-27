import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  constructor(private http: HttpClient) { }

  getOneListing(id: string | null) {
    const { appUrl } = environment;

    // const token = localStorage.getItem('token');
    // const headers = new HttpHeaders({
    //   'Authorization': `Bearer ${token}`,
    // })

    return this.http.get<any>(`${appUrl}/businessVersion/getProduct/${id}`);
  }

  getAllListings(id: string | null) {
    const { appUrl } = environment;

    // const token = localStorage.getItem('token');
    // const headers = new HttpHeaders({
    //   'Authorization': `Bearer ${token}`,
    // })

    return this.http.get<any>(`${appUrl}/businessVersion/getAll/${id}`);
  }

  addListing(listingData: any) {
    const { appUrl } = environment;

    const json = JSON.stringify(listingData);
    // const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      // 'Authorization': `Bearer ${token}`,
    })

    return this.http.post<any>(`${appUrl}/businessVersion/addProduct`, json, { headers });
  }

  editListing( id: string | null, listingData: any) {
    const { appUrl } = environment;

    const json = JSON.stringify(listingData);
    // const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      // 'Authorization': `Bearer ${token}`,
    })

    return this.http.put<any>(`${appUrl}/businessVersion/updateProduct/${id}`, json, { headers });
  }

  deleteListing(id: string | null) {
    const { appUrl } = environment;

    // const token = localStorage.getItem('token');
    // const headers = new HttpHeaders({
    //   'Authorization': `Bearer ${token}`,
    // })

    return this.http.delete<any>(`${appUrl}/businessVersion/deleteProduct/${id}`);
  }
}
