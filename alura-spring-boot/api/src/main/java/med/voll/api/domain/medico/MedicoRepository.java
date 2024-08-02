package med.voll.api.domain.medico;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Page<Medico> findAllByAtivoTrue(Pageable paginacao);

    @Query(value = """
            Select m from Medico m 
                where m.ativo = true
                and
                m.especialidade = :especialidade
                and not exists
                (Select c from Consulta c 
                        where c.data = :data 
                        and c.medico.id = m.id) 
                ORDER BY function('RAND')
                limit 1
            """)
    Medico escolherMedicoAleatorioLivreNaData(Especialidade especialidade, LocalDateTime data);

    Medico findByIdAndAtivoTrue(Long idMedico);
}
