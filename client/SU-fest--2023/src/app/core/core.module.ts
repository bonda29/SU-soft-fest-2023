import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home/home.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { RouterModule } from '@angular/router';
import { SharedModule } from '../shared/shared.module';



@NgModule({
  declarations: [
    HomeComponent,
    NotFoundComponent,
  ],
  imports: [
    CommonModule,
    RouterModule,

    SharedModule,
  ],
})
export class CoreModule { }
