/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphicsLib.react;

import GraphicsLib.react.Stroke.Shape;
import GraphicsLib.react.Stroke.Shape.DB;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Lion's laptop
 * 
 */

public abstract class Reaction implements I.React {

    public static Map<Shape, List> byShape = new HashMap<>();
    public String shapeName;
    // a list of reactions that blank screen can do.
    // only for the undo procedure.
    public static Reaction.List initialReactions = new Reaction.List();
    
    public Reaction(String shapeName) {
        this.shapeName = shapeName;
        
        Shape s = Stroke.theShapeDB.byName.get(shapeName);
        if (shapeName.equals("DOT")) {
            s = Shape.shapeDot;
        }
        System.out.println("Reaction() shape: " + s + " shape name: " + s.name);
        List list = byShape.get(s);
        if (list == null) {
            list = new List();
            byShape.put(s, list);
        }
        list.add(this);
    }
            
    public static void add(Reaction r) {
        Shape s = Stroke.theShapeDB.byName.get(r.shapeName);
        List list = byShape.get(s);
        if (list == null) {
            list = new List();
            byShape.put(s, list);
        }
        list.add(r);
    }
    
    public void removeReaction() {
        Shape s = Stroke.theShapeDB.byName.get(shapeName);
        List list = byShape.get(s);
        if (list != null) {
            list.remove(this);
        }
    }  
    
    public static void removeList(Mass mass) {
        for (Reaction r : mass) {
            r.removeReaction();
        }
    }
    
    public static void clearAll() {
        byShape.clear();
    }
    
    public static void addList(Reaction.List rl) {
        for (Reaction r : rl) {
            add(r);
        }
    }
    
    public static Reaction bestReaction(Stroke s) {
        System.out.print("bestReaction(): " + s.shape);
        List list = byShape.get(s.shape);
        if (list == null) {
            System.out.println("No reactions matched." + s.shape.name);
        } else {
            return list.bestReaction(s);
        }
        return null;
    }
    
    
            
    @Override
    public abstract int bid(Stroke s);

    @Override
    public abstract void act(Stroke s);
    
    public static class List extends ArrayList<Reaction> {
        
        public Reaction bestReaction(Stroke s) {
            Reaction res = null;
            int bestBid = UC.noBid;
            for (Reaction r : this) {
                int curBid = r.bid(s);
                if (curBid < bestBid) {
                    bestBid = curBid;
                    res = r;
                }
            }
            return res;
        }
        
     
    }
    
}
