/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tivi
 */
public class Vektori {
    public float x;
    public float y;

    Vektori(float x, float y) {
        this.x = x;
        this.y = y;
    }

    void lisaa(Vektori toinen) {
        x += toinen.x;
        y += toinen.y;
    }

    Vektori erotus(Vektori toinen) {
        return new Vektori(x - toinen.x, y - toinen.y);
    }
    
    float suuruus() {
        return (float) Math.sqrt(x * x + y * y);
    }
    
    float hervanta() {
        return Math.abs(x * 2) + Math.abs(y * y);
    }
}
