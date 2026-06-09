import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { CreateDeck, Deck, DeckDetailed } from '../models/deck.model';

@Injectable({ providedIn: 'root' })
export class DeckService {
  private readonly http = inject(HttpClient);
  private readonly base = `${environment.apiBaseUrl}/decks`;

  getAll(): Observable<Deck[]> {
    return this.http.get<Deck[]>(`${this.base}/all`);
  }

  getPaged(page = 0, size = 10): Observable<Deck[]> {
    return this.http.get<Deck[]>(`${this.base}/paged`, { params: { page, size } });
  }

  getById(id: number): Observable<DeckDetailed> {
    return this.http.get<DeckDetailed>(`${this.base}/${id}`);
  }

  create(payload: CreateDeck): Observable<Deck> {
    return this.http.post<Deck>(this.base, payload);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(this.base, { params: { id } });
  }
}
