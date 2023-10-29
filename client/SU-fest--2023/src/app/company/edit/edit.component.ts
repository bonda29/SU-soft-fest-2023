import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CompanyService } from 'src/app/services/company.service';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit{
  listing: any | undefined;

  constructor (
    private formBuilder: FormBuilder,
    private companyService: CompanyService,
    private activeRoute: ActivatedRoute,
    private router: Router,
    ) {}

  editForm = this.formBuilder.group({
    name: ['', [Validators.required]],
    description: ['', [Validators.required, Validators.minLength(4)]],
    price: ['', [Validators.required]],
  })

  handleSubmit() {
    const listingData = {
      name: this.editForm.get('name')?.value,
      description: this.editForm.get('description')?.value,
      price: this.editForm.get('price')?.value,
    }

    this.companyService.editListing(this?.listing.id, listingData).subscribe(
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
            this.editForm.setValue({
              name: this.listing.name,
              description: this.listing.description,
              price: this.listing.price,
            })
          }
        )
      }
    )
  }
}
