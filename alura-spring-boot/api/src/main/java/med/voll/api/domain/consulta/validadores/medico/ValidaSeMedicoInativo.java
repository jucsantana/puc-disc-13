package med.voll.api.domain.consulta.validadores.medico;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.validadores.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class ValidaSeMedicoInativo implements ValidadorAgendamentoDeConsulta {

    @Autowired
    MedicoRepository repository;

    public void validar(DadosAgendamentoConsulta dados) {
        Medico medico;
        if(dados.idMedico()==null) {
           medico= repository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
           if(medico==null)
               throw new ValidacaoException("Não existe médico disponível nessa data");
        }else{
           medico = repository.findByIdAndAtivoTrue(dados.idMedico());
            if(medico==null)
               throw new ValidacaoException("Consulta não pode ser marcada para médico excluído!");
        }

    }
}
