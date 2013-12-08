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
    
}
