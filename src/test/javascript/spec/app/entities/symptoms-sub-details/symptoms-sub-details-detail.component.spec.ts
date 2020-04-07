import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PandemicTrackingTestModule } from '../../../test.module';
import { SymptomsSubDetailsDetailComponent } from 'app/entities/symptoms-sub-details/symptoms-sub-details-detail.component';
import { SymptomsSubDetails } from 'app/shared/model/symptoms-sub-details.model';

describe('Component Tests', () => {
  describe('SymptomsSubDetails Management Detail Component', () => {
    let comp: SymptomsSubDetailsDetailComponent;
    let fixture: ComponentFixture<SymptomsSubDetailsDetailComponent>;
    const route = ({ data: of({ symptomsSubDetails: new SymptomsSubDetails(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PandemicTrackingTestModule],
        declarations: [SymptomsSubDetailsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SymptomsSubDetailsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SymptomsSubDetailsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load symptomsSubDetails on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.symptomsSubDetails).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
