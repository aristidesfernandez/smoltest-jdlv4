import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CurrencyTypeFormService } from './currency-type-form.service';
import { CurrencyTypeService } from '../service/currency-type.service';
import { ICurrencyType } from '../currency-type.model';
import { IEstablishment } from 'app/entities/establishment/establishment.model';
import { EstablishmentService } from 'app/entities/establishment/service/establishment.service';

import { CurrencyTypeUpdateComponent } from './currency-type-update.component';

describe('CurrencyType Management Update Component', () => {
  let comp: CurrencyTypeUpdateComponent;
  let fixture: ComponentFixture<CurrencyTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let currencyTypeFormService: CurrencyTypeFormService;
  let currencyTypeService: CurrencyTypeService;
  let establishmentService: EstablishmentService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CurrencyTypeUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(CurrencyTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CurrencyTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    currencyTypeFormService = TestBed.inject(CurrencyTypeFormService);
    currencyTypeService = TestBed.inject(CurrencyTypeService);
    establishmentService = TestBed.inject(EstablishmentService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Establishment query and add missing value', () => {
      const currencyType: ICurrencyType = { id: 456 };
      const establishment: IEstablishment = { id: 41944 };
      currencyType.establishment = establishment;

      const establishmentCollection: IEstablishment[] = [{ id: 18506 }];
      jest.spyOn(establishmentService, 'query').mockReturnValue(of(new HttpResponse({ body: establishmentCollection })));
      const additionalEstablishments = [establishment];
      const expectedCollection: IEstablishment[] = [...additionalEstablishments, ...establishmentCollection];
      jest.spyOn(establishmentService, 'addEstablishmentToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ currencyType });
      comp.ngOnInit();

      expect(establishmentService.query).toHaveBeenCalled();
      expect(establishmentService.addEstablishmentToCollectionIfMissing).toHaveBeenCalledWith(
        establishmentCollection,
        ...additionalEstablishments.map(expect.objectContaining)
      );
      expect(comp.establishmentsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const currencyType: ICurrencyType = { id: 456 };
      const establishment: IEstablishment = { id: 27717 };
      currencyType.establishment = establishment;

      activatedRoute.data = of({ currencyType });
      comp.ngOnInit();

      expect(comp.establishmentsSharedCollection).toContain(establishment);
      expect(comp.currencyType).toEqual(currencyType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICurrencyType>>();
      const currencyType = { id: 123 };
      jest.spyOn(currencyTypeFormService, 'getCurrencyType').mockReturnValue(currencyType);
      jest.spyOn(currencyTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ currencyType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: currencyType }));
      saveSubject.complete();

      // THEN
      expect(currencyTypeFormService.getCurrencyType).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(currencyTypeService.update).toHaveBeenCalledWith(expect.objectContaining(currencyType));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICurrencyType>>();
      const currencyType = { id: 123 };
      jest.spyOn(currencyTypeFormService, 'getCurrencyType').mockReturnValue({ id: null });
      jest.spyOn(currencyTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ currencyType: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: currencyType }));
      saveSubject.complete();

      // THEN
      expect(currencyTypeFormService.getCurrencyType).toHaveBeenCalled();
      expect(currencyTypeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICurrencyType>>();
      const currencyType = { id: 123 };
      jest.spyOn(currencyTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ currencyType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(currencyTypeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEstablishment', () => {
      it('Should forward to establishmentService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(establishmentService, 'compareEstablishment');
        comp.compareEstablishment(entity, entity2);
        expect(establishmentService.compareEstablishment).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
