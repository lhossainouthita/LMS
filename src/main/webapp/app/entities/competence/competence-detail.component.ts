import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompetence } from 'app/shared/model/competence.model';

@Component({
  selector: 'jhi-competence-detail',
  templateUrl: './competence-detail.component.html'
})
export class CompetenceDetailComponent implements OnInit {
  competence: ICompetence;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ competence }) => {
      this.competence = competence;
    });
  }

  previousState() {
    window.history.back();
  }
}
