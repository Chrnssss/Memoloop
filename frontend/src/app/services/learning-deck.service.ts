import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { LearningDeck } from '../models/learning-deck.model';
import { StudiedCard } from '../models/studied-card.model';

@Injectable({ providedIn: 'root' })
export class LearningDeckService {
  private readonly http = inject(HttpClient);
  private readonly base = `${environment.apiBaseUrl}/learningdecks`;

  getById(id: number): Observable<LearningDeck> {
    return this.http.get<LearningDeck>(`${this.base}/${id}`);
  }

  create(deckId: number): Observable<LearningDeck> {
    return this.http.post<LearningDeck>(`${this.base}/${deckId}`, {});
  }

  nextCard(learningDeckId: number): Observable<StudiedCard> {
    return this.http.post<StudiedCard>(`${this.base}/${learningDeckId}/next`, {});
  }
}
