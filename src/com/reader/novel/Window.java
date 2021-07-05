package com.reader.novel;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URI;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;

public class Window implements ActionListener{

	protected File file;
	
	private JFrame frame;
	private JPanel panel, panel2, choicePanel, menuPanel, readingPanel;
	
	private JTextPane bookArea;
	private JScrollPane scrollPane;
	private StyledDocument doc, pythonDoc;
	
	private SimpleAttributeSet attributeSet, pythonAttributeSet;
	
	private CardLayout cl;
	
	private Color black = Color.BLACK;
	private Color white = Color.WHITE;
	private Color gray = Color.GRAY;
	private Color lightGray = Color.LIGHT_GRAY;
	
	private JButton visitSiteButton, homeButton, undoButton, indexButton, prevButton, nextButton, firstButton, lastButton;
	private JButton goURLButton, copyNovelButton, directoryButton, readButton, continueReadingButton;
	
	private JLabel urlLabel, urlLabel2, prefLabel;
	private JTextField urlField, urlField2, directoryField;
	
	private JLabel orLabel;
	
	private Font urlFont = new Font("arial", Font.ITALIC, 14);
	private Font boldFont = new Font("arial", Font.BOLD, 14);
	private Font bookAreaFont = new Font("Arial", Font.PLAIN, 16);
	
	private Desktop desktop = Desktop.getDesktop();
	
	String name;
	int width, height;
	
	private JPanel pythonPanel;
	private JLabel currentURLLabel, novelPathLabel, novelNameLabel, consoleLabel;
	private JTextField pythonURLTextField, pythonPathTextField, novelNameTextField;
	private JButton pythonURLButton, changePath, pythonBackButton, pythonStartButton;
	private JTextPane pythonTextPane;
	private JScrollPane pythonScrollPane;
	
	private NovelFileReader novelFileReader;
	BookReader bookReader;
	public Window(String name, int width, int height, BookReader bookReader) {
		novelFileReader = new NovelFileReader(this);
		
		this.name = name;
		this.width = width;
		this.height = height;
		
		this.bookReader = bookReader;
		
		initComponents();	
	}
	
	private void initComponents() {
		frame = new JFrame(name);
		
		AddCardLayout();
			
		AddJPanel();
		AddJPanel2();
		AddMenuPanel();
		AddMenuDesign();
		AddGoURLButton();
		AddCopyNovelButton();
		AddDirectoryButton();
		AddReadButton();
		AddContinueReadingButton();
		
//		AddJFileChooser();
		
		AddReadingPanel();
		AddURLLabel();
		AddURLField2();
		AddVisitSiteButton();
		AddBookArea();
		AddChoicePanel();
		
		AddHomeButton();
		AddLeftButton();
		AddPrevButton();
		AddIndexButton();
		AddNextButton();
		AddRightButton();
		AddUndoButton();
		
		AddPythonPanel();
//		AddPathchooser();
		
		AddJFrame();	
		
	}
	
	private void AddCardLayout() {
		cl = new CardLayout();
	}
	
	private void URLRequest(String url) {
		try {
			desktop.browse(new URI(url));			
		}
		catch(Exception ee) {
			ee.getStackTrace();
			AlertMessage("WRONG URL OR NO CONNECTION");
		}
	}
	
	private boolean CheckURL(String url) {
		try {
			new URL(url).toURI();
			return true;
		}
		catch(Exception ee) {
			return false;
		}
	}
	
	
	private void AddVisitSiteButton() {
		visitSiteButton = new JButton("Visit Site");
		visitSiteButton.setBackground(black);
		visitSiteButton.setForeground(white);
		visitSiteButton.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, lightGray));
		visitSiteButton.setBounds(width - 130, 15, 85, 40);
		visitSiteButton.addActionListener(this);
		readingPanel.add(visitSiteButton);
	}
	
	private void AddHomeButton() {
		homeButton = new JButton("Home");
		homeButton.setBackground(black);
		homeButton.setForeground(white);
		homeButton.setBounds(10, 10, 80, 80);
		homeButton.addActionListener(this);
		choicePanel.add(homeButton);
	}
	
	private void AddLeftButton() {
		firstButton = new JButton("<<");
		firstButton.setBackground(black);
		firstButton.setForeground(white);
		firstButton.setBounds((width - 40) / 2 - 232, 10, 80, 80);
		firstButton.addActionListener(this);
		choicePanel.add(firstButton);
	}
	
	private void AddPrevButton() {
		prevButton = new JButton("Prev");
		prevButton.setBackground(black);
		prevButton.setForeground(white);
		prevButton.setBounds((width - 40) / 2 - 142, 10, 80, 80);
		prevButton.addActionListener(this);
		choicePanel.add(prevButton);
	}
	
	private void AddIndexButton() {
		indexButton = new JButton("Index");
		indexButton.setBackground(black);
		indexButton.setForeground(white);
		indexButton.setBounds((width - 40) / 2 - 52, 10, 80, 80);
		indexButton.addActionListener(this);
		choicePanel.add(indexButton);
	}
	
	private void AddNextButton() {
		nextButton = new JButton("Next");
		nextButton.setBackground(black);
		nextButton.setForeground(white);
		nextButton.setBounds((width - 40) / 2 + 38, 10, 80, 80);
		nextButton.addActionListener(this);
		choicePanel.add(nextButton);
	}
	
	private void AddRightButton() {
		lastButton = new JButton(">>");
		lastButton.setBackground(black);
		lastButton.setForeground(white);
		lastButton.setBounds((width - 40) / 2 + 128, 10, 80, 80);
		lastButton.addActionListener(this);
		choicePanel.add(lastButton);
	}
	
	private void AddUndoButton() {
		undoButton = new JButton("Undo");
		undoButton.setBackground(black);
		undoButton.setForeground(white);
		undoButton.setBounds(width - 142, 10, 80, 80);
		undoButton.addActionListener(this);
		choicePanel.add(undoButton);
	}
	
	private void AddGoURLButton() {
		goURLButton = new JButton("Go to URL");
		goURLButton.setBackground(black);
		goURLButton.setForeground(white);
		goURLButton.setBounds((width - 32) / 2 - 105, 190, 100, 25);
		goURLButton.addActionListener(this);
		menuPanel.add(goURLButton);
	}
	
	private void AddCopyNovelButton() {
		copyNovelButton = new JButton("Copy Novel");
		copyNovelButton.setBackground(black);
		copyNovelButton.setForeground(white);
		copyNovelButton.setBounds((width - 32) / 2 + 5, 190, 100, 25);
		copyNovelButton.addActionListener(this);
		menuPanel.add(copyNovelButton);
	}
	
	private void AddDirectoryButton() {
		directoryButton = new JButton("Open Files");
		directoryButton.setBackground(black);
		directoryButton.setForeground(white);
		directoryButton.setBounds((width - 32) / 2 - 50, 300, 100, 25);
		directoryButton.addActionListener(this);
		menuPanel.add(directoryButton);
	}
	
	private void AddReadButton() {
		readButton = new JButton("Start Reading");
		readButton.setBackground(black);
		readButton.setForeground(white);
		readButton.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.CYAN));
		readButton.setFont(boldFont);
		readButton.setBounds((width - 32) / 2 - 150, 380, 300, 50);
		readButton.addActionListener(this);
		menuPanel.add(readButton);
	}
	
	private void AddContinueReadingButton() {
		continueReadingButton = new JButton("Continue Reading");
		continueReadingButton.setBackground(black);
		continueReadingButton.setForeground(white);
		continueReadingButton.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.CYAN));
		continueReadingButton.setFont(boldFont);
		continueReadingButton.setBounds((width - 32) / 2 - 150, 440, 300, 50);
		continueReadingButton.addActionListener(this);
		menuPanel.add(continueReadingButton);
	}
	
	private void AddURLLabel() {
		urlLabel2 = new JLabel("U R L :");
		urlLabel2.setForeground(white);
		urlLabel2.setBounds(20, 20, 50, 25);
		readingPanel.add(urlLabel2);
	}
	
	private void AddURLField2() {
		urlField2 = new JTextField("");
		urlField2.setBackground(black);
		urlField2.setForeground(white);
		urlField2.setFont(urlFont);
		urlField2.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, white));
		urlField2.setHorizontalAlignment(SwingConstants.CENTER);
		urlField2.setBounds(60, 20, 700, 30);
		readingPanel.add(urlField2);
	}
	
	private void AddJPanel() {
		panel = new JPanel();
		panel.setBackground(black);
		panel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, gray));
		panel.setLayout(null);
		frame.add(panel);
	}
	
	private void AddJPanel2() {
		panel2 = new JPanel();
		panel2.setBackground(white);
		panel2.setBounds(10, 10, width - 37, height - 60);
		panel2.setLayout(cl);
		panel.add(panel2);
	}
	
	private void AddPythonPanel() {
		pythonPanel = new JPanel();
		pythonPanel.setBackground(Color.WHITE);
		pythonPanel.setLayout(null);
		panel2.add(pythonPanel, "3");
		
		currentURLLabel = new JLabel("Current URL :");
		currentURLLabel.setBounds(20, 20, 100, 50);	
		pythonPanel.add(currentURLLabel);
		
		pythonURLTextField = new JTextField();
		pythonURLTextField.setFont(urlFont);
		pythonURLTextField.setHorizontalAlignment(SwingConstants.CENTER);
		pythonURLTextField.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, black));
		pythonURLTextField.setBounds(110, 30, 605, 30);
		pythonPanel.add(pythonURLTextField);
		
		pythonURLButton = new JButton("Go to URL");
		pythonURLButton.setBackground(Color.WHITE);
		pythonURLButton.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, black));
		pythonURLButton.setBounds(740, 30, 100, 30);
		pythonURLButton.addActionListener(this);
		pythonPanel.add(pythonURLButton);
		
		novelPathLabel = new JLabel("Novel Path    :");
		novelPathLabel.setBounds(20, 80, 100, 50);	
		pythonPanel.add(novelPathLabel);
		
		pythonPathTextField = new JTextField("D:\\Novels\\");
		pythonPathTextField.setFont(boldFont);
		pythonPathTextField.setHorizontalAlignment(SwingConstants.CENTER);
		pythonPathTextField.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, black));
		pythonPathTextField.setBounds(110, 90, 605, 30);
		pythonPanel.add(pythonPathTextField);
		
		changePath = new JButton("Change Path");
		changePath.setBackground(Color.WHITE);
		changePath.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, black));
		changePath.setBounds(740, 90, 100, 30);
		changePath.addActionListener(this);
		pythonPanel.add(changePath);
		
		novelNameLabel = new JLabel("Novel Name  :");
		novelNameLabel.setBounds(20, 140, 100, 50);	
		pythonPanel.add(novelNameLabel);
		
		novelNameTextField = new JTextField();
		novelNameTextField.setFont(boldFont);
		novelNameTextField.setHorizontalAlignment(SwingConstants.CENTER);
		novelNameTextField.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, black));
		novelNameTextField.setBounds(110, 150, 605, 30);
		pythonPanel.add(novelNameTextField);
		
		pythonBackButton = new JButton("Back");
		pythonBackButton.setBackground(Color.WHITE);
		pythonBackButton.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, black));
		pythonBackButton.setBounds(190, 210, 100, 30);
		pythonBackButton.addActionListener(this);
		pythonPanel.add(pythonBackButton);

		consoleLabel = new JLabel("P y t h o n  C o n s o l e");
		consoleLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		consoleLabel.setBounds(120, 200, 605, 50);
		consoleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pythonPanel.add(consoleLabel);
		
		pythonStartButton = new JButton("Start Scraping");
		pythonStartButton.setBackground(Color.WHITE);
		pythonStartButton.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, black));
		pythonStartButton.setBounds(550, 210, 100, 30);
		pythonStartButton.addActionListener(this);
		pythonPanel.add(pythonStartButton);
		
		pythonTextPane = new JTextPane();
		pythonTextPane.setBackground(Color.BLACK);
		pythonTextPane.setForeground(Color.WHITE);
		pythonTextPane.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, gray));
		pythonTextPane.setEditable(false);
		pythonTextPane.setFont(bookAreaFont);
		pythonTextPane.requestFocus();
		
		pythonScrollPane = new JScrollPane(pythonTextPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pythonScrollPane.setBounds(115, 260, 605, 300);
		pythonScrollPane.getVerticalScrollBar().setUnitIncrement(5);
	
		pythonDoc = pythonTextPane.getStyledDocument();
		pythonAttributeSet = new SimpleAttributeSet();
		
		//TODO: USE ThIS IN THREADING
		try {
			pythonDoc.insertString(pythonDoc.getLength(), "meow:", pythonAttributeSet);
		}
		catch(Exception ee) {
			
		}
		
		pythonPanel.add(pythonScrollPane);
		//TODO: COMPONENTS
	}
	
	
	private void AddMenuPanel() {
		menuPanel = new JPanel();
		menuPanel.setBackground(black);
		menuPanel.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, lightGray));
		menuPanel.setLayout(null);
		menuPanel.setBounds(10, 10, width - 37, height - 60);
		panel2.add(menuPanel, "1");
	}
	
	private void AddMenuDesign() {
		urlLabel = new JLabel("Copy New Novel");
		urlLabel.setForeground(white);
		urlLabel.setBounds((width - 32) / 2 - 45, 90, 100, 100);
		menuPanel.add(urlLabel);
		
		urlField = new JTextField(novelFileReader.getDefaultURL());
		urlField.setHorizontalAlignment(SwingConstants.CENTER);
		urlField.setBackground(black);
		urlField.setForeground(white);
		urlField.setFont(urlFont);
		urlField.setBounds((width - 32) / 2 - (300), 150, 600, 30);
		menuPanel.add(urlField);
		
		orLabel = new JLabel("or choose existing Novel in the DataBase");
		orLabel.setForeground(white);
		orLabel.setBounds((width - 32) / 2 - 125, 200, 250, 100);
		menuPanel.add(orLabel);
		
		directoryField = new JTextField(novelFileReader.getNovelFilePath());
		directoryField.setHorizontalAlignment(SwingConstants.CENTER);
		directoryField.setBackground(black);
		directoryField.setForeground(white);
		directoryField.setFont(urlFont);
		directoryField.setBounds((width - 32) / 2 - (300), 260, 600, 30);
		menuPanel.add(directoryField);
		
		prefLabel = new JLabel("Continue Reading : " + novelFileReader.getPrefPath().get("Continue_Reading", ""));
		prefLabel.setForeground(white);
		prefLabel.setBounds(-50, 460, width + 60, 100);
		prefLabel.setFont(urlFont);
		prefLabel.setHorizontalAlignment(SwingConstants.CENTER);
		menuPanel.add(prefLabel);
		
		
	}
	
	private void AddReadingPanel() {
		readingPanel = new JPanel();
		readingPanel.setBackground(black);
		readingPanel.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, lightGray));
		readingPanel.setLayout(null);
		readingPanel.setBounds(width/2 - (width  - 40 + 19) / 2, height - 150, width - 40, 100);
		panel2.add(readingPanel, "2");
	}
	
	private void AddChoicePanel() {
		choicePanel = new JPanel();
		choicePanel.setBackground(black);
		choicePanel.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, lightGray));
		choicePanel.setLayout(null);
		choicePanel.setBounds(width/2 - (width  - 40 + 24) / 2, height - 170, 848, 100);
		readingPanel.add(choicePanel);
	}
	
	private void AddBookArea() {
		bookArea = new JTextPane();
		bookArea.setBackground(Color.BLACK);
		bookArea.setForeground(Color.WHITE);
		bookArea.setEditable(false);
		bookArea.setAlignmentX(40);
		bookArea.setFont(bookAreaFont);
		bookArea.requestFocus();
		
		scrollPane = new JScrollPane(bookArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(width/2 - 442, 70, 847, 450);
		scrollPane.getVerticalScrollBar().setUnitIncrement(5);
		
		doc = bookArea.getStyledDocument();
		attributeSet = new SimpleAttributeSet();
		
		readingPanel.add(scrollPane);
	}
	
	private void AddJFrame() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		
		frame.setResizable(false);
		frame.setVisible(true);
		
		frame.setFocusable(true);
		
		cl.show(panel2, "1");
	}
	
	
	public void AlertMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	public int ConfirmMessage(String message) {
		return JOptionPane.showConfirmDialog(null, message);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == goURLButton) {
			URLRequest(urlField.getText());
		}
		else if(e.getSource() == copyNovelButton) {
//			AlertMessage("ERROR PROGRAMMER TOO NOOB TO CONNECT PYTHON AND JAVA :<");//TODO : fix
			if(CheckURL(urlField.getText())) {
				//Add JPanel
				pythonURLTextField.setText(urlField.getText());
				cl.show(panel2, "3");
			}
			else {
				AlertMessage("Not a valid URL");
			}
		}
		else if(e.getSource() == directoryButton) {
			directoryField.setText(novelFileReader.ChooseFile());
		}
		else if(e.getSource() == readButton) {
			if(novelFileReader.ReadNovelFile(directoryField.getText())) {
				novelFileReader.ListIndex();
				AddInList(directoryField.getText());
				cl.show(panel2, "2");
			}
			else {
				AlertMessage("Please choose a Novel to read!");
			}
		}
		else if(e.getSource() == continueReadingButton) {
			if(novelFileReader.ReadNovelFile(novelFileReader.getPrefPath().get("Continue_Reading", ""))) {
				novelFileReader.ListIndex();
				AddInList(novelFileReader.getPrefPath().get("Continue_Reading", ""));
				cl.show(panel2, "2");
			}
		}
		else if(e.getSource() == visitSiteButton) {
			URLRequest(urlField2.getText());
		}
		else if(e.getSource() == homeButton) {
			prefLabel.setText("Continue Reading : " + novelFileReader.getPrefPath().get("Continue_Reading", ""));
			cl.show(panel2, "1");
		}
		else if(e.getSource() == firstButton) {
			AddInList(novelFileReader.getFirst());
			novelFileReader.ReadNovelFile(novelFileReader.getFirst());
		}
		else if(e.getSource() == prevButton) {
			if(!novelFileReader.ReadNovelFile(novelFileReader.getPrevious())) {
				AlertMessage("Novel is at the Beginning");
				novelFileReader.ReadNovelFile(novelFileReader.getPrefPath().get("Continue_Reading", ""));
			}
			else {
				AddInList(novelFileReader.getPrefPath().get("Continue_Reading", ""));
			}
		}
		else if(e.getSource() == indexButton) {
			novelFileReader.ShowIndex();
		}
		else if(e.getSource() == nextButton) {
			if(!novelFileReader.ReadNovelFile(novelFileReader.getNext())) {
				AlertMessage("Novel is at the End");
				novelFileReader.ReadNovelFile(novelFileReader.getPrefPath().get("Continue_Reading", ""));
			}
			else {
				AddInList(novelFileReader.getPrefPath().get("Continue_Reading", ""));
			}
		}
		else if(e.getSource() == lastButton) {
			AddInList(novelFileReader.getLast());
			novelFileReader.ReadNovelFile(novelFileReader.getLast());
		}
		else if(e.getSource() == undoButton) {
			novelFileReader.Undo();
			novelFileReader.ListIndex();
		}
		else if(e.getSource() == pythonURLButton) {
			URLRequest(pythonURLTextField.getText());
		}
		else if(e.getSource() == changePath) {
			pythonPathTextField.setText(novelFileReader.ChoosePath());
		}
		else if(e.getSource() == pythonBackButton) {
			cl.show(panel2, "1");
		}
		else if(e.getSource() == pythonStartButton) {
			//TODO: write html in file
			if(novelFileReader.WriteHTML(pythonURLTextField.getText() + "\r" + getCurrentURL() + "\r" + pythonPathTextField.getText() + "\\" + "\r" + novelNameTextField.getText()) == 0) {
				bookReader.CopyNovel("python D:\\[Programming]\\Python\\Web_Novel_Reader.py");
			}
			//start  python program
		}
	}
	
	private String getCurrentURL() {
		String[] url = pythonURLTextField.getText().split("/");
		StringBuffer url2 = new StringBuffer();
		for(int i = 0; i < url.length - 1; i++) {
			url2.append(url[i] + "/");
		}
		return url2.toString();
	}
	
	private void AddInList(String directory) {
		novelFileReader.AddUndoList(directory);
	}
	
	public JTextPane getBookArea() {
		return bookArea;
	}
	
	public StyledDocument getPyythonStyleDocument() {
		return pythonDoc;
	}
	
	public StyledDocument getStyleDocument() {
		return doc;
	}
	
	public SimpleAttributeSet getSimpleAttributeSet() {
		return attributeSet;
	}
	
	public SimpleAttributeSet getPythonSimpleAttributeSet() {
		return pythonAttributeSet;
	}
	
	public JTextField getDirectoryField() {
		return directoryField;
	}
	
	public JButton getDirectoryButton() {
		return directoryButton;
	}
	
	public JButton getChangePathButton() {
		return changePath;
	}
	
	public JTextField getURLTextField2() {
		return urlField2;
	}
	
	public JFrame gettFrame() {
		return frame;
	}
	
	public NovelFileReader getNFR() {
		return novelFileReader;
	}
}
