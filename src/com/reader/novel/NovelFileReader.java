package com.reader.novel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.prefs.Preferences;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class NovelFileReader {

	File[] filesInDirectory;
	
	File novelFile;
	File currentDirectoryFile;
	
	File novel;
	FileInputStream novelFIS;
	InputStreamReader novelISR;
	BufferedReader novelBR;
	
	StringBuffer novelSB;
	
	JFileChooser fileChooser,  pathChooser;
	
	String defaultURL = "https://m.wuxiaworld.co/";
	String words;
	
	File htmlData;
	FileWriter fileWriter;
	BufferedWriter bufferedWriter;
	
	private Preferences pref = Preferences.userRoot();
	
	private String firstDirectory = "";
	private String lastDirectory = "";
	private String nextDirectory = "";
	private String previousDirectory = "";
	
	int[] chapters;
	
	private String[] numericValue;
	private String[] prev_next;
	private String[] fileDirectory;
	
	private StringBuffer[] index;
	
	LinkedList<String> undoList = new LinkedList<String>();
	
	private Window window;
	public NovelFileReader(Window window) {
		this.window = window;
		
		novelFile = new File(getNovelFilePath());
		AddHTMLData();
		AddJFileChooser();
	}
	
	public int WriteHTML(String htmlData) {
		try {
			if(this.htmlData.createNewFile()) {
				window.AlertMessage("Creating New File at : " + getHTMLFilePath());
			}
			fileWriter = new FileWriter(this.htmlData, false);
			bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(htmlData);
			bufferedWriter.close();
			
			return window.ConfirmMessage("Start Scraping?");
		}
		catch(Exception ee){
			window.AlertMessage("Error Writing HTML Data");
			ee.printStackTrace();
			
			return 1;
		}
	}
	
	private void AddHTMLData() {
		htmlData =  new File(getHTMLFilePath());
	}
	
	public String getHTMLFilePath() {
		return "D:\\Novels\\HTMLDATA.txt";
	}
	
	private void AddJFileChooser() {
		fileChooser = new JFileChooser("");
		fileChooser.setCurrentDirectory(getNovelFile());
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		pathChooser = new JFileChooser("");
		pathChooser.setCurrentDirectory(getNovelFile());
		pathChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	}
	
	public void Undo() {
		try {
			if(undoList.size() > 1) {
				undoList.remove(undoList.size() - 1);
				ReadNovelFile(undoList.get(undoList.size() - 1));
			}
		}
		catch(Exception ee) {
		}
	}

	public void AddUndoList(String directory) {
		undoList.add(directory);
	}
	
	public void ListIndex() {
		SortChapters();
		index = new StringBuffer[chapters.length];
		boolean once = true;
		for(int i = 0; i < chapters.length; i++) {
			index[i] = new StringBuffer();
			for(int j = 0; j < numericValue.length - 1; j++) {
				index[i].append(numericValue[j] + "_");
			}
			if(once) {
				once = false;
				firstDirectory = index[i].toString() + chapters[0] + ".txt";
				lastDirectory = index[i].toString() + chapters[chapters.length - 1] + ".txt";
			}
			index[i].append("Chapter_" + String.valueOf(chapters[i]));
		}
	}
	
	public void ShowIndex() {
		try {
			String[] chapterIndex = new String[index.length];
			
			for(int i = 0; i < chapterIndex.length; i++) {
				chapterIndex[i] = index[i].toString();
			}
			String[] highlight = pref.get("Continue_Reading", "").replaceAll(".txt", "").split("_");
			
			StringBuffer newChapter = new StringBuffer();
			for(int i = 0; i < highlight.length - 1; i++) {
				newChapter.append(highlight[i] + "_");
			}
			
			newChapter.append("Chapter_" + highlight[highlight.length - 1]);
			
			String chapter = JOptionPane.showInputDialog(null, "Select a chapter", "Index", JOptionPane.QUESTION_MESSAGE, null, chapterIndex, newChapter.toString()).toString();
			chapter = chapter.replaceAll("Chapter_", "")  + ".txt";
			AddUndoList(chapter);
			ReadNovelFile(chapter);
		}
		catch(Exception ee) {
			
		}
		
	}
	
	public void SortChapters() {
		try {
			filesInDirectory = new File(pref.get("Directory", "")).listFiles();
			fileDirectory = new String[filesInDirectory.length];
			chapters = new int[fileDirectory.length];
			for(int i = 0; i < filesInDirectory.length; i++) {
				fileDirectory[i] = filesInDirectory[i].getAbsolutePath();
				fileDirectory[i] = fileDirectory[i].replaceAll(".txt", "");
				numericValue = fileDirectory[i].split("_");
				chapters[i] = Integer.parseInt(numericValue[numericValue.length - 1]);
			}
			
			Arrays.sort(chapters);
		}
		catch(Exception ee) {
			window.AlertMessage("ERROR SORTING CHAPTERS");
		}
	}
	
	public String ChooseFile() {
		try {
			if(fileChooser.showOpenDialog(window.getDirectoryButton()) == JFileChooser.APPROVE_OPTION) {
				pref.put("Directory", fileChooser.getCurrentDirectory().toString());
				SortChapters();
				return fileChooser.getSelectedFile().getAbsolutePath();
			}
		}
		catch(Exception ee) {
			window.AlertMessage("Error No File Detected");
		}
		
		return "D:\\Novels";
	}
	
	public String ChoosePath() {
		try {
			if(pathChooser.showOpenDialog(window.getDirectoryButton()) == JFileChooser.APPROVE_OPTION) {
				return pathChooser.getSelectedFile().getPath();
			}
		}
		catch(Exception ee) {
			window.AlertMessage("Error No File Detected");
		}
		
		return "D:\\Novels";
	}
	
	public boolean ReadNovelFile(String directory) {
		window.getBookArea().setText("");
		words = "";
		try {
			novelSB = new StringBuffer();
			novel = new File(directory);
			novelFIS = new FileInputStream(novel);
			novelISR = new InputStreamReader(novelFIS);
			novelBR = new BufferedReader(novelISR);
			
			while((words = novelBR.readLine()) != null) {
				novelSB.append(words);
			}
			RewriteNovel(novelSB.toString(), directory);
			
			window.getBookArea().setCaretPosition(0);
			
			novelBR.close();
			novelISR.close();
			novelFIS.close();
			
			pref.put("Continue_Reading", directory);
			return true;
		}
		catch(Exception ee) {
		}
		
		return false;
	}
	
	public void RewriteNovel(String novel, String directory) {
		try {
			String[] prev_next = novel.split("<split>");
			previousDirectory = prev_next[0];
			nextDirectory = prev_next[1];
			
			String[] urlNovel = prev_next[prev_next.length - 1].split("b'");
			window.getURLTextField2().setText(urlNovel[0]);
			
			for(int i = 1; i < urlNovel.length; i++) {	
				urlNovel[i] = urlNovel[i].replaceAll("<script>ChapterMid\\(\\);</script>", "");
				urlNovel[i] = urlNovel[i].replaceAll("\\\\xe3\\\\x80\\\\x80", "\t");
				urlNovel[i] = urlNovel[i].replaceAll("\\\\xe2\\\\x80\\\\x93", "-");
				urlNovel[i] = urlNovel[i].replaceAll("\\\\xe2\\\\x80\\\\x94", "-");
				urlNovel[i] = urlNovel[i].replaceAll("\\\\xe2\\\\x80\\\\x98", "'");
				urlNovel[i] = urlNovel[i].replaceAll("\\\\xe2\\\\x80\\\\x99", "'");
				urlNovel[i] = urlNovel[i].replaceAll("\\\\xe2\\\\x80\\\\x9c", "\"");
				urlNovel[i] = urlNovel[i].replaceAll("\\\\xe2\\\\x80\\\\x9d", "\"");
				urlNovel[i] = urlNovel[i].replaceAll("\\\\xe2\\\\x80\\\\xa6", "...");
				urlNovel[i] = urlNovel[i].replaceAll("\\\\'\\\\''", "\"");
				urlNovel[i] = urlNovel[i].replaceAll("\\\\'", "'");
				urlNovel[i] = urlNovel[i].replaceAll("\\\\n", "");
				urlNovel[i] = urlNovel[i].replaceAll("<br/>", "\\\n");
				urlNovel[i] = urlNovel[i].replaceAll("\\\\r</div>'", "");	
				urlNovel[i] = urlNovel[i].replaceAll("&lt;", "<");	
				urlNovel[i] = urlNovel[i].replaceAll("&gt;", ">");
//				urlNovel[i] = urlNovel[i].replaceAll("Lin Ming", "Jan Falcon");
			}
			
			String[] story = urlNovel[1].split("</div>\\\\r");
			
			try {
				window.getStyleDocument().insertString(window.getStyleDocument().getLength(), directory + "\n\n\r" + story[1], window.getSimpleAttributeSet());
			}
			catch(Exception ee) {
				ee.getStackTrace();
				window.AlertMessage("ERROR REFORMATING STORY");
			}
		}
		catch(Exception ee) {
			window.AlertMessage("Error No File Detected");
		}
	}
	
	public String getFirst() {
		return firstDirectory;
	}
	
	public String getLast() {
		return lastDirectory;
	}
	
	public String getPrevious() {
		return previousDirectory;
	}
	
	public String getNext() {
		return nextDirectory;
	}
	
	public String[] getPrev_Next() {
		return prev_next;
	}
	
	public String getNovelFilePath() {
		return "D:\\Novels";
	}
	
	public String getDefaultURL() {
		return defaultURL;
	}
	
	public File getNovelFile() {
		return novelFile;
	}
	
	public File getCurrentDirectoryFile() {
		return currentDirectoryFile;
	}
	
	public Preferences getPrefPath() {
		return pref;
	}
	
}
