import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BookmarkService } from 'src/app/services/bookmark.service';
import { CompanyService } from 'src/app/services/company.service';
import { CustomerService } from 'src/app/services/customer.service';
import { LoaderService } from 'src/app/services/loader.service';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})
export class DetailsComponent {
  listing: any | undefined;
  cartStore: number | undefined = 0;
  cartOpen: boolean = false;

  constructor(
    public loaderService: LoaderService,
    private companyService: CompanyService,
    private customerService: CustomerService,
    public bookmarkService: BookmarkService,
    private activeRoute: ActivatedRoute,
    private router: Router,
  ) { }

  addToCart() {
    let cart = localStorage.getItem('cart');
    if(!cart) {
      cart = '';
    }
    let cartArr = cart.split('|');
    
    this.activeRoute.params.subscribe(
      (data) => {
        cartArr.push(data['listingId']);
        if(cartArr[0] == ''){
          cartArr.shift();
        }
        cart = cartArr.join('|');
        localStorage.setItem('cart',cart);
        this.router.navigate(['customer-home']);
      },
      (err) => {
        console.log(err);
      }
    )
    
  }

  openCart() {
    this.cartOpen = !this.cartOpen;
  }

  addBookmark(listingId: any) {    
    if (this.bookmarkService.bookmarked == true) {
      this.bookmarkService.bookmarked = false;
      this.customerService.removeBookmark(localStorage.getItem('id'), listingId)
        .subscribe(
          (data) => {
            console.log(data);
          },
          (err) => {
            console.log(err);
          }
        )
    } else {
      this.bookmarkService.bookmarked = true;
      this.customerService.addBookmark(localStorage.getItem('id'), listingId)
        .subscribe(
          (data) => {
            console.log(data);
          },
          (err) => {
            console.log(err);
          }
        );
    }
  }

  ngOnInit(): void {
    this.loaderService.showLoader()
    this.activeRoute.params.subscribe(
      (data) => {
        this.companyService.getOneListing(data['listingId']).subscribe(
          (data) => {
            this.listing = data;
            this.loaderService.hideLoader();
          },
          (err) => {
            console.log(err);
          }
        )
        this.customerService.getBookmarks(localStorage.getItem('id'))
          .subscribe(
            (arr) => {
              if(arr.includes(data['listingId'])){
                this.bookmarkService.showBookmark();
              } else {
                this.bookmarkService.hideBookmark();
              }
            },
            (err) => {
              console.log(err);
            }
          )
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
