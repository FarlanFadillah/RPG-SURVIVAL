package com.quadTree;

import java.awt.Rectangle;
import java.util.List;

import com.ai.AINode;
import com.id.ID;
import com.main.Game;
import com.obj.GameObject;

public class Quad {
    public Point topLeft;
    public Point botRight;
    QuadNode n;
    Quad topLeftTree;
    Quad topRightTree;
    Quad botLeftTree;
    Quad botRightTree;
    public Game game;
    

    // Default constructor
    public Quad() {
        topLeft = new Point(0, 0);
        botRight = new Point(0, 0);
        n = null;
        topLeftTree = null;
        topRightTree = null;
        botLeftTree = null;
        botRightTree = null;
    }
 
    // Parameterized constructor
    public Quad(Point topL, Point botR, Game game) {
        this.game = game;
        n = null;
        topLeftTree = null;
        topRightTree = null;
        botLeftTree = null;
        botRightTree = null;
        topLeft = topL;
        botRight = botR;
    }
    // Insert a node into the quadtree
    public void insert(QuadNode node, List<GameObject> objects, AINode[][] gameObject) {
        if (node == null)
        return;
        
        if (!inBoundary(node.pos))
        return;

        if(node.gameObject.getID() == ID.Entity && !objects.contains(node.gameObject)) {
        	objects.add(node.gameObject);
        	return;
        }
        
        if (Math.abs(topLeft.x - botRight.x) <= 1 && Math.abs(topLeft.y - botRight.y) <= 1) {
            // We are at a quad of unit area; cannot subdivide further
            if (n == null){
                n = node;
            }
            if(gameObject != null){
                gameObject[node.gameObject.x/64][node.gameObject.y/64] = new AINode(node.gameObject.x/64,node.gameObject.y/64);
                gameObject[node.gameObject.x/64][node.gameObject.y/64].solid = true;
            }
            return;
        }
 
        if ((topLeft.x + botRight.x) / 2 >= node.pos.x) {
            if ((topLeft.y + botRight.y) / 2 >= node.pos.y) {
                // Indicates topLeftTree
                if (topLeftTree == null)
                    topLeftTree = new Quad(new Point(topLeft.x, topLeft.y),new Point((topLeft.x + botRight.x) / 2, (topLeft.y + botRight.y) / 2), game);
                topLeftTree.insert(node, objects, gameObject);
            } else {
                // Indicates botLeftTree
                if (botLeftTree == null)
                botLeftTree = new Quad(
                    new Point(topLeft.x, (topLeft.y + botRight.y) / 2),
                    new Point((topLeft.x + botRight.x) / 2, botRight.y), game);
                    botLeftTree.insert(node, objects, gameObject);
                }
            } else {
                if ((topLeft.y + botRight.y) / 2 >= node.pos.y) {
                    // Indicates topRightTree
                    if (topRightTree == null)
                    topRightTree = new Quad(
                            new Point((topLeft.x + botRight.x) / 2, topLeft.y),
                            new Point(botRight.x, (topLeft.y + botRight.y) / 2), game);
                            topRightTree.insert(node, objects, gameObject);
                } else {
                        // Indicates botRightTree
                        if (botRightTree == null)
                        botRightTree = new Quad(
                            new Point((topLeft.x + botRight.x) / 2, (topLeft.y + botRight.y) / 2),
                            new Point(botRight.x, botRight.y), game);
                        botRightTree.insert(node, objects, gameObject);
                }
            }
    }
 
    // Search for a node in the quadtree
    public QuadNode search(Point p) {
        if (!inBoundary(p))
            return null;
 
        if (n != null)
            return n;
 
        if ((topLeft.x + botRight.x) / 2 >= p.x) {
            if ((topLeft.y + botRight.y) / 2 >= p.y) {
                // Indicates topLeftTree
                if (topLeftTree == null)
                    return null;
                return topLeftTree.search(p);
            } else {
                // Indicates botLeftTree
                if (botLeftTree == null)
                    return null;
                return botLeftTree.search(p);
            }
        } else {
            if ((topLeft.y + botRight.y) / 2 >= p.y) {
                // Indicates topRightTree
                if (topRightTree == null)
                    return null;
                return topRightTree.search(p);
            } else {
                // Indicates botRightTree
                if (botRightTree == null)
                    return null;
                return botRightTree.search(p);
            }
        }
    }
 
    // Check if a point is within the boundary of the quadtree
    public boolean inBoundary(Point p) {
        return (p.x >= topLeft.x && p.x <= botRight.x && p.y >= topLeft.y && p.y <= botRight.y);
    }

    public void tick(Rectangle rectangle, Quad quad) {
        // TODO Auto-generated method stub

        try {
            if(Math.abs(quad.topLeft.x - quad.botRight.x) <= 1 && Math.abs(quad.topLeft.y - quad.botRight.y) <= 1){
                if(rectangle.contains(new Rectangle(quad.n.pos.x, quad.n.pos.y, 1, 1))){
                    quad.n.gameObject.tick();
                }
            }else{
                if(rectangle.intersects(new Rectangle(quad.topLeft.x, quad.topLeft.y, (quad.topLeft.x+quad.botRight.x)/2, (quad.topLeft.y+quad.botRight.y)/2))){
                    tick(rectangle, quad.topLeftTree);
                }
                if(rectangle.intersects(new Rectangle((quad.topLeft.x+quad.botRight.x)/2, quad.topLeft.y, (quad.topLeft.x+quad.botRight.x)/2, (quad.topLeft.y+quad.botRight.y)/2))){
                    tick(rectangle, quad.topRightTree);
                }
                if(rectangle.intersects(new Rectangle(quad.topLeft.x, (quad.topLeft.y+quad.botRight.y)/2, (quad.topLeft.x+quad.botRight.x)/2, (quad.topLeft.y+quad.botRight.y)/2))){
                    tick(rectangle, quad.botLeftTree);
                }
                if(rectangle.intersects(new Rectangle((quad.topLeft.x+quad.botRight.x)/2, (quad.topLeft.y+quad.botRight.y)/2, (quad.topLeft.x+quad.botRight.x)/2, (quad.topLeft.y+quad.botRight.y)/2))){
                    tick(rectangle, quad.botRightTree);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    public List<GameObject> GetObject(Rectangle rectangle, Quad quad, List<GameObject> objects){
        try {
            if(Math.abs(quad.topLeft.x - quad.botRight.x) <= 1 && Math.abs(quad.topLeft.y - quad.botRight.y) <= 1){
                if(rectangle.contains(new Rectangle(quad.n.pos.x, quad.n.pos.y, 1, 1)) && quad.n.gameObject.getID() != ID.Entity){
                    objects.add(quad.n.gameObject);
                }
                
            }else{
                    if(rectangle.intersects(new Rectangle(quad.topLeft.x, quad.topLeft.y, (quad.topLeft.x+quad.botRight.x)/2, (quad.topLeft.y+quad.botRight.y)/2))){
                        GetObject(rectangle, quad.topLeftTree, objects);
                    }
                    if(rectangle.intersects(new Rectangle((quad.topLeft.x+quad.botRight.x)/2, quad.topLeft.y, (quad.topLeft.x+quad.botRight.x)/2, (quad.topLeft.y+quad.botRight.y)/2))){
                        GetObject(rectangle, quad.topRightTree, objects);
                    }
                    if(rectangle.intersects(new Rectangle(quad.topLeft.x, (quad.topLeft.y+quad.botRight.y)/2, (quad.topLeft.x+quad.botRight.x)/2, (quad.topLeft.y+quad.botRight.y)/2))){
                        GetObject(rectangle, quad.botLeftTree, objects);
                    }
                    if(rectangle.intersects(new Rectangle((quad.topLeft.x+quad.botRight.x)/2, (quad.topLeft.y+quad.botRight.y)/2, (quad.topLeft.x+quad.botRight.x)/2, (quad.topLeft.y+quad.botRight.y)/2))){
                        GetObject(rectangle, quad.botRightTree, objects);
                    }
            }
        } catch (Exception e) {
        }

        return objects;
    }
    
    //remove object
    public void remove(QuadNode node){
        node.gameObject = null;
        node = null;
    }
}