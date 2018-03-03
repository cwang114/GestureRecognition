/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.music;

import GraphicsLib.react.I;
import GraphicsLib.react.Mass;
import GraphicsLib.react.Reaction;
import GraphicsLib.react.Stroke;
import GraphicsLib.react.UC;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Lion's laptop
 */
public class Staff extends Mass {
    private int nLines = UC.defaultStaffLineCount;
    private int dy = 10;    // half spacing between those lines.
    private static int leftMargin = 100;
    private static int rightMargin = 900;
    
    private int y;
    
    
//    public static Reaction createStaff = new Reaction("E-E") {
//        @Override
//        public int bid (Stroke s) {
//            return 100;
//        }
//        
//        @Override
//        public void act(Stroke s) {
//            new Staff(s.vs.my());
//        }
//    };
//    // static block that runs when the class initializes.
//    static {Reaction.initialReactions.add(createStaff);}
    
    
    
    public Staff(int y) {
        super(Music.BACK);
        this.y = y; 
        // System.out.println("Staff= " + y);
    }

    @Override
    public void show(Graphics g) {
        showAt(g, y, nLines, dy);
        // System.out.println("show: "+ y);
    }
    
    public static void showAt(Graphics g, int y, int nLines, int dy) {
        g.setColor(Color.black);
        for (int i = 0; i < nLines; i++) {
            g.drawLine(leftMargin, y + 2 * i * dy, rightMargin, y + 2 * i * dy);    
        }
    }
    
    public static class List extends ArrayList<Staff> {

       
        
    }
    
    
}
