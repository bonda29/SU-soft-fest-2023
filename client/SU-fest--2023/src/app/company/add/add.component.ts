import { Component } from '@angular/core';
import { FormBuilder,Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CompanyService } from 'src/app/services/company.service';

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css']
})
export class AddComponent {
  imageToUpload: File | null = null;
  errorMessage: string | undefined;

  constructor(
    private formBuilder: FormBuilder,
    private companyService: CompanyService,
    private router: Router,
  ) {}

  addForm = this.formBuilder.group({
    name: ['', [Validators.required]],
    description: ['', [Validators.required, Validators.minLength(4)]],
    price: ['', [Validators.required]],
  })

  changeFile(event: any) {
    this.imageToUpload = event.target.files.item(0);
  }

  handleSubmit() {
    if(this.imageToUpload == null) {
      const listingData = {
        name: this.addForm.get('name')?.value,
        description: this.addForm.get('description')?.value,
        price: this.addForm.get('price')?.value,
        companyId: localStorage.getItem('id'),
      }

      this.companyService.addListing(listingData)
        .subscribe(
          (data) => {
            this.router.navigate(['business-home']);
          },
          (err) => {
            this.errorMessage = err.error;
          }
        )
    } else {
      const listingData = {
        name: this.addForm.get('name')?.value,
        description: this.addForm.get('description')?.value,
        price: this.addForm.get('price')?.value,
        companyId: localStorage.getItem('id'),
        image: this.imageToUpload,
      }
  
      this.companyService.addListing(listingData)
        .subscribe(
          (data) => {
            this.router.navigate(['business-home']);
          },
          (err) => {
            this.errorMessage = err.error;
          }
        )
    }
  }
}
