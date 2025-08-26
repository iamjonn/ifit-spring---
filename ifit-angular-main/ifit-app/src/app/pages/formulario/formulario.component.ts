import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UsuarioService, Usuario } from '../../services/usuario.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-formulario',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './formulario.component.html',
  styleUrls: ['./formulario.component.css'],
})
export class FormularioComponent implements OnInit {
  router = inject(Router);
  usuarioService = inject(UsuarioService);
  http = inject(HttpClient);

  todosExercicios: any = {};
  mensagemSucesso: string | null = null;
  mensagemErro: string | null = null;

  form: Usuario & {
    idade?: number;
    frequencia?: number;
    nivel?: string;
    lesao?: string;
    local?: string;
    treino?: any[];
  } = {
    nome: '',
    email: '',
    objetivo: '',
    idade: 0,
    frequencia: 3,
    nivel: '',
    lesao: '',
    local: '',
    treino: []
  };

  ngOnInit(): void {
    this.http.get('http://localhost:3000/exercicios').subscribe({
      next: (data) => this.todosExercicios = data,
      error: () => this.mensagemErro = 'Erro ao carregar exercícios.'
    });
  }

  gerarTreino(formRef: NgForm) {
    this.mensagemSucesso = null;
    this.mensagemErro = null;

    if (formRef.invalid || this.form.idade! < 16 || this.form.idade! > 120) {
      this.mensagemErro = 'Verifique os dados preenchidos.';
      return;
    }

    const treinoGerado = this.montarTreino();
    this.form.treino = treinoGerado;

    this.usuarioService.criarUsuario(this.form).subscribe({
      next: (res) => {
        this.mensagemSucesso = 'Usuário cadastrado! Redirecionando...';
        this.router.navigate(['/treino', res.id]);
      },
      error: () => {
        this.mensagemErro = 'Erro ao cadastrar usuário.';
      }
    });
  }

  montarTreino() {
    const dias = Number(this.form.frequencia);
    const partes = ['Tórax', 'Costas', 'Pernas'];
    const partesSelecionadas: string[] = [];

    let i = 0;
    while (partesSelecionadas.length < dias) {
      const parte = partes[i % partes.length];
      if (this.form.lesao?.toLowerCase() !== 'nenhuma' && parte === this.form.lesao) {
        i++;
        continue;
      }
      partesSelecionadas.push(parte);
      i++;
    }

    const objetivo = this.form.objetivo?.toLowerCase();
    const local = this.form.local?.toLowerCase();

    const treinos = partesSelecionadas.map(parte => {
      const lista = this.todosExercicios[parte]?.filter((ex: any) =>
        ex.objetivo.toLowerCase() === objetivo &&
        (local === 'academia' || ex.local.toLowerCase() === local)
      ) || [];

      const selecionados = this.sortearExercicios(lista, 5 + Math.floor(Math.random() * 3));

      // Salvar apenas os IDs dos exercícios
      return {
        parte,
        exercicios: selecionados.map((ex: any) => ex.id)
      };
    });

    return treinos;
  }

  sortearExercicios(lista: any[], qtd: number) {
    const copia = [...lista];
    const resultado = [];

    while (resultado.length < qtd && copia.length > 0) {
      const i = Math.floor(Math.random() * copia.length);
      resultado.push(copia[i]);
      copia.splice(i, 1);
    }

    return resultado;
  }
}
