import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISituation } from 'app/shared/model/situation.model';

@Component({
  selector: 'jhi-situation-detail',
  templateUrl: './situation-detail.component.html'
})
export class SituationDetailComponent implements OnInit {
  situation: ISituation;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ situation }) => {
      this.situation = situation;
    });
  }

  previousState() {
    window.history.back();
  }
}
