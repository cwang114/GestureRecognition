
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphicsLib;
  
  import java.awt.Color;
import java.awt.FontMetrics;
  import java.awt.Graphics;
import java.io.Serializable;
  import java.util.Random;  
  
/**
   *
   * @author Marlin
   */

/**
public class G{
  public static Random RND = new Random();

  public static class V{ // basically a 2D Vector
    public int x, y;

    public V(int x, int y){this.x = x; this.y = y;}
    public V(){this.x = 0; this.y = 0;}
    public V(V v){x = v.x; y = v.y;} // used to make copies

    public V set(int xx, int yy){x = xx; y = yy; return this;}
    public V set(V c){x = c.x; y = c.y; return this;} // copy values
    public V add(int dx, int dy){x += dx; y += dy; return this;}
    public V add(V c){x += c.x; y += c.y; return this;}
    public V newCopy(){return new V(x,y);}// creates a new V,copy of this
    public V constrain(BBox bb){x = bb.h.constrain(x); y = bb.v.constrain(y); return this;}
    public V randomize(BBox bb){x = bb.h.rnd(); y = bb.v.rnd(); return this;}
    public static V newRnd(VS vs){
      return new V(RND.nextInt(vs.size.x)+vs.loc.x, RND.nextInt(vs.size.y)+vs.loc.y);
    }
    public void bounce(BBox bb, V vel){ // bounces vel inside rect when at bdry
      if(bb.h.lo == x && vel.x <0){vel.x = - vel.x;}
      if(bb.h.hi == x && vel.x >0){vel.x = - vel.x;}
      if(bb.v.lo == y && vel.y <0){vel.y = - vel.y;}
      if(bb.v.hi == y && vel.y >0){vel.y = - vel.y;}
    }
    public V lineTo(Graphics g, V p){g.drawLine(x,y,p.x,p.y); return p;}
  }

  public static class VS{ // Vector + Size
    public V loc, size;

    public VS(V v, V s){loc = v; size = s;}
    public void draw(Graphics g, Color c){
      g.setColor(c); g.drawRect(loc.x, loc.y, size.x, size.y);
    }
    public VS newCopy(){return new VS(loc.newCopy(), size.newCopy());}
    public void fill(Graphics g, Color c){
      g.setColor(c); g.fillRect(loc.x, loc.y, size.x, size.y);
    }
    public boolean contains(int x, int y){
      return x>=loc.x && y>=loc.y && x<(loc.x+size.x) && y<(loc.y+size.y);
    }
    public VS inflate(int x){ // add x on all sides
      loc.add(-x,-x);
      size.add(x+x,x+x);
      return this;
    }
  }

  public static class LoHi{
    public int lo, hi, s, m; // size (never zero!) & midpoint

    public LoHi(int min, int max){this.lo = min; this.hi = max; sm();}
    public LoHi(int v){this.lo = v; this.hi = v; sm();}
    public LoHi(LoHi x){lo = x.lo; hi = x.hi; sm();} // clone a LoHi
    private void sm(){m = (hi+lo)/2; s = hi-lo; if(s==0){s=1;}}
    public int rnd(){return RND.nextInt(s) + lo;}
    public int constrain(int v){if(v<lo){return lo;}; return (v>hi)?hi:v;}
    public void set(int x){lo = x; hi = x; sm();}
    public void add(int v){if(lo>v){lo = v;} if(hi<v){hi = v;} sm(); }
    public String toString(){return "["+lo+".."+hi+"]";}
    public int fromNorm(int x, int ns){return x*s/ns + lo;} // ns is norm size
      // allows you to decode an x in ns coordinates into a bounding box range.
  }

  public static class BBox{
    public LoHi h, v; // horizontal and vertical ranges
    public BBox(int x, int y){h = new LoHi(x,x); v = new LoHi(y,y);}
    public BBox(int x1, int y1, int x2, int y2){h = new LoHi(x1,x2); v = new LoHi(y1,y2);}
    public BBox(BBox b){h = new LoHi(b.h); v = new LoHi(b.v);}
    public void show(Graphics g){g.drawRect(h.lo, v.lo, h.s, v.s);}
    public void show(Graphics g, Color c){g.setColor(c); this.show(g);}
    public void draw(Graphics g, Color c){this.show(g,c);}
    public void set(int x, int y){h.set(x); v.set(y);}
    public void add(int x, int y){h.add(x); v.add(y);}
    public void add(V vec){h.add(vec.x); v.add(vec.y);}
    public String toString(){return "H:"+ h + " V:" + v;}
  }

  public static class PL{ // polyline
    public V[] points;
    public PL(int count){
      points = new V[count]; 
      for(int i = 0; i<count; i++){points[i] = new V();}
    }
    public int size(){return points.length;}
    public void show(Graphics g, int n){ // allows me to show first n points of Ink.BUFFER
      for(int i = 1; i<n; i++){
        g.drawLine(points[i-1].x, points[i-1].y, points[i].x, points[i].y);
      }
    }
    public void show(Graphics g){show(g,points.length);} // shows whole thing

    public void showDots(Graphics g, int n){
      for(int i = 0; i<n; i++){
        g.fillOval(points[i].x, points[i].y, 13, 13);}
    }
    public void showDots(Graphics g){showDots(g, points.length);}
  }

}
 */ 
public class G{
    public static int dotSize = 4;
    public static Color dotColor = Color.RED;
    public static Color lineColor = Color.BLACK;
    
    public static Random RND = new Random(); // a shared random number generator
    public static class V implements Serializable {
      public int x,y;
      public V(int x, int y){this.x = x; this.y = y;}
      public V(){x=0;y=0;}
      public V(V v){x = v.x; y = v.y;}
      public void set(int x, int y){this.x = x; this.y = y;}
      public void set(V v){x = v.x; y = v.y;}
      public void show(Graphics g){
        int hd = dotSize/2, d = dotSize;
        g.setColor(dotColor); 
        g.drawOval(x-hd, y-hd, d, d);
      }
      public int dist(V v) {
          return (this.x-v.x) * (this.x-v.x) + (this.y-v.y) * (this.y-v.y);
      }
    }
      
    public static class VS{
      public V loc, size;
      public VS(int x, int y, int dx, int dy){
        loc = new V(x,y); size = new V(dx, dy);
      }
      public VS(BBox b){loc = new V(b.h.lo, b.v.lo); size = new V(b.h.s, b.v.s);}
      public void showButton(Graphics g, Color c, String text) {
          g.setColor(c);
          g.fillRect(loc.x, loc.y, size.x, size.y);
          FontMetrics fm = g.getFontMetrics();     
          int w = fm.stringWidth(text);     
          int a = fm.getAscent();     // the height above the base line
          int d = fm.getDescent();     // the height below the base line
          g.setColor(Color.BLACK);
          g.drawString(text, loc.x+(size.x-w)/2, a+loc.y+(size.y-(a+d))/2);          
      }
      public boolean hit(int x, int y) {
          return (loc.x <= x) && (loc.y <= y) && (x <= (loc.x+size.x)) && (y <= (loc.y+size.y));
      }
    }
  
    public static class LoHi{ 
      public int lo, hi, s, m; // size (never zero!) & midpoint
      private void sm(){m = (hi+lo)/2; s = hi-lo; if(s==0){s=1;}}
      public LoHi(int min, int max){this.lo = min; this.hi = max; sm();}
      public LoHi(int v){this.lo = v; this.hi = v; sm();}
      public LoHi(LoHi x){lo = x.lo; hi = x.hi; sm();} // clone a LoHi
      public void set(int x){lo = x; hi = x; sm();}
      public void add(int v){if(lo>v){lo = v;} if(hi<v){hi = v;} sm(); } 
      public String toString(){return "["+lo+".."+hi+"]";} 
    }
    
    public static class BBox{
      public LoHi h, v; // horizontal and vertical ranges
      public BBox(int x, int y){h = new LoHi(x,x); v = new LoHi(y,y);}
      public BBox(int x1, int y1, int x2, int y2){h = new LoHi(x1,x2); v = new LoHi(y1,y2);}
      public BBox(BBox b){h = new LoHi(b.h); v = new LoHi(b.v);}
      public void show(Graphics g){g.drawRect(h.lo, v.lo, h.s, v.s);}
      public void show(Graphics g, Color c){g.setColor(c); this.show(g);}
      public void draw(Graphics g, Color c){this.show(g,c);}
      public void set(int x, int y){h.set(x); v.set(y);}
      public void add(int x, int y){h.add(x); v.add(y);}
      public void add(V vec){h.add(vec.x); v.add(vec.y);}
      public String toString(){return "H:"+ h + " V:" + v;}
    }
    
    public static class PL implements Serializable {
      public V[] points;
      public PL(int n){points = new V[n]; for(int i=0;i<n;i++){points[i]=new V();}}
    
      public PL(int N, PL pl, int n){
        points = new V[N];
        int N1 = N-1; int n1 = n-1; // the last points in the two PLs
        // subsampling
        for(int i = 0; i<N; i++){
          points[i] = new V(pl.points[i*n1/N1]);
        }
      }
      public void show(Graphics g, int n){
        g.setColor(lineColor);
        for(int i = 1; i<n; i++){
          g.drawLine(points[i-1].x, points[i-1].y, points[i].x, points[i].y);
        }
      }
      public void show(Graphics g){show(g, points.length);}
      public void showDots(Graphics g, int n){
        for(int i = 0; i<n; i++){points[i].show(g);}            
      }
    }
  }