package no.oslomet.cs.algdat.Eksamen;


import java.util.*;

public class EksamenSBinTre<T> {
    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public EksamenSBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    public int antall() {
        return antall;
    }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot); // går til den første i postorden
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() {
        return antall == 0;
    }

    //Basert på programkode 5.2.3a i kompendiet
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");
        Node<T> p = rot;
        Node<T> q = null;
        int målenr = 0;

        while (p != null) {                                 //Etter while-løkken vil p være høyre eller venstrebarn av en bladnode, i forhold til hvilken verdi som skal legges inn.
            q = p;                                          //Etter while-løkken vil q være forelder av p.
            målenr = comp.compare(verdi, p.verdi);
            if(målenr < 0) {
                p = p.venstre;
            } else {
                p = p.høyre;
            }
        }
        p = new Node<>(verdi, q);                           //Her blir p en ny node med q som forelder.

        if(q == null) {                                     //Hvis q er null, altså hvis det ikke er noen verdier i treet, så er rot = p.
            rot = p;
        } else if(målenr < 0) {                             //Hvis verdi < p.verdi så er p venstrebarn av q.
            q.venstre = p;
        } else {                                            //Hvis verdi > p.verdi så er p høyrebarn av q
            q.høyre = p;
        }
        antall++;
        return true;
    }

    //Basert på programkode 5.2.8d i kompendiet
    public boolean fjern(T verdi) {
        if(verdi == null) {
            return false;
        }

        Node<T> p = rot;                                    //Noden som skal fjernes
        Node<T> q = null;                                   //Foreldrenoden til p
        while (p != null) {
            if(comp.compare(verdi, p.verdi) < 0) {
                q = p;
                p = p.venstre;
            } else if(comp.compare(verdi, p.verdi) > 0) {
                q = p;
                p = p.høyre;
            } else {
                break;
            }
        }

        if(p == null) {                                     //Hvis verdien ikke ble funnet
            return false;
        }
        if(p.venstre == null || p.høyre == null) {          //Hvis p har ett eller ingen barn
        Node<T> r;
            if(p.venstre != null) {                         //Ett barn, enten venstre eller høyre.
                r = p.venstre;
            } else {
                r = p.høyre;
            }
            if(p.venstre != null || p.høyre != null) {      //Hvis p har bare ett barn skal r.forelder = q.
                r.forelder = q;
            }

            if(p == rot) {                                  //Hvis rot skal fjernes
                rot = r;
            } else if(p == q.venstre) {                     //Hvis p ikke har barn og er venstrebarn
                q.venstre = r;
            } else {                                        //Hvis p ikke har barn og er høyrebarn
                q.høyre = r;
            }
        } else { //Hvis p har to barn
            Node<T> s = p;
            Node<T> t = p.høyre;

            while (t.venstre != null) { //Traverserer treet inorden for å finne første verdi inorden for det høyre subtreet til p.
                s = t;
                t = t.venstre;
            }

            p.verdi = t.verdi; //Gir verdien som skal fjernes samme verdi som verdien nederst til venstre i p sitt høyre subtre.

            if (s != p) { //Hvis verdien som skal fjernes ikke har barnebarn.
                s.venstre = t.høyre;
            } else { //Hvis verdien som skal fjernes har barnebarn.
                s.høyre = t.høyre;
            }
        }
        endringer++;
        antall--;
        return true;
    }

    public int fjernAlle(T verdi) {
        int antallAvVerdi = 0;
        if (!tom()) { //Hvis treet er tomt returneres 0.
            while (inneholder(verdi)) { //Mens treet inneholder verdien skal den fjernes og antall økes.
                fjern(verdi);
                antallAvVerdi++;
            }
        }
        endringer++;
        return antallAvVerdi;
    }

    public int antall(T verdi) {
        Node<T> p = rot;
        int antallAvVerdi = 0;

        while (p != null) {                                 //Traverserer treet og sjekker verdiene til alle nodene vi går forbi.
            if(comp.compare(verdi, p.verdi) < 0) {          //Hvis verdien er mindre enn p.verdi går vi til venstre, ellers høyre.
                p = p.venstre;
            } else {
                if(verdi.equals(p.verdi)) {                 //Hvis verdien er lik p.verdi økes antall med 1.
                    antallAvVerdi++;
                }
                p = p.høyre;
            }
        }
        return antallAvVerdi;
    }

    public void nullstill() {
        if(!tom()) {
            nullstill(rot);
        }
        rot = null;
        antall = 0;
        endringer = 0;
    }

    //Rekursiv nullstill metode. Den nullstiller alt unntatt selve rotnoden i preorden.
    //Basert på oppgave 5 i avsnitt 5.2.8 i kompendiet.
    private void nullstill(Node<T> p) {
        p.verdi = null;
        if(p.venstre != null) {                             //Traverserer treet rekursivt i preorden og nullstiller hver rot en etter en.
            nullstill(p.venstre);                           //Hvis p.venstre != null så kaller metoden seg selv med p.venstre som ny parameter, det samme for p.høyre.
            p.venstre = null;
        }
        if(p.høyre != null) {
            nullstill(p.høyre);
            p.høyre = null;
        }
    }

    //Basert på kode fra 5.1.7h i kompendiet
    private static <T> Node<T> førstePostorden(Node<T> p) {
        while (p != null) {
            if(p.venstre != null) {                         //Traverserer treet i postorden, returnerer den første noden i postorden når vi har gått så langt ned til venstre i treet som mulig.
                p = p.venstre;
            } else if(p.høyre != null) {
                p = p.høyre;
            } else {
                return p;
            }
        }
        return null;
    }

    private static <T> Node<T> nestePostorden(Node<T> p) {
        if(p.forelder == null) {                            //Hvis p.forelder er lik null så er p den siste i postorden.
            return null;
        } else if(p == p.forelder.høyre){                   //Hvis p er høyrebarn så er neste i postorden p.forelder.
            p = p.forelder;
        } else {                                            //Hvis p er venstrebarn så er det to muligheter, at p er enebarn eller at p har en søsken.
            if(p.forelder.høyre == null) {                  //Hvis p er alene barn så er neste i postorden p.forelder.
                p = p.forelder;
            } else {                                        //Hvis p.forelder har et høyrebarn så må vi traversere det høyre subtreet til p.forelder for å finne neste i postorden.
                p = p.forelder.høyre;
                p = førstePostorden(p);
            }
        }
        return p;
    }

    public void postorden(Oppgave<? super T> oppgave) {
        Node<T> p = førstePostorden(rot);
        while (p != null) {                                 //Bruker oppgave.utførOppgave på p.verdi helt til nestePostorden(p) returnerer null, som er når p = rot.
            oppgave.utførOppgave(p.verdi);
            p = nestePostorden(p);
        }
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        if(p.venstre != null) {                             //En rekursiv traversering i postorden er når metoden kaller seg selv to ganger og oppgaven utføres etter de to kallene.
            postordenRecursive(p.venstre, oppgave);
        }
        if(p.høyre != null) {
            postordenRecursive(p.høyre, oppgave);
        }
        oppgave.utførOppgave(p.verdi);
    }

    public ArrayList<T> serialize() {
        ArrayList<T> liste = new ArrayList<>();
        ArrayDeque<Node<T>> kø = new ArrayDeque<>();
        kø.add(rot);
        while (!kø.isEmpty()) {
            Node<T> p = kø.removeFirst();                           //Tar ut den første i køen og legger til barna deres i køen senere, hvis den har noen.
            liste.add(p.verdi);                             //Legger den inn i listen - i nivåorden.
            if(p.venstre != null) {                         //Venstrebarnet blir lagt inn i køen først, deretter høyrebarnet. For at de skal være i nivåorden.
                kø.addLast(p.venstre);
            }
            if(p.høyre != null) {
                kø.add(p.høyre);
            }
        }
        return liste;
    }

    static <K> EksamenSBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        EksamenSBinTre<K> tre = new EksamenSBinTre<>(c);
        //Ettersom arrayet som er serialized allerede er i nivåorden i arrayet, så trenger jeg bare å legge de inn 1 og 1 i treet så blir de i nivåorden her og.
        for(K k : data) {
            tre.leggInn(k);
        }
        return tre;
    }

    public static void main(String[] args) {
        EksamenSBinTre<Integer> tre = new EksamenSBinTre<>(Comparator.naturalOrder());
        tre.leggInn(12);
        tre.leggInn(7);
        tre.leggInn(11);
        tre.leggInn(3);
        tre.leggInn(17);
        tre.leggInn(15);
        tre.leggInn(6);
        tre.leggInn(8);
        tre.leggInn(10);
        tre.leggInn(4);
        tre.leggInn(16);
        tre.leggInn(13);
        tre.leggInn(14);
        tre.leggInn(5);
        System.out.println(tre.toStringPostOrder());
    }
} // ObligSBinTre