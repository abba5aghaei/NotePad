import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Scanner;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MegaFrame extends JFrame {
	
	private static final long serialVersionUID = 4615463056818971848L;
	TextLineNumber liner;
	public static JTextArea text;
	TextUndoManager textUndo;
	public static String fName;
	private FontFrame frameFont;
	private FindFrame frameFind;
	private JFileChooser inoutFile;
	public static Scanner input;
	public static BufferedWriter output;
	private ReplaceFrame frameReplace;
	private String docName = "Untitled", primary = "";
	public static boolean flagSave = true, flagFirst = true, flagFinish = true;
	public static int index = 0, bgR, bgG, bgB, fgR, fgG, fgB, fStyle, fSize, linebgR, linebgG, linebgB, linefgR, linefgG, linefgB, linecrR, linecrG, linecrB;
	private JMenuItem New, open, save, saveAs, exit, print, undo, redo, redo2, cut, copy, undo2, cut2, copy2, paste2, paste, delet, delet2, replace, selectAll, selectAll2, find, date, font, mntmLowerCase, mntmUpperCase, about;
	private JCheckBoxMenuItem wordWrap, Showliner, clWhite, clYellow, clOrange, clPink, clRed, clGreen, clCyan, clBlue, clViolet, clLight, clGray, clDark, clBlack;
	
	MegaFrame(String[] setting) {
		
		try {
			bgR = Integer.parseInt(setting[0]);
			bgG = Integer.parseInt(setting[1]);
			bgB = Integer.parseInt(setting[2]);
			
			fgR = Integer.parseInt(setting[3]);
			fgG = Integer.parseInt(setting[4]);
			fgB = Integer.parseInt(setting[5]);
			
			fName = setting[6];
			fStyle = Integer.parseInt(setting[7]);
			fSize = Integer.parseInt(setting[8]);
			
			linebgR = Integer.parseInt(setting[9]);
			linebgG = Integer.parseInt(setting[10]);
			linebgB = Integer.parseInt(setting[11]);
			
			linefgR = Integer.parseInt(setting[12]);
			linefgG = Integer.parseInt(setting[13]);
			linefgB = Integer.parseInt(setting[14]);
			
			linecrR = Integer.parseInt(setting[15]);
			linecrG = Integer.parseInt(setting[16]);
			linecrB = Integer.parseInt(setting[17]);
			}
			catch (Exception e) {}
		
		setTitle("Mega Note - "+docName);
		setBounds(250, 80, 900, 600);
		getContentPane().setBackground(new Color(30, 30, 100));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage("Data/megaIcon.png"));
		addWindowListener(new WindowListener() {

			public void windowActivated(WindowEvent we) {}
			public void windowClosed(WindowEvent we) {}
			public void windowClosing(WindowEvent we) {
				
				if(flagSave)
					Exit();
				else
				{
					int r = JOptionPane.showConfirmDialog(null, new JLabel("Do you want to save changes to "+docName+"?"), 
							"Mega Note", JOptionPane.YES_NO_OPTION);
					
					switch(r)
					{
					case 0 : saveFile();
					
					case 1 : Exit();
					}
				}
			}
			public void windowDeactivated(WindowEvent we) {}
			public void windowDeiconified(WindowEvent we) {}
			public void windowIconified(WindowEvent we) {}
			public void windowOpened(WindowEvent we) {}
		});
		
		text = new JTextArea();
		text.setLineWrap(true);
		text.setBackground(new Color(bgR, bgG, bgB));
		text.setForeground(new Color(fgR, fgG, fgB));
		text.setFont(new Font(fName, fStyle, fSize));
		
		textUndo = new TextUndoManager();
		
		text.setDocument(new TextUndoPlainDocument(textUndo) );
		
		text.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent ke) {}
			public void keyReleased(KeyEvent ke) {}
			public void keyTyped(KeyEvent ke) {
				
				if(text.getText().equals(primary))
					flagSave = true;
				else
					flagSave = false;
			}
		});
		
		liner = new TextLineNumber(text);
		liner.setBackground(new Color(linebgR, linebgG, linebgB));
		liner.setCurrentLineForeground(new Color(linecrR, linecrG, linecrB));
		liner.setForeground(new Color(linefgR, linefgG, linefgB));
		liner.setPreferredSize(new Dimension(30, Integer.MAX_VALUE-10000));
		liner.setFont(new Font("Tohama", Font.PLAIN, 12));
		liner.setDigitAlignment( TextLineNumber.CENTER );
		
		JScrollPane scrollPane = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setRowHeaderView(liner);
		
		getContentPane().add(scrollPane);
		
		frameFind = new FindFrame();
		
		frameReplace = new ReplaceFrame();
		
		frameFont = new FontFrame();
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files only", "txt","html", "xml");
		
		inoutFile = new JFileChooser();
		inoutFile.setFileFilter(filter);
		inoutFile.setDialogTitle("Mega Note");
		inoutFile.setMultiSelectionEnabled(false);
		
		MenuHandller mh = new MenuHandller();
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		New = new JMenuItem("New");
		New.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		New.addActionListener(mh);
		mnFile.add(New);
		
		open = new JMenuItem("Open...");
		//open.setIcon(new ImageIcon(MegaFrame.class.getResource("/javax/swing/plaf/metal/icons/ocean/directory.gif")));
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		open.addActionListener(mh);
		mnFile.add(open);
		
		save = new JMenuItem("Save");
		//save.setIcon(new ImageIcon(MegaFrame.class.getResource("/javax/swing/plaf/metal/icons/ocean/floppy.gif")));
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		save.addActionListener(mh);
		mnFile.add(save);
		
		saveAs = new JMenuItem("Save As...");
		saveAs.addActionListener(mh);
		mnFile.add(saveAs);
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		
		print = new JMenuItem("Print...");
		//print.setIcon(new ImageIcon(MegaFrame.class.getResource("/javax/swing/plaf/metal/icons/ocean/minimize.gif")));
		print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
		print.addActionListener(mh);
		mnFile.add(print);
		
		exit = new JMenuItem("Exit");
		exit.addActionListener(mh);
		mnFile.add(exit);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		undo = new JMenuItem("Undo");
		//undo.setIcon(new ImageIcon(MegaFrame.class.getResource("/com/sun/javafx/scene/web/skin/Undo_16x16_JFX.png")));
		undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
		undo.addActionListener(mh);
		mnEdit.add(undo);
		
		redo = new JMenuItem("Redo");
		//redo.setIcon(new ImageIcon(MegaFrame.class.getResource("/com/sun/javafx/scene/web/skin/Redo_16x16_JFX.png")));
		redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK));
		redo.addActionListener(mh);
		mnEdit.add(redo);
		
		JSeparator separator_1 = new JSeparator();
		mnEdit.add(separator_1);
		
		cut = new JMenuItem("Cut");
		//cut.setIcon(new ImageIcon(MegaFrame.class.getResource("/com/sun/javafx/scene/web/skin/Cut_16x16_JFX.png")));
		cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		cut.addActionListener(mh);
		mnEdit.add(cut);
		
		copy = new JMenuItem("Copy");
		//copy.setIcon(new ImageIcon(MegaFrame.class.getResource("/com/sun/javafx/scene/web/skin/Copy_16x16_JFX.png")));
		copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		copy.addActionListener(mh);
		mnEdit.add(copy);
		
		paste = new JMenuItem("Paste");
		//paste.setIcon(new ImageIcon(MegaFrame.class.getResource("/com/sun/javafx/scene/web/skin/Paste_16x16_JFX.png")));
		paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
		paste.addActionListener(mh);
		mnEdit.add(paste);
		
		delet = new JMenuItem("Delete");
		//delet.setIcon(new ImageIcon(MegaFrame.class.getResource("/org/eclipse/jface/dialogs/images/message_error.png")));
		delet.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
		delet.addActionListener(mh);
		mnEdit.add(delet);
		
		JSeparator separator_2 = new JSeparator();
		mnEdit.add(separator_2);
		
		find = new JMenuItem("Find...");
		find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
		find.addActionListener(mh);
		mnEdit.add(find);
		
		replace = new JMenuItem("Replace");
		replace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
		replace.addActionListener(mh);
		mnEdit.add(replace);
		
		JSeparator separator_3 = new JSeparator();
		mnEdit.add(separator_3);
		
		selectAll = new JMenuItem("Select All");
		//selectAll.setIcon(new ImageIcon(MegaFrame.class.getResource("/com/sun/javafx/scene/web/skin/AlignJustified_16x16_JFX.png")));
		selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		selectAll.addActionListener(mh);
		mnEdit.add(selectAll);
		
		date = new JMenuItem("Time/Date");
		date.setIcon(null);
		date.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
		date.addActionListener(mh);
		mnEdit.add(date);
		
		JMenu mnFormat = new JMenu("Format");
		menuBar.add(mnFormat);
		
		wordWrap = new JCheckBoxMenuItem("Word Wrap");
		wordWrap.setSelected(true);
		wordWrap.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent ce) {
				
				if(text.getLineWrap())
					text.setLineWrap(false);
				
				else if(!text.getLineWrap())
					text.setLineWrap(true);
			}
		});
		mnFormat.add(wordWrap);
		
		Showliner = new JCheckBoxMenuItem("Show line numbers");
		Showliner.setSelected(true);
		Showliner.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent ce) {
				
				if(liner.isVisible())
					liner.setVisible(false);
				
				else if(!liner.isVisible())
					liner.setVisible(true);
			}
		});
		mnFormat.add(Showliner);
		
		font = new JMenuItem("Font...");
		font.addActionListener(mh);
		mnFormat.add(font);
		
		JSeparator separator_5 = new JSeparator();
		mnFormat.add(separator_5);
		
		JMenu mnConvertTextTo = new JMenu("Convert to");
		mnFormat.add(mnConvertTextTo);
		
		mntmLowerCase = new JMenuItem("Lower case");
		mntmLowerCase.addActionListener(mh);
		mnConvertTextTo.add(mntmLowerCase);
		
		mntmUpperCase = new JMenuItem("Upper case");
		mntmUpperCase.addActionListener(mh);
		mnConvertTextTo.add(mntmUpperCase);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		about = new JMenuItem("About Mega Note");
		//about.setIcon(new ImageIcon(MegaFrame.class.getResource("/icons/full/message_info.png")));
		about.addActionListener(mh);
		mnHelp.add(about);
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(text, popupMenu);
		
		undo2 = new JMenuItem("Undo");
		//undo2.setIcon(new ImageIcon(MegaFrame.class.getResource("/com/sun/javafx/scene/web/skin/Undo_16x16_JFX.png")));
		undo2.addActionListener(mh);
		popupMenu.add(undo2);
		
		redo2 = new JMenuItem("Redo");
		//redo2.setIcon(new ImageIcon(MegaFrame.class.getResource("/com/sun/javafx/scene/web/skin/Redo_16x16_JFX.png")));
		redo2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK));
		redo2.addActionListener(mh);
		popupMenu.add(redo2);
		
		JSeparator separator_4 = new JSeparator();
		popupMenu.add(separator_4);
		
		cut2 = new JMenuItem("Cut");
		//cut2.setIcon(new ImageIcon(MegaFrame.class.getResource("/com/sun/javafx/scene/web/skin/Cut_16x16_JFX.png")));
		cut2.addActionListener(mh);
		popupMenu.add(cut2);
		
		copy2 = new JMenuItem("Copy");
		//copy2.setIcon(new ImageIcon(MegaFrame.class.getResource("/com/sun/javafx/scene/web/skin/Copy_16x16_JFX.png")));
		copy2.addActionListener(mh);
		popupMenu.add(copy2);
		
		paste2 = new JMenuItem("Paste");
		//paste2.setIcon(new ImageIcon(MegaFrame.class.getResource("/com/sun/javafx/scene/web/skin/Paste_16x16_JFX.png")));
		paste2.addActionListener(mh);
		popupMenu.add(paste2);
		
		delet2 = new JMenuItem("Delete");
		//delet2.setIcon(new ImageIcon(MegaFrame.class.getResource("/org/eclipse/jface/dialogs/images/message_error.png")));
		delet2.addActionListener(mh);
		popupMenu.add(delet2);
		
		selectAll2 = new JMenuItem("Select All");
		//selectAll2.setIcon(new ImageIcon(MegaFrame.class.getResource("/com/sun/javafx/scene/web/skin/AlignJustified_16x16_JFX.png")));
		selectAll2.addActionListener(mh);
		
		JSeparator separator_6 = new JSeparator();
		popupMenu.add(separator_6);
		popupMenu.add(selectAll2);
		
		JPopupMenu popupMenu2 = new JPopupMenu();
		addPopup(liner, popupMenu2);
		
		JMenu mnColor = new JMenu("Color");
		popupMenu2.add(mnColor);
		
		clWhite = new JCheckBoxMenuItem("White");
		clWhite.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				
				liner.setBackground(Color.WHITE);
				liner.setCurrentLineForeground(Color.black);
				liner.setForeground(Color.gray);
			}
		});
		mnColor.add(clWhite);
		
		clYellow = new JCheckBoxMenuItem("Yellow");
		clYellow.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				
				liner.setBackground(Color.YELLOW);
				liner.setCurrentLineForeground(Color.black);
				liner.setForeground(Color.gray);
			}
		});
		mnColor.add(clYellow);
		
		clOrange = new JCheckBoxMenuItem("Orange");
		clOrange.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				
				liner.setBackground(Color.orange);
				liner.setCurrentLineForeground(Color.black);
				liner.setForeground(Color.gray);
			}
		});
		mnColor.add(clOrange);
		
		clPink = new JCheckBoxMenuItem("Pink");
		clPink.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				
				liner.setBackground(Color.pink);
				liner.setCurrentLineForeground(Color.black);
				liner.setForeground(Color.gray);
			}
		});
		mnColor.add(clPink);
		
		clRed = new JCheckBoxMenuItem("Red");
		clRed.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				
				liner.setBackground(Color.red);
				liner.setCurrentLineForeground(Color.black);
				liner.setForeground(Color.gray);
			}
		});
		mnColor.add(clRed);
		
		clGreen = new JCheckBoxMenuItem("Green");
		clGreen.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				
				liner.setBackground(Color.green);
				liner.setCurrentLineForeground(Color.black);
				liner.setForeground(Color.gray);
			}
		});
		mnColor.add(clGreen);
		
		clCyan = new JCheckBoxMenuItem("Cyan");
		clCyan.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				
				liner.setBackground(Color.cyan);
				liner.setCurrentLineForeground(Color.black);
				liner.setForeground(Color.gray);
			}
		});
		mnColor.add(clCyan);
		
		clBlue = new JCheckBoxMenuItem("Blue");
		clBlue.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				
				liner.setBackground(Color.blue);
				liner.setCurrentLineForeground(Color.black);
				liner.setForeground(Color.gray);
			}
		});
		mnColor.add(clBlue);
		
		clViolet = new JCheckBoxMenuItem("Violet");
		clViolet.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				
				liner.setBackground(new Color(70, 70, 100));
				liner.setCurrentLineForeground(Color.black);
				liner.setForeground(Color.gray);
			}
		});
		mnColor.add(clViolet);
		
		clLight = new JCheckBoxMenuItem("Light Gray");
		clLight.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				
				liner.setBackground(Color.lightGray);
				liner.setCurrentLineForeground(Color.black);
				liner.setForeground(Color.white);
			}
		});
		mnColor.add(clLight);
		
		clGray = new JCheckBoxMenuItem("Gray");
		clGray.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				
				liner.setBackground(Color.gray);
				liner.setCurrentLineForeground(Color.black);
				liner.setForeground(Color.white);
			}
		});
		mnColor.add(clGray);
		
		clDark = new JCheckBoxMenuItem("Dark Gray");
		clDark.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				
				liner.setBackground(Color.darkGray);
				liner.setCurrentLineForeground(Color.black);
				liner.setForeground(Color.white);
			}
		});
		mnColor.add(clDark);
		
		clBlack = new JCheckBoxMenuItem("Black");
		clBlack.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent ae) {
				
				liner.setBackground(Color.black);
				liner.setCurrentLineForeground(Color.yellow);
				liner.setForeground(Color.white);
			}
		});
		mnColor.add(clBlack);
		
		ButtonGroup clgp = new ButtonGroup();
		
		clgp.add(clBlack);
		clgp.add(clBlue);
		clgp.add(clDark);
		clgp.add(clGray);
		clgp.add(clLight);
		clgp.add(clViolet);
		clgp.add(clCyan);
		clgp.add(clGreen);
		clgp.add(clRed);
		clgp.add(clYellow);
		clgp.add(clWhite);
		clgp.add(clOrange);
		clgp.add(clPink);
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
				}
			});
		}
	public void saveFile() {
		
		inoutFile = new JFileChooser();
		inoutFile.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		int res = inoutFile.showSaveDialog(null);
		
		if(res == JFileChooser.CANCEL_OPTION)
		{
			inoutFile.cancelSelection();
			
			flagFinish = false;
		}
		
		else if(res == JFileChooser.APPROVE_OPTION)
		 try {
			 
			 File sf = inoutFile.getSelectedFile();
			 
			 Path pf = Paths.get(sf.getAbsolutePath());
			 
			 output = Files.newBufferedWriter(pf, StandardCharsets.UTF_8);
			 
			 output.write(text.getText());
			 
			 primary = text.getText();
			
			 docName = sf.getName();
			 
			 setTitle("Mega Note - "+docName);
			
			 output.close();
			
			 flagSave = true;
			
			 flagFirst = false;
			
			 flagFinish = true;
		 }
		catch(Exception ioe)
		{
			JOptionPane.showMessageDialog(null, "Eror occured in saving file", "Eror", JOptionPane.ERROR);
			
			flagFinish = false;
		}
	}
	public void Exit() {
		
		File info = new File("Data/setting.set");
		
		try {
			output = new BufferedWriter(new FileWriter(info));
			
			output.write(String.valueOf(text.getBackground().getRed())+"\n");
			output.write(String.valueOf(text.getBackground().getGreen())+"\n");
			output.write(String.valueOf(text.getBackground().getBlue())+"\n");
			
			output.write(String.valueOf(text.getForeground().getRed())+"\n");
			output.write(String.valueOf(text.getForeground().getGreen())+"\n");
			output.write(String.valueOf(text.getForeground().getBlue())+"\n");
			
			output.write(text.getFont().getFontName()+"\n");
			output.write(String.valueOf(text.getFont().getStyle())+"\n");
			output.write(String.valueOf(text.getFont().getSize())+"\n");
			
			output.write(String.valueOf(liner.getBackground().getRed())+"\n");
			output.write(String.valueOf(liner.getBackground().getGreen())+"\n");
			output.write(String.valueOf(liner.getBackground().getBlue())+"\n");
			
			output.write(String.valueOf(liner.getForeground().getRed())+"\n");
			output.write(String.valueOf(liner.getForeground().getGreen())+"\n");
			output.write(String.valueOf(text.getForeground().getBlue())+"\n");
			
			output.write(String.valueOf(liner.getCurrentLineForeground().getRed())+"\n");
			output.write(String.valueOf(liner.getCurrentLineForeground().getGreen())+"\n");
			output.write(String.valueOf(liner.getCurrentLineForeground().getBlue()));
			
			output.close();
			}
		catch (IOException e) {e.printStackTrace();}
		
		dispose();
		System.exit(0);
	}
	public class MenuHandller implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			Object source = e.getSource();
			
			if(source == New)
			{
				if(flagSave)
					{
					docName = "Untitled";
					
					setTitle("Mega Note - "+docName);
					
					text.setText("");
					
					primary = "";
					
					flagSave = true;
					
					flagFirst = true;
					}
				else
				{
					int r = JOptionPane.showConfirmDialog(null, new JLabel("Do you want to save changes to "+docName+"?"), 
							"Mega Note", JOptionPane.YES_NO_CANCEL_OPTION);
					
					switch(r)
					{
					case 0 : saveFile();
					
					case 1 :
					{
						docName = "Untitled";
						
						text.setText("");
						
						primary = "";
						
						flagSave = true;
						
						flagFirst = true;
						
						break;
					}
						
					case 2 :
					}
				}
			}
			else if(source == open)
			{
				if(flagSave)
				{
					String temp = "";
					
					inoutFile.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
					
					int res = inoutFile.showOpenDialog(null);
					
					if(res == JFileChooser.CANCEL_OPTION)
						inoutFile.cancelSelection();
					
					else if(res == JFileChooser.APPROVE_OPTION)
					{
					File of = inoutFile.getSelectedFile();
					
					try {
						
						BufferedReader bf = Files.newBufferedReader(of.toPath(), StandardCharsets.UTF_8);
						
						input = new Scanner(bf);
						
					} catch (FileNotFoundException e1) {
						
						JOptionPane.showMessageDialog(null, "Eror", "File Not Found.", JOptionPane.ERROR_MESSAGE);
					} 
					catch (IOException e1) {
						
						JOptionPane.showMessageDialog(null, "Eror", "Eror in opening file.", JOptionPane.ERROR_MESSAGE);
					}
					while(input.hasNext())
					temp += input.nextLine() + "\n";
					
					text.setText(temp);
					
					primary = temp;
					
					docName = of.getName();
					
					setTitle("Mega Note - "+docName);
					
					input.close();
					
					flagSave = true;
					
					flagFirst = false;
				}
				}
			else
			{
				int r = JOptionPane.showConfirmDialog(null, new JLabel("Do you want to save changes to "+docName+"?"), 
						"Mega Note", JOptionPane.YES_NO_CANCEL_OPTION);
				
				switch(r)
				{
				case 0 : saveFile();
				
				case 1 :
				{
					String temp = "";
					
					inoutFile.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
					
					int res = inoutFile.showOpenDialog(null);
					
					if(res == JFileChooser.CANCEL_OPTION)
						inoutFile.cancelSelection();
					
					else if(res == JFileChooser.APPROVE_OPTION)
					{
					File of = inoutFile.getSelectedFile();
					
					try {
						
						BufferedReader bf = Files.newBufferedReader(of.toPath(), StandardCharsets.UTF_8);
						
						input = new Scanner(bf);
					} catch (FileNotFoundException e1) {
						
						JOptionPane.showMessageDialog(null, "Eror", "File Not Found.", JOptionPane.ERROR_MESSAGE);
					} 
					catch (IOException e1) {
						
						JOptionPane.showMessageDialog(null, "Eror", "Eror in opening file.", JOptionPane.ERROR_MESSAGE);
					}
					while(input.hasNext())
					temp += input.nextLine() + "\n";
					
					text.setText(temp);
					
					primary = temp;
					
					docName = of.getName();
					
					setTitle("Mega Note - "+docName);
					
					input.close();
					
					flagSave = true;
					
					flagFirst = false;
					
					break;
				}
				}
				case 2 :
				}
			}
			}
			else if(source == save)
			{
				if(flagFirst)
					saveFile();
				else
				{
					 File sf = inoutFile.getSelectedFile();
					 
					 try {
						 
						 Path pf = Paths.get(sf.getAbsolutePath());
						 
						 output = Files.newBufferedWriter(pf, StandardCharsets.UTF_8);
						 
						 output.write(text.getText());
						 
						 primary = text.getText();
							
						 docName = sf.getName();
							
						 setTitle("Mega Note - "+docName);
							
						 output.close();
						
						 flagSave = true;
							
						 flagFirst = false;
							
						 flagFinish = true;
						 
					}
					 catch (FileNotFoundException e1) {
						
						e1.printStackTrace();
						
					}
					 catch (IOException e1) {
						
						e1.printStackTrace();
					}
				}
			}
			else if(source == saveAs)
			{
				saveFile();
			}
			else if(source == print)
			{
				class tryPrint implements Printable {
					
					public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
						
						if(pageIndex != 0)
							return NO_SUCH_PAGE;
						
						Graphics2D g2d = (Graphics2D) g;
						
						g2d.setFont(text.getFont());
						
						g2d.setPaint(Color.BLACK);
						
						g2d.drawString(text.getText(), 144, 144);
						
						return PAGE_EXISTS;
					}
				}
				PrinterJob pj = PrinterJob.getPrinterJob();
				
				pj.setPrintable(new tryPrint());
				
				if(pj.printDialog())
					try {
						pj.print();
					}
				catch (PrinterException pe){
					
					JOptionPane.showMessageDialog(null, "Eror occured in printing", "Mega Note", JOptionPane.ERROR_MESSAGE);
				}
			}
			else if(source == exit)
			{
				if(flagSave)
					Exit();
				else
				{
					int r = JOptionPane.showConfirmDialog(null, new JLabel("Do you want to save changes to "+docName+"?"), 
							"Mega Note", JOptionPane.YES_NO_CANCEL_OPTION);
					
					switch(r)
					{
					case 0 : saveFile();
					
					case 1 : if(flagFinish) Exit();
						
					case 2 :
					}
				}
			}
			else if(source == undo || source == undo2)
			{
				if(textUndo.canUndo())
					textUndo.undo();
			}
			else if(source == redo || source == redo2)
			{
				if(textUndo.canRedo())
					textUndo.redo();
			}
			else if(source == cut || source == cut2)
			{
				StringSelection textSelection = new StringSelection(text.getSelectedText());
				
				Clipboard clp = Toolkit.getDefaultToolkit().getSystemClipboard();
				
				clp.setContents(textSelection, null);
				
				text.replaceSelection("");
				
				flagSave = false;
			}
			else if(source == copy || source == copy2)
			{
				StringSelection textSelection = new StringSelection(text.getSelectedText());
				
				Clipboard clp = Toolkit.getDefaultToolkit().getSystemClipboard();
				
				clp.setContents(textSelection, null);
			}
			else if(source == paste || source == paste2)
			{
				String place = "";
				
				Clipboard clp = Toolkit.getDefaultToolkit().getSystemClipboard();
				
				Transferable t = clp.getContents(null);
				
				try {
					place = (String) t.getTransferData(DataFlavor.stringFlavor);
				} catch (UnsupportedFlavorException e1) {
					
					e1.printStackTrace();
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
				text.insert(place, text.getCaretPosition());
				
				flagSave = false;
			}
			else if(source == delet || source == delet2)
			{
				text.replaceSelection("");
				
				flagSave = false;
			}
			else if(source == selectAll || source == selectAll2)
			{
				text.selectAll();
			}
			else if(source == find)
			{
				index = 0;
				
				frameFind.setVisible(true);
			}
			else if(source == replace)
			{
				index = 0;
				
				frameReplace.setVisible(true);
			}
			else if(source == date)
			{
				Date now = new Date();
				
				@SuppressWarnings("deprecation")
				String dt = now.toLocaleString();
				
				text.insert(dt, text.getCaretPosition());
				
				flagSave = false;
			}
			else if(source == font)
			{
				frameFont.setVisible(true);
			}
			else if(source == mntmLowerCase)
			{
				try {
				text.replaceSelection(text.getSelectedText().toLowerCase());
				
				flagSave = false;
				}
				catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "No text selected", "Mega Note", JOptionPane.ERROR_MESSAGE);
				}
			}
			else if(source == mntmUpperCase)
			{
				try {
					text.replaceSelection(text.getSelectedText().toUpperCase());
					
					flagSave = false;
					}
					catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "No text selected", "Mega Note", JOptionPane.ERROR_MESSAGE);
					}
			}
			else if(source == about)
			{
				JOptionPane.showMessageDialog(null, "Programmer: Sayyid abbas aghaei\nVersion 2.0(Build 26/3/2017)\nContact us: @abba5aghaei", 
						"About Mega Note", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
}