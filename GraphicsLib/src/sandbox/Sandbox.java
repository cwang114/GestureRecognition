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

/**
 *
 * @author Lion's laptop


 *Test the Ink class

public class SandBox extends Window{
    public static Ink ink = new Ink(); 
    public static final int numOfDots = 10;
    public static PL pl = new PL(numOfDots);
   
    public SandBox(){
        super("Ink", 1000, 1000);        
    }
     @Override
    protected void paintComponent(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 2000, 2000);
        g.setColor(Color.BLACK);
        // tell the ink buffer to show itself
        Ink.buffer.show(g);
        // show the boundary box
        Ink.buffer.box.show(g);
        // fill that PL
        // subsample that particular ink buffer
        subsample();
        //Ink.buffer.showDots(g, Ink.buffer.n);
        // show dots on PL
        pl.showDots(g, numOfDots);
        
    }
    
    public static void subsample(){
        // run through 0 - n in ink buffer and pull out numOfDots dots equally spaced
        for(int i = 0; i < numOfDots; i++){
            V v = new V(Ink.buffer.points[(Ink.buffer.n*i)/numOfDots]);
            
            // translate the boundary box of dots to the upleft side the bbox.
            v.x = v.x - Ink.buffer.box.h.lo; // removing the BBox bias
            v.y = v.y - Ink.buffer.box.v.lo;
            // scale down
            v.x = v.x*100/Ink.buffer.box.h.s;
            v.y = v.y*100/Ink.buffer.box.h.s;
            pl.points[i].set(v);
            
            
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e){
      // call the ink.buffer routine
      Ink.buffer.firstPoint(e.getX(), e.getY());
      PANEL.repaint();
    }
     
    @Override
    public void mouseDragged(MouseEvent e){
      Ink.buffer.addPoint(e.getX(), e.getY());
      PANEL.repaint();
      
    }
  
    public void mouseReleased(MouseEvent e){
      
    }
    
    public static void main(String[] args){
        PANEL = new SandBox();
        launch();
    }
    
}
*/
public class Sandbox extends Window{
    public static ArrayList<Ink> inkList = new ArrayList<>();
    public static Norm.List normList = new Norm.List();
    
/**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
      PANEL = new Sandbox();
      launch();
    }
    
    public Sandbox() {
      super("Sandbox", 1000, 800);
    }
    @Override
    protected void paintComponent(Graphics g){
      g.setColor(Color.WHITE); g.fillRect(0, 0, 2000, 2000);
      g.setColor(Color.RED);
      g.drawRect(10, 10, 100, 100);
      Ink.buffer.show(g);

// save all the ink traces.
//      for(Ink ink : inkList) {
//          ink.show(g);
//      }
      
      Ink.buffer.box.show(g);
      
      if(Ink.buffer.n > 0) {
          Norm norm = new Norm();
          VS vs = new VS(10, 10, 100, 100);
          norm.showAt(g, vs);
      }
      
      normList.showList(g);
    }
  
    @Override
    public void mousePressed(MouseEvent e){
      Ink.buffer.addFirst(e.getX(), e.getY());
    }
  
    @Override
    public void mouseDragged(MouseEvent e){
      Ink.buffer.addPoint(e.getX(), e.getY());
      Window.PANEL.repaint();
    }
  
    public void mouseReleased(MouseEvent e){
      inkList.add(new Ink());
      System.out.println("The number of points is: " + Ink.buffer.n);
      if (inkList.size() > 1) {
          // fetch the last two emelents out.
          Ink ink1 = inkList.get(inkList.size() - 1);
          Ink ink2 = inkList.get(inkList.size() - 2);
          System.out.println("The distance is: " + (ink1.norm.distNorm(ink2.norm))/1000000);
      } else {
          System.out.println("The inklist is empty.");
      }
      
      Norm n = new Norm();
      normList.addDiff(n);
      
    }
  }
