package ads.metodista.br.metolabs;

/**
 * Created by rafaelsakurai on 26/04/17.
 */

public class Lab {

    private String id;
    private String nome;
    private String status;

    public Lab(String nome) {
        this.nome = nome;
        this.status = "Fechado";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Lab{" +
                "_id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
