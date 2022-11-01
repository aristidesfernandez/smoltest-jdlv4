import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EventTypeModelDetailComponent } from './event-type-model-detail.component';

describe('EventTypeModel Management Detail Component', () => {
  let comp: EventTypeModelDetailComponent;
  let fixture: ComponentFixture<EventTypeModelDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EventTypeModelDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ eventTypeModel: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EventTypeModelDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EventTypeModelDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load eventTypeModel on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.eventTypeModel).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
