import { TestBed } from '@angular/core/testing';

import { HttpgeneralService } from './httpgeneral.service';

describe('HttpgeneralService', () => {
  let service: HttpgeneralService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HttpgeneralService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
