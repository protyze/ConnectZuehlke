import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

import { HttpClient } from '@angular/common/http';

import { Capability } from '../domain/Capabilities';

@Injectable({
  providedIn: 'root'
})
export class CapabilitiesService {
  private capabilities: Array<Capability> = [];

  capabilitiesFetched = new Subject<void>();

  constructor(private http: HttpClient) { }

  public fetchCapabilities() {
    this.http.get('api/capabilities').subscribe((data: Array<Capability>) => {
      this.capabilities = data;
      this.capabilitiesFetched.next();
    });
  }

  public getCapabilities() {
    return this.capabilities;
  }
}
