
import javax.media.opengl.GL;
import javax.media.opengl.GL2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tivi
 */
public class Pallura {
    public Vektori paikka = new Vektori(100, 100);
    public Vektori nopeus = new Vektori(0, 0);
    public float r = 0.7f;
    public float g = 0.1f;
    public float b = 0.3f;

    public Pallura() {
    }

    void liikuta() {
        paikka.lisaa(nopeus);
        nopeus.y -= 0.01;
        if (paikka.y < 0) {
            nopeus.y *= -1;
        }
    }

    void piirra(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(paikka.x, paikka.y, 0);
        gl.glBegin(GL.GL_TRIANGLES);
        gl.glColor3f(r, g, b);
        gl.glVertex2f(-10, 10);
        gl.glVertex2f(10, 10);
        gl.glVertex2f(10, -10);
        gl.glVertex2f(-10, 10);
        gl.glVertex2f(10, -10);
        gl.glVertex2f(-10, -10);
        gl.glEnd();
        gl.glPopMatrix();
    }
    
}
