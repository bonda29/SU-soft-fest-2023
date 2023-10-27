import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { AddComponent } from './add/add.component';
import { DetailsComponent } from './details/details.component';
import { EditComponent } from './edit/edit.component';

const routes: Routes = [
    {path: 'business-home' , component: HomeComponent},
    {path: 'business-add', component: AddComponent},
    {path: 'business-details/:listingId', component: DetailsComponent},
    {path: 'business-edit/:listingId', component: EditComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CompanyRoutingModule { }
