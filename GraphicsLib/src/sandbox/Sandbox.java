/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox;

import GraphicsLib.G.VS;
import GraphicsLib.Window;
import GraphicsLib.react.Ink;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import GraphicsLib.react.Ink.Buffer;
import GraphicsLib.react.Ink.Norm;
import GraphicsLib.react.Reaction;
import GraphicsLib.react.Stroke;
import GraphicsLib.react.Stroke.Shape;
import GraphicsLib.react.Stroke.Shape.DB;


public class Sandbox extends Window{
    public static ArrayList<Ink> inkList = new ArrayList<>();
    public static Norm.List normList = new Norm.List();
    public static Norm lastNorm;
    // two buttons
    public static final int buttonWidth = 100;
    public static final int buttonHeight = 50;
    public static final int buttonMargin = 10;
    public static VS noButton = new VS(buttonMargin, buttonMargin, buttonWidth, buttonHeight);
    public static VS oopsButton = new VS(buttonMargin, 2*buttonMargin+buttonHeight, buttonWidth, buttonHeight);
    public static boolean buttonClicked;
    
    public static int numOfString = 100;
    public static String[] testStrings = {"N-N", "S-S", "N-E", "N-E", "N-E"};
    public static ArrayList<Circle> circles = new ArrayList<>();
    
/**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
      PANEL = new Sandbox();
      circles.add(new Circle(100, 200, 50));
      circles.add(new Circle(300, 100, 50));
      circles.add(new Circle(500, 500, 50));
      // Stroke.theShapeDB = DB.load();
      
      launch();
    }
    
    public Sandbox() {
      super("Sandbox", 1000, 800);
    }
    
    public void train(ArrayList<String> name) {
        // user draws stroke and defines the shape and its name.
        
    }
    
    @Override
    protected void paintComponent(Graphics g){
      // background
      g.setColor(Color.WHITE); g.fillRect(0, 0, 2000, 2000);     

      Ink.buffer.show(g);
      
      // show the circles
      for (Circle c : Circle.list) {
          c.show(g);
      }
    }
  
    @Override
    public void mousePressed(MouseEvent e){
      int x = e.getX();
      int y = e.getY();

        Ink.buffer.addFirst(e.getX(), e.getY());
    
        
    }
  
    @Override
    public void mouseDragged(MouseEvent e){
      Ink.buffer.addPoint(e.getX(), e.getY());
      Window.PANEL.repaint();
    }
  
    public void mouseReleased(MouseEvent e){

        Stroke s = new Stroke();
        Reaction r = Reaction.bestReaction(s);
        if (r != null) {
            r.act(s);
        }
        PANEL.repaint();
      
    }
  }
