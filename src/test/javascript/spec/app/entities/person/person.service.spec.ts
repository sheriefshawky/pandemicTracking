import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { PersonService } from 'app/entities/person/person.service';
import { IPerson, Person } from 'app/shared/model/person.model';

describe('Service Tests', () => {
  describe('Person Service', () => {
    let injector: TestBed;
    let service: PersonService;
    let httpMock: HttpTestingController;
    let elemDefault: IPerson;
    let expectedResult: IPerson | IPerson[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PersonService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Person(0, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Person', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Person()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Person', () => {
        const returnedFromService = Object.assign(
          {
            userId: 1,
            mobileNo: 'BBBBBB',
            deviceId: 'BBBBBB',
            verificationCode: 'BBBBBB',
            status: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Person', () => {
        const returnedFromService = Object.assign(
          {
            userId: 1,
            mobileNo: 'BBBBBB',
            deviceId: 'BBBBBB',
            verificationCode: 'BBBBBB',
            status: 1
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

      it('should delete a Person', () => {
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
