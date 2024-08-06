package med.voll.api.domain.consulta.validadores.medico;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ValidaConsultaConcorrenteTest{

    @InjectMocks
    ValidaConsultaConcorrente validador;

    @Mock
    private ConsultaRepository repository;

    @Test
    @DisplayName("Deve devolver uma excecao devido ao médico já possuir consulta no horário")
    void cenario_1(){

        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);

        var dadosAgendamentoConsulta = mock(DadosAgendamentoConsulta.class);
        when(dadosAgendamentoConsulta.data()).thenReturn(proximaSegundaAs10);
        when(dadosAgendamentoConsulta.idMedico()).thenReturn(1L);

        BDDMockito.given(repository.existsByMedicoIdAndData(dadosAgendamentoConsulta.idMedico(), proximaSegundaAs10)).willReturn(Boolean.TRUE);

        ValidacaoException validacaoException = assertThrows(ValidacaoException.class, () -> {
            validador.validar(dadosAgendamentoConsulta);
        });

        assertEquals("Médico já possui outra consulta agendada nesse mesmo horário!", validacaoException.getMessage());
    }

    @Test
    @DisplayName("Deve permitir cadastrar o médico na consulta no horário")
    void cenario_2(){

        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);

        var dadosAgendamentoConsulta = mock(DadosAgendamentoConsulta.class);
        when(dadosAgendamentoConsulta.data()).thenReturn(proximaSegundaAs10);
        when(dadosAgendamentoConsulta.idMedico()).thenReturn(1L);

        BDDMockito.given(repository.existsByMedicoIdAndData(dadosAgendamentoConsulta.idMedico(), proximaSegundaAs10)).willReturn(Boolean.FALSE);

        assertDoesNotThrow(() -> {validador.validar(dadosAgendamentoConsulta);});
    }

}