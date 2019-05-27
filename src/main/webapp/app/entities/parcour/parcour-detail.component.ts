import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IParcour } from 'app/shared/model/parcour.model';

@Component({
  selector: 'jhi-parcour-detail',
  templateUrl: './parcour-detail.component.html'
})
export class ParcourDetailComponent implements OnInit {
  parcour: IParcour;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ parcour }) => {
      this.parcour = parcour;
    });
  }

  previousState() {
    window.history.back();
  }
}
