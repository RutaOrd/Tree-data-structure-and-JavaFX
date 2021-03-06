/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab3Ordinaitė;
import laborai.gui.MyException;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

/**
 *
 * @author rordi
 */
public class KnygaGamyba {
    
     private static Knyga[] knygos;
    private static int pradinisIndeksas = 0, galinisIndeksas = 0;
    private static boolean arPradzia = true;

    public static Knyga[] generuoti(int kiekis) {
        knygos = new Knyga[kiekis];
        for (int i = 0; i < kiekis; i++) {
            knygos[i] = new Knyga.Builder().buildRandom();
        }
        return knygos;
    }
     

    public static Knyga[] generuotiIrIsmaisyti(int aibesDydis,
            double isbarstymoKoeficientas) throws MyException {
        return generuotiIrIsmaisyti(aibesDydis, aibesDydis, isbarstymoKoeficientas);
    }

    /**
     *
     * @param aibesDydis
     * @param aibesImtis
     * @param isbarstymoKoeficientas
     * @return Gražinamas aibesImtis ilgio masyvas
     * @throws MyException
     */
    public static Knyga[] generuotiIrIsmaisyti(int aibesDydis,
            int aibesImtis, double isbarstymoKoeficientas) throws MyException {
        knygos = generuoti(aibesDydis);
        return ismaisyti(knygos, aibesImtis, isbarstymoKoeficientas);
    }

    // Galima paduoti masyvą išmaišymui iš išorės
    public static Knyga[] ismaisyti(Knyga[] knyguBaze,
            int kiekis, double isbarstKoef) throws MyException {
        if (knyguBaze == null) {
            throw new IllegalArgumentException("AutoBaze yra null");
        }
        if (kiekis <= 0) {
            throw new MyException(String.valueOf(kiekis), 1);
        }
        if (knyguBaze.length < kiekis) {
            throw new MyException(knyguBaze.length + " >= " + kiekis, 2);
        }
        if ((isbarstKoef < 0) || (isbarstKoef > 1)) {
            throw new MyException(String.valueOf(isbarstKoef), 3);
        }

        int likusiuKiekis = knyguBaze.length - kiekis;
        int pradziosIndeksas = (int) (likusiuKiekis * isbarstKoef / 2);

        Knyga[] pradineAutomobiliuImtis = Arrays.copyOfRange(knyguBaze, pradziosIndeksas, pradziosIndeksas + kiekis);
        Knyga[] likusiAutomobiliuImtis = Stream
                .concat(Arrays.stream(Arrays.copyOfRange(knyguBaze, 0, pradziosIndeksas)),
                        Arrays.stream(Arrays.copyOfRange(knyguBaze, pradziosIndeksas + kiekis, knyguBaze.length)))
                .toArray(Knyga[]::new);

        Collections.shuffle(Arrays.asList(pradineAutomobiliuImtis)
                .subList(0, (int) (pradineAutomobiliuImtis.length * isbarstKoef)));
        Collections.shuffle(Arrays.asList(likusiAutomobiliuImtis)
                .subList(0, (int) (likusiAutomobiliuImtis.length * isbarstKoef)));

        KnygaGamyba.pradinisIndeksas = 0;
        galinisIndeksas = likusiAutomobiliuImtis.length - 1;
        KnygaGamyba.knygos = likusiAutomobiliuImtis;
        return pradineAutomobiliuImtis;
    }

    public static Knyga gautiIsBazes() throws MyException {
        if ((galinisIndeksas - pradinisIndeksas) < 0) {
            throw new MyException(String.valueOf(galinisIndeksas - pradinisIndeksas), 4);
        }
        // Vieną kartą Automobilis imamas iš masyvo pradžios, kitą kartą - iš galo.     
        arPradzia = !arPradzia;
        return knygos[arPradzia ? pradinisIndeksas++ : galinisIndeksas--];
    }
   
    
}
