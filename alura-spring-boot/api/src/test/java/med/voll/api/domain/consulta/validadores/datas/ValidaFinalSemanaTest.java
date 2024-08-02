package med.voll.api.domain.consulta.validadores.datas;


import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.validadores.ValidadorAgendamentoDeConsulta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class ValidaFinalSemanaTest {

    @Test
    @DisplayName("deve validar data agendamento fora do dia de funcionamento")
    void validador_cenario1(){
        var data = adjustToWeekend(LocalDateTime.now());
        var dadosAgendamentoConsulta = mock(DadosAgendamentoConsulta.class);
        when(dadosAgendamentoConsulta.data()).thenReturn(data);
        ValidadorAgendamentoDeConsulta validador = new ValidaFinalSemana();
        ValidacaoException validacaoException = assertThrows(ValidacaoException.class, () -> {validador.validar(dadosAgendamentoConsulta);});
        assertEquals("Data fornecida está fora do dia de funcionamento da clinica!", validacaoException.getMessage());
    }

    @Test
    @DisplayName("deve validar data agendamento para dia de funcionamento")
    void validador_cenario2(){
        var data = adjustToWeek(LocalDateTime.now());
        var dadosAgendamentoConsulta = mock(DadosAgendamentoConsulta.class);
        when(dadosAgendamentoConsulta.data()).thenReturn(data);
        ValidadorAgendamentoDeConsulta validador = new ValidaFinalSemana();
        assertDoesNotThrow(() -> {validador.validar(dadosAgendamentoConsulta);});

    }

    private static LocalDateTime adjustToWeekend(LocalDateTime dateTime) {
       return dateTime.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
    }

    private static LocalDateTime adjustToWeek(LocalDateTime dateTime) {
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();

        if (dayOfWeek == DayOfWeek.SATURDAY) {
            return dateTime.plusDays(2);
        } else if (dayOfWeek == DayOfWeek.SUNDAY) {
            return dateTime.plusDays(1);
        }
        return dateTime;
    }


}