/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.music;

import GraphicsLib.react.UC;
import java.util.ArrayList;

/**
 *
 * @author Lion's laptop
 */
public class Time {
    
    public int x;

    public Time(int x) {
        this.x = x;
    }
    
    public static class List extends ArrayList<Time> {
        // factory method. 
        public Time getTime(int x) {
            Time result = null;
            int bestDistance;
            if (this.size() == 0) {
               return new Time(x);
            } else {
                result = this.get(0);
                bestDistance = Math.abs(x - result.x);
                for (Time t: this) {
                    int d = Math.abs(t.x - x);
                    if (d < bestDistance) {
                        bestDistance = d;
                        result = t;
                    }
                    
                }
            }
            // see if the bestDistance is good enough.
            return (bestDistance < UC.timeThreshold) ? result : new Time(x);
            
        }
    }

}
