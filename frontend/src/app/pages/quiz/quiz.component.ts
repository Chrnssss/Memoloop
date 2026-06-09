import { CommonModule } from '@angular/common';
import { Component, OnInit, computed, inject, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { Quiz, QuizQuestion } from '../../models/quiz.model';
import { QuizService } from '../../services/quiz.service';

@Component({
  selector: 'app-quiz',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './quiz.component.html',
  styleUrl: './quiz.component.css'
})
export class QuizComponent implements OnInit {
  private readonly route = inject(ActivatedRoute);
  private readonly quizService = inject(QuizService);

  quizId!: number;
  quiz = signal<Quiz | null>(null);
  currentIndex = signal(0);
  showingResult = signal(false);

  loading = signal(false);
  submitting = signal(false);
  error = signal<string | null>(null);

  currentQuestion = computed<QuizQuestion | null>(() => {
    const q = this.quiz();
    if (!q) return null;
    return q.questions[this.currentIndex()] ?? null;
  });

  scorePercentage = computed(() => {
    const q = this.quiz();
    if (!q || q.totalQuestions === 0) return 0;
    return Math.round((q.score / q.totalQuestions) * 100);
  });

  progressPercentage = computed(() => {
    const q = this.quiz();
    if (!q || q.totalQuestions === 0) return 0;
    const answered = q.questions.filter(x => x.answered).length;
    return Math.round((answered / q.totalQuestions) * 100);
  });

  isLastQuestion = computed(() => {
    const q = this.quiz();
    if (!q) return false;
    return this.currentIndex() === q.questions.length - 1;
  });

  ngOnInit(): void {
    this.quizId = Number(this.route.snapshot.paramMap.get('id'));
    this.load();
  }

  private load(): void {
    this.loading.set(true);
    this.error.set(null);
    this.quizService.getById(this.quizId).subscribe({
      next: quiz => {
        this.applyQuiz(quiz);
        this.loading.set(false);
      },
      error: err => {
        this.error.set(this.formatError(err));
        this.loading.set(false);
      }
    });
  }

  private applyQuiz(quiz: Quiz): void {
    this.quiz.set(quiz);
    if (quiz.completed) {
      this.currentIndex.set(quiz.questions.length - 1);
      this.showingResult.set(true);
    } else {
      const firstUnanswered = quiz.questions.findIndex(q => !q.answered);
      this.currentIndex.set(firstUnanswered === -1 ? 0 : firstUnanswered);
      this.showingResult.set(false);
    }
  }

  selectChoice(choiceCardId: number): void {
    const question = this.currentQuestion();
    if (!question || question.answered || this.submitting()) return;
    this.submitting.set(true);
    this.quizService.answer(this.quizId, {
      questionId: question.id,
      selectedCardId: choiceCardId
    }).subscribe({
      next: result => {
        this.quiz.update(q => {
          if (!q) return q;
          const updatedQuestions = q.questions.map(qq =>
            qq.id === question.id
              ? {
                  ...qq,
                  answered: true,
                  selectedCardId: choiceCardId,
                  correct: result.correct,
                  correctCardId: result.correctCardId
                }
              : qq
          );
          return { ...q, questions: updatedQuestions, score: result.score, completed: result.completed };
        });
        this.submitting.set(false);
      },
      error: err => {
        this.error.set(this.formatError(err));
        this.submitting.set(false);
      }
    });
  }

  next(): void {
    const q = this.quiz();
    if (!q) return;
    if (this.isLastQuestion() && q.completed) {
      this.showingResult.set(true);
      return;
    }
    if (this.currentIndex() < q.questions.length - 1) {
      this.currentIndex.set(this.currentIndex() + 1);
    }
  }

  retry(): void {
    const q = this.quiz();
    if (!q) return;
    this.loading.set(true);
    this.error.set(null);
    this.quizService.create(q.deck.id, q.totalQuestions).subscribe({
      next: newQuiz => {
        this.quizId = newQuiz.id;
        this.applyQuiz(newQuiz);
        this.loading.set(false);
        history.replaceState({}, '', `/quiz/${newQuiz.id}`);
      },
      error: err => {
        this.error.set(this.formatError(err));
        this.loading.set(false);
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
