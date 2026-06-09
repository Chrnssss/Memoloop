import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Card, CreateCard } from '../models/card.model';

@Injectable({ providedIn: 'root' })
export class CardService {
  private readonly http = inject(HttpClient);
  private readonly api = environment.apiBaseUrl;

  getById(id: number): Observable<Card> {
    return this.http.get<Card>(`${this.api}/cards/${id}`);
  }

  addToDeck(deckId: number, payload: CreateCard): Observable<Card> {
    return this.http.post<Card>(`${this.api}/decks/${deckId}/cards`, payload);
  }
}
