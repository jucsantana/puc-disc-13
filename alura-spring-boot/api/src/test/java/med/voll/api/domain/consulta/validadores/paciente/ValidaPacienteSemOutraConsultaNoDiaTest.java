package med.voll.api.domain.consulta.validadores.paciente;

import med.voll.api.BaseTestDataBase;
import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class ValidaPacienteSemOutraConsultaNoDiaTest extends BaseTestDataBase {

    @Autowired
    ValidaPacienteSemOutraConsultaNoDia validador;

    @Test
    @Transactional
    @DisplayName("Deve devolver uma excecao devido ao paciente já possuir consulta dia")
    void cenario_1(){

        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);

        var medico = cadastrarMedico("Fulano", "fulando@voll.med", "123456", Especialidade.CARDIOLOGIA);
        var paciente = cadastrarPaciente("ciclano","ciclano@globo.com", "12345678910");
        cadastrarConsulta(medico, paciente, proximaSegundaAs10);

        var dadosAgendamentoConsulta = mock(DadosAgendamentoConsulta.class);
        when(dadosAgendamentoConsulta.data()).thenReturn(proximaSegundaAs10);
        when(dadosAgendamentoConsulta.idPaciente()).thenReturn(paciente.getId());

        ValidacaoException validacaoException = assertThrows(ValidacaoException.class, () -> {
            validador.validar(dadosAgendamentoConsulta);
        });

        assertEquals("Paciente já possui uma consulta agendada nesse dia!", validacaoException.getMessage());
    }

    @Test
    @Transactional
    @DisplayName("Deve permitir cadastrar o paciente na consulta no horário")
    void cenario_2(){

        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);

        cadastrarMedico("Fulano", "fulando@voll.med", "123456", Especialidade.CARDIOLOGIA);
        var paciente = cadastrarPaciente("ciclano","ciclano@globo.com", "12345678910");

        var dadosAgendamentoConsulta = mock(DadosAgendamentoConsulta.class);
        when(dadosAgendamentoConsulta.data()).thenReturn(proximaSegundaAs10);
        when(dadosAgendamentoConsulta.idPaciente()).thenReturn(paciente.getId());

        assertDoesNotThrow(() -> validador.validar(dadosAgendamentoConsulta));
    }

    @Test
    @Transactional
    @DisplayName("Deve permitir cadastrar paciente, com consulta futura agenda")
    void cenario_3(){

        var segundaAs10DaquiaDoisMeses = LocalDate.now().plusMonths(2)
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);

        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);

        var medico = cadastrarMedico("Fulano", "fulando@voll.med", "123456", Especialidade.CARDIOLOGIA);
        var paciente = cadastrarPaciente("ciclano","ciclano@globo.com", "12345678910");
        cadastrarConsulta(medico, paciente, segundaAs10DaquiaDoisMeses);

        var dadosAgendamentoConsulta = mock(DadosAgendamentoConsulta.class);
        when(dadosAgendamentoConsulta.data()).thenReturn(proximaSegundaAs10);
        when(dadosAgendamentoConsulta.idPaciente()).thenReturn(paciente.getId());

        assertDoesNotThrow(() -> {validador.validar(dadosAgendamentoConsulta);});
    }





}