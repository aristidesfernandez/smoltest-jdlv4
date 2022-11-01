import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFormulaCounterType } from '../formula-counter-type.model';
import { FormulaCounterTypeService } from '../service/formula-counter-type.service';

@Injectable({ providedIn: 'root' })
export class FormulaCounterTypeRoutingResolveService implements Resolve<IFormulaCounterType | null> {
  constructor(protected service: FormulaCounterTypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFormulaCounterType | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((formulaCounterType: HttpResponse<IFormulaCounterType>) => {
          if (formulaCounterType.body) {
            return of(formulaCounterType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
