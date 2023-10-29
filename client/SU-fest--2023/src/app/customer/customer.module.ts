import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CustomerRoutingModule } from './customer-routing.module';
import { HomeComponent } from './home/home.component';
import { DetailsComponent } from './details/details.component';
import { SharedModule } from '../shared/shared.module';
import { CoreModule } from '../core/core.module';
import { BookmarksComponent } from './bookmarks/bookmarks.component';



@NgModule({
  declarations: [
    HomeComponent,
    DetailsComponent,
    BookmarksComponent
  ],
  imports: [
    CommonModule,
    CustomerRoutingModule,

    SharedModule,
    CoreModule,
  ]
})
export class CustomerModule { }
