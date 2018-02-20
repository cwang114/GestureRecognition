/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphicsLib.react;

import GraphicsLib.G;
import GraphicsLib.Window;
import static GraphicsLib.Window.PANEL;
import static GraphicsLib.Window.launch;
import GraphicsLib.react.Ink.Norm;
import GraphicsLib.react.Stroke.Shape;
import GraphicsLib.react.Stroke.Shape.DB;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Scanner;
import sandbox.Sandbox;

/**
 *
 * @author Lion's laptop
 */
public class ShapeTrainer extends Window{
    
    // two buttons
    public static final int buttonWidth = 100;
    public static final int buttonHeight = 50;
    public static final int buttonMargin = 10;
    public static G.VS selectShapeButton = new G.VS(buttonMargin, buttonMargin, buttonWidth, buttonHeight);
    public static G.VS saveDBButton = new G.VS(buttonMargin, 2*buttonMargin+buttonHeight, buttonWidth, buttonHeight);
    public static boolean buttonClicked;
    public static String currentShape = "N-N"; // default shape
    public static boolean selectingShape = false;
    public static Scanner sc=new Scanner(System.in);
   
/**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
       Stroke.theShapeDB = DB.load();
       PANEL = new ShapeTrainer();      
      launch();
    }
    
    public ShapeTrainer() {
      super("ShapeTrainer", 1000, 800);
    }
    
    
    @Override
    protected void paintComponent(Graphics g){
      // background
      g.setColor(Color.WHITE); g.fillRect(0, 0, 2000, 2000);
      
//      g.setColor(Color.RED);
//      g.drawRect(10, 10, 100, 100);     
      
      // draw buttons
      selectShapeButton.showButton(g, Color.PINK, "Select Shape");
      saveDBButton.showButton(g, Color.LIGHT_GRAY, "Save DB");
    
      Ink.buffer.box.show(g);      
      if(Ink.buffer.n > 0) {
          Ink.buffer.show(g);
      }      

      // prompt 
      if (selectingShape) {
         g.drawString("Type in the name of a shape: ", 10, 200);
      } else {
         g.drawString("Please enter the" + currentShape, 10, 200); 
      }
      
      
      // fetch the shape out the database.
      Shape shape = Stroke.theShapeDB.byName.get(currentShape);
      if (shape == null) {
          shape = new Shape(currentShape);
      }
      
      // print the prototype list
      shape.prototypes.showList(g);
      
    }
  
    @Override
    public void mousePressed(MouseEvent e){
      int x = e.getX();
      int y = e.getY();
      if (selectShapeButton.hit(x, y)) {
          // if selectShapeButton is hit, prompt the user input the shape name.
          selectingShape = true;
          PANEL.repaint();
           
          System.out.println("Please enter the name of the shape.");  
          currentShape = sc.next(); 
          
          selectingShape = false;
          
          //System.out.println("hit no button");
          buttonClicked = true;
      } else if (saveDBButton.hit(x, y)) {
          DB.saveDB();
          System.out.println("db saved.");
          buttonClicked = true;
      } else {  

          // if button not clicked, start collecting ink.
          Ink.buffer.addFirst(e.getX(), e.getY());
          buttonClicked = false;
      }     
    
    }
  
    @Override
    public void mouseDragged(MouseEvent e){
      if (!buttonClicked) {
        Ink.buffer.addPoint(e.getX(), e.getY());
      }      
      Window.PANEL.repaint();
    }
  
    public void mouseReleased(MouseEvent e){
        if (!buttonClicked) {            
            Shape shape = Stroke.theShapeDB.byName.get(currentShape);
            if (shape == null) {
                shape = new Shape(currentShape);
            }
            System.out.println(shape.prototypes == null);
            shape.prototypes.addOrBlend(Norm.getNorm());
        } else {
            buttonClicked = false;
        }
        PANEL.repaint();      
    }
    
}
