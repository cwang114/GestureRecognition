/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphicsLib.react;

import GraphicsLib.G.VS;
import GraphicsLib.react.Ink.Norm;
import GraphicsLib.react.Stroke.Shape.DB;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Lion's laptop
 */
public class Stroke {
    
    // recognition process
    public static int lastStrokeDistance;
    public static Shape.DB theShapeDB = Shape.DB.load(); 
    public Shape shape;
    public VS vs = new VS(Ink.buffer.box); // convert the BBox in ink buffer into VS
    
    
    public Stroke() {
        Norm norm = Norm.getNorm();
        shape = theShapeDB.find(norm);
        
    }
    
    
    public static class Shape implements Serializable {
        public String name;
        public Norm.List prototypes;
        public static Shape shapeDot = new Shape();
        
        private Shape() {
            this.name = "DOT";
            prototypes = new Norm.List();
            prototypes.add(Norm.DOT);
                    
        }
        public Shape(String name) {
            this.name = name;
            prototypes = new Norm.List();
            theShapeDB.add(this);
        }
        
        
        // give the result of match of a norm comparing with norm list.
        private int bestMatch(Norm norm) {
            int bestSoFar = Integer.MAX_VALUE;
            if (prototypes.isEmpty()) {
                  return bestSoFar;
            }             
            for(Norm n : prototypes) {
                int d = n.distNorm(norm);
                if (d < bestSoFar) {
                    bestSoFar = d;
                }
            }
            return bestSoFar;
            
        }
        public static class DB extends ArrayList<Shape> implements Serializable {           
            
            public static String FNAME = "ShapeDB.dat";
            
            // find the Shape object by the name.            
            public Map<String, Shape> byName = new HashMap<>();
            
            // find in db which is the nearest neighbor
            public Shape find(Norm norm) {
                if (norm == Norm.DOT) {
                    return shapeDot;
                }
                int bestNumber = Integer.MAX_VALUE;
                Shape result = null;
                // go through every single shape and find the best match.
                for (Shape s : this) {
                    int bestMatchScore = s.bestMatch(norm);
                    if (bestMatchScore < bestNumber) {
                        bestNumber = bestMatchScore;
                        result = s;
                    }
                }
                lastStrokeDistance = bestNumber;
                return result;
            }

            @Override
            public boolean add(Shape shape) {
                super.add(shape);
                byName.put(shape.name, shape);
                return true;
            }
            
            public static DB load(){
                DB res;
                try {
                  FileInputStream fin = new FileInputStream(FNAME);
                  ObjectInputStream oin = new ObjectInputStream(fin);
                  res = (DB) oin.readObject();
                  oin.close();
                  fin.close();
                }catch(Exception e) {                  
                  System.out.println(e);
                  e.printStackTrace();
                  System.out.println("Couldn't find file "+ FNAME + " so used default values.");
                  res = new DB();
                }
                return res;
              }

              // here is the routine to write out a single person list to FNAME
              public static void saveDB(){
                try {
                  FileOutputStream fout =new FileOutputStream(FNAME);
                  ObjectOutputStream oout = new ObjectOutputStream(fout);
                  oout.writeObject(theShapeDB);
                  oout.close();
                  fout.close();
                  }catch(Exception e) {
                    System.out.println("WTF? Saving file: "+ FNAME);
                    e.printStackTrace();
                  }   

              }
        }

    }
}
