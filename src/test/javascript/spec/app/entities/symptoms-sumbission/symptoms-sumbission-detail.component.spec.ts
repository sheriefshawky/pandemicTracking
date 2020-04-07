import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PandemicTrackingTestModule } from '../../../test.module';
import { SymptomsSumbissionDetailComponent } from 'app/entities/symptoms-sumbission/symptoms-sumbission-detail.component';
import { SymptomsSumbission } from 'app/shared/model/symptoms-sumbission.model';

describe('Component Tests', () => {
  describe('SymptomsSumbission Management Detail Component', () => {
    let comp: SymptomsSumbissionDetailComponent;
    let fixture: ComponentFixture<SymptomsSumbissionDetailComponent>;
    const route = ({ data: of({ symptomsSumbission: new SymptomsSumbission(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PandemicTrackingTestModule],
        declarations: [SymptomsSumbissionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SymptomsSumbissionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SymptomsSumbissionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load symptomsSumbission on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.symptomsSumbission).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
