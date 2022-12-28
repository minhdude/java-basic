package set_cover_problem;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import javax.swing.JScrollPane;



public class SCPGUI extends JFrame {

	final static int MAX_COST = 100;
	final static int MIN_COST = 1;

	SortedSet<Integer> _elementsNotCovered;
	protected TreeSet<ElementSet> _solnSets; // Mảng kết quả
	protected double _objFn;
	protected double _coverage;       // phần trăm bảo hiểm thực tế đạt được
	protected long _compTime;         // thời gian tính toán (ms)
	protected boolean _solved;        // mô hình đã được giải quyết hay chưa
	private JPanel contentPane;
	private JTextField textElements;
	private JTextField textSet;
	private JTextField textAlpha;
	private JTextField textMax_M;
	private JTextField textMin_M;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JTextArea textStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SCPGUI frame = new SCPGUI();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SCPGUI() {

		SCPModel scpmodel = new SCPModel();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 759, 661);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblNewLabel = new JLabel("Enter number of elements (n):");
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(55, 66, 225, 28);
		contentPane.add(lblNewLabel);

		lblNewLabel_1 = new JLabel("Enter number of sets (m):");
		lblNewLabel_1.setForeground(new Color(0, 0, 0));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(55, 106, 225, 28);
		contentPane.add(lblNewLabel_1);

		lblNewLabel_2 = new JLabel("Enter minium coverage: ");
		lblNewLabel_2.setForeground(new Color(0, 0, 0));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_2.setBounds(55, 146, 255, 28);
		contentPane.add(lblNewLabel_2);

		lblNewLabel_3 = new JLabel("Enter max m: ");
		lblNewLabel_3.setForeground(new Color(0, 0, 0));
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_3.setBounds(55, 186, 255, 28);
		contentPane.add(lblNewLabel_3);

		lblNewLabel_4 = new JLabel("Enter min m: ");
		lblNewLabel_4.setForeground(new Color(0, 0, 0));
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_4.setBounds(55, 226, 255, 28);
		contentPane.add(lblNewLabel_4);

		textElements = new JTextField();
		textElements.setForeground(new Color(255, 99, 71));
		textElements.setToolTipText("");
		textElements.setColumns(10);
		textElements.setBounds(290, 73, 93, 20);
		contentPane.add(textElements);

		textSet = new JTextField();
		textSet.setForeground(new Color(255, 99, 71));
		textSet.setColumns(10);
		textSet.setBounds(290, 113, 93, 20);
		contentPane.add(textSet);

		textAlpha = new JTextField();
		textAlpha.setForeground(new Color(255, 99, 71));
		textAlpha.setColumns(10);
		textAlpha.setBounds(290, 154, 93, 20);
		contentPane.add(textAlpha);

		textMax_M = new JTextField();
		textMax_M.setForeground(new Color(255, 99, 71));
		textMax_M.setColumns(10);
		textMax_M.setBounds(290, 194, 93, 20);
		contentPane.add(textMax_M);

		textMin_M = new JTextField();
		textMin_M.setForeground(new Color(255, 99, 71));
		textMin_M.setColumns(10);
		textMin_M.setBounds(290, 234, 93, 20);
		contentPane.add(textMin_M);

		JLabel lblNewLabel = new JLabel("JAVA SET COVER PROBLEM SOLVER");
		lblNewLabel.setForeground(new Color(255, 99, 71));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblNewLabel.setBounds(172, 10, 502, 34);
		contentPane.add(lblNewLabel);

		JButton btnReset = new JButton("Reset");
		btnReset.setForeground(new Color(255, 255, 255));
		btnReset.setBackground(new Color(255, 99, 71));
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetField();
				textStatus.setText("");
				SCPModel scpmodel1 = new SCPModel();
				scpmodel.set(scpmodel1);
			}
		});
		btnReset.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnReset.setBounds(553, 191, 104, 23);
		contentPane.add(btnReset);

		JButton btnEdit = new JButton("Print");
		btnEdit.setForeground(new Color(255, 255, 255));
		btnEdit.setBackground(new Color(255, 99, 71));
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!checkAll()) {
					return;
				}

				int n = Integer.valueOf(textElements.getText());
				int m = Integer.valueOf(textSet.getText());
				int MAX_M = Integer.valueOf(textMax_M.getText());
				int MIN_M = Integer.valueOf(textMin_M.getText());
				for (int i = 1; i <= m; i++) {
					double cost = (Math.random() * (MAX_COST - MIN_COST)) + MIN_COST;
					int j;
					List<Integer> elements = new ArrayList<>();
					int n_el = (int) (Math.random() * (MAX_M - MIN_M + 1)) + MIN_M;
					int dem = 0;
					while (dem != n_el) {
						j = (int) (Math.random() * n) + 1;
						dem++;
						elements.add(j);
					}
					scpmodel.addElementSet(i, cost, elements);
				}
				textStatus.setText("" + scpmodel + "\n");
				textStatus.append("Minimum coverage (alpha):" + "\t");
				double alpha = Double.valueOf(textAlpha.getText());
				if (alpha == -1)
					textStatus.append("unspecified\n");
				else{
					textStatus.append("" + 100 * alpha);
				}
			}
		});
		btnEdit.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnEdit.setBounds(553, 111, 104, 23);
		contentPane.add(btnEdit);

		JButton btnDelete1 = new JButton("Greedy");
		btnDelete1.setForeground(new Color(255, 255, 255));
		btnDelete1.setBackground(new Color(255, 99, 71));
		btnDelete1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!checkAll())
					return;
				reset();
				if(!scpmodel.check()) {
					JOptionPane.showMessageDialog(contentPane, "Print First", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				int num_to_cover = (int)Math.ceil(Integer.valueOf(textAlpha.getText()) * scpmodel.getNumElements());
				int num_can_leave_uncovered = scpmodel.getNumElements() - num_to_cover;
				boolean all_selected = false;

				_elementsNotCovered = new TreeSet<>();
				_elementsNotCovered.addAll(scpmodel.getAllEs());

				long start = System.currentTimeMillis();
				textStatus.setText("Running '" + "Greedy's algorithm" + "'..." + "\n\n");

				while (_elementsNotCovered.size() > 0 && _elementsNotCovered.size() > num_can_leave_uncovered && !all_selected) {
					ElementSet CurrBestSet = nextBestSet(scpmodel);
					if(CurrBestSet == null)
						break;
					_elementsNotCovered.removeAll(CurrBestSet.getTheseEs());
					_solnSets.add(CurrBestSet);
					_objFn += CurrBestSet.getCost();
					textStatus.append("- Selected: " + CurrBestSet + "\n");
					if(_solnSets.size() == scpmodel.getNumElementSets())
						all_selected = true;
				}

				_coverage = (scpmodel.getNumElements() - _elementsNotCovered.size())/(double)scpmodel.getNumElements();
				_solved = true;
				_compTime = System.currentTimeMillis() - start;
				if (_coverage < Integer.valueOf(textAlpha.getText()))
					textStatus.append("\nWARNING: Impossible to reach " + 100*Integer.valueOf(textAlpha.getText()) +  " coverage level.\n" + "\n");

				textStatus.append("Done." + "\n");
				textStatus.append("\n'" + "Greedy's algorithm" + "' results:" + "\n");
				textStatus.append("'" + "Greedy's algorithm" + "'   Time to solve: " + _compTime + "\n");
				textStatus.append("'" + "Greedy's algorithm" + "'   Objective function value: "+ _objFn + "\n");
				textStatus.append("'" + "Greedy's algorithm" + "'   Coverage level: " + 100*_coverage + 100*Integer.valueOf(textAlpha.getText()) + "\n");
				textStatus.append("'" + "Greedy's algorithm" + "'   Number of sets selected: " + _solnSets.size() + "\n");
				textStatus.append("'" + "Greedy's algorithm" + "'   Sets selected: ");
				for (ElementSet s : _solnSets)
					textStatus.append(s.getId() + " ");
				textStatus.append("\n");
			}
		});
		btnDelete1.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnDelete1.setBounds(553, 151, 104, 23);
		contentPane.add(btnDelete1);

		JButton btnDelete2 = new JButton("Quit");
		btnDelete2.setForeground(new Color(255, 255, 255));
		btnDelete2.setBackground(new Color(255, 99, 71));
		btnDelete2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnDelete2.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnDelete2.setBounds(332, 597, 80, 23);
		contentPane.add(btnDelete2);

		JButton btnDelete3 = new JButton("Information Team");
		btnDelete3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InformationTeam.frame.setVisible(true);
				InformationTeam.frame.setLocationRelativeTo(null);
			}
		});
		btnDelete3.setBackground(new Color(255, 255, 240));
		btnDelete3.setForeground(new Color(0, 139, 139));
		btnDelete3.setFont(new Font("Roboto", Font.BOLD, 12));
		btnDelete3.setBounds(598, 595, 147, 29);
		contentPane.add(btnDelete3);

		textStatus = new JTextArea();
		textStatus.setLocation(10, 10);
		textStatus.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(textStatus);
		textStatus.setColumns(10);
		JScrollPane scrollPane = new JScrollPane(textStatus, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 279, 730, 311);
		contentPane.add(scrollPane);

	}

	public void solve() {

	}
	public ElementSet nextBestSet(SCPModel scpmodel) {
		double minratio = Double.MAX_VALUE;
		double ratio;
		ElementSet Best = null;
		for (ElementSet e : scpmodel.getElementSetIterable()) {
			ratio = e.getCost() / e.countElementsCovered(_elementsNotCovered);
			if (ratio < minratio) {
				minratio = ratio;
				Best = e;
			}
		}
		return Best;
	}

	public void reset() {
		_solnSets = new TreeSet<>();
		_elementsNotCovered = null;
		_objFn = 0d;
		_coverage = 0d;
		_compTime = 0;
		_solved = false;
	}

	public void resetField() {
		textElements.setText("");
		textSet.setText("");
		textAlpha.setText("");
		textMax_M.setText("");
		textMin_M.setText("");
	}

	public boolean checkAll() {
		if(!validataEmtyField()) {
			return false;
		}
		StringBuilder sb = new StringBuilder();
		try {
			Integer.parseInt(textElements.getText());
		} catch (NumberFormatException e) {
			sb.append("The elements must be real numbers \n");
		}
		try {
			Integer.parseInt(textSet.getText());
		} catch (NumberFormatException e) {
			sb.append("The set must be real numbers \n");
		}
		try {
			double price = Double.parseDouble(textAlpha.getText());
			if (price > 1 || price <= 0 ) {
				sb.append("The minimum coverage must be between 0 and 1!!! \n");
			}
		} catch(Exception e8) {
			sb.append("The minimum coverage must be real numbers \n");
		}
		try {
			Integer.parseInt(textMax_M.getText());
		} catch (NumberFormatException e) {
			sb.append("The max m must be real numbers \n");
		}
		try {
			Integer.parseInt(textMin_M.getText());
		} catch (NumberFormatException e) {
			sb.append("The min m must be real numbers \n");
		}
		if (sb.length() > 0) {
			JOptionPane.showMessageDialog(contentPane, sb.toString(), "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	public boolean validataEmtyField() {
		StringBuilder sb = new StringBuilder();
		if(textElements.getText().equals("")) {
			sb.append("The number of elements cannot be blank \n");
		}
		if(textSet.getText().equals("")) {
			sb.append("The number of sets cannot be blank \n");
		}
		if(textAlpha.getText().equals("")) {
			sb.append("The minimum coverage cannot be blank \n");
		}
		if(textMax_M.getText().equals("")) {
			sb.append("The max m cannot be blank \n");
		}
		if(textMin_M.getText().equals("")) {
			sb.append("The min m cannot be blank \n");
		}
		if(sb.length() > 0 ) {
			JOptionPane.showMessageDialog(contentPane, sb.toString(), "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
}
