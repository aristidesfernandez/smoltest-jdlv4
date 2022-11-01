import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IFormulaCounterType } from '../formula-counter-type.model';
import { FormulaCounterTypeService } from '../service/formula-counter-type.service';

import { FormulaCounterTypeRoutingResolveService } from './formula-counter-type-routing-resolve.service';

describe('FormulaCounterType routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: FormulaCounterTypeRoutingResolveService;
  let service: FormulaCounterTypeService;
  let resultFormulaCounterType: IFormulaCounterType | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(FormulaCounterTypeRoutingResolveService);
    service = TestBed.inject(FormulaCounterTypeService);
    resultFormulaCounterType = undefined;
  });

  describe('resolve', () => {
    it('should return IFormulaCounterType returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFormulaCounterType = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultFormulaCounterType).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFormulaCounterType = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultFormulaCounterType).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IFormulaCounterType>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFormulaCounterType = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultFormulaCounterType).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
