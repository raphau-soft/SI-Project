import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NewRoomComponent } from './new-room/new-room.component';
import { NewPositionComponent } from './new-position/new-position.component';
import { NewEmployeeComponent } from './new-employee/new-employee.component';
import { UpdateEmployeeComponent } from './update-employee/update-employee.component';
import { TableComponent } from './table/table.component';
import { SummaryComponent } from './summary/summary.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { BuildingComponent } from './building/building.component';
import { InBuildingComponent } from './in-building/in-building.component';
import { UpdatePositionComponent } from './update-position/update-position.component';

const routes: Routes = [
  { path: 'signup', component: RegisterComponent },
  { path: 'in-building/:id', component: InBuildingComponent},
  { path: 'signin', component: LoginComponent },
  { path: 'in-building/new-room/:id', component: NewRoomComponent },
  { path: 'building', component: BuildingComponent },
  { path: 'in-building/new-position/:id', component: NewPositionComponent },
  { path: 'in-building/new-employee/:id', component: NewEmployeeComponent },
  { path: 'update-employee/:id', component: UpdateEmployeeComponent },
  { path: 'update-position/:id', component: UpdatePositionComponent },
  { path: 'employees', component: TableComponent },
  { path: '', redirectTo: 'signin', pathMatch: 'full' },
  { path: 'summary', component: SummaryComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
