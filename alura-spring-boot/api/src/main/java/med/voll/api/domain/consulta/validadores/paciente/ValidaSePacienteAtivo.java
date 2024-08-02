package med.voll.api.domain.consulta.validadores.paciente;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.validadores.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ValidaSePacienteAtivo implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private PacienteRepository repository;

    public void validar(DadosAgendamentoConsulta dados) {

        var paciente = repository.findByIdAndAtivoTrue(dados.idPaciente());

        if(paciente==null) {
            throw new ValidacaoException("Consulta não pode ser agendada para paciente excluído!");
        }

    }
}
