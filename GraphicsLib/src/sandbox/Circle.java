/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox;

import GraphicsLib.G.V;
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
public class Circle extends Mass {
    public V center;
    public int radius;
    // public static ArrayList<Circle> list = new ArrayList<>();
    public static Layer CIRCLE = new Layer();
    public static Layer SQUARE = new Layer();
    public boolean blue = false;
    public boolean square = false;
    
    public static Reaction createCircle = new Reaction("O") {
        @Override
        public int bid(Stroke s) {
            return 100;
        }

        @Override
        public void act(Stroke s) {
            // create a new circle with a radius of average of vs x and y.
            new Circle(s.vs.mx(), s.vs.my(), (s.vs.size.x + s.vs.size.y)/4, false);
        }
        
    };
    
    
    public static Reaction createSquare = new Reaction("S-E") {
        @Override
        public int bid(Stroke s) {
            return 100;
        }

        @Override
        public void act(Stroke s) {
            // create a new circle with a radius of average of vs x and y.
            new Circle(s.vs.mx(), s.vs.my(), (s.vs.size.x + s.vs.size.y)/4, true);
        }
        
    };
    
    
    public Circle(int x, int y, int r, boolean sq) {
        // put CIRCLE in layer
        super(sq ? SQUARE : CIRCLE);
        center = new V(x, y);
        radius = r;
        this.square = sq;
        
        // resize reaction added
        add(new Reaction("S-S"){
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

        });
        
        // delete raction added
        add(new Reaction("S-N"){
            @Override
            public int bid(Stroke s) {
                // use absolute distance to bid.
                int sx = s.vs.mx();
                int sy = s.vs.loc.y;
                int distance = Math.abs(sx - center.x)+Math.abs(sy - center.y);
                if (distance < 20) {
                    return distance;
                } else {
                    // should not do this reaction.
                    return UC.noBid;
                }

            }

            @Override
            public void act(Stroke s) {
                // this refers to a Reaction for the circle.
                Circle.this.removeFromLayers();
            }

        });
        
        // changeColor reaction added
        add(new Reaction("DOT"){
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

        });
    }

    @Override
    public void show(Graphics g) {
        g.setColor(blue ? Color.BLUE : Color.RED);
        if (square) {
            g.fillRect(center.x - radius, center.y - radius, 2 * radius, 2 * radius);
        } else {
            g.fillOval(center.x - radius, center.y - radius, 2 * radius, 2 * radius);
        }
        
    }
    
    
}
