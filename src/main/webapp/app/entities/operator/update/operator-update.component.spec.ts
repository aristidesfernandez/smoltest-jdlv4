import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { OperatorFormService } from './operator-form.service';
import { OperatorService } from '../service/operator.service';
import { IOperator } from '../operator.model';
import { IMunicipality } from 'app/entities/municipality/municipality.model';
import { MunicipalityService } from 'app/entities/municipality/service/municipality.service';

import { OperatorUpdateComponent } from './operator-update.component';

describe('Operator Management Update Component', () => {
  let comp: OperatorUpdateComponent;
  let fixture: ComponentFixture<OperatorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let operatorFormService: OperatorFormService;
  let operatorService: OperatorService;
  let municipalityService: MunicipalityService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [OperatorUpdateComponent],
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
      .overrideTemplate(OperatorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OperatorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    operatorFormService = TestBed.inject(OperatorFormService);
    operatorService = TestBed.inject(OperatorService);
    municipalityService = TestBed.inject(MunicipalityService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Municipality query and add missing value', () => {
      const operator: IOperator = { id: 456 };
      const municipality: IMunicipality = { id: 2116 };
      operator.municipality = municipality;

      const municipalityCollection: IMunicipality[] = [{ id: 69298 }];
      jest.spyOn(municipalityService, 'query').mockReturnValue(of(new HttpResponse({ body: municipalityCollection })));
      const additionalMunicipalities = [municipality];
      const expectedCollection: IMunicipality[] = [...additionalMunicipalities, ...municipalityCollection];
      jest.spyOn(municipalityService, 'addMunicipalityToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ operator });
      comp.ngOnInit();

      expect(municipalityService.query).toHaveBeenCalled();
      expect(municipalityService.addMunicipalityToCollectionIfMissing).toHaveBeenCalledWith(
        municipalityCollection,
        ...additionalMunicipalities.map(expect.objectContaining)
      );
      expect(comp.municipalitiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const operator: IOperator = { id: 456 };
      const municipality: IMunicipality = { id: 89219 };
      operator.municipality = municipality;

      activatedRoute.data = of({ operator });
      comp.ngOnInit();

      expect(comp.municipalitiesSharedCollection).toContain(municipality);
      expect(comp.operator).toEqual(operator);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOperator>>();
      const operator = { id: 123 };
      jest.spyOn(operatorFormService, 'getOperator').mockReturnValue(operator);
      jest.spyOn(operatorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ operator });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: operator }));
      saveSubject.complete();

      // THEN
      expect(operatorFormService.getOperator).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(operatorService.update).toHaveBeenCalledWith(expect.objectContaining(operator));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOperator>>();
      const operator = { id: 123 };
      jest.spyOn(operatorFormService, 'getOperator').mockReturnValue({ id: null });
      jest.spyOn(operatorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ operator: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: operator }));
      saveSubject.complete();

      // THEN
      expect(operatorFormService.getOperator).toHaveBeenCalled();
      expect(operatorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOperator>>();
      const operator = { id: 123 };
      jest.spyOn(operatorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ operator });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(operatorService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareMunicipality', () => {
      it('Should forward to municipalityService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(municipalityService, 'compareMunicipality');
        comp.compareMunicipality(entity, entity2);
        expect(municipalityService.compareMunicipality).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
