import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CartPopupComponent } from './cart-popup/cart-popup.component';
import { SpinnerComponent } from './spinner/spinner.component';
import { ListingComponent } from './listing/listing.component';



@NgModule({
  declarations: [
    CartPopupComponent,
    SpinnerComponent,
    ListingComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    CartPopupComponent,
    SpinnerComponent,
    ListingComponent,
  ]
})
export class SharedModule { }
