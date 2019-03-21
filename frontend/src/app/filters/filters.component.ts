import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

import { CapabilitiesService } from '../services/capabilities.service';

import { Filter } from '../domain/Filter';

@Component({
  selector: 'app-filters',
  templateUrl: './filters.component.html',
  styleUrls: ['./filters.component.scss']
})
export class FiltersComponent implements OnInit {

  @Input() selectedFilters: Filter;
  @Output() filtersChanged = new EventEmitter<Filter>();

  //filterForm: FormGroup;
  capabilities: Array<string> = [];

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
