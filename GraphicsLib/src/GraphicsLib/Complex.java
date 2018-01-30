/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphicsLib;

import static java.lang.Math.cos;
import static java.lang.Math.sin;



/**
 *
 * @author Lion's laptop
 */
public class Complex {
    public double real;
    public double imaginary;
    
    // constructor 1
    public Complex(double real, double imaginary){
        this.real = real;
        this.imaginary = imaginary;
    }
    //constructor 2
    public Complex (double theta){
        this.real = cos(theta);
        this.imaginary = sin(theta);
    }
    public Complex add(Complex a){
        return new Complex(real+a.real, imaginary+a.imaginary);
    }
    public Complex mul(Complex a){
        return new Complex((real*a.real-imaginary*a.imaginary), 
                (real*a.imaginary+a.real*imaginary));
    }

    @Override
    public String toString() {
        return "Complex{" + "real=" + real + ", imaginary=" + imaginary + '}';
    }
    
    
}
