# Mappeeksamen i Algoritmer og Datastrukturer Høst 2020

# Krav til innlevering

Se oblig-tekst for alle krav, og husk spesielt på følgende:

* Git er brukt til å dokumentere arbeid (minst 2 commits per oppgave, beskrivende commit-meldinger)	
* git bundle er levert inn
* Hovedklassen ligger i denne path'en i git: src/no/oslomet/cs/algdat/Eksamen/EksamenSBinTre.java
* Ingen debug-utskrifter
* Alle testene i test-programmet kjører og gir null feil (også spesialtilfeller)
* Readme-filen her er fyllt ut som beskrevet

# Oppgaven er levert av:
* Øyvind Ødegård Stenberg - s188886@oslomet.no

# Beskrivelse av oppgaveløsning (4-8 linjer/setninger per oppgave)

Jeg har brukt git til å dokumentere arbeidet mitt. Jeg har 23 commits totalt, og hver logg-melding beskriver det jeg har gjort av endringer.
Jeg startet med å importere prosjektet for så å legge kildekoden inn i git.

Oppgave 1 har 2 commits.
Oppgave 2 har 2 commits.
Oppgave 3 har 6 commits.
Oppgave 4 har 3 commits (den ene ble committa samtidig som en endring i oppgave 3, så den teller ikke på totalen).
Oppgave 5 har 4 commits.
Oppgave 6 har 7 commits.
README.me har 9 commits. Usikker på om de skal regnes med i totalt antall commits, men jeg har ikke regnet med dem.

Oppgavebeskrivelser:

* Oppgave 1: Løste oppgaven ved å først og fremst ikke la det være mulig å legge inn en null-verdi. Deretter instansierte jeg noden p til å være
             lik rot-noden, og den andre noden q lik null. En int verdi (målenr) ble også instansiert til å være 0, den skal få verdien sin fra comparatoren. 
             En while-løkke ble brukt for å både sjekke hvilke subtre (venstre/høyre) verdien skal legges inn i, men også for å konstant oppdatere p og q 
             med tanke på hvilke subtrær verdien må "gjennom". Hvis målenr < 0 går vi til venstre, hvis målenr > 0 går vi til høyre. Etter while-løkken lages 
             den nye verdien som skal legges inn ved bruk av p = new Node<>(verdi, q). Hvis rot er null er q også null så det gir ikke noe problem. Ellers 
             sjekkes verdien av målenr (som ble kontinuerlig oppdatert i while-løkken) for å sette riktige høyre / venstre pekere for foreldrenoden (q).
             
* Oppgave 2: Løste oppgave 2 ved å lage en node p lik rot og en int antallAvVerdi som er antall ganger verdien er i treet. Noden p traverserer treet fra roten 
             gjennom en while-løkke. Hvis verdien det letes etter er mindre enn p.verdi (dette sjekkes via en Comparator) går man til venstre, ellers til høyre 
             og hvis verdien er lik p.verdi økes antallAvVerdi. Til slutt returneres antallAvVerdi når man har gått gjennom hele treet.
             
* Oppgave 3: Løste oppgave 3 - førstePostorden - ved å bruke en while-løkke som traverserer gjennom hele treet i postorden før den returnerer verdien til den 
             første. Hvis node p sitt venstrebarn ikke er null så er p = p.venstre, ellers hvis node p sitt høyrebarn ikke er null så er p = p.høyre. Når metoden 
             har gått ned p sitt venstre subtre returneres p som nå er første postorden. Hvis p er null så returneres null. 
             
             Oppgave 3 - nestePostorden - ble løst ved å finne alle variantene for neste postorden i et tre. Først og fremst hvis p.forelder == null så er man på siste 
             i postorden og da skal det returneres null. Hvis p er høyrebarn av foreldrenoden sin så skal p.forelder returneres. Ellers, altså hvis p er et venstrebarn, 
             må det sjekkes om foreldrenoden har et høyrebarn. Hvis foreldrenoden har et høyrebarn skal det letes etter første i postorden i det høyre subtreet til 
             foreldrenoden. Derfor brukes metoden førstePostorden på p etter at p blir omgjort til p.forelder.høyre. Hvis foreldrenoden ikke har et høyrebarn skal 
             p = p.forelder. Etter at alle muligheter er gått gjennom skal p returneres.
             
* Oppgave 4: Løste oppgave 4 - postordenRecursive - ved å kalle seg selv to ganger, og deretter (i postorder) utføre oppgaven til parameteret "oppgave". Ved det 
             første kallet sjekkes det om Node p.venstre ikke er lik null, hvis det er sant kaller metoden seg selv med p.venstre som endret parameter. Ved det 
             andre kallet sjekkes det om Node p.høyre ikke er lik null, hvis det er sant kaller metoden seg selv med p.høyre som endre parameter. Som sagt så 
             utføres oppgaven etter disse to rekursive kallene og det er det som gjør at traverseringen skjer i postorden.
             
             Oppgave 4 - postorden - Denne ble løst ganske enkelt ved å først instansiere en ny node (p) som skal være den første i postorden, ved bruk av metoden 
             førstePostorden med rot som parameter. Deretter brukes en while-løkke, og mens den noden (p) som ble instansiert ikke er lik null skal først "oppgave" 
             parameteren utføre metoden utføreOppgave på noden (p) sin verdi. Etter det blir noden (p) lik neste node i postorden via metoden nestePostorden. 
             Dette skjer helt til noden (p) er lik null.
             
* Oppgave 5: Oppgave 5 - serialize - ble løst ved å initialisere Arraylisten som skal returneres og køen som skal brukes. Jeg valgte å bruke et ArrayDeque av type 
             Node<T>. Deretter legges rot inn i køen og så bruker jeg en while-løkke for å traversere treet i nivåorden som looper helt til køen er tom. Først i 
             løkken lager jeg en hjelpevariabel av type Node kalt (p) som tar ut Noden i køen først (fordi det skal gjøres i nivåorden) før det sjekkes om Node 
             p.venstre != null, hvis ikke den er det så legges p.venstre inn i køen. Hvis p.høyre != null så legges p.høyre inn i køen. Til slutt returneres listen.
             
             Oppgave 5 - deserialize - ble løst ved å initialisere treet som skal returneres. Ettersom arrayet som er serialized allerede er sortert i nivåorden så 
             trengs det bare å legge inn verdiene fra arrayet 1 etter 1 i treet så blir de i nivåorden i treet også. Dette ble gjort ganske enkelt via en foreach 
             løkke ved bruk av leggInn metoden. Til slutt ble treet returnert.

* Oppgave 6: Oppgave 6 - fjern - ble løst ved å først sjekke om verdien er null, og så returnere false hvis den er det. Deretter ble hjelpevariabler instansiert der
             node p er rot og node q er null til å begynne med. Etter en while-løkke der verdien sammenliknes med p.verdi vil eventuelt p bli lik verdien som skal fjernes 
             mens q vil bli foreldernoden til p. Hvis p nå er lik null (altså hvis verdien ikke ble funnet) returneres false. Så må metoden sjekke de 3 forskjellige 
             mulighetene vi har - at p har bare ett barn, at p har ingen barn eller at p har to barn. Hvis p.venstre == null eller p.høyre == null vil gi de to første 
             mulighetene for p så inni den if-setningen sjekkes det om det p.venstre != null hvis det er det så vil en ny node r instansieres til å være lik p.venstre, 
             og motsatt hvis p.høyre != null. Etter det må r.forelder settes og den blir da lik q som er foreldernoden til p. Hvis node p har to barn må vi finne 
             den neste verdien i p sitt høyre subtre som er inorden, og p sin verdi blir da lik den neste verdien inorden (som ble instansiert her som t). Noden t 
             sin forelder ble også instansiert som s. Hvis s != p har ikke p barnebarn, ellers har p barnebarn. Venstre og høyre pekere blir satt i forhold til dette. 
             Til slutt blir antall redusert og true returnert som vil si at verdien har blitt fjernet.
            
            Oppgave 6 - fjernAlle - ble løst ved å instansiere en int antallAvVerdi lik 0 og hvis treet er tomt returneres 0. Ellers sjekkes det om (i en while-løkke) 
            treet inneholder verdien, om den gjør det brukes fjern metoden med verdi som parameter og antallAvVerdi blir økt med 1. Til slutt returneres antallAvVerdi.
            
            Oppgave 6 - nullstill - ble løst ved rekursivt ved å lage en ny privat nullstill metode med Node<T> p som parameter. Den traverserer treet i preorden, altså 
            den starter med å nulle ut p.verdi. Deretter sjekker den om p.venstre != null, og hvis det er sant kaller metoden seg selv rekursivt med p.venstre som 
            parameter før p.venstre blir nullet. Etter det sjekkes det om p.høyre != null og hvis det er sant kaller metoden seg selv rekursivt med p.høyre som
            parameter før p.høyre blir nullet. I selve nullstill metoden som brukes i testen sjekkes det først om treet ikke er tomt og hvis det er sant kjøres 
            den rekursive nullstill metoden med rot som parameter. Til slutt blir rot lik null og antall lik 0.