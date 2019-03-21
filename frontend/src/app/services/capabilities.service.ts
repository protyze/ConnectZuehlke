import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CapabilitiesService {
  private capabilities: Array<string> = ['Development', 'Java', 'Whatever'];

  capabilitiesFetched = new Subject<void>();

  constructor(private http: HttpClient) { }

  public fetchCapabilities() {
    this.http.get('api/capabilities').subscribe((data: Array<{ id: number, name: string }>) => {
      this.capabilities = data.map(capability => capability.name);
      this.capabilitiesFetched.next();
    });
  }

  public getCapabilities() {
    return this.capabilities;
  }
}
