import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { SymptomsSumbissionService } from 'app/entities/symptoms-sumbission/symptoms-sumbission.service';
import { ISymptomsSumbission, SymptomsSumbission } from 'app/shared/model/symptoms-sumbission.model';

describe('Service Tests', () => {
  describe('SymptomsSumbission Service', () => {
    let injector: TestBed;
    let service: SymptomsSumbissionService;
    let httpMock: HttpTestingController;
    let elemDefault: ISymptomsSumbission;
    let expectedResult: ISymptomsSumbission | ISymptomsSumbission[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SymptomsSumbissionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new SymptomsSumbission(0, 0, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            submissionTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a SymptomsSumbission', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            submissionTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            submissionTime: currentDate
          },
          returnedFromService
        );

        service.create(new SymptomsSumbission()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a SymptomsSumbission', () => {
        const returnedFromService = Object.assign(
          {
            userId: 1,
            submissionTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            submissionTime: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of SymptomsSumbission', () => {
        const returnedFromService = Object.assign(
          {
            userId: 1,
            submissionTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            submissionTime: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a SymptomsSumbission', () => {
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
