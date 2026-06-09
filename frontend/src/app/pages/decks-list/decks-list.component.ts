import { CommonModule } from '@angular/common';
import { Component, OnInit, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Deck } from '../../models/deck.model';
import { DeckService } from '../../services/deck.service';

@Component({
  selector: 'app-decks-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './decks-list.component.html',
  styleUrl: './decks-list.component.css'
})
export class DecksListComponent implements OnInit {
  private readonly deckService = inject(DeckService);

  decks = signal<Deck[]>([]);
  loading = signal(false);
  error = signal<string | null>(null);
  newDeckName = '';
  creating = signal(false);

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.loading.set(true);
    this.error.set(null);
    this.deckService.getAll().subscribe({
      next: decks => {
        this.decks.set(decks);
        this.loading.set(false);
      },
      error: err => {
        this.error.set(this.formatError(err));
        this.loading.set(false);
      }
    });
  }

  create(): void {
    const name = this.newDeckName.trim();
    if (!name) return;
    this.creating.set(true);
    this.deckService.create({ name }).subscribe({
      next: deck => {
        this.decks.update(list => [...list, deck]);
        this.newDeckName = '';
        this.creating.set(false);
      },
      error: err => {
        this.error.set(this.formatError(err));
        this.creating.set(false);
      }
    });
  }

  remove(deck: Deck): void {
    if (!confirm(`Delete deck "${deck.name}"?`)) return;
    this.deckService.delete(deck.id).subscribe({
      next: () => this.decks.update(list => list.filter(d => d.id !== deck.id)),
      error: err => this.error.set(this.formatError(err))
    });
  }

  private formatError(err: unknown): string {
    if (err && typeof err === 'object' && 'message' in err) {
      return String((err as { message: unknown }).message);
    }
    return 'Something went wrong.';
  }
}
