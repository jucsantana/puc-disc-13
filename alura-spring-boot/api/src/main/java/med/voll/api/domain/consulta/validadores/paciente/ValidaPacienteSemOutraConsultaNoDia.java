package med.voll.api.domain.consulta.validadores.paciente;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.validadores.ValidadorAgendamentoDeConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ValidaPacienteSemOutraConsultaNoDia implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private ConsultaRepository repository;

    public void validar(DadosAgendamentoConsulta dados) {
        final int HORARIO_ABERTURA_CLINICA=7;
        final int HORARIO_FECHAMENTO_CLINICA=18;

        var primeiroHorario = dados.data().withHour(HORARIO_ABERTURA_CLINICA);
        var ultimoHorario = dados.data().withHour(HORARIO_FECHAMENTO_CLINICA);
        var pacientePossuiOutraConsultaNoDia = repository.existsByPacienteIdAndDataBetween(dados.idPaciente(), primeiroHorario, ultimoHorario);

        if(pacientePossuiOutraConsultaNoDia){
            throw new ValidacaoException("Paciente já possui uma consulta agendada nesse dia!");
        }

    }
}
