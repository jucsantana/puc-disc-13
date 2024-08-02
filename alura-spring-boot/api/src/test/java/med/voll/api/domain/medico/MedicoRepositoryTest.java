package med.voll.api.domain.medico;

import med.voll.api.BaseTestDataBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest extends BaseTestDataBase {

    @Autowired
    private MedicoRepository repository;


    @Test
    @DisplayName("Deveria devolver null quando unico medico cadastrado nao esta diponivel na data")
    void escolherMedicoAleatorioLivreNaDataCenário1() {
        //given ou arrange
        var proximaSegundaAs10 = LocalDate.now()
                                            .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                                            .atTime(10,0);


        var medico = cadastrarMedico("Fulano", "fulando@voll.med", "123456", Especialidade.CARDIOLOGIA);
        var paciente = cadastrarPaciente("ciclano","ciclano@globo.com", "12345678910");
        cadastrarConsulta(medico, paciente, proximaSegundaAs10);


        // when ou act
        var medicoLivre = repository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA,proximaSegundaAs10);

        //assert or then
        assertThat(medicoLivre).isNull();
    }

    @Test
    @DisplayName("Deveria devolver medico cadastrado diponivel na data")
    void escolherMedicoAleatorioLivreNaDataCenário2() {

        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);

        var medico = cadastrarMedico("Fulano", "fulando@voll.med", "123456", Especialidade.CARDIOLOGIA);
        var medicoLivre = repository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA,proximaSegundaAs10);
        assertThat(medicoLivre).isEqualTo(medico);
    }



}