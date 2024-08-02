package med.voll.api.domain.consulta.validadores.datas;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.validadores.ValidadorAgendamentoDeConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
@Component
public class ValidaHorarioAntecedencia implements ValidadorAgendamentoDeConsulta {

    public void validar(DadosAgendamentoConsulta dados) {
        var diferenca=0L;
        final int INTERVALO_MINIMO_AGENDAMENTO_CONSULTA=30;
        var agora = LocalDateTime.now();
        if(dados.data().toLocalDate().isBefore(agora.toLocalDate()) || dados.data().toLocalDate().isEqual(agora.toLocalDate())) {
             diferenca = Duration.between(dados.data(), agora).toMinutes();
        }else{
            diferenca = Duration.between(agora, dados.data()).toMinutes();
        }
        if(diferenca<INTERVALO_MINIMO_AGENDAMENTO_CONSULTA){
            throw new ValidacaoException("Consulta deve ser agendada com antecedência mínima de 30 minutos!");
        }
    }
}
