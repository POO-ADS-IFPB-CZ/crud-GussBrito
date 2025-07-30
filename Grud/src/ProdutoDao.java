// ProdutoDao.java
import java.util.List;
import java.util.stream.Collectors;

public class ProdutoDao {

    private GenericDao<Produto> dao;

    public ProdutoDao() {
        // O arquivo "produtos.dat" será salvo na pasta do usuário
        this.dao = new GenericDao<>("produtos.dat");
    }

    public List<Produto> listarProdutos() {
        return dao.listar();
    }

    public void adicionarProduto(Produto produto) {
        List<Produto> produtos = listarProdutos();
        produtos.add(produto);
        dao.salvar(produtos);
    }

    public void removerProduto(Produto produto) {
        List<Produto> produtos = listarProdutos();
        produtos.remove(produto); // O método remove() usa o equals() que definimos
        dao.salvar(produtos);
    }

    public void atualizarProduto(Produto produtoAtualizado) {
        List<Produto> produtos = listarProdutos();
        // Encontra o produto pelo código e o substitui
        List<Produto> produtosAtualizados = produtos.stream()
                .map(p -> p.getCodigo() == produtoAtualizado.getCodigo() ? produtoAtualizado : p)
                .collect(Collectors.toList());
        dao.salvar(produtosAtualizados);
    }
}