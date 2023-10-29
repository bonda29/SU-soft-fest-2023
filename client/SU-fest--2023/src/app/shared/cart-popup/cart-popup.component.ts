import { ChangeDetectionStrategy, ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { CompanyService } from 'src/app/services/company.service';
import { CustomerService } from 'src/app/services/customer.service';
import { LoaderService } from 'src/app/services/loader.service';

@Component({
  selector: 'app-cart-popup',
  templateUrl: './cart-popup.component.html',
  styleUrls: ['./cart-popup.component.css']
})
export class CartPopupComponent implements OnInit {
  @Input() cartOpen: boolean | undefined;
  @Output() closePopup = new EventEmitter();

  cartItems: any = {};
  itemsToDisplay: any = [];
  noItems: boolean = false;
  popupLoading: boolean = false;

  constructor(
    private companyService: CompanyService,
    private customerService: CustomerService,
    private authService: AuthService,
    private router: Router,
  ) { }

  closeCart() {
    this.closePopup.emit();
  }

  addToCart(value: any) {
    let cart = localStorage.getItem('cart');
    if(!cart) {
      cart = '';
    }
    let cartArr = cart.split('|');
    cartArr.push(value);
    if(cartArr[0] == ''){
      cartArr.shift();
    }
    cart = cartArr.join('|');
    localStorage.setItem('cart',cart);
    
    for(let item of this.itemsToDisplay) {
      if(item[0].id == value) {
        item[1]++;
      }
    }    
  }

  removeFromCart(value: any) {
    let cart = localStorage.getItem('cart');

    let cartArr = cart?.split('|');

    if(cartArr){
      for(let i = 0; i < cartArr.length; i++) {
        if(cartArr[i] == value){
          cartArr.splice(i,1);
          break;
        }
      }
      cart = cartArr.join('|');
      localStorage.setItem('cart',cart);
    }

    for(let item of this.itemsToDisplay) {
      if(item[0].id == value) {
        item[1]--;
      }
    }    
  }

  purchase() {
    this.customerService.purchaseCart(localStorage.getItem('cart')?.split('|')).subscribe(
      (data) => {
        window.location.href = `${JSON.parse(data).url}`;
        localStorage.removeItem('cart');
      },
      (err) => {
        console.log(err);
      }
    )
  }
  
  purchaseCrypto() {    
    this.customerService.purchaseCartCrypto(localStorage.getItem('cart')?.split('|')).subscribe(
      (data) => {
        window.location.href = `${JSON.parse(data).url.split('"')[1]}`;
        localStorage.removeItem('cart');
      },
      (err) => {
        console.log(err);  
      }
    )
  }

  ngOnInit(): void {
    this.popupLoading = true;
    let itemsArr = localStorage.getItem('cart')?.split('|');
    if (itemsArr) {
      for (let item of itemsArr) {
        this.companyService.getOneListing(item).subscribe(
          (data) => {
            let id = data.id;
            if (this.cartItems[id]) {
              this.cartItems[id]++;
            } else {
              this.cartItems[id] = 1;
            }
          },
          (err) => {
            console.log(err);
          }
        )
      }

      setTimeout(() => {
        let cartEntries = Object.entries(this.cartItems);
        for(let kvp of cartEntries) {
          this.companyService.getOneListing(kvp[0]).subscribe(
            (data) => {
              this.itemsToDisplay.push([data,kvp[1]]);              
            },
            (err) => {
              console.log(err);
            }
          )
        }
        this.noItems = false;
        this.popupLoading = false;
      }, 2000);
    } else {
      this.popupLoading = false;
      this.noItems = true;
    }
  }
}
