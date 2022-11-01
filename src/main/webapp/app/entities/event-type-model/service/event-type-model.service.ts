import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEventTypeModel, NewEventTypeModel } from '../event-type-model.model';

export type PartialUpdateEventTypeModel = Partial<IEventTypeModel> & Pick<IEventTypeModel, 'id'>;

export type EntityResponseType = HttpResponse<IEventTypeModel>;
export type EntityArrayResponseType = HttpResponse<IEventTypeModel[]>;

@Injectable({ providedIn: 'root' })
export class EventTypeModelService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/event-type-models');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(eventTypeModel: NewEventTypeModel): Observable<EntityResponseType> {
    return this.http.post<IEventTypeModel>(this.resourceUrl, eventTypeModel, { observe: 'response' });
  }

  update(eventTypeModel: IEventTypeModel): Observable<EntityResponseType> {
    return this.http.put<IEventTypeModel>(`${this.resourceUrl}/${this.getEventTypeModelIdentifier(eventTypeModel)}`, eventTypeModel, {
      observe: 'response',
    });
  }

  partialUpdate(eventTypeModel: PartialUpdateEventTypeModel): Observable<EntityResponseType> {
    return this.http.patch<IEventTypeModel>(`${this.resourceUrl}/${this.getEventTypeModelIdentifier(eventTypeModel)}`, eventTypeModel, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEventTypeModel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEventTypeModel[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEventTypeModelIdentifier(eventTypeModel: Pick<IEventTypeModel, 'id'>): number {
    return eventTypeModel.id;
  }

  compareEventTypeModel(o1: Pick<IEventTypeModel, 'id'> | null, o2: Pick<IEventTypeModel, 'id'> | null): boolean {
    return o1 && o2 ? this.getEventTypeModelIdentifier(o1) === this.getEventTypeModelIdentifier(o2) : o1 === o2;
  }

  addEventTypeModelToCollectionIfMissing<Type extends Pick<IEventTypeModel, 'id'>>(
    eventTypeModelCollection: Type[],
    ...eventTypeModelsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const eventTypeModels: Type[] = eventTypeModelsToCheck.filter(isPresent);
    if (eventTypeModels.length > 0) {
      const eventTypeModelCollectionIdentifiers = eventTypeModelCollection.map(
        eventTypeModelItem => this.getEventTypeModelIdentifier(eventTypeModelItem)!
      );
      const eventTypeModelsToAdd = eventTypeModels.filter(eventTypeModelItem => {
        const eventTypeModelIdentifier = this.getEventTypeModelIdentifier(eventTypeModelItem);
        if (eventTypeModelCollectionIdentifiers.includes(eventTypeModelIdentifier)) {
          return false;
        }
        eventTypeModelCollectionIdentifiers.push(eventTypeModelIdentifier);
        return true;
      });
      return [...eventTypeModelsToAdd, ...eventTypeModelCollection];
    }
    return eventTypeModelCollection;
  }
}
