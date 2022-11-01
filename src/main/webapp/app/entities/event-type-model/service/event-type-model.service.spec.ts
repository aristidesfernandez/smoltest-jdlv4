import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEventTypeModel } from '../event-type-model.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../event-type-model.test-samples';

import { EventTypeModelService } from './event-type-model.service';

const requireRestSample: IEventTypeModel = {
  ...sampleWithRequiredData,
};

describe('EventTypeModel Service', () => {
  let service: EventTypeModelService;
  let httpMock: HttpTestingController;
  let expectedResult: IEventTypeModel | IEventTypeModel[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EventTypeModelService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a EventTypeModel', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const eventTypeModel = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(eventTypeModel).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EventTypeModel', () => {
      const eventTypeModel = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(eventTypeModel).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EventTypeModel', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EventTypeModel', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a EventTypeModel', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEventTypeModelToCollectionIfMissing', () => {
      it('should add a EventTypeModel to an empty array', () => {
        const eventTypeModel: IEventTypeModel = sampleWithRequiredData;
        expectedResult = service.addEventTypeModelToCollectionIfMissing([], eventTypeModel);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(eventTypeModel);
      });

      it('should not add a EventTypeModel to an array that contains it', () => {
        const eventTypeModel: IEventTypeModel = sampleWithRequiredData;
        const eventTypeModelCollection: IEventTypeModel[] = [
          {
            ...eventTypeModel,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEventTypeModelToCollectionIfMissing(eventTypeModelCollection, eventTypeModel);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EventTypeModel to an array that doesn't contain it", () => {
        const eventTypeModel: IEventTypeModel = sampleWithRequiredData;
        const eventTypeModelCollection: IEventTypeModel[] = [sampleWithPartialData];
        expectedResult = service.addEventTypeModelToCollectionIfMissing(eventTypeModelCollection, eventTypeModel);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(eventTypeModel);
      });

      it('should add only unique EventTypeModel to an array', () => {
        const eventTypeModelArray: IEventTypeModel[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const eventTypeModelCollection: IEventTypeModel[] = [sampleWithRequiredData];
        expectedResult = service.addEventTypeModelToCollectionIfMissing(eventTypeModelCollection, ...eventTypeModelArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const eventTypeModel: IEventTypeModel = sampleWithRequiredData;
        const eventTypeModel2: IEventTypeModel = sampleWithPartialData;
        expectedResult = service.addEventTypeModelToCollectionIfMissing([], eventTypeModel, eventTypeModel2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(eventTypeModel);
        expect(expectedResult).toContain(eventTypeModel2);
      });

      it('should accept null and undefined values', () => {
        const eventTypeModel: IEventTypeModel = sampleWithRequiredData;
        expectedResult = service.addEventTypeModelToCollectionIfMissing([], null, eventTypeModel, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(eventTypeModel);
      });

      it('should return initial array if no EventTypeModel is added', () => {
        const eventTypeModelCollection: IEventTypeModel[] = [sampleWithRequiredData];
        expectedResult = service.addEventTypeModelToCollectionIfMissing(eventTypeModelCollection, undefined, null);
        expect(expectedResult).toEqual(eventTypeModelCollection);
      });
    });

    describe('compareEventTypeModel', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEventTypeModel(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEventTypeModel(entity1, entity2);
        const compareResult2 = service.compareEventTypeModel(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareEventTypeModel(entity1, entity2);
        const compareResult2 = service.compareEventTypeModel(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareEventTypeModel(entity1, entity2);
        const compareResult2 = service.compareEventTypeModel(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
