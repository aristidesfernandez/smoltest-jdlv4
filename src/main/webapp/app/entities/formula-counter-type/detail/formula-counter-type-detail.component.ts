import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFormulaCounterType } from '../formula-counter-type.model';

@Component({
  selector: 'jhi-formula-counter-type-detail',
  templateUrl: './formula-counter-type-detail.component.html',
})
export class FormulaCounterTypeDetailComponent implements OnInit {
  formulaCounterType: IFormulaCounterType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ formulaCounterType }) => {
      this.formulaCounterType = formulaCounterType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
