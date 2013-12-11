
import com.jogamp.common.nio.Buffers;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import javax.media.opengl.glu.gl2.GLUgl2;

public class Nunnigrafiikka extends Frame implements GLEventListener {
    private ArrayList<Pallura> pallurat = new ArrayList<>();
    void lisaaPallura( Pallura p ) {
        pallurat.add( p );
    }

    private static class Suihkutin {
        public Vektori paikka;
        public char nappi;
        public boolean painettu;
        
        Suihkutin(Vektori paikka, char nappi) {
            this.paikka = paikka;
            this.nappi = nappi;
            this.painettu = false;
        }
    }
    private ArrayList<Suihkutin> suihkimet = new ArrayList<>();
    
    void lisaaSuihkutin(Vektori paikka, final char nappi) {
        final Suihkutin s = new Suihkutin(paikka, nappi);
        suihkimet.add(s);
    
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == nappi)
                    s.painettu = true;
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyChar() == nappi)
                    s.painettu = false;
            }
        });
    }

    FPSAnimator animator = null;
    Nunnigrafiikka() {
        super("Nunni Music");

        GLProfile glp = GLProfile.getDefault();
        GLCapabilities kyvyt = new GLCapabilities(glp);
        GLCanvas piirtopinta = new GLCanvas(kyvyt);

        setSize(800, 600);
        add(piirtopinta);
        setVisible(true);

        // Tämä on tämmöistä "boilerplatea" että rastikin toimii
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        piirtopinta.addGLEventListener(this);

        animator = new FPSAnimator(piirtopinta, 60);
        animator.add(piirtopinta);
        animator.setUpdateFPSFrames(20, System.out);
        animator.start();
    }

    @Override
    public synchronized void display(GLAutoDrawable drawable) {
        for (Suihkutin s : suihkimet) {
            if (s.painettu) {
                for( Pallura p : pallurat ) {
                    float vaikutus = 1.0f / p.paikka.erotus(s.paikka).hervanta();
                    p.nopeus.y += 0.6f * vaikutus * vaikutus * p.koko * p.koko;
                }
            }
        }

        for( Pallura p : pallurat ) {
            p.liikuta();
        }
        render(drawable);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    static Texture kehitaPallo(GL gl) {
        int koko = 32;
        FloatBuffer puhveli = FloatBuffer.allocate(koko * koko * 3);

        for (int y = 0; y < koko; ++y) {
            for (int x = 0; x < koko; ++x) {
                float dx = (x - koko / 2) / (float) koko;
                float dy = (y - koko / 2) / (float) koko;

                puhveli.put((float) Math.max(0, 0.5f - Math.sqrt(dx * dx + dy * dy)));
                puhveli.put((float) Math.max(0, 0.5f - Math.sqrt(dx * dx + dy * dy)));
                puhveli.put((float) Math.max(0, 0.5f - Math.sqrt(dx * dx + dy * dy)));
            }
        }
        
        puhveli.rewind();

        TextureData data = new TextureData(
                GLProfile.getDefault(),
                GL2.GL_RGB, koko, koko, 0, GL2.GL_RGB, GL2.GL_FLOAT,
                true, false, false,
                puhveli,
                null);

        return new Texture(gl, data);
    }

    
    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        GLUgl2 glu = new GLUgl2();
        glu.gluOrtho2D(-1.0, 1.0, -1.0, 1.0);
        
        Texture pallo = kehitaPallo(gl);
        pallo.bind(gl);
        pallo.enable(gl);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
    }

    boolean ekaKerta = true;
    FloatBuffer paikkaPuhveli = null;
    FloatBuffer kuvankulmaPuhveli = null;
    int[] VBO = new int[ 2 ];
    private void render(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        if (paikkaPuhveli == null) {
             paikkaPuhveli = Buffers.newDirectFloatBuffer(pallurat.size() * 2 * 4);
             gl.glGenBuffers( 2, VBO, 0 );
        }

        if (kuvankulmaPuhveli == null)
            kuvankulmaPuhveli = Buffers.newDirectFloatBuffer(pallurat.size() * 2 * 4);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glBlendFunc(GL2.GL_ONE, GL2.GL_ONE);
        gl.glEnable(GL2.GL_BLEND);
        
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
    
        paikkaPuhveli.rewind();
        kuvankulmaPuhveli.rewind();
        for( Pallura p : pallurat ) {
            p.lisaaPaikat(paikkaPuhveli);
            p.lisaaKuvankulmat(kuvankulmaPuhveli);
        }
        paikkaPuhveli.rewind();
        kuvankulmaPuhveli.rewind();
        
        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, VBO[0]);
        gl.glBufferData(GL2.GL_ARRAY_BUFFER, pallurat.size() * 8 * 4, paikkaPuhveli, GL2.GL_STREAM_DRAW);
        gl.glVertexPointer(2, GL2.GL_FLOAT, 0, 0);
        gl.glEnableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, VBO[1]);
        gl.glBufferData(GL2.GL_ARRAY_BUFFER, pallurat.size() * 8 * 4, kuvankulmaPuhveli, GL2.GL_STREAM_DRAW);
        gl.glTexCoordPointer(2, GL2.GL_FLOAT, 0, 0);

        int kaari = 16;
        gl.glLoadIdentity();
        for (int i = 0; i < kaari; i++) {
            gl.glColor3f(
                    1f - (i * 2) / (float) kaari,
                    i < kaari / 2 ? (i * 2) / (float) kaari : 1f - ((i - (kaari / 2)) * 2) / (float) kaari,
                    (float) Math.max(0, -1. + (i * 2) / (float) kaari));
                    
            gl.glDrawArrays(GL2.GL_QUADS, 0, pallurat.size() * 4);
            gl.glTranslatef(0, 0.02f, 0);
            gl.glScalef(0.98f, 1.0f, 1.0f);
        }
    }

    public final float r = 0.17f;
    public final float g = 0.06f;
    public final float b = 0.08f;
}