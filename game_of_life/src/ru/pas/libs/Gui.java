package ru.pas.libs;

import ru.pas.Life;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.border.Border;

public class Gui extends JFrame {
	
	private JLabel[][] cell;
	private JLabel result = new JLabel();
	private JButton start = new JButton("Start");
	private JButton step = new JButton("Step");
	private JButton glider = new JButton("Glider");
	private int mapSideLength = 6;
	private int squareLength = 50;
	private int cellsQuantity = mapSideLength * mapSideLength; 
	private Color lightGreen = new Color(153, 255, 153);
	private Color lightRed = new Color(255, 153, 153);
	private Color lighBlue = new Color(153, 153, 255);
	public boolean[][] firstGeneration;
	public Gui(int _mapSideLength){
		mapSideLength = _mapSideLength;
		firstGeneration = new boolean[mapSideLength][mapSideLength];
	}
	public void run(){
		this.setTitle("Life");
		setBounds(100, 100, mapSideLength*squareLength + 70, mapSideLength*squareLength + 195);
		JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());
		this.add(mainPanel);
		Border solidBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
		Dimension labelSize = new Dimension(squareLength, squareLength);
		Dimension buttonSize = new Dimension(2*squareLength, squareLength);
		Dimension messageSize = new Dimension(7*squareLength, squareLength/2);
		cell = new JLabel[mapSideLength][mapSideLength];
		for(int i = 0; i < mapSideLength; i++){
			for (int j = 0; j < mapSideLength; j++){
				cell[i][j] = new JLabel(/*Integer.toString(i)*/);
				cell[i][j].setPreferredSize(labelSize);
				cell[i][j].setOpaque(true);
				if((int)(Math.random()*2) == 1)
					firstGeneration[i][j] = true;
				else
					firstGeneration[i][j] = false;
				setCellState(firstGeneration[i][j], i, j);
				mainPanel.add(cell[i][j]);
			}
		}
		start.addActionListener(new ListenerAction());
		start.setPreferredSize(buttonSize);
		mainPanel.add(start);
		glider.addActionListener(new ListenerActionGlider());
		glider.setPreferredSize(buttonSize);
		mainPanel.add(glider);
		step.addActionListener(new ListenerActionStep());
		step.setPreferredSize(buttonSize);
		mainPanel.add(step);
		mainPanel.add(result);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	class ListenerAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
			Life.start(firstGeneration);
			start.setEnabled(false);
		}
    }
	
	class ListenerActionStep implements ActionListener {
        public void actionPerformed(ActionEvent e) {
			Life.next += 1;
		}
    }
	
	class ListenerActionGlider implements ActionListener {
        public void actionPerformed(ActionEvent e) {
			for(int i = 0; i < mapSideLength; i++)
				for (int j = 0; j < mapSideLength; j++)
					if((i == 0 && j == 1) || (i == 1 && j == 2) || (i == 2 && j == 0) || (i == 2 && j == 1) || (i == 2 && j == 2)){
						firstGeneration[i][j] = true;
						setCellState(firstGeneration[i][j], i, j);
					}else{
						firstGeneration[i][j] = false;
						setCellState(firstGeneration[i][j], i, j);
					}
		}
    }
	
	public void setCellState(boolean life, int VCoordinate, int HCoordinate){
		if(life)
			cell[VCoordinate][HCoordinate].setBackground(lightGreen);
		else
			cell[VCoordinate][HCoordinate].setBackground(lightRed);
	}
	
	public void setNewGeneration(boolean newGeneration[][]) {
		for(int i = 0; i < mapSideLength; i++)
			for (int j = 0; j < mapSideLength; j++){
					setCellState(newGeneration[i][j], i, j);
			}
	}
	public void setTxt(String message){
		result.setText(message);
	}
}