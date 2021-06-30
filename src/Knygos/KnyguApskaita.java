/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab3Ordinaitė;
import laborai.studijosktu.BstSetKTU;
import laborai.studijosktu.SetADT;

/**
 *
 * @author rordi
 */
public class KnyguApskaita {
     public static SetADT<String> knyguAutoriai(Knyga[] knygos) {
        SetADT<Knyga> uni = new BstSetKTU<>(Knyga.pagalAutoriu);
        SetADT<String> kart = new BstSetKTU<>();
        for (Knyga a : knygos) {
            int sizeBefore = uni.size();
            uni.add(a);

            if (sizeBefore == uni.size()) {
                kart.add(a.ImtiAutoriu());
            }
        }
        return kart;
    }
     
    
  
}
