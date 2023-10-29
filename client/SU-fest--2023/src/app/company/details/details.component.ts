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
  noImage: boolean = false;

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
        if(err.status == 200) {
          this.router.navigate(['business-home']);
        }
      }
    )
  }

  ngOnInit(): void {
    this.activeRoute.params.subscribe(
      (data) => {
        this.companyService.getOneListing(data['listingId']).subscribe(
          (data) => {
            if(!data.image){
              this.noImage = true;
            }
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