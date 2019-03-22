import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

import { CapabilitiesService } from '../services/capabilities.service';
import { LocationsService } from '../services/locations.service';

import { Filter } from '../domain/Filter';
import { Location } from '../domain/Location';
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
  locations: Array<Location> = [];

  dropdownSettings = {
    singleSelection: false,
    idField: 'id',
    textField: 'cityName',
    selectAllText: 'Select All',
    unSelectAllText: 'UnSelect All',
    itemsShowLimit: 3,
    allowSearchFilter: true
  };

  skillsDropdownSettings = {
    ...this.dropdownSettings,
    textField: 'name'
  }

  constructor(private serviceLocations: LocationsService, private serviceCapabilities: CapabilitiesService) {

  }

  ngOnInit() {
    this.getCapabilities();
    this.serviceCapabilities.capabilitiesFetched.subscribe(() => {
      this.getCapabilities();
    });
    this.serviceCapabilities.fetchCapabilities();

    this.getLocations();
    this.serviceLocations.locationsFetched.subscribe(() => {
      this.serviceLocations.getLocations();
    });
    this.serviceLocations.fetchLocations();
  }

  onFormSubmit(form) {
    const { numberOfEmployees, locations, skills } = form.value;
    console.log(form);

    if (!form.invalid) {
      this.onFilterChanged({
        ...this.selectedFilters,
        numberOfEmployees,
        locations,
        skills
      });
    }
  }

  onFilterChanged(newFilters: Filter) {
    this.filtersChanged.emit(newFilters);
  }

  getLocations() {
    this.locations = this.serviceLocations.getLocations();
  }

  getCapabilities() {
    this.capabilities = this.serviceCapabilities.getCapabilities();
  }
}
