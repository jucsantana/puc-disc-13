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

class ValidaHorarioAntecedenciaTest {

    @Test
    @DisplayName("Deve agendar consulta com mais de 30 minutos de antecedencia")
    void cenario1(){
        var data = LocalDateTime.now().minusMinutes(30);
        var dadosAgendamentoConsulta = mock(DadosAgendamentoConsulta.class);
        when(dadosAgendamentoConsulta.data()).thenReturn(data);
        ValidadorAgendamentoDeConsulta validador = new ValidaHorarioAntecedencia();
        assertDoesNotThrow(() -> {validador.validar(dadosAgendamentoConsulta);});
    }

    @Test
    @DisplayName("Deve recusar agendamento de consulta menos de 30 minutos de antecedencia")
    void cenario2() {
        var data = LocalDateTime.now().plusMinutes(5);
        var dadosAgendamentoConsulta = mock(DadosAgendamentoConsulta.class);
        when(dadosAgendamentoConsulta.data()).thenReturn(data);
        ValidadorAgendamentoDeConsulta validador = new ValidaHorarioAntecedencia();
        ValidacaoException validacaoException = assertThrows(ValidacaoException.class, () -> {
            validador.validar(dadosAgendamentoConsulta);
        });
        assertEquals("Consulta deve ser agendada com antecedência mínima de 30 minutos!", validacaoException.getMessage());
    }

}