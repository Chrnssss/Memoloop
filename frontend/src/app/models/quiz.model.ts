import { Deck } from './deck.model';

export interface QuizChoice {
  cardId: number;
  translation: string;
}

export interface QuizQuestion {
  id: number;
  orderIndex: number;
  prompt: string;
  choices: QuizChoice[];
  answered: boolean;
  selectedCardId: number | null;
  correct: boolean | null;
  correctCardId: number | null;
}

export interface Quiz {
  id: number;
  deck: Deck;
  score: number;
  totalQuestions: number;
  completed: boolean;
  createdAt: string;
  questions: QuizQuestion[];
}

export interface QuizAnswer {
  questionId: number;
  selectedCardId: number;
}

export interface QuizAnswerResult {
  questionId: number;
  correct: boolean;
  correctCardId: number;
  score: number;
  totalQuestions: number;
  completed: boolean;
}
