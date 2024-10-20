package main.java;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Node extends JButton implements ActionListener{

	Node parent;
	int col;
	int row;
	int gCost;
	int hCost;
	int fCost;
	
	boolean start;
	boolean goal;
	boolean solid;
	boolean open;
	boolean checked;
	
	public Node(int col, int row) {
		this.col = col;
		this.row = row;
		
		setBackground(Color.WHITE);
		setForeground(Color.BLACK);
		addActionListener(this);
	}
	
	public void setAsStart() {
		setBackground(Color.BLUE);
		setForeground(Color.WHITE);
		setText("START");
		start = true;
	}
	
	public void setAsGoal() {
		setBackground(Color.RED);
		setForeground(Color.WHITE);
		setText("END");
		goal = true;
	}
	
	public void setAsSolid() {
		setBackground(Color.BLACK);
		setForeground(Color.BLACK);
		solid = true;
	}
	
	public void setAsOpen() {
		open = true;
	}
	
	public void setAsChecked() {
		if (!start && !goal) {
			setBackground(Color.ORANGE);
			setForeground(Color.BLACK);
		}
		checked = true;
	}
	
	public void setAsPath() {
		setBackground(Color.GREEN);
		setForeground(Color.BLACK);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		setAsSolid();
	}

}
