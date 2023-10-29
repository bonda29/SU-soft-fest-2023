import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { DetailsComponent } from './details/details.component';
import { BookmarksComponent } from './bookmarks/bookmarks.component';
import { UserGuard } from '../guards/user.guard';

const routes: Routes = [
    {path:'customer-home',canActivate:[UserGuard] , component:HomeComponent},
    {path:'customer-details/:listingId',canActivate:[UserGuard] , component:DetailsComponent},
    {path: 'bookmarks',canActivate:[UserGuard] , component: BookmarksComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CustomerRoutingModule { }
