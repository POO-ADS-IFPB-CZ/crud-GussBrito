// TelaPrincipal.java
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TelaPrincipal extends JFrame {

    private final ProdutoDao produtoDao;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JTextField txtCodigo;
    private final JTextField txtDescricao;
    private final JTextField txtPreco;
    private final JButton btnAdicionar;
    private final JButton btnAtualizar;
    private final JButton btnRemover;

    public TelaPrincipal() {
        this.produtoDao = new ProdutoDao();

        setTitle("Cadastro de Produtos");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a tela

        // --- PAINEL DE ENTRADA ---
        JPanel inputPanel = new JPanel(new GridLayout(1, 6, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        txtCodigo = new JTextField();
        txtDescricao = new JTextField();
        txtPreco = new JTextField();

        inputPanel.add(new JLabel("Código:"));
        inputPanel.add(txtCodigo);
        inputPanel.add(new JLabel("Descrição:"));
        inputPanel.add(txtDescricao);
        inputPanel.add(new JLabel("Preço:"));
        inputPanel.add(txtPreco);

        // --- TABELA ---
        String[] colunas = {"Código", "Descrição", "Preço"};
        tableModel = new DefaultTableModel(colunas, 0) {
            // Torna as células da tabela não editáveis
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // --- PAINEL DE BOTÕES ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnAdicionar = new JButton("Adicionar");
        btnAtualizar = new JButton("Atualizar");
        btnRemover = new JButton("Remover");
        buttonPanel.add(btnAdicionar);
        buttonPanel.add(btnAtualizar);
        buttonPanel.add(btnRemover);

        // Adicionando componentes ao Frame
        setLayout(new BorderLayout(0, 10));
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // --- LÓGICA DOS EVENTOS ---
        configurarEventos();

        // Carrega os dados iniciais do arquivo na tabela
        atualizarTabela();
    }

    private void configurarEventos() {
        btnAdicionar.addActionListener(e -> adicionarProduto());
        btnRemover.addActionListener(e -> removerProduto());
        btnAtualizar.addActionListener(e -> atualizarProduto());

        // Evento para quando uma linha da tabela é clicada com o mouse
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    txtCodigo.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    txtDescricao.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    txtPreco.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    txtCodigo.setEditable(false); // Não permite editar o código ao selecionar
                }
            }
        });
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa a tabela
        List<Produto> produtos = produtoDao.listarProdutos();
        for (Produto p : produtos) {
            tableModel.addRow(new Object[]{p.getCodigo(), p.getDescricao(), p.getPreco()});
        }
    }

    private void limparCampos() {
        txtCodigo.setText("");
        txtDescricao.setText("");
        txtPreco.setText("");
        txtCodigo.setEditable(true); // Permite editar o código novamente
        table.clearSelection();
    }

    private void adicionarProduto() {
        try {
            int codigo = Integer.parseInt(txtCodigo.getText());
            String descricao = txtDescricao.getText();
            double preco = Double.parseDouble(txtPreco.getText());

            if (descricao.isEmpty()) {
                JOptionPane.showMessageDialog(this, "A descrição não pode ser vazia.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Produto p = new Produto(codigo, descricao, preco);
            produtoDao.adicionarProduto(p);
            atualizarTabela();
            limparCampos();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Código e Preço devem ser números válidos.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerProduto() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int codigo = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja remover o produto?", "Confirmar Remoção", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            Produto p = new Produto(codigo, "", 0); // O equals só usa o código, então o resto não importa
            produtoDao.removerProduto(p);
            atualizarTabela();
            limparCampos();
        }
    }

    private void atualizarProduto() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para atualizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int codigo = Integer.parseInt(txtCodigo.getText());
            String descricao = txtDescricao.getText();
            double preco = Double.parseDouble(txtPreco.getText());

            if (descricao.isEmpty()) {
                JOptionPane.showMessageDialog(this, "A descrição não pode ser vazia.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Produto p = new Produto(codigo, descricao, preco);
            produtoDao.atualizarProduto(p);
            atualizarTabela();
            limparCampos();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Preço deve ser um número válido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }
}