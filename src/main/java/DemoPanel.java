package main.java;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

public class DemoPanel extends JPanel {

	final int COLUMNS = 20;
	final int ROWS = 10;
	final int NODE_SIZE = 20;
	final int SCREEN_WIDTH = COLUMNS * NODE_SIZE;
	final int SCREEN_HEIGHT = ROWS * NODE_SIZE;

	// NODES
	Node[][] node = new Node[COLUMNS][ROWS];
	Node startNode;
	Node goalNode;
	Node currentNode;

	ArrayList<Node> openList = new ArrayList<>();
	ArrayList<Node> checkedList = new ArrayList<>();

	// OTHERS
	boolean goalReached = false;
	int step = 0;

	public DemoPanel() {
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.BLACK);
		this.setLayout(new GridLayout(ROWS, COLUMNS));

		this.addKeyListener(new Keyhandler(this));
		this.setFocusable(true);
		this.requestFocusInWindow();
		
		int col = 0;
		int row = 0;

		while (col < COLUMNS && row < ROWS) {
			node[col][row] = new Node(col, row);
			this.add(node[col][row]);

			col++;
			if (col == COLUMNS) {
				col = 0;
				row++;
			}
		}

		setStartNode(3, 6);
		setGoalNode(11, 3);
	}

	private void setStartNode(int col, int row) {
		node[col][row].setAsStart();
		startNode = node[col][row];
		currentNode = startNode;
	}

	private void setGoalNode(int col, int row) {
		node[col][row].setAsGoal();
		goalNode = node[col][row];
	}

	private void setSolidNode(int col, int row) {
		node[col][row].setAsSolid();
	}

	private void setCostOnNode() {
		int col = 0;
		int row = 0;

		while (col < COLUMNS && row < ROWS) {
			getCost(node[col][row]);
			col++;
			if (col == COLUMNS) {
				col = 0;
				row++;
			}
		}

	}

	private void getCost(Node node) {
		// Get G cost (distance from start node)
		int xDistance = Math.abs(node.col - startNode.col);
		int yDistance = Math.abs(node.row - startNode.row);
		node.gCost = xDistance + yDistance;

		// Get H Cost (The distance from end node)
		xDistance = Math.abs(node.col - goalNode.col);
		yDistance = Math.abs(node.row - goalNode.row);
		node.hCost = xDistance + yDistance;

		// Get F Cost (H Cost + G Cost)
		node.fCost = node.gCost + node.hCost;

		// Display cost on main.java.Node
		if (node != startNode && node != goalNode) {
			node.setText("<HTML>F: " + node.fCost + "<br>G: " + node.gCost + "<br>H: " + node.hCost + "</html>");
		}
	}

	public void search() {

		if (!goalReached) {
			int col = currentNode.col;
			int row = currentNode.row;

			currentNode.setAsChecked();
			checkedList.add(currentNode);
			openList.remove(currentNode);

			// OPEN THE UP NODE
			if (row - 1 >= 0) {
				openNode(node[col][row - 1]);
			}

			// OPEN THE LEFT NODE
			if (col - 1 >= 0) {
				openNode(node[col - 1][row]);
			}
			// OPEN THE DOWN NODE
			if (row + 1 < ROWS) {
				openNode(node[col][row + 1]);
			}
			// OPEN THE RIGHT NODE
			if (col + 1 < COLUMNS) {
				openNode(node[col + 1][row]);
			}

			int bestNodeIndex = 0;
			int bestNodeCost = 999;

			for (int i = 0; i < openList.size(); i++) {

				// CHECK IF F COST IS BETTER
				if (openList.get(i).fCost < bestNodeCost) {
					bestNodeIndex = i;
					bestNodeCost = openList.get(i).fCost;
				}
				// If F cost is equal check g cost.
				else if (openList.get(i).fCost == bestNodeCost) {
					if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
						bestNodeIndex = i;
					}
				}
			}
			// After the loop grab the best node and make it our next step
			currentNode = openList.get(bestNodeIndex);
			
			if (currentNode == goalNode) {
				goalReached = true;
			}

		}
	}

	public void autoSearch() {

		while (!goalReached && step < 300) {
			int col = currentNode.col;
			int row = currentNode.row;

			currentNode.setAsChecked();
			checkedList.add(currentNode);
			openList.remove(currentNode);

			// OPEN THE UP NODE
			if (row - 1 >= 0) {
				openNode(node[col][row - 1]);
			}

			// OPEN THE LEFT NODE
			if (col - 1 >= 0) {
				openNode(node[col - 1][row]);
			}
			// OPEN THE DOWN NODE
			if (row + 1 < ROWS) {
				openNode(node[col][row + 1]);
			}
			// OPEN THE RIGHT NODE
			if (col + 1 < COLUMNS) {
				openNode(node[col + 1][row]);
			}

			int bestNodeIndex = 0;
			int bestNodeCost = 999;

			for (int i = 0; i < openList.size(); i++) {

				// CHECK IF F COST IS BETTER
				if (openList.get(i).fCost < bestNodeCost) {
					bestNodeIndex = i;
					bestNodeCost = openList.get(i).fCost;
				}
				// If F cost is equal check g cost.
				else if (openList.get(i).fCost == bestNodeCost) {
					if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
						bestNodeIndex = i;
					}
				}
			}
			// After the loop grab the best node and make it our next step
			currentNode = openList.get(bestNodeIndex);
			
			if (currentNode == goalNode) {
				goalReached = true;
				trackThepath();
			}
			step++;
		}
	}
	
	private void openNode(Node node) {
		// If node has not been opened yet add it to the open list
		if (!node.open && !node.checked && !node.solid) {
			node.setAsOpen();
			node.parent = currentNode;
			openList.add(node);
		}

	}
	
	private void trackThepath() {
		
		// backtrack and draw best path
		Node current = goalNode;
		
		while (current != startNode) {
			current = current.parent;
			
			if (current != startNode) {
				current.setAsPath();
			}
		}
		
		
	}
}
