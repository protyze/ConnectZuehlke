import { Component, OnInit, OnDestroy } from '@angular/core';

import { Filter } from '../domain/Filter';

import { EmployeeService } from '../services/employee.service';
import { Team } from '../domain/Team';

@Component({
  selector: 'app-team-generator',
  templateUrl: './team-generator.component.html',
  styleUrls: ['./team-generator.component.scss']
})
export class TeamGeneratorComponent implements OnInit, OnDestroy {
  private filters: Filter;
  private teamResults: Array<Team> = [];

  constructor(private swEmployee: EmployeeService) { }

  ngOnInit() {
    this.swEmployee.teamsFetched.subscribe(() => {
      this.getTeams();
    });
  }

  onFilterChanged(filters: Filter) {
    this.filters = filters;
    // TODO: API Request
    this.swEmployee.fetchTeams(filters);
  }

  getTeams() {
    this.teamResults = this.swEmployee.getTeams();
  }

  ngOnDestroy() {
    this.swEmployee.teamsFetched.unsubscribe();
  }
}
