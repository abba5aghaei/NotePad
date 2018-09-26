import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class FontFrame extends JFrame {
	
	private static final long serialVersionUID = -8360111695226656913L;
	private Font appFont = new Font("Tahoma", Font.PLAIN, 11);
	
	FontFrame() {
		
		getContentPane().setBackground(Color.WHITE);
		setTitle("Set Font and Color");
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(350, 150, 327, 304);
		getContentPane().setLayout(null);
		
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		
		Font[] allFont = e.getAllFonts();
		
		String[] allFontNames = new String[allFont.length];
		
		for(int i=0 ; i<allFontNames.length ; i++)
			allFontNames[i] = allFont[i].getFontName();
		
		String[] allStyle = { "Plain", "Bold", "Italic", "Bold Italic" };
		
		int[] styleType = { Font.PLAIN, Font.BOLD, Font.ITALIC, Font.BOLD+Font.ITALIC };
		
		String[] allSize = { "8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24",
				             "26", "28", "30", "36", "48", "72" };
		
		JLabel lblFont = new JLabel("Font:");
		lblFont.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFont.setBounds(15, 15, 46, 14);
		getContentPane().add(lblFont);
		
		JTextField fontSet = new JTextField(MegaFrame.fName);
		fontSet.setBounds(15, 30, 121, 25);
		fontSet.setEditable(false);
		getContentPane().add(fontSet);
		
		JLabel lblStyle = new JLabel("Font Style:");
		lblStyle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStyle.setBounds(146, 15, 68, 14);
		getContentPane().add(lblStyle);
		
		JTextField styleSet = new JTextField("Plain");
		styleSet.setBounds(146, 30, 86, 25);
		styleSet.setEditable(false);
		getContentPane().add(styleSet);
		
		JLabel lblSize = new JLabel("Size:");
		lblSize.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSize.setBounds(242, 15, 46, 14);
		getContentPane().add(lblSize);
		
		JTextField sizeSet = new JTextField(String.valueOf(MegaFrame.text.getFont().getSize()));
		sizeSet.setBounds(242, 30, 55, 25);
		getContentPane().add(sizeSet);
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		JList listFont = new JList(allFontNames);
		listFont.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listFont.setSelectedValue(fontSet.getText(), true);
		listFont.addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent le) {
				
				fontSet.setText((String) listFont.getSelectedValue());
			}});
		JScrollPane scrollPaneFont = new JScrollPane(listFont);
		scrollPaneFont.setBounds(15, 60, 121, 110);
		getContentPane().add(scrollPaneFont);
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JList listStyle = new JList(allStyle);
		listStyle.setSelectedIndex(0);
		listStyle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listStyle.addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent le) {
				
				styleSet.setText((String) listStyle.getSelectedValue());
			}});
		JScrollPane scrollPaneStyle = new JScrollPane(listStyle);
		scrollPaneStyle.setBounds(146, 60, 86, 110);
		getContentPane().add(scrollPaneStyle);
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JList listSize = new JList(allSize);
		listSize.setSelectedIndex(3);
		listSize.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listSize.addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent le) {
				
				sizeSet.setText((String) listSize.getSelectedValue());
			}});
		JScrollPane scrollPaneSize = new JScrollPane(listSize);
		scrollPaneSize.setBounds(242, 60, 55, 110);
		getContentPane().add(scrollPaneSize);
		
		JLabel lblBackGround = new JLabel("Select a color:");
		lblBackGround.setFont(appFont);
		lblBackGround.setBounds(16, 181, 74, 18);
		getContentPane().add(lblBackGround);
		
		JTextField Rc = new JTextField(String.valueOf(MegaFrame.text.getBackground().getRed()));
		Rc.setBounds(25, 234, 28, 20);
		getContentPane().add(Rc);
		Rc.setColumns(3);
		
		JTextField Bc = new JTextField(String.valueOf(MegaFrame.text.getBackground().getBlue()));
		Bc.setBounds(109, 234, 28, 20);
		getContentPane().add(Bc);
		Bc.setColumns(3);
		
		JTextField Gc = new JTextField(String.valueOf(MegaFrame.text.getBackground().getGreen()));
		Gc.setBounds(67, 234, 28, 20);
		getContentPane().add(Gc);
		Gc.setColumns(3);
		
		String[] comboColors = { "White", "Pink", "Yellow", "Orange", "Red",
				                 "Green", "Blue", "Cyan", "Gray", "Black" };
		
		Color[] colors = { Color.WHITE, Color.PINK, Color.YELLOW, Color.ORANGE,
		Color.RED, Color.GREEN, Color.BLUE, Color.CYAN, Color.GRAY, Color.BLACK };
		
		JComboBox<Object> comboBox = new JComboBox<Object>(comboColors);
		comboBox.setBounds(15, 197, 121, 20);
		comboBox.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				
				Rc.setText(String.valueOf(colors[comboBox.getSelectedIndex()].getRed()));
				
				Gc.setText(String.valueOf(colors[comboBox.getSelectedIndex()].getGreen()));
				
				Bc.setText(String.valueOf(colors[comboBox.getSelectedIndex()].getBlue()));
			}
		});
		getContentPane().add(comboBox);
		
		JLabel lblR = new JLabel("R:");
		lblR.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblR.setBounds(15, 237, 17, 14);
		getContentPane().add(lblR);
		
		JLabel lblG = new JLabel("G:");
		lblG.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblG.setBounds(56, 237, 17, 14);
		getContentPane().add(lblG);
		
		JLabel lblB = new JLabel("B:");
		lblB.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblB.setBounds(98, 237, 15, 14);
		getContentPane().add(lblB);
		
		JButton applyFont = new JButton("Change Font");
		applyFont.setFont(appFont);
		applyFont.setBounds(156, 181, 141, 18);
		applyFont.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				
				try {
					MegaFrame.text.setFont(new Font(allFontNames[listFont.getSelectedIndex()], styleType[listStyle.getSelectedIndex()], Integer.parseInt(sizeSet.getText())));
					
					MegaFrame.flagSave = false;
					
					JOptionPane.showMessageDialog(null, "Font Changed Succesfully", "Mega Note", JOptionPane.INFORMATION_MESSAGE);
				}
				catch (Exception e)
				{
					JOptionPane.showMessageDialog(null, "Invalid Font selected", "Eror", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		getContentPane().add(applyFont);
		
		JButton applyBG = new JButton("Apply to Background");
		applyBG.setFont(appFont);
		applyBG.setBounds(156, 209, 141, 18);
		applyBG.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				
				try {
					
					MegaFrame.text.setBackground(new Color(Integer.parseInt(Rc.getText()), Integer.parseInt(Gc.getText()), Integer.parseInt(Bc.getText())));
				
				    JOptionPane.showMessageDialog(null, "BackGround Color Changed Succesfully", "Mega Note", JOptionPane.INFORMATION_MESSAGE);
				    }
				    catch (Exception e)
				    {
					JOptionPane.showMessageDialog(null, "Invalid Color, please enter from 0 to 256", "Eror", JOptionPane.ERROR_MESSAGE);
				    }
				}	
		});
		getContentPane().add(applyBG);
		
		JButton applyTC = new JButton("Apply to Text");
		applyTC.setFont(appFont);
		applyTC.setBounds(156, 235, 141, 18);
		applyTC.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				
				try {
					
					MegaFrame.text.setForeground(new Color(Integer.parseInt(Rc.getText()), Integer.parseInt(Gc.getText()), Integer.parseInt(Bc.getText())));
				
				    JOptionPane.showMessageDialog(null, "Text Color Changed Succesfully", "Mega Note", JOptionPane.INFORMATION_MESSAGE);
				    }
				    catch (Exception e)
				    {
					JOptionPane.showMessageDialog(null, "Invalid Color, please enter from 0 to 256", "Eror", JOptionPane.ERROR_MESSAGE);
				    }
				}	
		});
		getContentPane().add(applyTC);
	}
}
