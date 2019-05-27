import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IQuestion, Question } from 'app/shared/model/question.model';
import { QuestionService } from './question.service';
import { ISituation } from 'app/shared/model/situation.model';
import { SituationService } from 'app/entities/situation';

@Component({
  selector: 'jhi-question-update',
  templateUrl: './question-update.component.html'
})
export class QuestionUpdateComponent implements OnInit {
  question: IQuestion;
  isSaving: boolean;

  situations: ISituation[];

  editForm = this.fb.group({
    id: [],
    numQuestion: [null, [Validators.required]],
    contenuQuestion: [null, [Validators.required]],
    situationId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected questionService: QuestionService,
    protected situationService: SituationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ question }) => {
      this.updateForm(question);
      this.question = question;
    });
    this.situationService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISituation[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISituation[]>) => response.body)
      )
      .subscribe((res: ISituation[]) => (this.situations = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(question: IQuestion) {
    this.editForm.patchValue({
      id: question.id,
      numQuestion: question.numQuestion,
      contenuQuestion: question.contenuQuestion,
      situationId: question.situationId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const question = this.createFromForm();
    if (question.id !== undefined) {
      this.subscribeToSaveResponse(this.questionService.update(question));
    } else {
      this.subscribeToSaveResponse(this.questionService.create(question));
    }
  }

  private createFromForm(): IQuestion {
    const entity = {
      ...new Question(),
      id: this.editForm.get(['id']).value,
      numQuestion: this.editForm.get(['numQuestion']).value,
      contenuQuestion: this.editForm.get(['contenuQuestion']).value,
      situationId: this.editForm.get(['situationId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuestion>>) {
    result.subscribe((res: HttpResponse<IQuestion>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackSituationById(index: number, item: ISituation) {
    return item.id;
  }
}
