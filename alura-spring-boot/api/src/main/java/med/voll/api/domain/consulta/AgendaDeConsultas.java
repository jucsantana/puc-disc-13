package med.voll.api.domain.consulta;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.validadores.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validadores;

    public Consulta agendar(DadosAgendamentoConsulta dados){
        var medico = ObterMedico(dados.idMedico(), dados.especialidade(), dados.data());
        var paciente = obterPacientePeloId(dados.idPaciente());
        validadores.forEach(v -> v.validar(dados));
        var consulta = new Consulta(null, medico, paciente, dados.data());
        return consultaRepository.save(consulta);
    }


    private Medico ObterMedico(Long idMedico, Especialidade especialidade, LocalDateTime data) {
        return  idMedico == null
                         ? escolherMedico(especialidade, data)
                         : obterMedicoPeloId(idMedico);
    }
    private Medico escolherMedico(Especialidade especialidade, LocalDateTime data) {
        if(especialidade == null){
            throw new ValidacaoException("Especialidade é obrigatória quando médico não for escolhido!");
        }
        return medicoRepository.escolherMedicoAleatorioLivreNaData(especialidade, data);
    }

    private Paciente obterPacientePeloId(Long idPaciente) {
        var paciente = pacienteRepository.findById(idPaciente);
        if (paciente.isPresent()) {
            return paciente.get();
        } else{
            throw new ValidacaoException("Id do paciente informado não existe!");
        }
    }

    private Medico obterMedicoPeloId(Long idMedico) {
        var medicoOpt = medicoRepository.findById(idMedico);
        if (medicoOpt.isPresent()) {
           return medicoOpt.get();
        } else{
            throw new ValidacaoException("Id do medico informado não existe!");
        }
    }

}
