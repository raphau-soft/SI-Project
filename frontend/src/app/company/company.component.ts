import { Component, OnInit } from '@angular/core';
import { CompanyService } from '../company.service';
import { Company } from '../classes/Company'
import { RoomService } from '../room.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-company',
  templateUrl: './company.component.html',
  styleUrls: ['./company.component.css']
})
export class CompanyComponent implements OnInit {

  companies: Company[];
  

  constructor(private companyService: CompanyService, private router: Router) { }

  ngOnInit() {
    this.companyService.getCompanies().subscribe(
      data => {
        this.companies = JSON.parse(data);
        console.log(this.companies);
      }
    );
  }

}
