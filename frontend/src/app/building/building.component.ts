import { Component, OnInit } from '@angular/core';
import { BuildingService } from '../building.service';
import { Building } from '../classes/building'
import { RoomService } from '../room.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-building',
  templateUrl: './building.component.html',
  styleUrls: ['./building.component.css']
})
export class BuildingComponent implements OnInit {

  buildings: Building[];
  

  constructor(private buildingService: BuildingService, private router: Router) { }

  ngOnInit() {
    this.buildingService.getCompanies().subscribe(
      data => {
        this.buildings = JSON.parse(data);
        console.log(this.buildings);
      }
    );
  }

}
