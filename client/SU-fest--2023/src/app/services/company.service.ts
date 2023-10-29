import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, switchMap, switchScan, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  constructor(private http: HttpClient) { }

  getOneListing(id: string | null) {
    const { appUrl } = environment;

    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
    })

    return this.http.get<any>(`${appUrl}/businessVersion/product/${id}`, { headers });
  }

  getAllListings(id: string | null) {
    const { appUrl } = environment;

    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
    })

    return this.http.get<any>(`${appUrl}/businessVersion/products/${id}`, { headers });
  }

  uploadImage(image: File) {
    const { apiKey } = environment;
    const formData = new FormData();
    formData.append('image', image);
    formData.append('key', apiKey);

    return this.http.post<any>('https://api.imgbb.com/1/upload', formData);
  }

  addListing(listingData: any) {
    const { appUrl } = environment;

    if (listingData.image) {
      return this.uploadImage(listingData.image).pipe(
        switchMap((data: any) => {
          let finalData = {
            name: listingData.name,
            description: listingData.description,
            price: listingData.price,
            companyId: listingData.companyId,
            image: data.data.display_url,
          };

          const token = localStorage.getItem('token');
          const json = JSON.stringify(finalData);
          const headers = new HttpHeaders({
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
          });

          return this.http.post<any>(`${appUrl}/businessVersion/products`, json, { headers }).pipe(
            catchError((error: any) => {
              return throwError(error);
            })
          );
        }),
        catchError((error: any) => {
          return throwError(error);
        })
      );
    } else {
      let finalData = {
        name: listingData.name,
        description: listingData.description,
        price: listingData.price,
        companyId: listingData.companyId,
      };

      const token = localStorage.getItem('token');
      const json = JSON.stringify(finalData);
      const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      });

      return this.http.post<any>(`${appUrl}/businessVersion/products`, json, { headers });
    }
  }

  editListing(id: string | null, listingData: any) {
    const { appUrl } = environment;

    const json = JSON.stringify(listingData);
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    })

    return this.http.put<any>(`${appUrl}/businessVersion/product/${id}`, json, { headers });
  }

  deleteListing(id: string | null) {
    const { appUrl } = environment;

    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
    })

    return this.http.delete<any>(`${appUrl}/businessVersion/product/${id}`, { headers });
  }
}
