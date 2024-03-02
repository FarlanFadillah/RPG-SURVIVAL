package com.ai;


import java.util.ArrayList;

import com.main.Game;
import com.obj.Entity;

public class Pathfinder {
	
	Game game;
	AINode[][] AINode;
	ArrayList<AINode> openList = new ArrayList<>();
	public ArrayList<AINode> pathList = new ArrayList<>();
	AINode startAINode, goalAINode, currentAINode;
	boolean goalReached = false;
	int step = 0;
	
	public Pathfinder(Game game) {
		
		this.game = game;
		instantiateAINodes();
	}

	public void instantiateAINodes() {
		
		AINode = new AINode[game.tryWorld.tilem.WIDTHMAP][game.tryWorld.tilem.HEIGHTMAP];
		
		int col = 0;
		int row = 0;
		
		while(col < game.tryWorld.tilem.WIDTHMAP && row < game.tryWorld.tilem.HEIGHTMAP) {
			
			AINode[col][row] = new AINode(col, row);
			
			col++;
			if(col == game.tryWorld.tilem.WIDTHMAP) {
				col = 0;
				row++;
			}
		}
		
	}
	
	public void resetAINodes() {
		
		int col = 0;
		int row = 0;
		
		while(col < game.tryWorld.tilem.WIDTHMAP && row < game.tryWorld.tilem.HEIGHTMAP) {
			// Reset open, checked and solid state
			AINode[col][row].open = false;
			AINode[col][row].checked = false;
			AINode[col][row].solid = false;
			
			col++;
			if(col == game.tryWorld.tilem.WIDTHMAP) {
				col = 0;
				row++;
			}
		}
		
		// Reset other settings
		openList.clear();
		pathList.clear();
		goalReached = false;
		step = 0;
	}
	
	public void setAINodes(int startCol, int startRow, int goalCol, int goalRow, Entity entity) {
		
		resetAINodes();
		
		// Set Start and Goal Node
		startAINode = AINode[startCol][startRow];
		currentAINode = startAINode;
		goalAINode = AINode[goalCol][goalRow];
		openList.add(currentAINode);
		
		int col = 0;
		int row = 0;
		
		while(col < game.tryWorld.tilem.WIDTHMAP && row < game.tryWorld.tilem.HEIGHTMAP) {
			
			// Set Solid Node
			// Check Tiles
			//if(game.tryWorld.AINode[col][row].solid == true) {
			//	AINode[col][row].solid = true;
			//}
			
			// Set Cost
			getCost(AINode[col][row]);
			
			col++;
			if(col == game.tryWorld.tilem.WIDTHMAP) {
				col = 0;
				row++;
			}
		}
		
	}

	public void getCost(AINode AINode) {
		
		// g Cost
		int xDistance = Math.abs(AINode.col = startAINode.col);
		int yDistance = Math.abs(AINode.row = startAINode.row);
		AINode.gCost = xDistance + yDistance;
		// h Cost
		xDistance = Math.abs(AINode.col = startAINode.col);
		yDistance = Math.abs(AINode.row = startAINode.row);
		AINode.hCost = xDistance + yDistance;
		// f Cost
		AINode.fCost = AINode.gCost + AINode.hCost;
	}
	
	public boolean search() {
		
		while(goalReached == false && step < 500) {
			System.out.println(openList.size());
			int col = currentAINode.col;
			int row = currentAINode.row;
			
			//Check the current node
			currentAINode.checked = true;
			openList.remove(currentAINode);
			
			// Open the Up node
			if(row - 1 >= 0) {
				openAINode(AINode[col][row-1]);
			}
			// Open the Up node
			if(row - 1 >= 0) {
				openAINode(AINode[col][row-1]);
			}
			// Open the Up node
			if(row - 1 >= 0) {
				openAINode(AINode[col][row-1]);
			}
			
			// Find the best node
			int bestNodeIndex = 0;
			int bestNodefCost = 999;
			
			for(int i = 0; i < openList.size(); i++) {
				
				// Check if this node's F cost is better
				if(openList.get(i).fCost < bestNodefCost) {
					bestNodeIndex = i;
					bestNodefCost = openList.get(i).fCost;
					
				}
				
				// if F cost is equal, check the G cost
				else if(openList.get(i).fCost == bestNodefCost) {
					if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
						bestNodeIndex = i;
					}
				}
			}
			
			// if there is no node in the openList, end the loop
			if(openList.size() == 0) {
				break;
			}
			
			//after the loop, openList[bestNodeIndex] is the next step (= currentNode)
			currentAINode = openList.get(bestNodeIndex);
			
			if(currentAINode == goalAINode) {
				goalReached = true;
				trackThePath();
			}
			step++;	
		}
		
		return goalReached;
	}

	public void openAINode(AINode AINode) {
		
		if(AINode.open == false && AINode.checked == false && AINode.solid == false) {
			
			AINode.open = true;
			
		}
	}
	
	public void trackThePath() {
		
		AINode current = goalAINode;
		
		while(current != startAINode) {
			
			pathList.add(0,current);
			current = current.parent;
		}
		
	}
}
