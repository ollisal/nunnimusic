
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLProfile;

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
    public Vektori paikka = new Vektori(0, 0);
    public Vektori nopeus = new Vektori(0, 0);
    public float koko = 0.01f;

    public Pallura() {
    }

    void liikuta() {
        paikka.lisaa(nopeus);
        nopeus.x += 0.00001 - Math.random() * 0.00002;
        nopeus.y -= 0.0002 + Math.random() * 0.0002;
        nopeus.x *= 0.999;
        nopeus.y *= 0.999;
        if (paikka.y < -1.0f) {
            paikka.y = -1.0f + (float) Math.random() * 0.04f;
            nopeus.y *= -0.5;
        }
    }

    void piirra(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(paikka.x, paikka.y, 0);
        gl.glBegin(GL.GL_TRIANGLES);
        //gl.glColor3f(r, g, b);
        gl.glTexCoord2f(0.f, 1.0f);
        gl.glVertex2f(-koko, koko);
        gl.glTexCoord2f(1.f, 1.0f);
        gl.glVertex2f(koko, koko);
        gl.glTexCoord2f(1.f, .0f);
        gl.glVertex2f(koko, -koko);
        gl.glTexCoord2f(0.f, 1.0f);
        gl.glVertex2f(-koko, koko);
        gl.glTexCoord2f(1.f, .0f);
        gl.glVertex2f(koko, -koko);
        gl.glTexCoord2f(0.f, .0f);
        gl.glVertex2f(-koko, -koko);
        gl.glEnd();
        gl.glPopMatrix();
    }
    
    void lisaaPaikanNurkka(FloatBuffer puhveli, float eka, float toka) {
        puhveli.put(paikka.x + eka);
        puhveli.put(paikka.y + toka);
    }

    void lisaaPaikat(FloatBuffer puhveli)
    {
        lisaaPaikanNurkka(puhveli, -koko, -koko);
        lisaaPaikanNurkka(puhveli, koko, -koko);
        lisaaPaikanNurkka(puhveli, koko, koko);
        lisaaPaikanNurkka(puhveli, -koko, koko);
    }

    void lisaaKuvankulmat(FloatBuffer puhveli)
    {
        puhveli.put(0.0f);
        puhveli.put(0.0f);
        
        puhveli.put(1.0f);
        puhveli.put(0.0f);
        
        puhveli.put(1.0f);
        puhveli.put(1.0f);
        
        puhveli.put(0.0f);
        puhveli.put(1.0f);
    }
}
