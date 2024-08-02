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

class ValidaHorarioDepoisEncerramentoDaClinicaTest {

    @Test
    @DisplayName("Deve agendar consulta antes do horário de encerramento")
    void cenario1(){
        var data = LocalDateTime.now().withHour(17).withMinute(59);
        var dadosAgendamentoConsulta = mock(DadosAgendamentoConsulta.class);
        when(dadosAgendamentoConsulta.data()).thenReturn(data);
        ValidadorAgendamentoDeConsulta validador = new ValidaHorarioDepoisEncerramentoDaClinica();
        assertDoesNotThrow(() -> {validador.validar(dadosAgendamentoConsulta);});
    }

    @Test
    @DisplayName("Deve recusar agendamento de consulta depois do horário de encerramento")
    void cenario2() {
        var data = LocalDateTime.now().withHour(18).withMinute(00);
        var dadosAgendamentoConsulta = mock(DadosAgendamentoConsulta.class);
        when(dadosAgendamentoConsulta.data()).thenReturn(data);
        ValidadorAgendamentoDeConsulta validador =new ValidaHorarioDepoisEncerramentoDaClinica();
        ValidacaoException validacaoException = assertThrows(ValidacaoException.class, () -> {
            validador.validar(dadosAgendamentoConsulta);
        });
        assertEquals("Hora fornecida está depois do horário de funcionamento da clinica!", validacaoException.getMessage());
    }

}