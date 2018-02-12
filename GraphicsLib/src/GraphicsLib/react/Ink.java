/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphicsLib.react;
import GraphicsLib.G.BBox;
import GraphicsLib.G.LoHi;
import GraphicsLib.G.PL;
import GraphicsLib.G.V;
import GraphicsLib.G.VS;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Lion's laptop
 
*
public class Ink {
    public Norm norm;    
    public BBox box;    
    // a list of points with a number of UC.sizeOfInkBuffer
    public static Buffer buffer = new Buffer(); 
    
    public static class Buffer extends PL implements I.Show{
        public BBox box;
        public int n; // keeps track of the number of active in buffer array list.
        public Buffer(){
            super(UC.sizeOfInkBuffer);            
            box = new BBox(0,0);
        }
        public void firstPoint(int x, int y){
            // reset the BBox
            box.set(x, y);
            // set the x and y attribute for the first point in the buffer array list.
            this.points[0].set(x, y);
            // set n
            n = 1; // there is one point in the buffer.
        }
        // add an arbitrary point in the middle
        public void addPoint(int x, int y){
            if(n < UC.sizeOfInkBuffer-1){
                // put the point into the buffer
                this.points[n].set(x, y);
                // add the vector in the boundary box
                box.add(this.points[n]);
                n++;
            }          
            
        }
        
        // override the show routine in I.show
        @Override
        public void show(Graphics g){
            this.show(g, n);
        }
    }
    public static class Norm{
        
    }
}
*/
public class Ink implements I.Show{  
    public Norm norm; 
    public VS vs;
    public static final Buffer buffer = new Buffer();
    
    public Ink(){norm = new Norm(); vs = new VS(Ink.buffer.box);}
    
    public static class Norm extends PL implements Serializable {
      
      public int nBlend = 1; // the number of norms averaged
      private static final int N = UC.normSize;
      public static V temp = new V(), prev = new V();
      
      
      public Norm(){
        super(N, Ink.buffer, Ink.buffer.n);
        // horizontal size and vertical size
        
        LoHi h = Ink.buffer.box.h, v = Ink.buffer.box.v; // fetch out the two scales
        
        // scale factor: how big should I factor this ink?
        int s = (h.s > v.s)? h.s : v.s; // select the larger delta as a scale
        
        // Norming coordinates.
        for(int i = 0; i < N; i++){
          V p = points[i];
          p.set((p.x - h.m)*UC.normCoord/s,(p.y - v.m)*UC.normCoord/s);
        }
      }
      // blend norm into this.
      public void blend (Norm n) {
          for(int i = 0; i < N; i++) {
              // update a point with the averaged value of coordinates.
              int newX = (points[i].x * nBlend + n.points[i].x)/(nBlend+1);
              int newY = (points[i].y * nBlend + n.points[i].y)/(nBlend+1);
              points[i].set(newX, newY);              
          }
          nBlend += 1;
      }
      
      public static class List extends ArrayList<Norm> implements Serializable {
          public void addOrBlend(Norm n) {
              // choose add or blend based on the distance
              Norm best = bestMatch(n);
              
              if (best == null || best.distNorm(n) > UC.noMatch) {
                  
                  // add on another possibility into prototypes
                  add(n);
              } else {
                  best.blend(n);
              }
          }
          public void addDiff(Norm n) {
              int bestMatch = UC.noMatch;              
              // Go through all the existing elements in the list.
              for(Norm norm: this) {
                  if (norm.distNorm(n) < bestMatch) {
                      bestMatch = norm.distNorm(n);
                  }
              }
              if (bestMatch == UC.noMatch) {
                  this.add(n);
              }
          }
          // Show the norm list.
          public void showList(Graphics g) {
              int dx = 100;
              VS vs = new VS(100, 10, dx, dx);
              for (Norm norm: this) {
                  norm.showAt(g, vs);
                  g.drawString(""+norm.nBlend, vs.loc.x, vs.loc.y);
                  vs.loc.x += (dx+5);            
              }
          }
          public Norm bestMatch(Norm norm) {
              if (this.size() == 0) {
                  return null;
              } 
              Norm result = get(0);
              int bestSoFar = norm.distNorm(result);
              for(Norm n : this) {
                  int d = n.distNorm(norm);
                  if (d < bestSoFar) {
                      bestSoFar = d;
                      result = n;
                  }
              }
              return result;
          }
      }
      
        // Draw the normed dots on the screen.
        public void showDotsAt(Graphics g, VS vs){
          int s = (vs.size.x > vs.size.y) ? vs.size.x : vs.size.y;
          int mx = vs.loc.x + vs.size.x/2, my = vs.loc.y + vs.size.y/2;
          for(int i = 0; i<N; i++){
            V p = points[i];
            temp.set(p.x*s/UC.normCoord + mx, p.y*s/UC.normCoord + my);
            temp.show(g);
          }
        }
        // VS: vector and size. (V location, V size)
        public void showAt(Graphics g, VS vs){
          int s = (vs.size.x > vs.size.y) ? vs.size.x : vs.size.y;
          int mx = vs.loc.x + vs.size.x/2, my = vs.loc.y + vs.size.y/2;
          for(int i = 0; i<N; i++){
            V p = points[i];
            temp.set(p.x*s/UC.normCoord + mx, p.y*s/UC.normCoord + my);
            if(i>0){g.drawLine(prev.x, prev.y, temp.x, temp.y);}
            prev.set(temp);
          }
        }
      
        // Calculate the distance of two norms.
        public int distNorm(Norm norm) {
            int res = 0;
            for(int i = 0; i < N; i++) {
                res += this.points[i].dist(norm.points[i]);
            }
            return res;
        }
    }  
     
    public static class Buffer extends PL implements I.Show{ 
      public final BBox box = new BBox(0,0);
      public int n;
      private static final int N = UC.inkBufferSize;
      private Buffer(){super(N); n = 0;}
       
      @Override
      public void show(Graphics g) {this.show(g,n);}
  
      public void addFirst(int x, int y){
        points[0].x = x; points[0].y = y;
        n=1;
        box.set(x, y);
      }
  
      public void addPoint(int x, int y){
        if(n<N){
          points[n].x = x; points[n++].y = y;
          box.add(x, y);
        }
      }
    }
  
    @Override
    public void show(Graphics g) {norm.showAt(g, vs); }
  }