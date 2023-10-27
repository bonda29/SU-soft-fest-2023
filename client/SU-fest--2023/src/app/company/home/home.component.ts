import { Component, OnInit } from '@angular/core';
import { CompanyService } from 'src/app/services/company.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{
  listings: any[] | undefined;

  constructor(private companyService: CompanyService) {}

  ngOnInit(): void {
    this.companyService.getAllListings(localStorage.getItem('id'))
      .subscribe(
        (data) => {
          this.listings = data;
        },
        (err) => {
          console.log(err);
        }
      )
  }
}
