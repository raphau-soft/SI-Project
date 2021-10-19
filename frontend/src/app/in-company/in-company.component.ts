import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Room } from '../classes/Room';
import { Employee } from '../classes/Employee';
import { EmployeeService } from '../employee.service';
import { RoomService } from '../room.service';
import { PositionService } from '../position.service';
import { Position } from '../classes/Position';

@Component({
  selector: 'app-in-company',
  templateUrl: './in-company.component.html',
  styleUrls: ['./in-company.component.css']
})
export class InCompanyComponent implements OnInit {

  show = 0;
  company: string;
  rooms: Room[];
  positions: Position[];
  positionsToShow = [];
  roomsToShow = [];
  employeesToShow = [];
  columnsRooms = ['No.', 'Name', 'Capacity', 'Population', 'Width', "Height"];
  columnsEmployees = ['No.', 'Name', 'Surname', 'Position', 'Room', 'Salary'];
  columnsPositions = ['No.', 'Name', 'Min wage', 'Max wage', 'Usage']
  employees: Employee[];
  id: number;

  constructor(private roomService: RoomService, private route: ActivatedRoute, private router: Router,
    private employeeService: EmployeeService, private positionService: PositionService) { }

  ngOnInit() {
    this.id = +this.route.snapshot.paramMap.get('id');
    this.roomService.getRoomsByCompanyId(this.id).subscribe(
      data => {
        this.rooms = JSON.parse(data);
        if(this.rooms.length > 0){
          this.company = this.rooms[0].company.name;
        }
        for(let i = 0; i < this.rooms.length; i++){
          this.roomsToShow.push(new Room(this.rooms[i].id, this.rooms[i].number, this.rooms[i].name, this.rooms[i].capacity,
            this.rooms[i].population, this.rooms[i].width, this.rooms[i].height));
        }
      }
    )
    this.employeeService.getEmployeesByCompanyId(this.id).subscribe(
      data => {
        this.employees = JSON.parse(data);
        for(let i = 0; i < this.employees.length; i++){
          this.employeesToShow.push(new Employee(this.employees[i].id, this.employees[i].firstName, this.employees[i].lastName,
            this.employees[i].salary, this.employees[i].position, this.employees[i].room, this.employees[i].company, i+1));
        }
      }
    )
    this.positionService.getPositionsByCompanyId(this.id).subscribe(
      data => {
        this.positions = JSON.parse(data);
        for(let i = 0; i < this.positions.length; i++){
          this.positionsToShow.push(new Position(this.positions[i].id, this.positions[i].name, this.positions[i].minWage,
            this.positions[i].usage, this.positions[i].maxWage, i+1));
        }
      }
    )
  }

  showEmployees(){
    this.show = 1;
  }

  showRooms(){
    this.show = 0;
  }

  showPositions(){
    this.show = 2;
  }

  addRoom(){
    this.router.navigateByUrl("in-company/new-room/" + this.id);
  }
  
  addEmployee(){
    this.router.navigateByUrl("in-company/new-employee/" + this.id);
  }

  addPosition(){
    this.router.navigateByUrl("in-company/new-position/" + this.id);
  }

  removeRoom(data1){
    if(confirm("Are you sure to delete " + data1.name + "?")){
      this.roomService.deleteRoomById(data1.id).subscribe(
        data => {
          this.roomsToShow.forEach((element, index) => {
            if(element.id == data1.id) this.roomsToShow.splice(index, 1); 
          })
        }
      )
      this.employeesToShow = [];
      this.employeeService.getEmployeesByCompanyId(this.id).subscribe(
        data => {
          this.employees = JSON.parse(data);
          for(let i = 0; i < this.employees.length; i++){
            this.employeesToShow.push(new Employee(this.employees[i].id, this.employees[i].firstName, this.employees[i].lastName,
              this.employees[i].salary, this.employees[i].position, this.employees[i].room, this.employees[i].company, i));
          }
        }
      )
    }
  }

  removeEmployee(data1){
    if(confirm("Are you sure to delete " + data1.firstName + " " + data1.lastName + "?")){
      this.employeeService.deleteEmployeeById(data1.id).subscribe(
        data => {
          this.employeesToShow.forEach((element, index) => {
            if(element.id == data1.id) this.employeesToShow.splice(index, 1); 
          })
        }
      )
    }
  }

  removePosition(data1){
    if(confirm("Are you sure to delete " + data1.name + "?")){
      this.positionService.deletePositionById(data1.id).subscribe(
        data => {
          this.positionsToShow.forEach((element, index) => {
            if(element.id == data1.id) this.positionsToShow.splice(index, 1); 
          })
        }
      )
      this.employeesToShow = [];
      this.employeeService.getEmployeesByCompanyId(this.id).subscribe(
        data => {
          this.employees = JSON.parse(data);
          for(let i = 0; i < this.employees.length; i++){
            this.employeesToShow.push(new Employee(this.employees[i].id, this.employees[i].firstName, this.employees[i].lastName,
              this.employees[i].salary, this.employees[i].position, this.employees[i].room, this.employees[i].company, i));
          }
        }
      )
    }
  }

}
