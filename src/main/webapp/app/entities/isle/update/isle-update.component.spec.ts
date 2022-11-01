import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IsleFormService } from './isle-form.service';
import { IsleService } from '../service/isle.service';
import { IIsle } from '../isle.model';
import { IEstablishment } from 'app/entities/establishment/establishment.model';
import { EstablishmentService } from 'app/entities/establishment/service/establishment.service';

import { IsleUpdateComponent } from './isle-update.component';

describe('Isle Management Update Component', () => {
  let comp: IsleUpdateComponent;
  let fixture: ComponentFixture<IsleUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let isleFormService: IsleFormService;
  let isleService: IsleService;
  let establishmentService: EstablishmentService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [IsleUpdateComponent],
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
      .overrideTemplate(IsleUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(IsleUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    isleFormService = TestBed.inject(IsleFormService);
    isleService = TestBed.inject(IsleService);
    establishmentService = TestBed.inject(EstablishmentService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Establishment query and add missing value', () => {
      const isle: IIsle = { id: 456 };
      const establishment: IEstablishment = { id: 53887 };
      isle.establishment = establishment;

      const establishmentCollection: IEstablishment[] = [{ id: 91993 }];
      jest.spyOn(establishmentService, 'query').mockReturnValue(of(new HttpResponse({ body: establishmentCollection })));
      const additionalEstablishments = [establishment];
      const expectedCollection: IEstablishment[] = [...additionalEstablishments, ...establishmentCollection];
      jest.spyOn(establishmentService, 'addEstablishmentToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ isle });
      comp.ngOnInit();

      expect(establishmentService.query).toHaveBeenCalled();
      expect(establishmentService.addEstablishmentToCollectionIfMissing).toHaveBeenCalledWith(
        establishmentCollection,
        ...additionalEstablishments.map(expect.objectContaining)
      );
      expect(comp.establishmentsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const isle: IIsle = { id: 456 };
      const establishment: IEstablishment = { id: 98078 };
      isle.establishment = establishment;

      activatedRoute.data = of({ isle });
      comp.ngOnInit();

      expect(comp.establishmentsSharedCollection).toContain(establishment);
      expect(comp.isle).toEqual(isle);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIsle>>();
      const isle = { id: 123 };
      jest.spyOn(isleFormService, 'getIsle').mockReturnValue(isle);
      jest.spyOn(isleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ isle });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: isle }));
      saveSubject.complete();

      // THEN
      expect(isleFormService.getIsle).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(isleService.update).toHaveBeenCalledWith(expect.objectContaining(isle));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIsle>>();
      const isle = { id: 123 };
      jest.spyOn(isleFormService, 'getIsle').mockReturnValue({ id: null });
      jest.spyOn(isleService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ isle: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: isle }));
      saveSubject.complete();

      // THEN
      expect(isleFormService.getIsle).toHaveBeenCalled();
      expect(isleService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIsle>>();
      const isle = { id: 123 };
      jest.spyOn(isleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ isle });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(isleService.update).toHaveBeenCalled();
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
