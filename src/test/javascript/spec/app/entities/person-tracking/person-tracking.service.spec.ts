import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PersonTrackingService } from 'app/entities/person-tracking/person-tracking.service';
import { IPersonTracking, PersonTracking } from 'app/shared/model/person-tracking.model';

describe('Service Tests', () => {
  describe('PersonTracking Service', () => {
    let injector: TestBed;
    let service: PersonTrackingService;
    let httpMock: HttpTestingController;
    let elemDefault: IPersonTracking;
    let expectedResult: IPersonTracking | IPersonTracking[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PersonTrackingService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PersonTracking(0, 0, 0, 0, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            locationTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a PersonTracking', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            locationTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            locationTime: currentDate
          },
          returnedFromService
        );

        service.create(new PersonTracking()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PersonTracking', () => {
        const returnedFromService = Object.assign(
          {
            userId: 1,
            locationLongitude: 1,
            locationLatitude: 1,
            locationTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            locationTime: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PersonTracking', () => {
        const returnedFromService = Object.assign(
          {
            userId: 1,
            locationLongitude: 1,
            locationLatitude: 1,
            locationTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            locationTime: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a PersonTracking', () => {
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
