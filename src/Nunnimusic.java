
import java.util.Random;

public class Nunnimusic {
    public static void main(String[] args) {
        System.out.println("Hau hau vuh");
        
        int leveys=800, korkeus=600;
        
        Nunnigrafiikka grafiikka = new Nunnigrafiikka(leveys, korkeus);
        
        Random r = new Random();
        for( int i=0; i<1000; i++ ) {
            Pallura p = new Pallura();
            p.paikka = new Vektori(r.nextFloat()*leveys, r.nextFloat()*korkeus);
            grafiikka.lisaaPallura(p);
        }
    }
}
