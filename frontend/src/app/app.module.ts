import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { ReactiveFormsModule } from '@angular/forms';
import { NgxFloatButtonModule } from 'ngx-float-button';
import { FormsModule } from '@angular/forms';
import {MatStepperModule, MatInputModule, MatButtonModule, MatDividerModule, MatIconModule} from '@angular/material';
import { NgSelectModule } from '@ng-select/ng-select';
// tslint:disable-next-line: max-line-length
import { SvgCircleModule, SvgLineModule, SvgPolygonModule, SvgPolylineModule, SvgTextModule, SvgPathModule, SvgEllipseModule } from 'angular-svg';
import {DataTableModule} from 'angular-6-datatable';
import { HttpClientModule } from '@angular/common/http';

import { authInterceptorProviders } from './_helpers/auth.interceptor';
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
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { BuildingComponent } from './building/building.component';
import { InBuildingComponent } from './in-building/in-building.component';
import { UpdateRoomComponent } from './update-room/update-room.component';
import { UpdatePositionComponent } from './update-position/update-position.component';

@NgModule({
  declarations: [
    AppComponent,
    NewRoomComponent,
    NewPositionComponent,
    NewEmployeeComponent,
    UpdateEmployeeComponent,
    TableComponent,
    SummaryComponent,
    RegisterComponent,
    LoginComponent,
    BuildingComponent,
    InBuildingComponent,
    UpdateRoomComponent,
    UpdatePositionComponent,
  ],
  imports: [
    NgSelectModule,
    DataTableModule,
    BrowserModule,
    AppRoutingModule,
    StorageServiceModule,
    NgbModule,
    MatIconModule,
    MatStepperModule,
    MatDividerModule,
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
    HttpClientModule,
    SvgEllipseModule
  ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
