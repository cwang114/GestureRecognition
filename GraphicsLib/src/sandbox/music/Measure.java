/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.music;

import GraphicsLib.react.Mass;
import GraphicsLib.react.Reaction;
import GraphicsLib.react.Stroke;
import GraphicsLib.react.UC;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Lion's laptop
 */
public class Measure extends Mass {
    private int x;
    private Sys sys;
    
    
    private static int Reg = 1;
    private static int Two = 2;
    private static int Fine = 4;
    private static int RepeatLeft = 8;
    private static int RepeatRight = 16;
    
    private int style = Reg;
    
    
    public Measure(Sys sys, int x) {
        super(Music.BACK);
        this.sys = sys;
        this.x = x;
        
        add(new Reaction("S-S") {
            public int bid(Stroke s) {
                // how close the stroke to top and bottom line 
               int y2 = s.vs.my();
               int sX = s.vs.mx();
               if (Math.abs(x - sX) > 30) {
                   return UC.noBid;
               }
               if (y2 < sys.getY() || y2 > sys.getYBot()) {
                   return UC.noBid;
               }
               
               return Math.abs(x - sX);
            }
            public void act(Stroke s) {
                cycleStyle();
            }
        });
        
    }
    
    public void cycleStyle() {
        
        if (style <= Fine) {
            style *= 2;
            if (style == RepeatLeft) {
                style = Reg;
            }
            System.out.println("the style is "+ style);
        }        
    }

    @Override
    public void show(Graphics g) {
        Staff.List staffList = sys.staffs;
        g.setColor(Color.BLACK);
        int dy = sys.getDy();
        for (Staff s: staffList) {
            int y1 = s.y;
            int y2 = s.yBot();
            if (style <= Fine) {
                if (style == Reg) {
                    g.drawLine(x, y1, x, y2);
                } else if (style == Two) {
                    g.drawLine(x, y1, x, y2);
                    g.drawLine(x-dy, y1, x-dy, y2);
                } else if (style == Fine) {
                    g.drawLine(x-dy, y1, x-dy, y2);
                    g.fillRect(x, y1, dy, y2-y1);
                } 
            } else {
                // repeat left and right
            }
            
            
        }
    }
}
