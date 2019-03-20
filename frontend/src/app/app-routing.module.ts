import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {EmployeeListComponent} from './employee-list/employee-list.component';
import {PageNotFoundComponent} from './page-not-found/page-not-found.component';
import {EmployeeDetailComponent} from './employee-detail/employee-detail.component';
import {TeamGeneratorComponent} from './team-generator/team-generator.component';

const routes: Routes = [
  {path: 'employee/:code', component: EmployeeDetailComponent},
  {path: 'team-generator', component: TeamGeneratorComponent},
  {path: '', redirectTo: '/team-generator', pathMatch: 'full'},
  {path: '**', component: PageNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
