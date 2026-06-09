# memoloop — frontend

Angular 18 (standalone components) front-end for the **memoloop** Spring Boot API.

## Prerequisites

- Node.js 20+ and npm
- The backend running on `http://localhost:8080` (PostgreSQL must be up — see `application.properties`)

## Install

```bash
cd frontend
npm install
```

## Run

```bash
npm start
```

Opens on `http://localhost:4200` and talks to the backend on `http://localhost:8080`.

If your backend runs elsewhere, edit `src/environments/environment.ts`:

```ts
export const environment = {
  production: false,
  apiBaseUrl: 'http://your-host:8080'
};
```

## Build

```bash
npm run build
```

Output goes to `dist/memoloop-frontend/`.

## What's in here

| Route          | Page                                                         |
| -------------- | ------------------------------------------------------------ |
| `/`            | List all decks, create a new one, delete one                 |
| `/decks/:id`   | View deck cards, add a card, start a study session or quiz   |
| `/study/:id`   | Flashcard session: reveal translation, rate knowledge level  |
| `/quiz/:id`    | Multiple-choice quiz: pick translation, track score, replay  |

The study flow calls `POST /learningdecks/{deckId}` to start a session, then loops `POST /learningdecks/{ldId}/next` + `PUT /studiedcard` until the deck is exhausted.

The quiz flow calls `POST /quizzes/{deckId}` to generate a random quiz (default 10 questions, 4 choices each), then `POST /quizzes/{id}/answer` per question. Score updates live; final percentage is shown at the end. Needs a deck with at least 2 cards.
