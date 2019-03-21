import { TestBed } from '@angular/core/testing';

import { CapabilitiesService } from './capabilities.service';

describe('CapabilitiesService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CapabilitiesService = TestBed.get(CapabilitiesService);
    expect(service).toBeTruthy();
  });
});
