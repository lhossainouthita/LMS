import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPeriode } from 'app/shared/model/periode.model';

@Component({
  selector: 'jhi-periode-detail',
  templateUrl: './periode-detail.component.html'
})
export class PeriodeDetailComponent implements OnInit {
  periode: IPeriode;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ periode }) => {
      this.periode = periode;
    });
  }

  previousState() {
    window.history.back();
  }
}
