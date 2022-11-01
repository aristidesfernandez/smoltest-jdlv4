import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FormulaCounterTypeComponent } from './list/formula-counter-type.component';
import { FormulaCounterTypeDetailComponent } from './detail/formula-counter-type-detail.component';
import { FormulaCounterTypeUpdateComponent } from './update/formula-counter-type-update.component';
import { FormulaCounterTypeDeleteDialogComponent } from './delete/formula-counter-type-delete-dialog.component';
import { FormulaCounterTypeRoutingModule } from './route/formula-counter-type-routing.module';

@NgModule({
  imports: [SharedModule, FormulaCounterTypeRoutingModule],
  declarations: [
    FormulaCounterTypeComponent,
    FormulaCounterTypeDetailComponent,
    FormulaCounterTypeUpdateComponent,
    FormulaCounterTypeDeleteDialogComponent,
  ],
})
export class FormulaCounterTypeModule {}
