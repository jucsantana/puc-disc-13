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
import org.springframework.context.annotation.Import;
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
class ValidaConsultaConcorrenteTest extends BaseTestDataBase {

    @Autowired
    ValidaConsultaConcorrente validador;

    @Test
    @Transactional
    @DisplayName("Deve devolver uma excecao devido ao médico já possuir consulta no horário")
    void cenario_1(){

        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);

        var medico = cadastrarMedico("Fulano", "fulando@voll.med", "123456", Especialidade.CARDIOLOGIA);
        var paciente = cadastrarPaciente("ciclano","ciclano@globo.com", "12345678910");
        cadastrarConsulta(medico, paciente, proximaSegundaAs10);

        var dadosAgendamentoConsulta = mock(DadosAgendamentoConsulta.class);
        when(dadosAgendamentoConsulta.data()).thenReturn(proximaSegundaAs10);
        when(dadosAgendamentoConsulta.idMedico()).thenReturn(medico.getId());

        ValidacaoException validacaoException = assertThrows(ValidacaoException.class, () -> {
            validador.validar(dadosAgendamentoConsulta);
        });

        assertEquals("Médico já possui outra consulta agendada nesse mesmo horário!", validacaoException.getMessage());
    }

    @Test
    @Transactional
    @DisplayName("Deve permitir cadastrar o médico na consulta no horário")
    void cenario_2(){

        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);

        var medico = cadastrarMedico("Fulano", "fulando@voll.med", "123456", Especialidade.CARDIOLOGIA);

        var dadosAgendamentoConsulta = mock(DadosAgendamentoConsulta.class);
        when(dadosAgendamentoConsulta.data()).thenReturn(proximaSegundaAs10);
        when(dadosAgendamentoConsulta.idMedico()).thenReturn(medico.getId());

        assertDoesNotThrow(() -> {validador.validar(dadosAgendamentoConsulta);});
    }

}