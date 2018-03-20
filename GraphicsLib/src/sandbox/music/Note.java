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
public class Note extends Mass {
    public Time time;
    public int yIndex;   // indicates the index that the note live on the staff
    public Staff staff;
    public Stem upStem = null;
    public Stem downStem = null;
    
    public Note(Staff staff, int x, int y) {
        super(Music.NOTELAYER);
        this.time = staff.sys.times.getTime(x);
        // let the new note join the time.
        time.notes.add(this);
        this.staff = staff;
        this.yIndex = (y - staff.y + (staff.dy / 2))/ staff.dy;
        add(new Reaction("S-S") {
            @Override
            public int bid(Stroke s) {
                int y1 = s.vs.loc.y;
                int y2 = s.vs.by();
                int sx = s.vs.mx();
                if (!(getY() > y1 && getY() < y2)) {
                    return UC.noBid;
                } 
                if ((time.x - staff.dy > sx) || (sx > time.x + 4 * staff.dy)) {
                    return UC.noBid;
                }
                // the bid should win.
                return 10;
            }

            @Override
            public void act(Stroke s) {
                new Stem(s.vs.loc.y, s.vs.by(), time, s.vs.mx());
            }
            
        });
        
    }
    
    public int getY() {
        return staff.y + yIndex * staff.dy;
    }
    
    public void setUpStem(Stem s) {
        this.upStem = s;
    }
    
    public void setDownStem(Stem s) {
        this.downStem = s;
    }

    @Override
    public void show(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval(time.x, getY() - staff.dy, 3 * staff.dy, 2 * staff.dy);
    }
    
    
    
}
