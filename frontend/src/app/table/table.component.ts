import { Component, OnInit, ViewChild } from '@angular/core';

import { Employee } from '../classes/Employee';
import { Room } from '../classes/Room';
import { Position } from '../classes/Position';
import { TableService } from '../table.service';
import { Data } from '../classes/Data';
import { EmployeeService } from '../employee.service';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.sass']
})
export class TableComponent implements OnInit {

  employees: Employee[];
  positions: Position[];
  rooms: Room[];
  dataAll: Data[];
  firstNames = [];
  nameFilter = '';
  lastNameFilter = '';
  positionFilter = '';
  minFilter: number;
  maxFilter: number;
  min: number;
  max: number;
  data: any;

  // TODO probably observables

  columns = ['ID', 'First Name', 'Last Name', 'Position', 'Room', 'Salary'];

  constructor(
    private tableService: TableService,
    private employeeService: EmployeeService,
  ) { }

  ngOnInit() {

    // this.employees = this.tableService.getEmployees();
    // this.rooms = this.tableService.getRooms();
    // this.positions = this.tableService.getPositions();
    // this.maxFilter = this.employeeService.getMaxSalary();
    // this.minFilter = this.employeeService.getMinSalary();
    // this.min = this.minFilter;
    // this.max = this.maxFilter;
    // this.dataAll = this.prepareData();


  }


  prepareData() {
    // let employees: Employee[];

    // // tslint:disable-next-line: max-line-length
    // this.nameFilter === '' ? employees = this.employees : employees = this.employees.filter(employee => employee.firstName === this.nameFilter);

    // // tslint:disable-next-line: max-line-length
    // this.lastNameFilter === '' ? employees = employees : employees = employees.filter(employee => employee.lastName === this.lastNameFilter);

    // // tslint:disable-next-line: max-line-length
    // this.positionFilter === '' ? employees = employees : employees = employees.filter(employee => this.tableService.getPosition(employee.positionId) === this.positionFilter);
    // employees = employees.filter(employee => employee.salary >= this.minFilter && employee.salary <= this.maxFilter);



    // const data = [];
    // // tslint:disable-next-line: prefer-for-of
    // for (let i = 0; i < employees.length; i++) {
    //   let x = false;
    //   // tslint:disable-next-line: prefer-for-of
    //   for (let j = 0; j < this.firstNames.length; j++) {
    //     if (employees[i].firstName === this.firstNames[j]) {
    //       x = true;
    //       break;
    //     }
    //   }

    //   if (!x) {
    //     this.firstNames.push(employees[i].firstName);
    //   }

    //   data.push(new Data(
    //     employees[i].id,
    //     employees[i].firstName,
    //     employees[i].lastName,
    //     this.tableService.getPosition(employees[i].positionId),
    //     this.tableService.getRoom(employees[i].roomId),
    //     '$' + employees[i].salary.toString(),
    //   )
    //   );
    // }
    // return data;
  }

  remove(data: Data) {
    // this.tableService.removeEmp(data.id);
    // this.employees = this.tableService.getEmployees();
    // this.dataAll = this.prepareData();
  }

  refresh() {
    // this.dataAll = this.prepareData();
  }

  collapse() {
    const el = document.getElementById('filter');
    console.log(el.style.height);
    if (el.style.height === '0px' || el.style.height === '') {
      el.style.height = '150px';
    } else {
      el.style.height = '0px';
    }

  }

}
