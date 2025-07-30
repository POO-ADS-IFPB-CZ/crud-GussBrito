import java.io.Serializable;
import java.util.Objects;
public class Produto implements Serializable {

    // Identificador único para a versão da classe durante a serialização
    private static final long serialVersionUID = 1L;

    private int codigo;
    private String descricao;
    private double preco;

    public Produto(int codigo, String descricao, double preco){
        this.codigo = codigo;
        this.descricao = descricao;
        this.preco = preco;
    }

    //Getters e Setters


    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codiogo) {
        this.codigo = codiogo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    // Sobrescrevendo equals() e hashCode() para tratar um porduto como único pelo seu código.
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return codigo == produto.codigo;
    }
    @Override
    public int hashCode(){
        return Objects.hash(codigo);
    }
    @Override
    public String toString(){
        return "Produto{" +
                "codigo=" + codigo +
                ", descricao+'" + descricao + '\'' +
                ", preco=" +preco +
                '}';
    }

}
