import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PandemicTrackingTestModule } from '../../../test.module';
import { SymptomsSpecsDetailComponent } from 'app/entities/symptoms-specs/symptoms-specs-detail.component';
import { SymptomsSpecs } from 'app/shared/model/symptoms-specs.model';

describe('Component Tests', () => {
  describe('SymptomsSpecs Management Detail Component', () => {
    let comp: SymptomsSpecsDetailComponent;
    let fixture: ComponentFixture<SymptomsSpecsDetailComponent>;
    const route = ({ data: of({ symptomsSpecs: new SymptomsSpecs(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PandemicTrackingTestModule],
        declarations: [SymptomsSpecsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SymptomsSpecsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SymptomsSpecsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load symptomsSpecs on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.symptomsSpecs).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
