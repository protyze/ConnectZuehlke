import { Injectable } from '@angular/core';
import { Subject } from "rxjs";
import { HttpClient } from "@angular/common/http";
import { Location } from "../domain/Location";

@Injectable({
  providedIn: 'root'
})
export class LocationsService {

  private locations: Array<Location> = [{ id: 1, cityName: 'Bern' }, { id: 2, cityName: 'Luzern' }, { id: 3, cityName: 'Zürich' }];

  locationsFetched = new Subject<void>();

  constructor(private http: HttpClient) { }

  public fetchLocations() {
    this.http.get('api/locations').subscribe((data: Array<string>) => {
      this.locations = data.map((cityName, id) => (new Location(cityName, id)));
      this.locationsFetched.next();
    });
  }

  public getLocations() {
    return this.locations;
  }
}
