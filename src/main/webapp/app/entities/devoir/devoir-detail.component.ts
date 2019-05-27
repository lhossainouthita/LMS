import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDevoir } from 'app/shared/model/devoir.model';

@Component({
  selector: 'jhi-devoir-detail',
  templateUrl: './devoir-detail.component.html'
})
export class DevoirDetailComponent implements OnInit {
  devoir: IDevoir;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ devoir }) => {
      this.devoir = devoir;
    });
  }

  previousState() {
    window.history.back();
  }
}
