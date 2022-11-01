import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FormulaCounterTypeFormService } from './formula-counter-type-form.service';
import { FormulaCounterTypeService } from '../service/formula-counter-type.service';
import { IFormulaCounterType } from '../formula-counter-type.model';
import { IFormula } from 'app/entities/formula/formula.model';
import { FormulaService } from 'app/entities/formula/service/formula.service';
import { ICounterType } from 'app/entities/counter-type/counter-type.model';
import { CounterTypeService } from 'app/entities/counter-type/service/counter-type.service';

import { FormulaCounterTypeUpdateComponent } from './formula-counter-type-update.component';

describe('FormulaCounterType Management Update Component', () => {
  let comp: FormulaCounterTypeUpdateComponent;
  let fixture: ComponentFixture<FormulaCounterTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let formulaCounterTypeFormService: FormulaCounterTypeFormService;
  let formulaCounterTypeService: FormulaCounterTypeService;
  let formulaService: FormulaService;
  let counterTypeService: CounterTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FormulaCounterTypeUpdateComponent],
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
      .overrideTemplate(FormulaCounterTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FormulaCounterTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    formulaCounterTypeFormService = TestBed.inject(FormulaCounterTypeFormService);
    formulaCounterTypeService = TestBed.inject(FormulaCounterTypeService);
    formulaService = TestBed.inject(FormulaService);
    counterTypeService = TestBed.inject(CounterTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Formula query and add missing value', () => {
      const formulaCounterType: IFormulaCounterType = { id: 456 };
      const formula: IFormula = { id: 65435 };
      formulaCounterType.formula = formula;

      const formulaCollection: IFormula[] = [{ id: 53394 }];
      jest.spyOn(formulaService, 'query').mockReturnValue(of(new HttpResponse({ body: formulaCollection })));
      const additionalFormulas = [formula];
      const expectedCollection: IFormula[] = [...additionalFormulas, ...formulaCollection];
      jest.spyOn(formulaService, 'addFormulaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ formulaCounterType });
      comp.ngOnInit();

      expect(formulaService.query).toHaveBeenCalled();
      expect(formulaService.addFormulaToCollectionIfMissing).toHaveBeenCalledWith(
        formulaCollection,
        ...additionalFormulas.map(expect.objectContaining)
      );
      expect(comp.formulasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call CounterType query and add missing value', () => {
      const formulaCounterType: IFormulaCounterType = { id: 456 };
      const counterType: ICounterType = { counterCode: '60' };
      formulaCounterType.counterType = counterType;

      const counterTypeCollection: ICounterType[] = [{ counterCode: 'a2' }];
      jest.spyOn(counterTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: counterTypeCollection })));
      const additionalCounterTypes = [counterType];
      const expectedCollection: ICounterType[] = [...additionalCounterTypes, ...counterTypeCollection];
      jest.spyOn(counterTypeService, 'addCounterTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ formulaCounterType });
      comp.ngOnInit();

      expect(counterTypeService.query).toHaveBeenCalled();
      expect(counterTypeService.addCounterTypeToCollectionIfMissing).toHaveBeenCalledWith(
        counterTypeCollection,
        ...additionalCounterTypes.map(expect.objectContaining)
      );
      expect(comp.counterTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const formulaCounterType: IFormulaCounterType = { id: 456 };
      const formula: IFormula = { id: 47340 };
      formulaCounterType.formula = formula;
      const counterType: ICounterType = { counterCode: '16' };
      formulaCounterType.counterType = counterType;

      activatedRoute.data = of({ formulaCounterType });
      comp.ngOnInit();

      expect(comp.formulasSharedCollection).toContain(formula);
      expect(comp.counterTypesSharedCollection).toContain(counterType);
      expect(comp.formulaCounterType).toEqual(formulaCounterType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFormulaCounterType>>();
      const formulaCounterType = { id: 123 };
      jest.spyOn(formulaCounterTypeFormService, 'getFormulaCounterType').mockReturnValue(formulaCounterType);
      jest.spyOn(formulaCounterTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ formulaCounterType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: formulaCounterType }));
      saveSubject.complete();

      // THEN
      expect(formulaCounterTypeFormService.getFormulaCounterType).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(formulaCounterTypeService.update).toHaveBeenCalledWith(expect.objectContaining(formulaCounterType));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFormulaCounterType>>();
      const formulaCounterType = { id: 123 };
      jest.spyOn(formulaCounterTypeFormService, 'getFormulaCounterType').mockReturnValue({ id: null });
      jest.spyOn(formulaCounterTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ formulaCounterType: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: formulaCounterType }));
      saveSubject.complete();

      // THEN
      expect(formulaCounterTypeFormService.getFormulaCounterType).toHaveBeenCalled();
      expect(formulaCounterTypeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFormulaCounterType>>();
      const formulaCounterType = { id: 123 };
      jest.spyOn(formulaCounterTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ formulaCounterType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(formulaCounterTypeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareFormula', () => {
      it('Should forward to formulaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(formulaService, 'compareFormula');
        comp.compareFormula(entity, entity2);
        expect(formulaService.compareFormula).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCounterType', () => {
      it('Should forward to counterTypeService', () => {
        const entity = { counterCode: 'ABC' };
        const entity2 = { counterCode: 'CBA' };
        jest.spyOn(counterTypeService, 'compareCounterType');
        comp.compareCounterType(entity, entity2);
        expect(counterTypeService.compareCounterType).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
