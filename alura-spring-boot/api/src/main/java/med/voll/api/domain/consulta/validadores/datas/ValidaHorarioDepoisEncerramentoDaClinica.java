package med.voll.api.domain.consulta.validadores.datas;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.validadores.ValidadorAgendamentoDeConsulta;
import org.springframework.stereotype.Component;

@Component
public class ValidaHorarioDepoisEncerramentoDaClinica implements ValidadorAgendamentoDeConsulta {
    final int HORARIO_FECHAMENTO_CLINICA=18;

    public void validar(DadosAgendamentoConsulta dados) {
        if(dados.data().getHour() >= HORARIO_FECHAMENTO_CLINICA){
            throw new ValidacaoException("Hora fornecida está depois do horário de funcionamento da clinica!");
        }

    }
}
