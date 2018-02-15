/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox;

import GraphicsLib.G.V;
import GraphicsLib.react.I;
import GraphicsLib.react.Reaction;
import GraphicsLib.react.Stroke;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Lion's laptop
 */
public class Circle implements I.Show {
    public V center;
    public int radius;
    public static ArrayList<Circle> list = new ArrayList<>();
    public boolean blue = false;
    
    public Reaction createCircle = new Reaction("O") {
        @Override
        public int bid(Stroke s) {
            return 100;
        }

        @Override
        public void act(Stroke s) {
            // create a new circle with a radius of average of vs x and y.
            new Circle(s.vs.mx(), s.vs.my(), (s.vs.size.x + s.vs.size.y)/4);
        }
        
    };
    
    public Reaction resize = new Reaction("S-S"){
        @Override
        public int bid(Stroke s) {
            // use absolute distance to bid.
            return Math.abs(s.vs.loc.x - center.x);
        }

        @Override
        public void act(Stroke s) {
            // update radius of the nearest circle.
            radius = Math.abs(s.vs.loc.x - center.x);
            // change radius in individual object.
        }
        
    };
    
    public Reaction delete = new Reaction("S-N"){
        @Override
        public int bid(Stroke s) {
            // use absolute distance to bid.
            int sx = s.vs.mx();
            int sy = s.vs.loc.y;
            int distance = Math.abs(sx - center.x)+Math.abs(sy - center.y);
            return distance;
        }

        @Override
        public void act(Stroke s) {
            Circle.list.remove(Circle.this);
            // wipe out the reactions related to the circle.
            // find the list of reactions by byshape.get(shape);
            delete.removeReaction();
            resize.removeReaction();
            changeColor.removeReaction();
        }
        
    };
    
    public Reaction changeColor = new Reaction("DOT"){
        @Override
        public int bid(Stroke s) {
            // use absolute distance to bid.
            int sx = s.vs.mx();
            int sy = s.vs.loc.y;
            int distance = Math.abs(sx - center.x)+Math.abs(sy - center.y);
            return distance;
        }

        @Override
        public void act(Stroke s) {
            blue ^= true;
            
        }
        
    };
    
    public Circle(int x, int y, int r) {
        center = new V(x, y);
        radius = r;
        list.add(this);
    }

    @Override
    public void show(Graphics g) {
        g.setColor(blue ? Color.blue : Color.red);
        g.fillOval(center.x - radius, center.y - radius, 2 * radius, 2 * radius);
    }
    
    
}
