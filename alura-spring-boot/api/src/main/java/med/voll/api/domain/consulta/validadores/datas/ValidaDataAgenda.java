package med.voll.api.domain.consulta.validadores.datas;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.validadores.ValidadorAgendamentoDeConsulta;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
public class ValidaDataAgenda implements ValidadorAgendamentoDeConsulta {

    public void validar(DadosAgendamentoConsulta dados) {
            var hoje = LocalDate.now();
            var dataVencida = dados.data().toLocalDate().isBefore(hoje);
        if (dataVencida) {
            throw new ValidacaoException("A data de agendamento não pode ser uma data passada!");
        }
    }

}
