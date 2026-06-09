import { Card } from './card.model';

export interface Deck {
  id: number;
  name: string;
}

export interface DeckDetailed {
  id: number;
  name: string;
  cards: Card[];
}

export interface CreateDeck {
  name: string;
}
