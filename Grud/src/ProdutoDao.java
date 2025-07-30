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

    public boolean adicionarProduto(Produto produto) {
        // Verifica se o produto já existe pelo código
        if (produtoExiste(produto.getCodigo())) {
            return false; //Retorna 'false' se encontrou um duplicado
        }

        // Se não existe, adiciona normalmente
        List<Produto> produtos = listarProdutos();
        produtos.add(produto);
        dao.salvar(produtos);

        return true; // 3. Retorna 'true' para indicar sucesso
    }

    public void removerProduto(Produto produto) {
        List<Produto> produtos = listarProdutos();
        produtos.remove(produto); // O metodo remove() usa o equals() que definimos
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
    // novo metodo
    public boolean produtoExiste(int codigo){
        List<Produto> produtos = listarProdutos();
        //Usando stream para verificar se tem itens duplicados
        return produtos.stream().anyMatch(p -> p.getCodigo() == codigo );
    }
}