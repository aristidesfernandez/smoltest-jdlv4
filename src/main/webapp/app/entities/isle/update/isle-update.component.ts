import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IsleFormService, IsleFormGroup } from './isle-form.service';
import { IIsle } from '../isle.model';
import { IsleService } from '../service/isle.service';
import { IEstablishment } from 'app/entities/establishment/establishment.model';
import { EstablishmentService } from 'app/entities/establishment/service/establishment.service';

@Component({
  selector: 'jhi-isle-update',
  templateUrl: './isle-update.component.html',
})
export class IsleUpdateComponent implements OnInit {
  isSaving = false;
  isle: IIsle | null = null;

  establishmentsSharedCollection: IEstablishment[] = [];

  editForm: IsleFormGroup = this.isleFormService.createIsleFormGroup();

  constructor(
    protected isleService: IsleService,
    protected isleFormService: IsleFormService,
    protected establishmentService: EstablishmentService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEstablishment = (o1: IEstablishment | null, o2: IEstablishment | null): boolean =>
    this.establishmentService.compareEstablishment(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ isle }) => {
      this.isle = isle;
      if (isle) {
        this.updateForm(isle);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const isle = this.isleFormService.getIsle(this.editForm);
    if (isle.id !== null) {
      this.subscribeToSaveResponse(this.isleService.update(isle));
    } else {
      this.subscribeToSaveResponse(this.isleService.create(isle));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIsle>>): void {
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

  protected updateForm(isle: IIsle): void {
    this.isle = isle;
    this.isleFormService.resetForm(this.editForm, isle);

    this.establishmentsSharedCollection = this.establishmentService.addEstablishmentToCollectionIfMissing<IEstablishment>(
      this.establishmentsSharedCollection,
      isle.establishment
    );
  }

  protected loadRelationshipsOptions(): void {
    this.establishmentService
      .query()
      .pipe(map((res: HttpResponse<IEstablishment[]>) => res.body ?? []))
      .pipe(
        map((establishments: IEstablishment[]) =>
          this.establishmentService.addEstablishmentToCollectionIfMissing<IEstablishment>(establishments, this.isle?.establishment)
        )
      )
      .subscribe((establishments: IEstablishment[]) => (this.establishmentsSharedCollection = establishments));
  }
}
