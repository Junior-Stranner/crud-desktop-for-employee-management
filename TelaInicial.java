import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import Model.Funcionario;
import Respository.FuncionarioRepository;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class TelaInicial extends JFrame implements ActionListener {

	// VSCode = JFrame frame = new JFrame(title: "Cadastro de Funcionário");
	JFrame frame = new JFrame("Cadastro de Funcionários");

	JPanel painel = new JPanel();
	JLabel lTitulo = new JLabel();
	JLabel lNome = new JLabel();
	JLabel lCpf = new JLabel();
	JTextArea tNome = new JTextArea();
	JTextArea tCpf = new JTextArea();

	JButton bSalvar = new JButton();
	JButton bConsultar = new JButton();
	JButton bExcluir = new JButton();

	Funcionario funcionario = new Funcionario();
	FuncionarioRepository repository = new FuncionarioRepository();

	JTable tabela = new JTable();
	DefaultTableModel model = new DefaultTableModel();

	public void telaCadastro() {
		// VSCode = frame.setSize(width:450,height:230);
		frame.setSize(450, 350);

		// VSCode = painel.setSize(width:450,height:230);
		painel.setSize(450, 350);

		// VSCode = painel.setLayout(mgr: null);
		painel.setLayout(null);

		// VSCode = lTitulo.setText(text: "Cadastro de Funcionário");
		lTitulo.setText("Cadastro de Funcionários");

		// VSCode = lTitulo.setBounds(x: 150, y: 20, width: 150, height: 20);
		lTitulo.setBounds(150, 20, 150, 20);

		// VSCode = lNome.setText(text: Nome: ");
		lNome.setText("Nome: ");

		// VSCode = lNome.setBounds(x: 10, y: 50, width: 50, height: 20);
		lNome.setBounds(10, 50, 50, 20);

		tNome.setBounds(60, 50, 150, 20);

		// VSCode = lCpf.setCpf(text: CPF: ");
		lCpf.setText("CPF: ");

		// VSCode = lCpf.setBounds(x: 250, y: 50, width: 50, height: 20);
		lCpf.setBounds(250, 50, 50, 20);

		tCpf.setBounds(280, 50, 120, 20);

		tabela.setBounds(68, 150, 310, 150);

		bSalvar.setText("Salvar");
		bSalvar.setBounds(60, 100, 100, 30);
		bSalvar.addActionListener(this);

		bConsultar.setText("Consultar");
		bConsultar.setBounds(170, 100, 100, 30);
		bConsultar.addActionListener(new consultaAction());

		bExcluir.setText("Excluir");
		bExcluir.setBounds(280, 100, 100, 30);
		bExcluir.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = tabela.getSelectedRow();
				try{
				repository.excluir((int)tabela.getValueAt(tabela.getSelectedRow(), 0));
									
				}catch(Exception e1) {
					e1.printStackTrace();
				}
				model.removeRow(linhaSelecionada);
								
			}
		});
			
		// VSCode = frame.setVisible(b: true);
		frame.setVisible(true);

		frame.add(painel);
		painel.add(lTitulo);
		painel.add(lNome);
		painel.add(lCpf);
		painel.add(lNome);
		painel.add(lCpf);
		
		painel.add(tNome);
		painel.add(tCpf);
		
		painel.add(bSalvar);
		painel.add(bConsultar);
		painel.add(bExcluir);
		painel.add(tabela);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		funcionario.setNome(tNome.getText());
		funcionario.setCpf(tCpf.getText());
		System.out.println(funcionario.getNome());
		System.out.println(funcionario.getCpf());

		repository.insere(funcionario);
		tNome.setText(null);
		tCpf.setText(null);
		new consultaAction().actionPerformed(e);
		
	
		try {
			repository.consulta();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

	public class consultaAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			while(tabela.getModel().getRowCount()>0) {
				((DefaultTableModel) tabela.getModel()).setRowCount(0);
				((DefaultTableModel) tabela.getModel()).setColumnCount(0);
			}
			ArrayList<Funcionario> funcionarios = new ArrayList<>();
			try {
				funcionarios = repository.consulta();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
			model.addColumn("Id");
			model.addColumn("Nome");
			model.addColumn("CPF");
			model.addRow(new Object[] {"Id","nome","cpf"});

			for (int i = 0; i < funcionarios.size(); i++) {
				funcionario = funcionarios.get(i);
				model.addRow(new Object[] {funcionario.getId(),funcionario.getNome(),funcionario.getCpf()});
			}
			tabela.setModel(model);

			
		}
	}
}
