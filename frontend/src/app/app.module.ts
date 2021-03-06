import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faHeart } from '@fortawesome/free-solid-svg-icons';
import { faAngular, faJava } from '@fortawesome/free-brands-svg-icons';
import { intersectionObserverPreset, LazyLoadImageModule } from 'ng-lazyload-image';

import { library } from '@fortawesome/fontawesome-svg-core';
import { EmployeeListComponent } from './employee-list/employee-list.component';
import { EmployeeComponent } from './employee-list/employee/employee.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { SearchPipe } from './search.pipe';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {
  MatButtonModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatSelectModule,
  MatSidenavModule,
  MatSnackBarModule,
  MatToolbarModule,
  MatCardModule,
  MatGridListModule,
  MatButtonToggleModule,
  MatProgressBarModule,
  MatChipsModule
} from '@angular/material';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { NavigationComponent } from './navigation/navigation.component';
import { LayoutModule } from '@angular/cdk/layout';
import { SidebarComponent } from './navigation/sidebar/sidebar.component';
import { TopbarComponent } from './navigation/topbar/topbar.component';
import { EmployeeDetailComponent } from './employee-detail/employee-detail.component';
import { TeamGeneratorComponent } from './team-generator/team-generator.component';
import { AgmCoreModule } from '@agm/core';
import { ErrorRequestInterceptor } from './common/error-request-interceptor';
import { FiltersComponent } from './filters/filters.component';
import { TeamListComponent } from './team-list/team-list.component';
import { TeamComponent } from './team/team.component';
import { TeamMemberComponent } from './team-member/team-member.component';
import { EmployeeImageComponent } from './employee-image/employee-image.component';
import { RelationWheelComponent } from './chart/relation-wheel/relation-wheel/relation-wheel.component';
import { ATeamAudioComponent } from './a-team-audio/a-team-audio.component';

// For more icons, please checkout https://fontawesome.com/icons?d=gallery
library.add(faHeart);
library.add(faJava);
library.add(faAngular);

@NgModule({
  declarations: [
    AppComponent,
    EmployeeComponent,
    EmployeeListComponent,
    PageNotFoundComponent,
    SearchPipe,
    NavigationComponent,
    SidebarComponent,
    TopbarComponent,
    EmployeeDetailComponent,
    TeamGeneratorComponent,
    FiltersComponent,
    TeamListComponent,
    TeamComponent,
    RelationWheelComponent,
    TeamComponent,
    TeamMemberComponent,
    EmployeeImageComponent,
    ATeamAudioComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    FontAwesomeModule,
    LazyLoadImageModule.forRoot({
      preset: intersectionObserverPreset
    }),
    HttpClientModule,
    BrowserAnimationsModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    MatIconModule,
    MatSnackBarModule,
    MatCardModule,
    LayoutModule,
    MatToolbarModule,
    MatSidenavModule,
    MatListModule,
    MatGridListModule,
    MatButtonToggleModule,
    MatProgressBarModule,
    MatChipsModule,
    AgmCoreModule.forRoot({ apiKey: 'AIzaSyBrgp24CvFV3M0PZGByVDVEG0qn56k8Y-g' }),
    NgMultiSelectDropDownModule.forRoot()
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS, useClass: ErrorRequestInterceptor, multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
