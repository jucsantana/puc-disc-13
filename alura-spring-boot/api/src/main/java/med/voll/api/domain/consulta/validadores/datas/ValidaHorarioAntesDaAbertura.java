package med.voll.api.domain.consulta.validadores.datas;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.validadores.ValidadorAgendamentoDeConsulta;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ValidaHorarioAntesDaAbertura implements ValidadorAgendamentoDeConsulta {
    final int HORARIO_ABERTURA_CLINICA=7;
    public void validar(DadosAgendamentoConsulta dados) {
        if(dados.data().getHour()<HORARIO_ABERTURA_CLINICA){
            throw new ValidacaoException("Hora fornecida está fora do horário de abertura da clinica!");
        }
    }
}
