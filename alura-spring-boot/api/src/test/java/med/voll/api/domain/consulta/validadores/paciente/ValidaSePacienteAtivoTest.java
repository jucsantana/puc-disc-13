package med.voll.api.domain.consulta.validadores.paciente;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ValidaSePacienteAtivoTest{

    @InjectMocks
    ValidaSePacienteAtivo validador;

    @Mock
    PacienteRepository repository;

    @Test
    @DisplayName("Deve devolver uma excecao devido ao paciente ter sido excluido")
    void cenario_1(){

        var dadosAgendamentoConsulta = mock(DadosAgendamentoConsulta.class);
        when(dadosAgendamentoConsulta.idPaciente()).thenReturn(1L);

        BDDMockito.given(repository.findByIdAndAtivoTrue(dadosAgendamentoConsulta.idPaciente())).willReturn(null);

        ValidacaoException validacaoException = assertThrows(ValidacaoException.class, () -> {
            validador.validar(dadosAgendamentoConsulta);
        });

        assertEquals("Consulta não pode ser agendada para paciente excluído!", validacaoException.getMessage());
    }

    @Test
    @DisplayName("Deve permitir usar o cadastro do paciente ativo")
    void cenario_2(){

        var paciente = mock(Paciente.class);
        when(paciente.getId()).thenReturn(1L);

        var dadosAgendamentoConsulta = mock(DadosAgendamentoConsulta.class);
        when(dadosAgendamentoConsulta.idPaciente()).thenReturn(1L);

        BDDMockito.given(repository.findByIdAndAtivoTrue(dadosAgendamentoConsulta.idPaciente())).willReturn(paciente);

        assertDoesNotThrow(()-> validador.validar(dadosAgendamentoConsulta));

    }
}