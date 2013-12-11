
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Nunnimusic {
    public static void main(String[] args) {
        System.out.println("Hau hau vuh");
        
        Nunnigrafiikka grafiikka = new Nunnigrafiikka();

        synchronized (grafiikka) {
            char[] napit = { 'a', 'w', 's', 'e', 'd', 'f', 't', 'g', 'y', 'h', 'u', 'j' };
            for (int i = 0; i < napit.length; i++) {
                float vaakataso = -0.9f + i * (0.9f - -0.9f) / napit.length;
                grafiikka.lisaaSuihkutin(new Vektori(vaakataso, -1.5f), napit[i]);
            }
            
            Random r = new Random();
            for( int i = 0; i < 5000; i++ ) {
                Pallura p = new Pallura();
                p.paikka = new Vektori(-1.0f + 2.0f * r.nextFloat(), -1.0f + 2.0f * r.nextFloat() * r.nextFloat() * r.nextFloat());
                p.koko = 0.02f + r.nextFloat() * 0.03f;
                grafiikka.lisaaPallura(p);
            }
        }
    }
}
