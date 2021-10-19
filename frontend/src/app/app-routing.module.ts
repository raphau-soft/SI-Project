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
import { CompanyComponent } from './company/company.component';
import { InCompanyComponent } from './in-company/in-company.component';

const routes: Routes = [
  { path: 'signup', component: RegisterComponent },
  { path: 'in-company/:id', component: InCompanyComponent},
  { path: 'signin', component: LoginComponent },
  { path: 'in-company/new-room/:id', component: NewRoomComponent },
  { path: 'company', component: CompanyComponent },
  { path: 'in-company/new-position/:id', component: NewPositionComponent },
  { path: 'in-company/new-employee/:id', component: NewEmployeeComponent },
  { path: 'update-employee/:id', component: UpdateEmployeeComponent },
  { path: 'employees', component: TableComponent },
  { path: '', redirectTo: 'signin', pathMatch: 'full' },
  { path: 'summary', component: SummaryComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
