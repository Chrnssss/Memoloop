import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { StudiedCard } from '../models/studied-card.model';

@Injectable({ providedIn: 'root' })
export class StudiedCardService {
  private readonly http = inject(HttpClient);
  private readonly base = `${environment.apiBaseUrl}/studiedcard`;

  update(studiedCard: StudiedCard): Observable<StudiedCard> {
    return this.http.put<StudiedCard>(this.base, studiedCard);
  }
}
