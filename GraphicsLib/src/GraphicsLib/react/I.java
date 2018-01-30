/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphicsLib.react;

import java.awt.Graphics;

/**
 *
 * @author Lion's laptop
 */
public interface I {
    public static interface Act{
        public void act(Stroke s);
    }
    public static interface React extends Act{
        public int bid(Stroke s);
    }
    public static interface Show{
        public void show(Graphics g);
    }
    public static interface Hit{
        public boolean hit(int x, int y);
    }
    
}
