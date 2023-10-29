import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { AddComponent } from './add/add.component';
import { DetailsComponent } from './details/details.component';
import { EditComponent } from './edit/edit.component';
import { CompanyGuard } from '../guards/company.guard';

const routes: Routes = [
    {path: 'business-home',canActivate:[CompanyGuard] , component: HomeComponent},
    {path: 'business-add',canActivate:[CompanyGuard] , component: AddComponent},
    {path: 'business-details/:listingId',canActivate:[CompanyGuard] , component: DetailsComponent},
    {path: 'business-edit/:listingId',canActivate:[CompanyGuard] , component: EditComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CompanyRoutingModule { }
