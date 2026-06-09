import { CommonModule } from '@angular/common';
import { Component, OnInit, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { DeckDetailed } from '../../models/deck.model';
import { CardService } from '../../services/card.service';
import { DeckService } from '../../services/deck.service';
import { LearningDeckService } from '../../services/learning-deck.service';
import { QuizService } from '../../services/quiz.service';

@Component({
  selector: 'app-deck-detail',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './deck-detail.component.html',
  styleUrl: './deck-detail.component.css'
})
export class DeckDetailComponent implements OnInit {
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly deckService = inject(DeckService);
  private readonly cardService = inject(CardService);
  private readonly learningDeckService = inject(LearningDeckService);
  private readonly quizService = inject(QuizService);

  deckId!: number;
  deck = signal<DeckDetailed | null>(null);
  loading = signal(false);
  error = signal<string | null>(null);

  newWord = '';
  newTranslation = '';
  addingCard = signal(false);
  startingSession = signal(false);
  startingQuiz = signal(false);

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    this.deckId = Number(idParam);
    this.load();
  }

  load(): void {
    this.loading.set(true);
    this.error.set(null);
    this.deckService.getById(this.deckId).subscribe({
      next: deck => {
        this.deck.set(deck);
        this.loading.set(false);
      },
      error: err => {
        this.error.set(this.formatError(err));
        this.loading.set(false);
      }
    });
  }

  addCard(): void {
    const word = this.newWord.trim();
    const translation = this.newTranslation.trim();
    if (!word || !translation) return;
    this.addingCard.set(true);
    this.cardService.addToDeck(this.deckId, { word, translation }).subscribe({
      next: card => {
        this.deck.update(d => (d ? { ...d, cards: [...d.cards, card] } : d));
        this.newWord = '';
        this.newTranslation = '';
        this.addingCard.set(false);
      },
      error: err => {
        this.error.set(this.formatError(err));
        this.addingCard.set(false);
      }
    });
  }

  startSession(): void {
    this.startingSession.set(true);
    this.learningDeckService.create(this.deckId).subscribe({
      next: ld => {
        this.startingSession.set(false);
        this.router.navigate(['/study', ld.id]);
      },
      error: err => {
        this.error.set(this.formatError(err));
        this.startingSession.set(false);
      }
    });
  }

  startQuiz(): void {
    this.startingQuiz.set(true);
    this.quizService.create(this.deckId).subscribe({
      next: quiz => {
        this.startingQuiz.set(false);
        this.router.navigate(['/quiz', quiz.id]);
      },
      error: err => {
        this.error.set(this.formatError(err));
        this.startingQuiz.set(false);
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
