import { Component, OnInit } from '@angular/core';
import { CompanyService } from 'src/app/services/company.service';
import { CustomerService } from 'src/app/services/customer.service';
import { LoaderService } from 'src/app/services/loader.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{
  listings: any[] | undefined;
  noListings: boolean = false;
  cartStore: number | undefined = 0;
  cartOpen: boolean = false;

  constructor(
    private customerService: CustomerService,
    private companyService: CompanyService,
    public loaderService: LoaderService,
  ) {}

  searchCompanies(query: string) {
    this.loaderService.showLoader();
    
    this.customerService.searchCompanies(query).subscribe(
      (data) => {
        this.listings = [];
        if(data.length == 0){
          this.noListings = true;
        } else {
          this.noListings = false;
        }
        for(let company of data) {
          this.companyService.getAllListings(company.id).subscribe(
            (data) => {
              
              for(let product of data) {
                this.listings?.push(product);
              }
              
              this.loaderService.hideLoader();
            }
          )
        }
        this.loaderService.hideLoader();
      },
      (err) => {
        console.log(err);
      }
    )
  }

  openCart() {
    this.cartOpen = !this.cartOpen;
  }

  ngOnInit(): void {
    this.loaderService.showLoader();

    this.customerService.getAllListings().subscribe(
      (data) => {
        this.listings = data;
        if(this.listings){
          if(this.listings.length > 0){ 
            this.noListings = false;
          }
        }
        this.loaderService.hideLoader();
      },
      (err) => {
        console.log(err);
      }
    )

    this.cartStore = localStorage.getItem('cart')?.split('|').length;
    if(!this.cartStore) {
      this.cartStore = 0;
    }
  }
}
