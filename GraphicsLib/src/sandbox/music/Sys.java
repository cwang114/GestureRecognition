/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.music;

import GraphicsLib.react.Mass;
import GraphicsLib.react.Reaction;
import GraphicsLib.react.Stroke;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.Graphics;

/**
 *
 * @author Lion's laptop
 */
public class Sys {
    Staff.List staffs = new Staff.List();
    Time.List times = new Time.List();
    public static Sys.Def systemDef = new Def();
    public static Reaction systemDefBack = new Reaction("E-W") {
        public int bid(Stroke s) {
            return 100;
        }
        public void act(Stroke s) {
            systemDef.addToLayers();
        }
    };
    public static Reaction createSystem = new Reaction("E-E") {
        public int bid(Stroke s) {
            return 100;
        }
        public void act(Stroke s) {
            new Sys(s.vs.my());
        }
          
    };
    static {
        Reaction.initialReactions.add(systemDefBack);
        Reaction.initialReactions.add(createSystem);
    }
    
    public Sys(int y) {
        if (systemDef.yStaff.size() == 0) {
            return;
        }
        int biasFromTop = systemDef.yStaff.get(0);
        for (Integer sy: systemDef.yStaff) {
            // System.out.println("The y coord is " + (sy+y));
            new Staff(sy+y-biasFromTop);
            
        }
    }
    
    
    public static class Def extends Mass {
        // list of y coords for each staff
        public ArrayList<Integer> yStaff = new ArrayList<>();

        public Def() {
            super(Music.sysDef);
            // add new reactions
            add(new Reaction("E-E") {
                public int bid(Stroke s) {
                    return 10;
                }
                public void act(Stroke s) {
                    yStaff.add(s.vs.my());
                }
            });
            
            add(new Reaction("DOT") {
                public int bid(Stroke s) {
                    return 10;
                }
                public void act(Stroke s) {
                    Def.this.removeFromLayers();
                }
            });
        }
        
        @Override
        public void show(Graphics g) {
            g.setColor(Color.PINK);
            g.fillRect(0, 0, 2000, 2000);
            g.setColor(Color.black);
            g.drawString("Define your systems.", 120, 20);
            
            for (Integer y: yStaff) {
                Staff.showAt(g, y, 5, 10);
            }
        }
        
    }
    
}
