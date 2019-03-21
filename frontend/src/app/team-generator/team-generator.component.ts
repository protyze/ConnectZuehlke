import { Component, OnDestroy, OnInit } from '@angular/core';

import { Filter } from '../domain/Filter';

import { EmployeeService } from '../services/employee.service';
import { Team } from '../domain/Team';

@Component({
  selector: 'app-team-generator',
  templateUrl: './team-generator.component.html',
  styleUrls: ['./team-generator.component.scss']
})
export class TeamGeneratorComponent implements OnInit, OnDestroy {
  filters = new Filter(7);
  teamsResults: Array<Team> = [];
  isLoading: false;

  constructor(private employeeService: EmployeeService) {
  }

  ngOnInit() {
    this.getTeams();
    this.getLoadingState();
    this.employeeService
      .teamsFetched
      .subscribe(() => {
        this.getTeams();
      });
    this.employeeService.loadingChanged.subscribe(() => {
      this.getLoadingState();
    })
    this.employeeService.fetchTeams(this.filters);
  }

  onFilterChanged(filters: Filter) {
    this.filters = filters;
    this.employeeService.fetchTeams(filters);
  }

  getTeams() {
    this.teamsResults = this.employeeService.getTeams();
  }

  getLoadingState() {
    this.isLoading = this.employeeService.isLoading();
  }

  ngOnDestroy() {
    this.employeeService.teamsFetched.unsubscribe();
    this.employeeService.loadingChanged.unsubscribe();
  }
}
