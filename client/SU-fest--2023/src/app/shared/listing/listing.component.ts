import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BookmarkService } from 'src/app/services/bookmark.service';
import { CustomerService } from 'src/app/services/customer.service';

@Component({
  selector: 'app-listing',
  templateUrl: './listing.component.html',
  styleUrls: ['./listing.component.css']
})
export class ListingComponent implements OnInit {
  @Input() listing: any | undefined;
  isBookmarked: boolean = false;

  constructor(
    public bookmarkService: BookmarkService,
    private customerService: CustomerService,
  ) { }

  addBookmark(listingId: any) {    
    if (this.isBookmarked == true) {
      this.isBookmarked = false;
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
      this.isBookmarked = true;
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
    this.customerService.getBookmarks(localStorage.getItem('id'))
      .subscribe(
        (arr) => {   
          if (arr.includes(this.listing.id.toString())) {
            this.isBookmarked = true;
            this.bookmarkService.showBookmark();
          } else {
            this.isBookmarked = false;
            this.bookmarkService.hideBookmark();
          }
        },
        (err) => {
          console.log(err);
        }
      )
  }
}
