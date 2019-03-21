import { Capability } from './Capabilities';

export class Filter {
  skills: Array<Capability> = [];

  constructor(public numberOfEmployees = 5) {
  }
}
