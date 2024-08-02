package med.voll.api.domain.consulta.validadores.medico;

import med.voll.api.BaseTestDataBase;
import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.validadores.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.medico.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ValidaSeMedicoInativoTest extends BaseTestDataBase {

    @Autowired
    ValidaSeMedicoInativo validador;

    @Test
    @Transactional
    @DisplayName("Deve devolver uma excecao devido a nao existir medico disponivel para consulta na especialidade!")
    void cenario_1(){

        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);

        var dadosAgendamentoConsulta = mock(DadosAgendamentoConsulta.class);
        when(dadosAgendamentoConsulta.idMedico()).thenReturn(null);
        when(dadosAgendamentoConsulta.data()).thenReturn(proximaSegundaAs10);
        when(dadosAgendamentoConsulta.especialidade()).thenReturn(Especialidade.CARDIOLOGIA);

        ValidacaoException validacaoException = assertThrows(ValidacaoException.class, () -> {
            validador.validar(dadosAgendamentoConsulta);
        });

        assertEquals("Não existe médico disponível nessa data", validacaoException.getMessage());

    }

    @Test
    @Transactional
    @DisplayName("Deve devolver uma excecao devido ao medico informado nao existir!")
    void cenario_2(){

        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);

        var dadosAgendamentoConsulta = mock(DadosAgendamentoConsulta.class);
        when(dadosAgendamentoConsulta.data()).thenReturn(proximaSegundaAs10);
        when(dadosAgendamentoConsulta.idMedico()).thenReturn(100L);

        ValidacaoException validacaoException = assertThrows(ValidacaoException.class, () -> {
            validador.validar(dadosAgendamentoConsulta);
        });

        assertEquals("Consulta não pode ser marcada para médico excluído!", validacaoException.getMessage());

    }



}