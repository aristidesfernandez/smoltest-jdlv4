import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { CurrencyTypeFormService, CurrencyTypeFormGroup } from './currency-type-form.service';
import { ICurrencyType } from '../currency-type.model';
import { CurrencyTypeService } from '../service/currency-type.service';
import { IEstablishment } from 'app/entities/establishment/establishment.model';
import { EstablishmentService } from 'app/entities/establishment/service/establishment.service';

@Component({
  selector: 'jhi-currency-type-update',
  templateUrl: './currency-type-update.component.html',
})
export class CurrencyTypeUpdateComponent implements OnInit {
  isSaving = false;
  currencyType: ICurrencyType | null = null;

  establishmentsSharedCollection: IEstablishment[] = [];

  editForm: CurrencyTypeFormGroup = this.currencyTypeFormService.createCurrencyTypeFormGroup();

  constructor(
    protected currencyTypeService: CurrencyTypeService,
    protected currencyTypeFormService: CurrencyTypeFormService,
    protected establishmentService: EstablishmentService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEstablishment = (o1: IEstablishment | null, o2: IEstablishment | null): boolean =>
    this.establishmentService.compareEstablishment(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ currencyType }) => {
      this.currencyType = currencyType;
      if (currencyType) {
        this.updateForm(currencyType);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const currencyType = this.currencyTypeFormService.getCurrencyType(this.editForm);
    if (currencyType.id !== null) {
      this.subscribeToSaveResponse(this.currencyTypeService.update(currencyType));
    } else {
      this.subscribeToSaveResponse(this.currencyTypeService.create(currencyType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICurrencyType>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(currencyType: ICurrencyType): void {
    this.currencyType = currencyType;
    this.currencyTypeFormService.resetForm(this.editForm, currencyType);

    this.establishmentsSharedCollection = this.establishmentService.addEstablishmentToCollectionIfMissing<IEstablishment>(
      this.establishmentsSharedCollection,
      currencyType.establishment
    );
  }

  protected loadRelationshipsOptions(): void {
    this.establishmentService
      .query()
      .pipe(map((res: HttpResponse<IEstablishment[]>) => res.body ?? []))
      .pipe(
        map((establishments: IEstablishment[]) =>
          this.establishmentService.addEstablishmentToCollectionIfMissing<IEstablishment>(establishments, this.currencyType?.establishment)
        )
      )
      .subscribe((establishments: IEstablishment[]) => (this.establishmentsSharedCollection = establishments));
  }
}
