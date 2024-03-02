package com.ai;

public class AINode {
	
	AINode parent;
	public int col;
	public int row;
	int gCost;
	int hCost;
	int fCost;
	public boolean solid;
	public boolean open;
	public boolean checked;
	
	public AINode(int col, int row) {
		this.col = col;
		this.row = row;
	}

}
