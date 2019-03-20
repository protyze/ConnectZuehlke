import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

import { Filter } from '../domain/Filter';

@Component({
  selector: 'app-filters',
  templateUrl: './filters.component.html',
  styleUrls: ['./filters.component.scss']
})
export class FiltersComponent implements OnInit {

  @Input() selectedFilters: Filter;
  @Output() filtersChanged = new EventEmitter<Filter>();

  constructor() { }

  ngOnInit() {
  }

  onFilterChanged(newFilters: Filter) {
    this.filtersChanged.emit(newFilters);
  }

}
