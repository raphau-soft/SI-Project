import { Component, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { Circle, Line, Stroke, Polygon, Vertex, Polyline } from 'angular-svg';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass'],
})
export class AppComponent {

  constructor() {
  }
  title = 'EmployeeManager';

  openNav() {
    document.getElementById('mySidenav').style.width = '250px';
    document.getElementById('main').style.marginLeft = '250px';
  }

  closeNav() {
    document.getElementById('mySidenav').style.width = '0px';
    document.getElementById('main').style.marginLeft = '0px';
  }

}

// npm install angular svg