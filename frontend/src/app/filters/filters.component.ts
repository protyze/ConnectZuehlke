import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

import { CapabilitiesService } from '../services/capabilities.service';

import { Filter } from '../domain/Filter';

import { Capability } from '../domain/Capabilities';

@Component({
  selector: 'app-filters',
  templateUrl: './filters.component.html',
  styleUrls: ['./filters.component.scss']
})
export class FiltersComponent implements OnInit {

  @Input() selectedFilters: Filter;
  @Output() filtersChanged = new EventEmitter<Filter>();

  capabilities: Array<Capability> = [];

  dropdownSettings = {
    singleSelection: false,
    idField: 'id',
    textField: 'name',
    selectAllText: 'Select All',
    unSelectAllText: 'UnSelect All',
    itemsShowLimit: 3,
    allowSearchFilter: true
  };

  constructor(private serviceCapabilities: CapabilitiesService) {

  }

  ngOnInit() {
    this.getCapabilities();
    this.serviceCapabilities.capabilitiesFetched.subscribe(() => {
      this.getCapabilities();
    });
    this.serviceCapabilities.fetchCapabilities();
  }

  onFormSubmit(form) {
    const { numberOfEmployees, skills } = form.value;
    console.log(form);

    if (!form.invalid) {
      this.onFilterChanged({
        ...this.selectedFilters,
        numberOfEmployees,
        skills
      });
    }
  }

  onFilterChanged(newFilters: Filter) {
    this.filtersChanged.emit(newFilters);
  }

  getCapabilities() {
    this.capabilities = this.serviceCapabilities.getCapabilities();
  }
}
