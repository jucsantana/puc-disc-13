package med.voll.api.domain.consulta.validadores.datas;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class ValidaDataAgendaTest {

    static ValidaDataAgenda validador;

    @BeforeAll
    static void init(){
        validador = new ValidaDataAgenda();
    }

    @Test
    @DisplayName("Deve lanca excecao pois a data eh anterior a atual")
    void cenario_1(){
        var data = LocalDateTime.now().minusDays(1);
        var dadosAgendamentoConsulta = mock(DadosAgendamentoConsulta.class);
        when(dadosAgendamentoConsulta.data()).thenReturn(data);
        ValidacaoException validacaoException = assertThrows(ValidacaoException.class, () -> {
            validador.validar(dadosAgendamentoConsulta);
        });
        assertEquals("A data de agendamento não pode ser uma data passada!", validacaoException.getMessage());
    }

    @Test
    @DisplayName("Deve validar a data pois é igual a atual")
    void cenario_2(){
        var data = LocalDateTime.now();
        var dadosAgendamentoConsulta = mock(DadosAgendamentoConsulta.class);
        when(dadosAgendamentoConsulta.data()).thenReturn(data);
        assertDoesNotThrow(() -> {validador.validar(dadosAgendamentoConsulta);});
    }

    @Test
    @DisplayName("Deve validar a data pois é superior a atual")
    void cenario_3(){
        var data = LocalDateTime.now().plusDays(2);
        var dadosAgendamentoConsulta = mock(DadosAgendamentoConsulta.class);
        when(dadosAgendamentoConsulta.data()).thenReturn(data);
        assertDoesNotThrow(() -> {validador.validar(dadosAgendamentoConsulta);});
    }

}