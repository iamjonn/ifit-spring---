import { Component, WritableSignal, signal } from '@angular/core';
import { UsuarioService } from '../../services/usuario.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-treino',
  standalone: true,
  imports: [],
  templateUrl: './treino.component.html',
  styleUrl: './treino.component.css'
})
export class TreinoComponent {
  usuario: any;
  treinoGerado: WritableSignal<any[]> = signal([]);

  constructor(
    private usuarioService: UsuarioService,
    private route: ActivatedRoute
  ) {
    this.route.params.subscribe(params => {
      const id = params['id'];
      if (id) {
        this.usuarioService.getUsuarioById(id).subscribe({
          next: (data) => {
            this.usuario = data;
            this.montarTreinoCompleto(this.gerarTreino());
          },
          error: (err) => console.error('Erro ao buscar usuário:', err)
        });
      }
    });
  }

  private gerarTreino(): any[] {
    const objetivo = this.usuario?.objetivo;
    switch (objetivo) {
      case 'hipertrofia':
        return [
          { parte: 'superior', exercicios: [2, 3, 4] },
          { parte: 'inferior', exercicios: [1, 2, 3] },
          { parte: 'abdominal', exercicios: [1] }
        ];
      case 'emagrecimento':
        return [
          { parte: 'superior', exercicios: [1, 5, 6] },
          { parte: 'inferior', exercicios: [4, 5, 6] },
          { parte: 'cardio', exercicios: [1, 2] }
        ];
      default:
        return [];
    }
  }

  private montarTreinoCompleto(treinoBruto: any[]) {
    fetch('http://localhost:8080/api/exercicios')
      .then(res => res.json())
      .then((data) => {
        const treinoFinal = treinoBruto.map(dia => {
          const parte = dia.parte;
          const lista = data[parte] || [];

          const exercicios = dia.exercicios.map((id: number) => {
            return lista.find((ex: any) => ex.id === id);
          }).filter(Boolean);

          return { parte, exercicios };
        });

        this.treinoGerado.set(treinoFinal);
      })
      .catch(err => console.error('Erro ao montar treino:', err));
  }
}