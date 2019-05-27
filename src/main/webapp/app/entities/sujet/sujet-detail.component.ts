import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISujet } from 'app/shared/model/sujet.model';

@Component({
  selector: 'jhi-sujet-detail',
  templateUrl: './sujet-detail.component.html'
})
export class SujetDetailComponent implements OnInit {
  sujet: ISujet;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sujet }) => {
      this.sujet = sujet;
    });
  }

  previousState() {
    window.history.back();
  }
}
