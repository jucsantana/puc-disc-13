package med.voll.api.domain.medico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.endereco.Endereco;

import java.lang.reflect.Field;


@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String crm;
    private String telefone;
    private Boolean ativo;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Embedded
    private Endereco endereco;

    public Medico(DadosCadastroMedico dados) {
        this(null,
                dados.nome(),
                dados.email(),
                dados.crm(),
                dados.telefone(),
                Boolean.TRUE,
                dados.especialidade(),
                new Endereco(dados.endereco())
             );
    }

    public void atualizarInformacoes(DadosAtualizacaoMedico dados) {
        Field[] fields = DadosAtualizacaoMedico.class.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object valor = field.get(dados);
                if (valor != null) {
                    atualizarCampo(dados, field, valor);
                }
            } catch (IllegalAccessException | NoSuchFieldException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private void atualizarCampo(DadosAtualizacaoMedico dados, Field field, Object valor) throws NoSuchFieldException, IllegalAccessException {
        if(field.getType().equals(DadosEndereco.class)){
            atualizarInformacoesEndereco(dados);
        }else if(!field.getName().equalsIgnoreCase("id")){
            atualizarInformacoesMedico(field, valor);
        }
    }

    private void atualizarInformacoesMedico(Field field, Object valor) throws NoSuchFieldException, IllegalAccessException {
        Field fieldMedico = Medico.class.getDeclaredField(field.getName());
        fieldMedico.setAccessible(true);
        fieldMedico.set(this, valor);
    }

    private void atualizarInformacoesEndereco(DadosAtualizacaoMedico dados) {
        Endereco enderecoAtual = this.endereco==null? new Endereco(): this.endereco;
        this.endereco=enderecoAtual;
        enderecoAtual.atualizarInformacoes(dados.endereco());
    }

    public void inativar(){
        this.ativo=Boolean.FALSE;
    }

}