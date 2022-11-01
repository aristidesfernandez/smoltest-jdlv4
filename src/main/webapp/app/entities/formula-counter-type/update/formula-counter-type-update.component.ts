import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { FormulaCounterTypeFormService, FormulaCounterTypeFormGroup } from './formula-counter-type-form.service';
import { IFormulaCounterType } from '../formula-counter-type.model';
import { FormulaCounterTypeService } from '../service/formula-counter-type.service';
import { IFormula } from 'app/entities/formula/formula.model';
import { FormulaService } from 'app/entities/formula/service/formula.service';
import { ICounterType } from 'app/entities/counter-type/counter-type.model';
import { CounterTypeService } from 'app/entities/counter-type/service/counter-type.service';

@Component({
  selector: 'jhi-formula-counter-type-update',
  templateUrl: './formula-counter-type-update.component.html',
})
export class FormulaCounterTypeUpdateComponent implements OnInit {
  isSaving = false;
  formulaCounterType: IFormulaCounterType | null = null;

  formulasSharedCollection: IFormula[] = [];
  counterTypesSharedCollection: ICounterType[] = [];

  editForm: FormulaCounterTypeFormGroup = this.formulaCounterTypeFormService.createFormulaCounterTypeFormGroup();

  constructor(
    protected formulaCounterTypeService: FormulaCounterTypeService,
    protected formulaCounterTypeFormService: FormulaCounterTypeFormService,
    protected formulaService: FormulaService,
    protected counterTypeService: CounterTypeService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareFormula = (o1: IFormula | null, o2: IFormula | null): boolean => this.formulaService.compareFormula(o1, o2);

  compareCounterType = (o1: ICounterType | null, o2: ICounterType | null): boolean => this.counterTypeService.compareCounterType(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ formulaCounterType }) => {
      this.formulaCounterType = formulaCounterType;
      if (formulaCounterType) {
        this.updateForm(formulaCounterType);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const formulaCounterType = this.formulaCounterTypeFormService.getFormulaCounterType(this.editForm);
    if (formulaCounterType.id !== null) {
      this.subscribeToSaveResponse(this.formulaCounterTypeService.update(formulaCounterType));
    } else {
      this.subscribeToSaveResponse(this.formulaCounterTypeService.create(formulaCounterType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFormulaCounterType>>): void {
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

  protected updateForm(formulaCounterType: IFormulaCounterType): void {
    this.formulaCounterType = formulaCounterType;
    this.formulaCounterTypeFormService.resetForm(this.editForm, formulaCounterType);

    this.formulasSharedCollection = this.formulaService.addFormulaToCollectionIfMissing<IFormula>(
      this.formulasSharedCollection,
      formulaCounterType.formula
    );
    this.counterTypesSharedCollection = this.counterTypeService.addCounterTypeToCollectionIfMissing<ICounterType>(
      this.counterTypesSharedCollection,
      formulaCounterType.counterType
    );
  }

  protected loadRelationshipsOptions(): void {
    this.formulaService
      .query()
      .pipe(map((res: HttpResponse<IFormula[]>) => res.body ?? []))
      .pipe(
        map((formulas: IFormula[]) =>
          this.formulaService.addFormulaToCollectionIfMissing<IFormula>(formulas, this.formulaCounterType?.formula)
        )
      )
      .subscribe((formulas: IFormula[]) => (this.formulasSharedCollection = formulas));

    this.counterTypeService
      .query()
      .pipe(map((res: HttpResponse<ICounterType[]>) => res.body ?? []))
      .pipe(
        map((counterTypes: ICounterType[]) =>
          this.counterTypeService.addCounterTypeToCollectionIfMissing<ICounterType>(counterTypes, this.formulaCounterType?.counterType)
        )
      )
      .subscribe((counterTypes: ICounterType[]) => (this.counterTypesSharedCollection = counterTypes));
  }
}
