import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class BookmarkService {
  bookmarked: boolean = false;

  showBookmark() {
    this.bookmarked = true;
  }

  hideBookmark() {
    this.bookmarked = false;
  }
}
