package med.voll.api.domain.endereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    private String logradouro;
    private String bairro;
    private String cep;
    private String cidade;
    private String uf;
    private String numero;
    private String complemento;


    public Endereco(DadosEndereco dados) {
        this(dados.logradouro(),
        dados.bairro(),
        dados.cep(),
        dados.cidade(),
        dados.uf(),
        dados.numero(),
        dados.complemento());
    }

    public void atualizarInformacoes(DadosEndereco dados) {
        Field[] fields = DadosEndereco.class.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object valor = field.get(dados);
                if (valor != null) {
                    Field fieldEndereco = Endereco.class.getDeclaredField(field.getName());
                    fieldEndereco.setAccessible(true);
                    fieldEndereco.set(this, valor);
                }
            } catch (IllegalAccessException | NoSuchFieldException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
