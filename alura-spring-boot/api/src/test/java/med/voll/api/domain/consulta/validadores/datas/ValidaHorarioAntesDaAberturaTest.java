package med.voll.api.domain.consulta.validadores.datas;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.validadores.ValidadorAgendamentoDeConsulta;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ValidaHorarioAntesDaAberturaTest {

    @Test
    @DisplayName("Deve agendar consulta depois do horário de abertura")
    void cenario1(){
        var data = LocalDateTime.now().withHour(7);
        var dadosAgendamentoConsulta = mock(DadosAgendamentoConsulta.class);
        when(dadosAgendamentoConsulta.data()).thenReturn(data);
        ValidadorAgendamentoDeConsulta validador = new ValidaHorarioAntesDaAbertura();
        assertDoesNotThrow(() -> {validador.validar(dadosAgendamentoConsulta);});
    }

    @Test
    @DisplayName("Deve recusar agendamento de consulta antes do horário de atendimento")
    void cenario2() {
        var data = LocalDateTime.now().withHour(6).withMinute(30);
        var dadosAgendamentoConsulta = mock(DadosAgendamentoConsulta.class);
        when(dadosAgendamentoConsulta.data()).thenReturn(data);
        ValidadorAgendamentoDeConsulta validador =new ValidaHorarioAntesDaAbertura();
        ValidacaoException validacaoException = assertThrows(ValidacaoException.class, () -> {
            validador.validar(dadosAgendamentoConsulta);
        });
        assertEquals("Hora fornecida está fora do horário de abertura da clinica!", validacaoException.getMessage());
    }


}