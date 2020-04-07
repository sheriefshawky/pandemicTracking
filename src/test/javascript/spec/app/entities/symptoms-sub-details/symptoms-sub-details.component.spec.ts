import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { PandemicTrackingTestModule } from '../../../test.module';
import { SymptomsSubDetailsComponent } from 'app/entities/symptoms-sub-details/symptoms-sub-details.component';
import { SymptomsSubDetailsService } from 'app/entities/symptoms-sub-details/symptoms-sub-details.service';
import { SymptomsSubDetails } from 'app/shared/model/symptoms-sub-details.model';

describe('Component Tests', () => {
  describe('SymptomsSubDetails Management Component', () => {
    let comp: SymptomsSubDetailsComponent;
    let fixture: ComponentFixture<SymptomsSubDetailsComponent>;
    let service: SymptomsSubDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PandemicTrackingTestModule],
        declarations: [SymptomsSubDetailsComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: {
                subscribe: (fn: (value: Data) => void) =>
                  fn({
                    pagingParams: {
                      predicate: 'id',
                      reverse: false,
                      page: 0
                    }
                  })
              }
            }
          }
        ]
      })
        .overrideTemplate(SymptomsSubDetailsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SymptomsSubDetailsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SymptomsSubDetailsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SymptomsSubDetails(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.symptomsSubDetails && comp.symptomsSubDetails[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SymptomsSubDetails(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.symptomsSubDetails && comp.symptomsSubDetails[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
