package med.voll.api.domain.consulta.validadores.paciente;

import med.voll.api.BaseTestDataBase;
import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ValidaPacienteSemOutraConsultaNoDiaTest {

    @InjectMocks
    ValidaPacienteSemOutraConsultaNoDia validador;

    @Mock
    private ConsultaRepository repository;


    @Test
    @DisplayName("Deve devolver uma excecao devido ao paciente já possuir consulta dia")
    void cenario_1(){

        final int HORARIO_ABERTURA_CLINICA=7;
        final int HORARIO_FECHAMENTO_CLINICA=18;

        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);


        var dadosAgendamentoConsulta = mock(DadosAgendamentoConsulta.class);
        when(dadosAgendamentoConsulta.data()).thenReturn(proximaSegundaAs10);
        when(dadosAgendamentoConsulta.idPaciente()).thenReturn(1L);

        var primeiroHorario = dadosAgendamentoConsulta.data().withHour(HORARIO_ABERTURA_CLINICA);
        var ultimoHorario = dadosAgendamentoConsulta.data().withHour(HORARIO_FECHAMENTO_CLINICA);

        BDDMockito.given(repository.existsByPacienteIdAndDataBetween(dadosAgendamentoConsulta.idPaciente(),primeiroHorario,ultimoHorario)).willReturn(Boolean.TRUE);

        ValidacaoException validacaoException = assertThrows(ValidacaoException.class, () -> {
            validador.validar(dadosAgendamentoConsulta);
        });

        assertEquals("Paciente já possui uma consulta agendada nesse dia!", validacaoException.getMessage());
    }

    @Test
    @DisplayName("Deve permitir cadastrar o paciente na consulta no horário")
    void cenario_2(){
        final int HORARIO_ABERTURA_CLINICA=7;
        final int HORARIO_FECHAMENTO_CLINICA=18;

        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);


        var dadosAgendamentoConsulta = mock(DadosAgendamentoConsulta.class);
        when(dadosAgendamentoConsulta.data()).thenReturn(proximaSegundaAs10);
        when(dadosAgendamentoConsulta.idPaciente()).thenReturn(1L);

        var primeiroHorario = dadosAgendamentoConsulta.data().withHour(HORARIO_ABERTURA_CLINICA);
        var ultimoHorario = dadosAgendamentoConsulta.data().withHour(HORARIO_FECHAMENTO_CLINICA);

        BDDMockito.given(repository.existsByPacienteIdAndDataBetween(dadosAgendamentoConsulta.idPaciente(),primeiroHorario,ultimoHorario)).willReturn(Boolean.FALSE);


        assertDoesNotThrow(() -> validador.validar(dadosAgendamentoConsulta));
    }

}