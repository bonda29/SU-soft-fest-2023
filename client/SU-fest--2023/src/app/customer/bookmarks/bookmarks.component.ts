import { Component, OnInit } from '@angular/core';
import { CompanyService } from 'src/app/services/company.service';
import { CustomerService } from 'src/app/services/customer.service';
import { LoaderService } from 'src/app/services/loader.service';

@Component({
  selector: 'app-bookmarks',
  templateUrl: './bookmarks.component.html',
  styleUrls: ['./bookmarks.component.css']
})
export class BookmarksComponent implements OnInit{
  bookmarks: any[] | undefined;
  listings: any[] = [];
  noListings: boolean = false;

  constructor (
    private customerService: CustomerService,
    private companyService: CompanyService,
    public loaderService: LoaderService,
    ) {}

  ngOnInit(): void {
    this.customerService.getBookmarks(localStorage.getItem('id'))
      .subscribe(
        (data) => {          
          this.listings = [];
          if(data.length == 0) {
            this.noListings = true;
          } else {
            this.noListings = false;
          }

          for(let id of data) {    
            if(id) {
              this.companyService.getOneListing(id).subscribe(
                (data) => {
                this.listings.push(data);
                this.loaderService.hideLoader();
                }
              ) 
            }                 
          }
        if(!this.listings[0]){
          this.noListings = false;
        }
        },
        (err) => {
          console.log(err);
        }
      )

    if(!this.listings[0]){    
      this.noListings = true;
    }
  }
}
