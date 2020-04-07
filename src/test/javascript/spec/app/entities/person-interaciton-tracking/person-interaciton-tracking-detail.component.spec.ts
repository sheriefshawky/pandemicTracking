import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PandemicTrackingTestModule } from '../../../test.module';
import { PersonInteracitonTrackingDetailComponent } from 'app/entities/person-interaciton-tracking/person-interaciton-tracking-detail.component';
import { PersonInteracitonTracking } from 'app/shared/model/person-interaciton-tracking.model';

describe('Component Tests', () => {
  describe('PersonInteracitonTracking Management Detail Component', () => {
    let comp: PersonInteracitonTrackingDetailComponent;
    let fixture: ComponentFixture<PersonInteracitonTrackingDetailComponent>;
    const route = ({ data: of({ personInteracitonTracking: new PersonInteracitonTracking(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PandemicTrackingTestModule],
        declarations: [PersonInteracitonTrackingDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PersonInteracitonTrackingDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PersonInteracitonTrackingDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load personInteracitonTracking on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.personInteracitonTracking).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
