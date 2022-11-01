import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICommandDevice } from '../command-device.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../command-device.test-samples';

import { CommandDeviceService } from './command-device.service';

const requireRestSample: ICommandDevice = {
  ...sampleWithRequiredData,
};

describe('CommandDevice Service', () => {
  let service: CommandDeviceService;
  let httpMock: HttpTestingController;
  let expectedResult: ICommandDevice | ICommandDevice[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CommandDeviceService);
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

    it('should create a CommandDevice', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const commandDevice = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(commandDevice).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CommandDevice', () => {
      const commandDevice = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(commandDevice).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CommandDevice', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CommandDevice', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CommandDevice', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCommandDeviceToCollectionIfMissing', () => {
      it('should add a CommandDevice to an empty array', () => {
        const commandDevice: ICommandDevice = sampleWithRequiredData;
        expectedResult = service.addCommandDeviceToCollectionIfMissing([], commandDevice);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(commandDevice);
      });

      it('should not add a CommandDevice to an array that contains it', () => {
        const commandDevice: ICommandDevice = sampleWithRequiredData;
        const commandDeviceCollection: ICommandDevice[] = [
          {
            ...commandDevice,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCommandDeviceToCollectionIfMissing(commandDeviceCollection, commandDevice);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CommandDevice to an array that doesn't contain it", () => {
        const commandDevice: ICommandDevice = sampleWithRequiredData;
        const commandDeviceCollection: ICommandDevice[] = [sampleWithPartialData];
        expectedResult = service.addCommandDeviceToCollectionIfMissing(commandDeviceCollection, commandDevice);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(commandDevice);
      });

      it('should add only unique CommandDevice to an array', () => {
        const commandDeviceArray: ICommandDevice[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const commandDeviceCollection: ICommandDevice[] = [sampleWithRequiredData];
        expectedResult = service.addCommandDeviceToCollectionIfMissing(commandDeviceCollection, ...commandDeviceArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const commandDevice: ICommandDevice = sampleWithRequiredData;
        const commandDevice2: ICommandDevice = sampleWithPartialData;
        expectedResult = service.addCommandDeviceToCollectionIfMissing([], commandDevice, commandDevice2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(commandDevice);
        expect(expectedResult).toContain(commandDevice2);
      });

      it('should accept null and undefined values', () => {
        const commandDevice: ICommandDevice = sampleWithRequiredData;
        expectedResult = service.addCommandDeviceToCollectionIfMissing([], null, commandDevice, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(commandDevice);
      });

      it('should return initial array if no CommandDevice is added', () => {
        const commandDeviceCollection: ICommandDevice[] = [sampleWithRequiredData];
        expectedResult = service.addCommandDeviceToCollectionIfMissing(commandDeviceCollection, undefined, null);
        expect(expectedResult).toEqual(commandDeviceCollection);
      });
    });

    describe('compareCommandDevice', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCommandDevice(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCommandDevice(entity1, entity2);
        const compareResult2 = service.compareCommandDevice(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCommandDevice(entity1, entity2);
        const compareResult2 = service.compareCommandDevice(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCommandDevice(entity1, entity2);
        const compareResult2 = service.compareCommandDevice(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
