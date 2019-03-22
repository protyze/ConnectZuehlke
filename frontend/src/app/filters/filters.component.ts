import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

import {LocationsService} from "../services/locations.service";

import {Filter} from '../domain/Filter';
import {Location} from "../domain/Location";

@Component({
  selector: 'app-filters',
  templateUrl: './filters.component.html',
  styleUrls: ['./filters.component.scss']
})
export class FiltersComponent implements OnInit {

  @Input() selectedFilters: Filter;
  @Output() filtersChanged = new EventEmitter<Filter>();

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

  constructor(private serviceLocations:LocationsService) {

  }

  ngOnInit() {
    this.getLocations();
    this.serviceLocations.locationsFetched.subscribe(() => {
      this.serviceLocations.getLocations();
    });
    this.serviceLocations.fetchLocations();
  }

  onFormSubmit(form) {
    const { numberOfEmployees, locations } = form.value;
    console.log(form);

    if (!form.invalid) {
      this.onFilterChanged({
        ...this.selectedFilters,
        numberOfEmployees,
        locations
      });
    }
  }

  onFilterChanged(newFilters: Filter) {
    this.filtersChanged.emit(newFilters);
  }

  getLocations() {
    this.locations = this.serviceLocations.getLocations();
  }
}
