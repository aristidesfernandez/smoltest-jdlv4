import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CommandDeviceDetailComponent } from './command-device-detail.component';

describe('CommandDevice Management Detail Component', () => {
  let comp: CommandDeviceDetailComponent;
  let fixture: ComponentFixture<CommandDeviceDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CommandDeviceDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ commandDevice: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CommandDeviceDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CommandDeviceDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load commandDevice on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.commandDevice).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
