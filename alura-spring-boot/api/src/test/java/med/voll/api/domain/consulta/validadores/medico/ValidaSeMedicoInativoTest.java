package med.voll.api.domain.consulta.validadores.medico;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.medico.MedicoRepository;
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
class ValidaSeMedicoInativoTest{

    @InjectMocks
    private ValidaSeMedicoInativo validador;

    @Mock
    private MedicoRepository repository;

    @Test
    @DisplayName("Deve devolver uma excecao devido a nao existir medico disponivel para consulta na especialidade!")
    void cenario_1(){

        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);

        var dadosAgendamentoConsulta = mock(DadosAgendamentoConsulta.class);
        when(dadosAgendamentoConsulta.idMedico()).thenReturn(null);
        when(dadosAgendamentoConsulta.data()).thenReturn(proximaSegundaAs10);
        when(dadosAgendamentoConsulta.especialidade()).thenReturn(Especialidade.CARDIOLOGIA);

        BDDMockito.given(repository.escolherMedicoAleatorioLivreNaData(dadosAgendamentoConsulta.especialidade(), dadosAgendamentoConsulta.data())).willReturn(null);

        ValidacaoException validacaoException = assertThrows(ValidacaoException.class, () -> {
            validador.validar(dadosAgendamentoConsulta);
        });

        assertEquals("Não existe médico disponível nessa data", validacaoException.getMessage());

    }

    @Test
    @DisplayName("Deve devolver uma excecao devido ao medico informado nao existir!")
    void cenario_2(){

        var dadosAgendamentoConsulta = mock(DadosAgendamentoConsulta.class);
        when(dadosAgendamentoConsulta.idMedico()).thenReturn(100L);

        BDDMockito.given(repository.findByIdAndAtivoTrue(dadosAgendamentoConsulta.idMedico())).willReturn(null);

        ValidacaoException validacaoException = assertThrows(ValidacaoException.class, () -> {
            validador.validar(dadosAgendamentoConsulta);
        });

        assertEquals("Consulta não pode ser marcada para médico excluído!", validacaoException.getMessage());

    }



}