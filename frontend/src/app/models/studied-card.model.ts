import { Card } from './card.model';

export interface StudiedCard {
  id: number;
  card: Card;
  lastStudiedAt: string | null;
  views: number;
  knowledgeLevel: number;
}
