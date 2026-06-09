import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Quiz, QuizAnswer, QuizAnswerResult } from '../models/quiz.model';

@Injectable({ providedIn: 'root' })
export class QuizService {
  private readonly http = inject(HttpClient);
  private readonly base = `${environment.apiBaseUrl}/quizzes`;

  create(deckId: number, questions?: number): Observable<Quiz> {
    let params = new HttpParams();
    if (questions !== undefined && questions > 0) {
      params = params.set('questions', String(questions));
    }
    return this.http.post<Quiz>(`${this.base}/${deckId}`, {}, { params });
  }

  getById(id: number): Observable<Quiz> {
    return this.http.get<Quiz>(`${this.base}/${id}`);
  }

  answer(quizId: number, payload: QuizAnswer): Observable<QuizAnswerResult> {
    return this.http.post<QuizAnswerResult>(`${this.base}/${quizId}/answer`, payload);
  }

  delete(quizId: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/${quizId}`);
  }
}
