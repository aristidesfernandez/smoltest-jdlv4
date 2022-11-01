import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FormulaCounterTypeDetailComponent } from './formula-counter-type-detail.component';

describe('FormulaCounterType Management Detail Component', () => {
  let comp: FormulaCounterTypeDetailComponent;
  let fixture: ComponentFixture<FormulaCounterTypeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FormulaCounterTypeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ formulaCounterType: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(FormulaCounterTypeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FormulaCounterTypeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load formulaCounterType on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.formulaCounterType).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
