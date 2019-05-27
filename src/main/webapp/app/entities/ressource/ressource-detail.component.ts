import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRessource } from 'app/shared/model/ressource.model';

@Component({
  selector: 'jhi-ressource-detail',
  templateUrl: './ressource-detail.component.html'
})
export class RessourceDetailComponent implements OnInit {
  ressource: IRessource;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ ressource }) => {
      this.ressource = ressource;
    });
  }

  previousState() {
    window.history.back();
  }
}
