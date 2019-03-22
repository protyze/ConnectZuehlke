import { Location } from './Location';
import { Capabilities } from 'protractor';

export class Filter {
  locations: Array<Location> = [];
  skills: Array<Capabilities> = [];

  constructor(public numberOfEmployees = 5) {
  }
}
