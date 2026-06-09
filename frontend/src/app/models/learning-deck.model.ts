import { Deck } from './deck.model';
import { StudiedCard } from './studied-card.model';

export interface LearningDeck {
  id: number;
  learnedDeck: Deck;
  myStudiedCards: StudiedCard[];
}
