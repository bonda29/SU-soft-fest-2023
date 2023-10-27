import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CompanyService } from 'src/app/services/company.service';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})
export class DetailsComponent implements OnInit{
  listing: any | undefined;

  constructor(
    private companyService: CompanyService,
    private activeRoute: ActivatedRoute,
    private router: Router,
  ) { }

  deleteListing(){
    this.companyService.deleteListing(this.listing.id).subscribe(
      (data) => {
        this.router.navigate(['business-home']);
      },
      (err) => {
        console.log(err);
      }
    )
  }

  ngOnInit(): void {
    this.activeRoute.params.subscribe(
      (data) => {
        this.companyService.getOneListing(data['listingId']).subscribe(
          (data) => {
            this.listing = data;
          },
          (err) => {
            console.log(err);
          }
        )
      },
      (err) => {
        console.log(err);
      }
    )
  }
}
