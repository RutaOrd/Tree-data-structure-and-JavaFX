/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab3Ordinaitė;
import laborai.studijosktu.KTUable;
import laborai.studijosktu.Ks;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author rutord
 */
//padaryti papildomą rikiavimą pagal du kriteriijus
public class Knyga implements KTUable<Knyga>{
    
    final static private int priimtinųMetųRiba = 1980;
    final static private int esamiMetai = LocalDate.now().getYear();
    private static final String idCode = "BA";  
    private static int serNr = 0;
    private final String RegNr;


    final static private double valKursas = esamiMetai <= 2014 ? 1 : 1 / 3.4528;
    final static private double minKaina = 10.0;
    final static private double maxKaina = 200.0;
    

    private String autorius;
    private String pavadinimas;
    private String zanras;
    private int isleidimoMetai;
    private int puslapiuSk;
    private double kaina;
    
    public Knyga()
    {
         RegNr= idCode + (serNr++);
    }
            
    public Knyga( String autorius, String pavadinimas, String zanras, 
            int isleidimoMetai , int puslapiuSk, double kaina)
    {
    RegNr= idCode +(serNr++);
    this.autorius=autorius;
    this.pavadinimas=pavadinimas;
    this.zanras=zanras;
    this.isleidimoMetai=isleidimoMetai;
    this.puslapiuSk=puslapiuSk;
    this.kaina=kaina;
   
    }
    public Knyga(Builder builder)
    {
    RegNr=idCode +(serNr++);   
    this.autorius=builder.autorius;
    this.pavadinimas=builder.pavadinimas;
    this.zanras=builder.zanras;
    this.isleidimoMetai=builder.isleidimoMetai;
    this.puslapiuSk=builder.puslapiuSk;
    this.kaina=builder.kaina; 
 
    
    }
            
    
     @Override
    public String toString()
    {
   return ImtiRegNr() + "=" + autorius + " " + pavadinimas + " " + zanras
                + " " + isleidimoMetai + " " + ImtiPuslapius() + " " + 
           String.format("%4.1f", kaina);
    
    }
   
    public Knyga(String dataString)
    {
       RegNr= idCode + (serNr++);
       this.parse(dataString);
       
    }
    
    @Override
    public Knyga create (String dataString)
    {
       return new Knyga(dataString);
    }
    
    

  @Override
    public String validate() {
       String klaidosTipas = "";
	if (isleidimoMetai < priimtinųMetųRiba || isleidimoMetai > esamiMetai) {
		klaidosTipas = "Netinkami išleidimo metai, turi būti ["
				+ priimtinųMetųRiba + ":" + esamiMetai + "]";
		}
	if (kaina < minKaina || kaina > maxKaina) {
		klaidosTipas += " Kaina už leistinų ribų [" + minKaina
				+ ":" + maxKaina + "]";
	}
	return klaidosTipas;
    }

    @Override
    public final void parse(String dataString) {
     
        try {   
			Scanner ed = new Scanner(dataString); 

			autorius = ed.next();
                        pavadinimas = ed.next();
			zanras = ed.next();
			isleidimoMetai = ed.nextInt();
                        puslapiuSk = ed.nextInt();
                        kaina = ed.nextDouble();
                        
			
		} catch (InputMismatchException e) {
			Ks.ern("Blogas duomenų formatas -> " + dataString);
		} catch (NoSuchElementException e) {
			Ks.ern("Trūksta duomenų apie knygas -> " + dataString);
		}
    }
   
    public String ImtiAutoriu()
    {return autorius; }
    public String ImtiPavadinima()
    {return pavadinimas;}
    public String ImtiZanra()
    {return zanras;}
    public int ImtiMetus()
    {return isleidimoMetai;}
    public int ImtiPuslapius()
    {return puslapiuSk; }
    public void setPuslapiuSk(int puslapiuSk)
    { 
        this.puslapiuSk=puslapiuSk;
    }
    public double ImtiKaina()
    {return kaina;}
    
     public String ImtiRegNr() {  
        return RegNr;
    }


      @Override
    public int compareTo(Knyga a)
    {
		return ImtiRegNr().compareTo(a.ImtiRegNr());
    }
//    Pagal pavadinima
    public final static Comparator<Knyga> pagalPavadinima = new Comparator<Knyga>() {
		@Override
		public int compare(Knyga k1, Knyga k2) {
			int cmp = k1.ImtiPavadinima().compareTo(k2.ImtiPavadinima());
			return cmp;
		}
	};
    public static Comparator<Knyga> pagalKainaIrMetus = (Knyga k1, Knyga k2) ->{
                        
          if(k1.ImtiKaina()> k2.ImtiKaina())
              return 1;
          if(k1.ImtiKaina()< k2.ImtiKaina()) 
              return -1;
          if(k1.ImtiMetus() > k2.ImtiMetus()) 
              return 1;
          if(k1.ImtiMetus() < k2.ImtiMetus()) 
              return -1;
          return 0;
		
	};
//    Pagal kainą nuo mažiausio
    public final static Comparator<Knyga> pagalKainą = new Comparator<Knyga>() {
		@Override
		public int compare(Knyga kn1, Knyga kn2) {
			double k1 = kn1.ImtiKaina();
			double k2 = kn2.ImtiKaina();
			
			if (k1 < k2) {
				return -1;
			}
			if (k1 > k2) {
				return 1;
			}
			return 0;
		}
	};

    //Pagal puslapių sk. nuo mažiausiai
   public final static Comparator<Knyga> pagalPuslapiųSk = new Comparator<Knyga>() {
		@Override
		public int compare(Knyga kn1, Knyga kn2) {
			int k1 = kn1.ImtiPuslapius();
			int k2 = kn2.ImtiPuslapius();
			
			if (k1 < k2) {
				return -1;
			}
			if (k1 > k2) {
				return 1;
			}
			return 0;
		}
	};
    public static Comparator<Knyga> pagalAutoriu = (Knyga k1, Knyga k2) -> 
            k1.autorius.compareTo(k2.autorius); // pradžioje pagal autorius, o po to pagal pav.

    public static class Builder {

        private final static Random RANDOM = new Random(1949);  // Atsitiktinių generatorius
        private final static String[][] KNYGOS = { // galimų knygų masyvas
            
            {"Kazys", "Raudona", "Juoda", "Balta", "Eilėraščiai"},
            {"Maironis", "Eilėraščiai1", "Eilėraščiai2", "Bio", "Bio2", "Miškas"},
            {"Antanas-Škėma", "Drobulė", "Biografija", "Autobiografija"},
            {"Sigitas-Geda", "Lapai", "Kūriniai", "Bio", "Pasakos"},
            {"Stephanie-Meyer", "Saulėlydis", "Jaunatis", "Užtemimas"}
    
        };
        private final static String[] ZANRAS ={"Skaitiniai", "Literatūra"};

        private String autorius = "";
        private String pavadinimas = "";
        private String zanras = "";
        private int isleidimoMetai = -1;
        private int puslapiuSk = -1;
        private double kaina = -1.0;

        public Knyga build() {
            return new Knyga(this);
        }

        public Knyga buildRandom() {
            int ma = RANDOM.nextInt(KNYGOS.length);        // Autoriaus indeksas  0..
            int mo = RANDOM.nextInt(KNYGOS[ma].length - 1) + 1;// pavadinimo indeksas 1..  
            int za = RANDOM.nextInt(ZANRAS.length);
            return new Knyga(KNYGOS[ma][0],
                    KNYGOS[ma][mo],
                    ZANRAS[za],
                    1990 + RANDOM.nextInt(20),// metai tarp 1990 ir 2009
                    100 + RANDOM.nextInt(380),// 
                    10 + RANDOM.nextDouble() * 20);
        }

        public Builder isleidimoMetai(int isleidimoMetai) {
            this.isleidimoMetai = isleidimoMetai;
            return this;
        }

        public Builder puslapiuSk (int puslapiuSk) {
            this.puslapiuSk = puslapiuSk;
            return this;
        }

        public Builder zanras(String zanras) {
            this.zanras = zanras;
            return this;
        }

        public Builder autorius (String autorius) {
            this.autorius = autorius;
            return this;
        }
        public Builder pavadinimas (String pavadinimas) {
            this.pavadinimas = pavadinimas;
            return this;
        }

        public Builder kaina(double kaina) {
            this.kaina = kaina;
            return this;
        }
    }
}

