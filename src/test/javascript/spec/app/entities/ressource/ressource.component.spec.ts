/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LmsTestModule } from '../../../test.module';
import { RessourceComponent } from 'app/entities/ressource/ressource.component';
import { RessourceService } from 'app/entities/ressource/ressource.service';
import { Ressource } from 'app/shared/model/ressource.model';

describe('Component Tests', () => {
  describe('Ressource Management Component', () => {
    let comp: RessourceComponent;
    let fixture: ComponentFixture<RessourceComponent>;
    let service: RessourceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LmsTestModule],
        declarations: [RessourceComponent],
        providers: []
      })
        .overrideTemplate(RessourceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RessourceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RessourceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Ressource(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.ressources[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
