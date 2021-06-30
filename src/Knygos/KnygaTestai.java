/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab3Ordinaitė;
import laborai.studijosktu.Ks;
import laborai.studijosktu.AvlSetKTUx;
import laborai.studijosktu.SortedSetADTx;
import laborai.studijosktu.SetADT;
import laborai.studijosktu.BstSetKTUx;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;

/**
 *
 * @author rordi
 */
public class KnygaTestai {
    
    static Knyga[] knygos;
      
    static SortedSetADTx<Knyga> aSerija = new BstSetKTUx(new Knyga(), Knyga.pagalKainą);

    public static void main(String[] args) throws CloneNotSupportedException {
        Locale.setDefault(Locale.US); // Suvienodiname skaičių formatus
        aibėsTestas();}
        
    static SortedSetADTx generuotiAibe(int kiekis, int generN) {
    knygos = new Knyga[generN];
        for (int i = 0; i < generN; i++) {
            knygos[i] = new Knyga.Builder().buildRandom();
        }
        Collections.shuffle(Arrays.asList());
        aSerija.clear();
        for (int i = 0; i < kiekis; i++) {
            aSerija.add(knygos[i]);
        }
        return aSerija;
    }

    public static void aibėsTestas() throws CloneNotSupportedException {
        Knyga a1 = new Knyga("Maironis", "Kūriniai", "Eilėraščiai", 2002, 700, 20.5);
        Knyga a2 = new Knyga.Builder()
                .autorius("Maironis")
                .pavadinimas("Pavasaris")
                .zanras("Eilėraščiai")
                .isleidimoMetai(2000)
                .puslapiuSk(800)
                .kaina(35)
                .build();
        Knyga a3 = new Knyga.Builder().buildRandom() ;
        Knyga a4 = new Knyga("Antanas-Škėma", "Drobulė", "Romanas", 2005, 500, 15);
        Knyga a5 = new Knyga("Salomėja-Nėris", "Žiema", "Eilėraščiai", 2001, 800, 85);
        Knyga a6 = new Knyga("Jurgis-Savickis", "Vagis", "Romanas", 2015, 600, 60);
        Knyga a7 = new Knyga("Maironis", "Poezija", "Eilėraščiai", 2010, 400, 10);
        Knyga a8 = new Knyga("Antanas-Škėma", "Autobiografija", "Literatūra", 1997, 500, 40);
        Knyga a9 = new Knyga("Maironis", "Biografija", "Literatūra", 2000, 1000, 20);
        Knyga a10 = new Knyga ("Pranas", "Vagis", "Romanas", 2015, 600, 60);

        Knyga[] knyguMasyvas = {a9, a7, a8, a5, a1, a4};
        Knyga[] knyguMasyvas2 = {a9, a4, a10};
   
        Ks.oun("Knygų Aibė:");
        SortedSetADTx<Knyga> knyguAibe = new BstSetKTUx(new Knyga());
        

        for (Knyga a : knyguMasyvas) {
            knyguAibe.add(a);
            Ks.oun("Aibė papildoma: " + a + ". Jos dydis: " + knyguAibe.size());
        }

        Ks.oun("");
        Ks.oun(knyguAibe.toVisualizedString(""));

        SortedSetADTx<Knyga> knyguAibeKopija
                = (SortedSetADTx<Knyga>) knyguAibe.clone();

        knyguAibeKopija.add(a2);
        knyguAibeKopija.add(a6);
        knyguAibeKopija.add(a3);
        
        Ks.oun("Papildyta knygų aibės kopija:");
        Ks.oun(knyguAibeKopija.toVisualizedString(""));

        a9.setPuslapiuSk(100);

        Ks.oun("Originalas:");
        Ks.ounn(knyguAibe.toVisualizedString(""));

        Ks.oun("Ar elementai egzistuoja aibėje?");
        for (Knyga a : knyguMasyvas) {
            Ks.oun(a + ": " + knyguAibe.contains(a));
        }
        Ks.oun(a2 + ": " + knyguAibe.contains(a2));
        Ks.oun(a3 + ": " + knyguAibe.contains(a3));
        Ks.oun(a6 + ": " + knyguAibe.contains(a6));
        Ks.oun("");

        Ks.oun("Ar elementai egzistuoja aibės kopijoje?");
        for (Knyga a : knyguMasyvas ) {
            Ks.oun(a + ": " + knyguAibeKopija.contains(a));
        }
        Ks.oun(a2 + ": " + knyguAibeKopija.contains(a2));
        Ks.oun(a3 + ": " + knyguAibeKopija.contains(a3));
        Ks.oun(a6 + ": " + knyguAibeKopija.contains(a6));
        Ks.oun("");

        Ks.oun("Elementų šalinimas iš kopijos. Aibės dydis prieš šalinimą:  " + knyguAibeKopija.size());
        for (Knyga a : new Knyga[]{a2, a1, a9, a8, a5, a3, a4, a2, a7, a6, a7, a9}) {
            knyguAibeKopija.remove(a);
            Ks.oun("Iš knygų aibės kopijos pašalinama: " + a + ". Jos dydis: " + knyguAibeKopija.size());
        }
        Ks.oun("");

        Ks.oun("Knygų aibė su iteratoriumi:");
        Ks.oun("");
        for (Knyga a : knyguAibe) {
            Ks.oun(a);
        }
        Ks.oun("");
        Ks.oun("Knygų aibė AVL-medyje:");
        SortedSetADTx<Knyga> knyguAibe3 = new AvlSetKTUx(new Knyga());
        for (Knyga a : knyguMasyvas) {
            knyguAibe3.add(a);
        }
        Ks.ounn(knyguAibe3.toVisualizedString(""));

        Ks.oun("Knygų aibė su iteratoriumi:");
        Ks.oun("");
        for (Knyga a : knyguAibe3) {
            Ks.oun(a);
        }

        Ks.oun("");
        Ks.oun("Knygų aibė su atvirkštiniu iteratoriumi:");
        Ks.oun("");
        Iterator iter = knyguAibe3.descendingIterator();
        while (iter.hasNext()) {
            Ks.oun(iter.next());
        }

        Ks.oun("");
        Ks.oun("Knygų aibės toString() metodas:");
        Ks.ounn(knyguAibe3);

        // Išvalome ir suformuojame aibes skaitydami iš failo
        knyguAibe.clear();
        knyguAibe3.clear();

        Ks.oun("");
        Ks.oun("Knygų aibė DP-medyje:");
        knyguAibe.load("Duomenys\\knygos.txt");
        Ks.ounn(knyguAibe.toVisualizedString(""));
   

        Ks.oun("");
        Ks.oun("Knygų aibė AVL-medyje:");
        knyguAibe3.load("Duomenys\\knygos.txt");
        Ks.ounn(knyguAibe3.toVisualizedString(""));

     
    }
}
