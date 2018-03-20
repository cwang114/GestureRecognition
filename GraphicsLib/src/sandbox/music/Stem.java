/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.music;

import GraphicsLib.react.Mass;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Lion's laptop
 */
public class Stem extends Mass {
    private ArrayList<Note> note = new ArrayList<>();
    private boolean isUpStem = false;
    
    // keeps track of the notes
    // the type of stem depends on the x value of stroke and the time's y value.
    public Stem(int y1, int y2, Time time, int x) {
        super(Music.NOTELAYER);
        // find all notes in that particular time
        for (Note n: time.notes) {
            int y = n.getY();
            if (y <= y2 && y >= y1) {
                note.add(n);
                if (time.x < x) {
                    // should select upstem
                    n.setUpStem(this);
                    this.isUpStem = true;
                } else {
                    n.setDownStem(this);
                    this.isUpStem = false;
                }
            }
        }
    }

    
    @Override
    public void show(Graphics g) {
        int yLow = note.get(0).getY();
        int yHigh = note.get(0).getY();
        Time time = note.get(0).time;
        int x = time.x;
        int dy = note.get(0).staff.dy;
        for (Note n: note) {
            int y = n.getY();
            // update yLow and yHigh based on the y of current note.
            if (y < yLow) {
                yLow = y;
            }
            if (y > yHigh) {
                yHigh = y;
            }
        }
        g.setColor(Color.BLACK);
        if (isUpStem) {
            g.drawLine(x + 3*dy, yLow - 7*dy, x + 3* dy, yHigh);
        } else {
            g.drawLine(x, yLow, x, yHigh + 7 * dy);
        }
        
    }
    
}
