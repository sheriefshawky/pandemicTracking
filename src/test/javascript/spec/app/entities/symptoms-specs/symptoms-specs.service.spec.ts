import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { SymptomsSpecsService } from 'app/entities/symptoms-specs/symptoms-specs.service';
import { ISymptomsSpecs, SymptomsSpecs } from 'app/shared/model/symptoms-specs.model';

describe('Service Tests', () => {
  describe('SymptomsSpecs Service', () => {
    let injector: TestBed;
    let service: SymptomsSpecsService;
    let httpMock: HttpTestingController;
    let elemDefault: ISymptomsSpecs;
    let expectedResult: ISymptomsSpecs | ISymptomsSpecs[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SymptomsSpecsService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new SymptomsSpecs(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a SymptomsSpecs', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new SymptomsSpecs()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a SymptomsSpecs', () => {
        const returnedFromService = Object.assign(
          {
            code: 'BBBBBB',
            descriptionAr: 'BBBBBB',
            descriptionEn: 'BBBBBB',
            specType: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of SymptomsSpecs', () => {
        const returnedFromService = Object.assign(
          {
            code: 'BBBBBB',
            descriptionAr: 'BBBBBB',
            descriptionEn: 'BBBBBB',
            specType: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a SymptomsSpecs', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
