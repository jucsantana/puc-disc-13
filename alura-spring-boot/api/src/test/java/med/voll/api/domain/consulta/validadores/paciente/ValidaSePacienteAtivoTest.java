package med.voll.api.domain.consulta.validadores.paciente;

import med.voll.api.BaseTestDataBase;
import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class ValidaSePacienteAtivoTest extends BaseTestDataBase {

    @Autowired
    ValidaSePacienteAtivo validador;


    @Test
    @Transactional
    @DisplayName("Deve devolver uma excecao devido ao paciente ter sido excluido")
    void cenario_1(){
        var paciente = cadastrarPaciente("ciclano","ciclano@globo.com", "12345678910");
        paciente.excluir();

        var dadosAgendamentoConsulta = mock(DadosAgendamentoConsulta.class);
        when(dadosAgendamentoConsulta.idPaciente()).thenReturn(paciente.getId());

        ValidacaoException validacaoException = assertThrows(ValidacaoException.class, () -> {
            validador.validar(dadosAgendamentoConsulta);
        });

        assertEquals("Consulta não pode ser agendada para paciente excluído!", validacaoException.getMessage());
    }

    @Test
    @Transactional
    @DisplayName("Deve permitir usar o cadastro do paciente ativo")
    void cenario_2(){

        var paciente = cadastrarPaciente("ciclano","ciclano@globo.com", "12345678910");

        var dadosAgendamentoConsulta = mock(DadosAgendamentoConsulta.class);
        when(dadosAgendamentoConsulta.idPaciente()).thenReturn(paciente.getId());

        assertDoesNotThrow(()-> validador.validar(dadosAgendamentoConsulta));

    }
}