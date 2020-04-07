import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PandemicTrackingTestModule } from '../../../test.module';
import { PersonTrackingDetailComponent } from 'app/entities/person-tracking/person-tracking-detail.component';
import { PersonTracking } from 'app/shared/model/person-tracking.model';

describe('Component Tests', () => {
  describe('PersonTracking Management Detail Component', () => {
    let comp: PersonTrackingDetailComponent;
    let fixture: ComponentFixture<PersonTrackingDetailComponent>;
    const route = ({ data: of({ personTracking: new PersonTracking(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PandemicTrackingTestModule],
        declarations: [PersonTrackingDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PersonTrackingDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PersonTrackingDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load personTracking on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.personTracking).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
