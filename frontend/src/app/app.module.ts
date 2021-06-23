import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { ReactiveFormsModule } from '@angular/forms';
import { NgxFloatButtonModule } from 'ngx-float-button';
import { FormsModule } from '@angular/forms';
import {MatStepperModule, MatInputModule, MatButtonModule, MatPaginator, MatTableDataSource} from '@angular/material';
import { NgSelectModule } from '@ng-select/ng-select';
// tslint:disable-next-line: max-line-length
import { SvgCircleModule, SvgLineModule, SvgPolygonModule, SvgPolylineModule, SvgTextModule, SvgPathModule, SvgEllipseModule } from 'angular-svg';
import {DataTableModule} from 'angular-6-datatable';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { StorageServiceModule } from 'angular-webstorage-service';
import { NewRoomComponent } from './new-room/new-room.component';
import { NewPositionComponent } from './new-position/new-position.component';
import { NewEmployeeComponent } from './new-employee/new-employee.component';
import { UpdateEmployeeComponent } from './update-employee/update-employee.component';
import { TableComponent } from './table/table.component';
import { SummaryComponent } from './summary/summary.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  declarations: [
    AppComponent,
    NewRoomComponent,
    NewPositionComponent,
    NewEmployeeComponent,
    UpdateEmployeeComponent,
    TableComponent,
    SummaryComponent,
  ],
  imports: [
    NgSelectModule,
    DataTableModule,
    BrowserModule,
    AppRoutingModule,
    StorageServiceModule,
    NgbModule,
    MatStepperModule,
    MatInputModule,
    MatButtonModule,
    ReactiveFormsModule,
    FormsModule,
    NgxFloatButtonModule,
    BrowserAnimationsModule,
    SvgCircleModule,
    SvgLineModule,
    SvgPolygonModule,
    SvgPolylineModule,
    SvgTextModule,
    SvgPathModule,
    SvgEllipseModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
