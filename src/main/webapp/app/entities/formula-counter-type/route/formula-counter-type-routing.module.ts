import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FormulaCounterTypeComponent } from '../list/formula-counter-type.component';
import { FormulaCounterTypeDetailComponent } from '../detail/formula-counter-type-detail.component';
import { FormulaCounterTypeUpdateComponent } from '../update/formula-counter-type-update.component';
import { FormulaCounterTypeRoutingResolveService } from './formula-counter-type-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const formulaCounterTypeRoute: Routes = [
  {
    path: '',
    component: FormulaCounterTypeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FormulaCounterTypeDetailComponent,
    resolve: {
      formulaCounterType: FormulaCounterTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FormulaCounterTypeUpdateComponent,
    resolve: {
      formulaCounterType: FormulaCounterTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FormulaCounterTypeUpdateComponent,
    resolve: {
      formulaCounterType: FormulaCounterTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(formulaCounterTypeRoute)],
  exports: [RouterModule],
})
export class FormulaCounterTypeRoutingModule {}
