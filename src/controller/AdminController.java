package controller;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import javax.print.attribute.standard.JobHoldUntil;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.Connect;
import model.Products;

public class AdminController extends JFrame implements ActionListener, MouseListener {
	
	Connect con;
	
	JTable productTable;
	DefaultTableModel dtm;
	
	Vector<Object> tHeader;
	Vector<Products> products;	
	
	HashMap<Object, Runnable> commands;
	Products currentSelected;
	
	JMenuBar menuBar;
	JMenu menuMore;
	JMenuItem logout;
	
	JLabel productIdLbl, productNameLbl, productAuthorLbl, productPriceLbl, productStockLbl;
	JTextField productIdTxt, productNameTxt, productAuthorTxt, productPriceTxt, productStockTxt;
	
	JButton insertBtn, updateBtn, deleteBtn;
	
	Products getData() {
		int productId = Integer.parseInt(productIdTxt.getText());
		String productName = productNameTxt.getText();
		String productAuthor = productAuthorTxt.getText();
		int productPrice = Integer.parseInt(productPriceTxt.getText());
		int productStock = Integer.parseInt(productStockTxt.getText());
		
		return new Products(productId, productName, productAuthor, productPrice, productStock);
	}
	
	void getAllProducts() {
		ResultSet rs = con.executeQuery("SELECT * FROM products");
		dtm = new DefaultTableModel(tHeader, 0);
		
		try {
			while(rs.next()) {
				int productId = (int) rs.getObject(1);
				String productName = (String) rs.getObject(2);
				String productAuthor = (String) rs.getObject(3);
				int productPrice = (int) rs.getObject(4);
				int productStock = (int) rs.getObject(5);
				
				Products pd = new Products(productId, productName, productAuthor, productPrice, productStock);
				products.add(pd);
				dtm.addRow(pd.toObjects());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		productTable.setModel(dtm);
	}
	
	void initializeComponent() {
		dtm = new DefaultTableModel(tHeader, 0);
		productTable = new JTable(dtm);
		JScrollPane sp = new JScrollPane(productTable);
		
		menuBar = new JMenuBar();
		menuMore = new JMenu("More");
		logout = new JMenuItem("Logout");
		
		logout.addActionListener(this);
		
		productIdLbl = new JLabel("Product Id");
		productNameLbl = new JLabel("Product Name");
		productAuthorLbl = new JLabel("Product Author");
		productPriceLbl = new JLabel("Product Price");
		productStockLbl = new JLabel("Product Stock");
		
		productIdTxt = new JTextField();
		productNameTxt = new JTextField();
		productAuthorTxt = new JTextField();
		productPriceTxt = new JTextField();
		productStockTxt = new JTextField();
		
		insertBtn = new JButton("Insert");
		updateBtn = new JButton("Update");
		deleteBtn = new JButton("Delete");
		
		insertBtn.addActionListener(this);
		updateBtn.addActionListener(this);
		deleteBtn.addActionListener(this);
	
		productTable.addMouseListener(this);
		
		JPanel bottomPanel = new JPanel(new GridLayout(0, 1));
		JPanel dataPanel = new JPanel(new GridLayout(0, 2));
		dataPanel.add(productIdLbl);
		dataPanel.add(productIdTxt);
		
		dataPanel.add(productNameLbl);
		dataPanel.add(productNameTxt);
		
		dataPanel.add(productAuthorLbl);
		dataPanel.add(productAuthorTxt);
		
		dataPanel.add(productPriceLbl);
		dataPanel.add(productPriceTxt);
		
		dataPanel.add(productStockLbl);
		dataPanel.add(productStockTxt);
	
		bottomPanel.add(dataPanel);
			JPanel buttonPanel = new JPanel(new FlowLayout());
			buttonPanel.add(insertBtn);
			buttonPanel.add(updateBtn);
			buttonPanel.add(deleteBtn);
		bottomPanel.add(buttonPanel);
		
		menuMore.add(logout);
		menuBar.add(menuMore);
		setJMenuBar(menuBar);
	
		add(sp, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
	}
	
	void initializeCommands() {
		commands = new HashMap<Object, Runnable>();
		
		commands.put(insertBtn, () -> insert());
		commands.put(updateBtn, () -> update());
		commands.put(deleteBtn, () -> delete());
		commands.put(logout, () -> logout());
	}
	
	void initialize() {
		con = Connect.getInstance();
		
		tHeader = new Vector<>();
		tHeader.add("Product ID");
		tHeader.add("Product Name");
		tHeader.add("Product Author");
		tHeader.add("Product Price");
		tHeader.add("Product Stock");
		
		products = new Vector<Products>();
		
		setSize(600, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		initializeComponent();
		getAllProducts();
		
		setVisible(true);
	}
	
	void insert() {
		
//		try {
//			for (Products p : products) {
//				if(!productIdTxt.getText().equals(p.productId)) {
//					
//				}
//			}
//		} catch (Exception e) {
//			JOptionPane.showMessageDialog(this, "ID must be unique!", "Warning!", JOptionPane.WARNING_MESSAGE);
//		}
		
		if(productIdTxt.getText().isEmpty() == false) {
			if(productNameTxt.getText().isEmpty() == false) {
				if(productAuthorTxt.getText().isEmpty() == false) {
					if(productPriceTxt.getText().isEmpty() == false) {
						try {
							Double.parseDouble(productPriceTxt.getText());
						} catch (NumberFormatException e) {
							JOptionPane.showMessageDialog(this, "Price must be number!", "Warning!", JOptionPane.WARNING_MESSAGE);
							return;
						}
						if(productStockTxt.getText().isEmpty() == false) {
							try {
								Integer.parseInt(productStockTxt.getText());
							} catch (NumberFormatException e) {
								JOptionPane.showMessageDialog(this, "Stock must be number!", "Warning!", JOptionPane.WARNING_MESSAGE);
								return;
							}
							if(!productPriceTxt.getText().equals("0")) {
								if(!productStockTxt.getText().equals("0")) {
									Products pd = getData();
									String query = String.format(""
											+ "INSERT INTO products VALUES "
											+ "(%d, '%s', '%s', %d, %d)", pd.productId, pd.productName, pd.productAuthor, pd.productPrice, pd.productStock);
									
									con.executeUpdate(query);
									getAllProducts();
								} else {
									JOptionPane.showMessageDialog(this, "Stock must be more than 0", "Warning!", JOptionPane.WARNING_MESSAGE);
								}
							} else {
								JOptionPane.showMessageDialog(this, "Price must be more than 0", "Warning!", JOptionPane.WARNING_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(this, "Please fill the Product Stock column!", "Warning!", JOptionPane.WARNING_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(this, "Please fill the Product Price column!", "Warning!", JOptionPane.WARNING_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(this, "Please fill the Product Author column!", "Warning!", JOptionPane.WARNING_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(this, "Please fill the Product Name column!", "Warning!", JOptionPane.WARNING_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, "Please fill the Product Id column!", "Warning!", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	void update() {
		if(productIdTxt.getText().isEmpty() == false) {
			if(productNameTxt.getText().isEmpty() == false) {
				if(productAuthorTxt.getText().isEmpty() == false) {
					if(productPriceTxt.getText().isEmpty() == false) {
						try {
							Double.parseDouble(productPriceTxt.getText());
						} catch (NumberFormatException e) {
							JOptionPane.showMessageDialog(this, "Price must be number!", "Warning!", JOptionPane.WARNING_MESSAGE);
							return;
						}
						if(productStockTxt.getText().isEmpty() == false) {
							try {
								Integer.parseInt(productStockTxt.getText());
							} catch (NumberFormatException e) {
								JOptionPane.showMessageDialog(this, "Stock must be number!", "Warning!", JOptionPane.WARNING_MESSAGE);
								return;
							}
							if(!productPriceTxt.getText().equals("0")) {
								if(!productStockTxt.getText().equals("0")) {
									Products pd = getData();
									String query = String.format(""
													+ "UPDATE products "
													+ "SET productName = '%s',"
													+ "productAuthor = '%s',"
													+ "productPrice = %d,"
													+ "productStock = %d "
													+ "WHERE ProductId = %d", pd.productName, pd.productAuthor, pd.productPrice, pd.productStock, pd.productId);
									
									con.executeUpdate(query);
									getAllProducts();
								} else {
									JOptionPane.showMessageDialog(this, "Stock must be more than 0", "Warning!", JOptionPane.WARNING_MESSAGE);
								}
							} else {
								JOptionPane.showMessageDialog(this, "Price must be more than 0", "Warning!", JOptionPane.WARNING_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(this, "Please fill the Product Stock column!", "Warning!", JOptionPane.WARNING_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(this, "Please fill the Product Price column!", "Warning!", JOptionPane.WARNING_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(this, "Please fill the Product Author column!", "Warning!", JOptionPane.WARNING_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(this, "Please fill the Product Name column!", "Warning!", JOptionPane.WARNING_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, "Please fill the Product Id column!", "Warning!", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	void delete() {
		if(productIdTxt.getText().isEmpty() == false) {
			if(productNameTxt.getText().isEmpty() == false) {
				if(productAuthorTxt.getText().isEmpty() == false) {
					if(productPriceTxt.getText().isEmpty() == false) {
						try {
							Double.parseDouble(productPriceTxt.getText());
						} catch (NumberFormatException e) {
							JOptionPane.showMessageDialog(this, "Price must be number!", "Warning!", JOptionPane.WARNING_MESSAGE);
							return;
						}
						if(productStockTxt.getText().isEmpty() == false) {
							try {
								Integer.parseInt(productStockTxt.getText());
							} catch (NumberFormatException e) {
								JOptionPane.showMessageDialog(this, "Stock must be number!", "Warning!", JOptionPane.WARNING_MESSAGE);
								return;
							}
							if(!productPriceTxt.getText().equals("0")) {
								if(!productStockTxt.getText().equals("0")) {
									Products pd = getData();
									 String query = String.format(""
												     + "DELETE FROM products "
												     + "WHERE productId = '%s'", pd.productId);
									
									con.executeUpdate(query);
									getAllProducts();
								} else {
									JOptionPane.showMessageDialog(this, "Stock must be more than 0", "Warning!", JOptionPane.WARNING_MESSAGE);
								}
							} else {
								JOptionPane.showMessageDialog(this, "Price must be more than 0", "Warning!", JOptionPane.WARNING_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(this, "Please fill the Product Stock column!", "Warning!", JOptionPane.WARNING_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(this, "Please fill the Product Price column!", "Warning!", JOptionPane.WARNING_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(this, "Please fill the Product Author column!", "Warning!", JOptionPane.WARNING_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(this, "Please fill the Product Name column!", "Warning!", JOptionPane.WARNING_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, "Please fill the Product Id column!", "Warning!", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	void logout() {
		this.dispose();
		new AuthController();
	}
	
	void fillData() {
		int idx = productTable.getSelectedRow();
		
		currentSelected = products.get(idx);
		productIdTxt.setText("" + currentSelected.productId);
		productNameTxt.setText(currentSelected.productName);
		productAuthorTxt.setText(currentSelected.productAuthor);
		productPriceTxt.setText("" + currentSelected.productPrice);
		productStockTxt.setText("" + currentSelected.productStock);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		commands.get(e.getSource()).run();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == productTable) fillData();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
	
	public AdminController() {
		initialize();
		initializeCommands();
	}

}
