import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    loadComponent: () =>
      import('./pages/decks-list/decks-list.component').then(m => m.DecksListComponent)
  },
  {
    path: 'decks/:id',
    loadComponent: () =>
      import('./pages/deck-detail/deck-detail.component').then(m => m.DeckDetailComponent)
  },
  {
    path: 'study/:id',
    loadComponent: () =>
      import('./pages/study/study.component').then(m => m.StudyComponent)
  },
  {
    path: 'quiz/:id',
    loadComponent: () =>
      import('./pages/quiz/quiz.component').then(m => m.QuizComponent)
  },
  { path: '**', redirectTo: '' }
];
