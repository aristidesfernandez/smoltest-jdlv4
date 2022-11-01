import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFormulaCounterType } from '../formula-counter-type.model';
import { FormulaCounterTypeService } from '../service/formula-counter-type.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './formula-counter-type-delete-dialog.component.html',
})
export class FormulaCounterTypeDeleteDialogComponent {
  formulaCounterType?: IFormulaCounterType;

  constructor(protected formulaCounterTypeService: FormulaCounterTypeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.formulaCounterTypeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
