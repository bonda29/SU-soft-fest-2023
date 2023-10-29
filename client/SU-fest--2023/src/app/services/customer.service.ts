import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private http: HttpClient) { }

  getAllListings() {
    const { appUrl } = environment;

    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
    })

    return this.http.get<any>(`${appUrl}/businessVersion/products`, { headers });
  }

  searchCompanies(query: string | undefined) {
    const { appUrl } = environment;

    if(query == ""){
      query = ":all";
    }

    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
    })

    return this.http.get<any>(`${appUrl}/clientVersion/companies/${query}`, { headers });
  }

  purchaseCart(items: any[] | undefined) {
    const { appUrl } = environment;

    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
    })
        
    return this.http.post(`${appUrl}/payment/stripe`, items, { headers ,responseType: 'text'});
  }

  purchaseCartCrypto(items: any[] | undefined) {
    const { appUrl } = environment;

    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
    })
        
    return this.http.post(`${appUrl}/payment/coinbase`, items, { headers ,responseType: 'text'});
  }

  getBookmarks(id: string | null) {
    const { appUrl } = environment;

    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
    })

    return this.http.get<any>(`${appUrl}/clientVersion/bookmarks/${id}`, { headers });
  }

  addBookmark(id: string | null, listingId: string | null) {
    const { appUrl } = environment;
    
    const json = JSON.stringify(listingId);
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json',
    })

    return this.http.post<any>(`${appUrl}/clientVersion/bookmark/${id}`,json ,{ headers });
  }
  removeBookmark(id: string | null, listingId: string | null) {
    const { appUrl } = environment;
    
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
    })

    return this.http.delete<any>(`${appUrl}/clientVersion/bookmarks/${id}/${listingId}` ,{ headers });
  }
}
