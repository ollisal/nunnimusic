
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLCapabilitiesImmutable;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;

public class Nunnigrafiikka extends Frame {
    public Nunnigrafiikka() {
        super("Nunnigrafiikka");

        // Luodaan mahdollisuudet kauniiden pallojen piirtoon
        GLProfile profiili = GLProfile.getDefault();
        GLCapabilities kyvyt = new GLCapabilities(profiili);
        GLCanvas piirtopinta = new GLCanvas((GLCapabilitiesImmutable) kyvyt);

        // Pistetään nunni ruudulle
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
    }
}