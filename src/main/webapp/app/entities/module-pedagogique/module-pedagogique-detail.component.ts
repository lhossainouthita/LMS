import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IModulePedagogique } from 'app/shared/model/module-pedagogique.model';

@Component({
  selector: 'jhi-module-pedagogique-detail',
  templateUrl: './module-pedagogique-detail.component.html'
})
export class ModulePedagogiqueDetailComponent implements OnInit {
  modulePedagogique: IModulePedagogique;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ modulePedagogique }) => {
      this.modulePedagogique = modulePedagogique;
    });
  }

  previousState() {
    window.history.back();
  }
}
