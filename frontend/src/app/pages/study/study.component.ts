import { CommonModule } from '@angular/common';
import { Component, OnInit, inject, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { LearningDeck } from '../../models/learning-deck.model';
import { StudiedCard } from '../../models/studied-card.model';
import { LearningDeckService } from '../../services/learning-deck.service';
import { StudiedCardService } from '../../services/studied-card.service';

@Component({
  selector: 'app-study',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './study.component.html',
  styleUrl: './study.component.css'
})
export class StudyComponent implements OnInit {
  private readonly route = inject(ActivatedRoute);
  private readonly learningDeckService = inject(LearningDeckService);
  private readonly studiedCardService = inject(StudiedCardService);

  learningDeckId!: number;
  learningDeck = signal<LearningDeck | null>(null);
  currentCard = signal<StudiedCard | null>(null);
  revealed = signal(false);
  studiedCount = signal(0);

  loading = signal(false);
  saving = signal(false);
  error = signal<string | null>(null);
  finished = signal(false);

  readonly knowledgeLevels = [
    { value: 0, label: 'Again', hint: "Didn't know it" },
    { value: 1, label: 'Hard', hint: 'Barely remembered' },
    { value: 2, label: 'Good', hint: 'Got it' },
    { value: 3, label: 'Easy', hint: 'Knew it cold' }
  ];

  ngOnInit(): void {
    this.learningDeckId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadDeckAndNext();
  }

  loadDeckAndNext(): void {
    this.loading.set(true);
    this.error.set(null);
    this.learningDeckService.getById(this.learningDeckId).subscribe({
      next: ld => {
        this.learningDeck.set(ld);
        // Count cards that already have a knowledge rating from a prior session.
        this.studiedCount.set(
          ld.myStudiedCards.filter(sc => sc.lastStudiedAt !== null).length
        );
        this.fetchNext();
      },
      error: err => {
        this.error.set(this.formatError(err));
        this.loading.set(false);
      }
    });
  }

  fetchNext(): void {
    this.loading.set(true);
    this.revealed.set(false);
    this.learningDeckService.nextCard(this.learningDeckId).subscribe({
      next: studied => {
        if (!studied || !studied.id) {
          this.currentCard.set(null);
          this.finished.set(true);
        } else {
          this.currentCard.set(studied);
          this.finished.set(false);
        }
        this.loading.set(false);
      },
      error: err => {
        this.error.set(this.formatError(err));
        this.loading.set(false);
      }
    });
  }

  reveal(): void {
    this.revealed.set(true);
  }

  rate(level: number): void {
    const card = this.currentCard();
    if (!card) return;
    this.saving.set(true);
    this.studiedCardService.update({ ...card, knowledgeLevel: level }).subscribe({
      next: () => {
        this.studiedCount.update(c => c + 1);
        this.saving.set(false);
        this.fetchNext();
      },
      error: err => {
        this.error.set(this.formatError(err));
        this.saving.set(false);
      }
    });
  }

  private formatError(err: unknown): string {
    if (err && typeof err === 'object' && 'message' in err) {
      return String((err as { message: unknown }).message);
    }
    return 'Something went wrong.';
  }
}
