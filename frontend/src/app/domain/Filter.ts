import { Location } from './Location';

export class Filter {
  locations: Array<Location> = [];

  constructor(public numberOfEmployees = 5) {
  }
}
