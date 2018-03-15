/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.music;

import GraphicsLib.react.Mass;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Lion's laptop
 */
public class Note extends Mass {
    public int x;
    public int yIndex;   // indicates the index that the note live on the staff
    public Staff staff;
    
    public Note(Staff staff, int x, int y) {
        super(Music.NOTELAYER);
        this.x = x;
        this.staff = staff;
        this.yIndex = (y - staff.y + (staff.dy / 2))/ staff.dy;
        
    }
    
    public int getY() {
        return staff.y + yIndex * staff.dy;
    }

    @Override
    public void show(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval(x, getY() - staff.dy, 3 * staff.dy, 2 * staff.dy);
    }
    
    
    
}
