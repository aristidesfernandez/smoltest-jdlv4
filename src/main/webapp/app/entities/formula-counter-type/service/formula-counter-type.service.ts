import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFormulaCounterType, NewFormulaCounterType } from '../formula-counter-type.model';

export type PartialUpdateFormulaCounterType = Partial<IFormulaCounterType> & Pick<IFormulaCounterType, 'id'>;

export type EntityResponseType = HttpResponse<IFormulaCounterType>;
export type EntityArrayResponseType = HttpResponse<IFormulaCounterType[]>;

@Injectable({ providedIn: 'root' })
export class FormulaCounterTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/formula-counter-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(formulaCounterType: NewFormulaCounterType): Observable<EntityResponseType> {
    return this.http.post<IFormulaCounterType>(this.resourceUrl, formulaCounterType, { observe: 'response' });
  }

  update(formulaCounterType: IFormulaCounterType): Observable<EntityResponseType> {
    return this.http.put<IFormulaCounterType>(
      `${this.resourceUrl}/${this.getFormulaCounterTypeIdentifier(formulaCounterType)}`,
      formulaCounterType,
      { observe: 'response' }
    );
  }

  partialUpdate(formulaCounterType: PartialUpdateFormulaCounterType): Observable<EntityResponseType> {
    return this.http.patch<IFormulaCounterType>(
      `${this.resourceUrl}/${this.getFormulaCounterTypeIdentifier(formulaCounterType)}`,
      formulaCounterType,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFormulaCounterType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFormulaCounterType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFormulaCounterTypeIdentifier(formulaCounterType: Pick<IFormulaCounterType, 'id'>): number {
    return formulaCounterType.id;
  }

  compareFormulaCounterType(o1: Pick<IFormulaCounterType, 'id'> | null, o2: Pick<IFormulaCounterType, 'id'> | null): boolean {
    return o1 && o2 ? this.getFormulaCounterTypeIdentifier(o1) === this.getFormulaCounterTypeIdentifier(o2) : o1 === o2;
  }

  addFormulaCounterTypeToCollectionIfMissing<Type extends Pick<IFormulaCounterType, 'id'>>(
    formulaCounterTypeCollection: Type[],
    ...formulaCounterTypesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const formulaCounterTypes: Type[] = formulaCounterTypesToCheck.filter(isPresent);
    if (formulaCounterTypes.length > 0) {
      const formulaCounterTypeCollectionIdentifiers = formulaCounterTypeCollection.map(
        formulaCounterTypeItem => this.getFormulaCounterTypeIdentifier(formulaCounterTypeItem)!
      );
      const formulaCounterTypesToAdd = formulaCounterTypes.filter(formulaCounterTypeItem => {
        const formulaCounterTypeIdentifier = this.getFormulaCounterTypeIdentifier(formulaCounterTypeItem);
        if (formulaCounterTypeCollectionIdentifiers.includes(formulaCounterTypeIdentifier)) {
          return false;
        }
        formulaCounterTypeCollectionIdentifiers.push(formulaCounterTypeIdentifier);
        return true;
      });
      return [...formulaCounterTypesToAdd, ...formulaCounterTypeCollection];
    }
    return formulaCounterTypeCollection;
  }
}
