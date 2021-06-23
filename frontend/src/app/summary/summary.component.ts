import { Component, OnInit } from '@angular/core';
import { SummaryService } from '../summary.service';
import { Room } from '../classes/Room';
import { Position } from '../classes/Position';
import { Employee } from '../classes/Employee';

@Component({
  selector: 'app-summary',
  templateUrl: './summary.component.html',
  styleUrls: ['./summary.component.sass']
})
export class SummaryComponent implements OnInit {

  // TODO usage of rooms, employees per position etc

  rooms: Room[];
  positions: Position[];
  employees: Employee[];

  constructor(
    public summaryService: SummaryService,
  ) { }

  ngOnInit() {
    this.rooms = this.summaryService.getRooms();
    this.positions = this.summaryService.getPositions();
    this.employees = this.summaryService.getEmployees();
  }
}
