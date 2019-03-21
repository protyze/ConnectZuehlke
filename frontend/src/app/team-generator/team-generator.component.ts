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
  filters: Filter;
  teamsResults: Array<Team> = [];

  constructor(private employeeService: EmployeeService) {
  }

  ngOnInit() {
    this.employeeService.fetchTeams(this.filters);
    this.employeeService
      .teamsFetched
      .subscribe(() => {
        this.getTeams();
      });
  }

  onFilterChanged(filters: Filter) {
    this.filters = filters;
    // TODO: API Request
    this.employeeService.fetchTeams(filters);
  }

  getTeams() {
    this.teamsResults = this.employeeService.getTeams();
  }

  ngOnDestroy() {
    this.employeeService.teamsFetched.unsubscribe();
  }
}
