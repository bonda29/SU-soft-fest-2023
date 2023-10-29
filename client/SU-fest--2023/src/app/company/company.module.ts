import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CompanyRoutingModule } from './comany-routing.module';
import { HomeComponent } from './home/home.component';
import { AddComponent } from './add/add.component';
import { ReactiveFormsModule } from '@angular/forms';
import { EditComponent } from './edit/edit.component';
import { DetailsComponent } from './details/details.component';



@NgModule({
  declarations: [
    HomeComponent,
    AddComponent,
    EditComponent,
    DetailsComponent
  ],
  imports: [
    CommonModule,
    CompanyRoutingModule,
    ReactiveFormsModule,
  ]
})
export class CompanyModule { }
