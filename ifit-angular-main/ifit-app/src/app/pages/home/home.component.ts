import { Component, inject } from '@angular/core';
import { isPlatformBrowser, CommonModule } from '@angular/common';
import { PLATFORM_ID } from '@angular/core';
import { signal } from '@angular/core';
import { PlanosComponent } from '../../components/planos/planos.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, PlanosComponent],
  templateUrl: './home.component.html'
})
export class HomeComponent {
  private platformId = inject(PLATFORM_ID);

  nome = signal<string | null>(null);
  treino = signal<string[]>([]);

  planos = signal([
    {
      nome: 'Plano Básico',
      descricao: 'Ideal para iniciantes. Acesso comercial.',
      preco: 'R$ 69,90/mês'
    },
    {
      nome: 'Plano Plus',
      descricao: 'Inclui aulas e avaliação física.',
      preco: 'R$ 99,90/mês'
    },
    {
      nome: 'Plano Premium',
      descricao: 'Personal, nutrição e acesso completo.',
      preco: 'R$ 149,90/mês'
    }
  ]);

  constructor() {
    if (isPlatformBrowser(this.platformId)) {
      const state = window.history.state;
      if (state?.treino) {
        this.nome.set(state.nome);
        this.treino.set(state.treino);
      }
    }
  }
}
