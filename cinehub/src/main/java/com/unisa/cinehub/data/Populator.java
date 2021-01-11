package com.unisa.cinehub.data;

import com.unisa.cinehub.control.CatalogoControl;
import com.unisa.cinehub.control.GestioneCatalogoControl;
import com.unisa.cinehub.data.entity.*;
import com.unisa.cinehub.model.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class Populator implements ApplicationRunner {
    @Autowired
    private GestioneCatalogoControl gestioneCatalogoControl;
    @Autowired
    private CatalogoControl catalogoControl;
    @Autowired
    private UtenteService utenteService;

    public Populator(GestioneCatalogoControl gestioneCatalogoControl, CatalogoControl catalogoControl, UtenteService utenteService) {
        this.gestioneCatalogoControl = gestioneCatalogoControl;
        this.catalogoControl = catalogoControl;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Genere> generi = new ArrayList<>();
        generi.add(new Genere(Genere.NomeGenere.AZIONE));
        generi.add(new Genere(Genere.NomeGenere.DRAMMATICI));
        gestioneCatalogoControl.addFilm(new Film("Baby Driver", 2017, "Un giovane pilota è costretto a lavorare per un boss del crimine e deve usare tutta la propria abilità quando una rapina, destinata a fallire, minaccia la sua vita e la sua libertà.", "https://www.youtube.com/embed/oFiLrgCuFXo", "https://pad.mymovies.it/filmclub/2015/09/049/locandina.jpg"));
        gestioneCatalogoControl.addGeneriFilm(generi, 1l);
        gestioneCatalogoControl.addFilm(new Film("Harry Potter e la Pietra Filosofale", 2001, "1º novembre 1981. Al numero 4 della strada Privet Drive, a Little Whinging, in Inghilterra, il potentissimo mago Albus Silente, la strega Minerva McGranitt e il mezzogigante Rubeus Hagrid lasciano un neonato di un anno di nome Harry James Potter, rimasto orfano dei genitori, sulla porta di casa dei suoi zii babbani, Vernon e Petunia Dursley.",
                "https://www.youtube.com/embed/VyHV0BRtdxo",
                "https://live.staticflickr.com/8406/8640390608_3155fcc85e_n.jpg"));
        generi.clear();
        generi.add(new Genere(Genere.NomeGenere.FANTASY));
        gestioneCatalogoControl.addGeneriFilm(generi, 2l);
        gestioneCatalogoControl.addSerieTV(new SerieTv("La Casa di Carta",
                2017,
                "La storia narra gli sviluppi di una rapina estremamente ambiziosa e originale: irrompere nella Fábrica Nacional de Moneda y Timbre, a Madrid, far stampare migliaia di milioni di banconote e scappare con il bottino. L'ideatore di questa impresa è un uomo conosciuto come \"il Professore\". Il reclutamento di ogni singolo membro della squadra non è affatto casuale: il Professore, infatti, seleziona attentamente un gruppo di individui con precedenti penali, i quali, per motivi di estrazione sociale, non hanno nulla da perdere. Ciascun membro durante la rapina agisce vestito di rosso con una maschera del pittore spagnolo Salvador Dalí.",
                "https://www.youtube.com/embed/bNWnxJBFlDQ",
                "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fi2.wp.com%2Ftv.badtaste.it%2Fwp%2Fwp-content%2Fuploads-badtv%2F2019%2F01%2Fcasa-di-carta.jpg%3Ffit%3D300%252C450%26quality%3D85%26strip%3Dall%26ssl%3D1&f=1&nofb=1"));
        generi.clear();
        generi.add(new Genere(Genere.NomeGenere.AZIONE));
        generi.add(new Genere(Genere.NomeGenere.DRAMMATICI));
        gestioneCatalogoControl.addGeneriSerieTv( generi, 3l);
        gestioneCatalogoControl.addPuntata(new Puntata("Efectuar lo acordado",
                        1,
                        " ciascun componente della banda viene dato il nome di una città: Tokyo, narratrice della storia, il cui compagno è appena morto dopo uno scontro a fuoco per un furto; Mosca e Denver, padre e figlio; Berlino, eletto dal Professore a capo delle operazioni; Nairobi, l'altra donna del gruppo; Río, giovane hacker; Helsinki e Oslo, cugini serbi. Tutti i componenti hanno dei precedenti penali, tranne il Professore, scomparso da qualsiasi database da molti anni. Il Professore impone al gruppo di non indagare né esporre le proprie vite personali e reali identità, né instaurare relazioni sentimentali. Il colpo dovrà essere pulito; l'obiettivo è esclusivamente il furto, quindi il Professore impone di non uccidere o ferire nessun ostaggio. "),
                3l,
                1);
        gestioneCatalogoControl.addPuntata(new Puntata("Imprudencias letales ",
                        2,
                        "Río è vivo, è solo ferito alla testa. Berlino, al telefono con il Professore, gli rivela che Tokyo e Río hanno una relazione, ma lei smentisce tutto. Río, invece, quando sono soli, conferma a Berlino che ama Tokyo. Cominciano a esserci dei flashback ambientati nei cinque mesi di addestramento con il Professore. Nel frattempo la polizia prende le dovute contromisure e invia sul posto come negoziatrice l’ispettrice Raquel Murillo, una donna con problemi professionali e personali; vive con l'anziana madre e con sua figlia Paula, è separata da poco e ha denunciato per maltrattamenti l'ex marito, genio della Scientifica, che ora ha una relazione con sua sorella. Il braccio destro dell'ispettrice è Angel Rubio, segretamente innamorato di lei; inoltre la donna deve sottostare agli ordini del colonnello Prieto, a capo dei servizi segreti. "),
                3l,
                1);
        gestioneCatalogoControl.addPuntata(new Puntata("Errar al disparar",
                        3,
                        "Il gruppo assegna a ognuno degli ostaggi del lavoro da svolgere: stampare i soldi o scavare un tunnel per scappare. Nel frattempo Raquel Murillo nel bar, usando sempre il cellulare del Professore, anticipa ai suoi superiori al telefono che intende dimettersi qualora l'operazione venisse influenzata da Prieto e dai suoi interessi diplomatici. "),
                3l,
                1);
        gestioneCatalogoControl.addPuntata(
                        new Puntata("e acabaron las máscaras",
                        1,
                        "La polizia e la Scientifica stanno passando al setaccio il casale di Toledo in cerca di tracce; il Professore, che ha accompagnato Raquel, è in auto, estremamente nervoso, ma trova un quaderno di Paula, la figlia di Raquel, in cui la maestra scrive alla madre delle sue preoccupazioni per un invito da parte di Alberto, il padre. Dentro all'edificio vi sono moltissime prove, tattiche, impronte, DNA. Lo scopo del Professore, era proprio quello di portare la polizia a Toledo e farle perder tempo, calcolando almeno 3 giorni; l'ispettore Murillo, però, capisce in meno di un'ora che c'è qualcosa di strano, e manda a chiamare il suo ex marito, Alberto Vicuña, affinché compia un esame più approfondito del casale. Raquel si sfoga con Salvador perché si sente smarrita. Arriva finalmente Alberto, al quale Raquel presenta Salvador come il suo compagno. "),
                3l,
                2);

        utenteService.saveRegisteredUser(new Recensore("mariachiaranasto1@gmail.com", "Maria Chiara", "Nasto", LocalDate.of(2000, 2, 7), "xmariachiara", new BCryptPasswordEncoder().encode("ciao"), false, true));
        utenteService.saveRegisteredUser(new Recensore("g.cardaropoli99@gmail.com", "Giuseppe", "Cardaropoli", LocalDate.of(1999, 12, 3), "Peppe99", new BCryptPasswordEncoder().encode("pippo"), false, true));

    }
}
