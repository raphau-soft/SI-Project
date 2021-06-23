import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NewRoomComponent } from './new-room/new-room.component';
import { NewPositionComponent } from './new-position/new-position.component';
import { NewEmployeeComponent } from './new-employee/new-employee.component';
import { UpdateEmployeeComponent } from './update-employee/update-employee.component';
import { TableComponent } from './table/table.component';
import { SummaryComponent } from './summary/summary.component';

const routes: Routes = [
  { path: 'room', component: NewRoomComponent },
  { path: 'position', component: NewPositionComponent },
  { path: 'add-employees', component: NewEmployeeComponent },
  { path: 'update-employee/:id', component: UpdateEmployeeComponent },
  { path: 'employees', component: TableComponent },
  { path: '', redirectTo: 'employees', pathMatch: 'full' },
  { path: 'summary', component: SummaryComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
