/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphicsLib.react;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Lion's laptop
 */
public abstract class Mass extends Reaction.List implements I.Show {
    // when deleting the mass, go to the layer.
    public Layer layer;
    public Mass (Layer layer) {
        this.layer = layer;
        layer.add(this);
    }
    
    // remove all the reactions corresponding to mass.
    public void removeFromLayers() {
        // remove the mass.
        layer.remove(this);
        // remove the reaction list 
        Reaction.removeList(this);
    }

    @Override
    public abstract void show(Graphics g);
    
    

    public static class Layer extends ArrayList<I.Show> implements I.Show {
        
        public static ArrayList<Layer> all = new ArrayList<>();

        public Layer() {
            // take the layer just created and add into arraylist
            all.add(this);
        }
        
        public static void showAll(Graphics g) {
            for (Layer l : all) {
                l.show(g);
            }
        }
        
        public static void clearAll(Graphics g) {
            for (Layer l : all) {
                l.clear();
            }
        }
        
        @Override
        public void show(Graphics g) {
            for (I.Show i : this) {
                i.show(g);
            }
        }
    }
    
    
    
}
