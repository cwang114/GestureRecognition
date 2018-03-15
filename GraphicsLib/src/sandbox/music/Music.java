/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.music;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import GraphicsLib.G.VS;
import GraphicsLib.Window;
import static GraphicsLib.Window.PANEL;
import static GraphicsLib.Window.launch;
import GraphicsLib.react.Ink;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import GraphicsLib.react.Ink.Buffer;
import GraphicsLib.react.Ink.Norm;
import GraphicsLib.react.Mass;
import GraphicsLib.react.Mass.Layer;
import GraphicsLib.react.Reaction;
import GraphicsLib.react.Stroke;
import GraphicsLib.react.Stroke.Shape;
import GraphicsLib.react.Stroke.Shape.DB;
import sandbox.Circle;


public class Music extends Window {
    public static Layer BACK = new Layer();
    public static Layer NOTELAYER = new Layer();
    public static Layer sysDef = new Layer();
    public static Reaction initialStaff;
    
    
/**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
      PANEL = new Music();
      Reaction r = Sys.systemDefBack;
      // initialStaff = Staff.createStaff;
      launch();
    }
    
    public Music() {
      super("Music", 1000, 800);
    }

    
    @Override
    protected void paintComponent(Graphics g){
      // background
      g.setColor(Color.WHITE); g.fillRect(0, 0, 2000, 2000);     

      Ink.buffer.show(g);
     
      Layer.showAll(g);
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
//        Reaction r = Reaction.bestReaction(s);
//        if (r != null) {
//            r.act(s);
//        }
        if (s.doit()) {
            Ink.buffer.n = 0;
        }
        System.out.println("The name of current stroke is: " + s.shape.name);
        PANEL.repaint();
      
    }
  }

