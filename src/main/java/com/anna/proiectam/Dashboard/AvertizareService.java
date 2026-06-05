package com.anna.proiectam.Dashboard;

import com.anna.proiectam.InregistrareEmotie.InregistrareEmotieRepository;
import com.anna.proiectam.Utilizator.Utilizator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AvertizareService {

    private final InregistrareEmotieRepository inregistrareEmotieRepository;

    @Autowired
    public AvertizareService(InregistrareEmotieRepository inregistrareEmotieRepository) {
        this.inregistrareEmotieRepository = inregistrareEmotieRepository;
    }

    public record Avertizare(String emotie, int zile, String mesaj, String tip) {}

    public List<Avertizare> genereazaAvertizari(Utilizator utilizator) {
        List<Avertizare> rezultat = new ArrayList<>();
        LocalDate azi = LocalDate.now();

        for (EmotieRegula regula : REGULI) {
            int zile = calculeazaZileConsecutive(utilizator, regula.numeEmotie, azi);
            if (zile >= regula.prag1) {
                String mesaj = getMesaj(regula, zile);
                String tip = regula.tipNegativ ? (zile >= regula.prag3 ? "serios" : "negativ") : "pozitiv";
                rezultat.add(new Avertizare(regula.numeEmotie, zile, mesaj, tip));
            }
        }

        return rezultat;
    }

    private int calculeazaZileConsecutive(Utilizator utilizator, String numeEmotie, LocalDate azi) {
        int consecutive = 0;
        for (int i = 0; i < 60; i++) {
            LocalDate zi = azi.minusDays(i);
            boolean areEmotie = inregistrareEmotieRepository
                    .existsEmotieInZi(utilizator.getIdUtilizator(), numeEmotie, zi);
            if (areEmotie) consecutive++;
            else break;
        }
        return consecutive;
    }

    private String getMesaj(EmotieRegula regula, int zile) {
        if (zile >= regula.prag3) return regula.mesaj3;
        if (zile >= regula.prag2) return regula.mesaj2;
        return regula.mesaj1;
    }

    record EmotieRegula(
            String numeEmotie,
            boolean tipNegativ,
            int prag1, int prag2, int prag3,
            String mesaj1, String mesaj2, String mesaj3
    ) {}

    private static final List<EmotieRegula> REGULI = List.of(

            // ── RISC CLINIC RIDICAT ──

            new EmotieRegula("Tristețe", true, 5, 9, 14,
                    "5 zile de tristețe. E ok să o lași să fie acolo — dar încearcă să ieși puțin afară sau să vorbești cu cineva drag.",
                    "9 zile e mult pentru un singur om. Nu trebuie să duci asta singur(ă) — o conversație sinceră cu cineva în care ai încredere poate face diferența.",
                    "Două săptămâni de tristețe persistentă merită atenție profesională. Nu pentru că ceva e în neregulă cu tine, ci pentru că meriți suport real. ⚠️ Aceasta nu este consultanță medicală."
            ),

            new EmotieRegula("Gol", true, 4, 7, 14,
                    "4 zile în care ai simțit gol interior. Uneori asta înseamnă că ceva important lipsește — merită să explorezi ce.",
                    "7 zile de gol interior e un semnal că ceva în rutina sau relațiile tale are nevoie de atenție.",
                    "Două săptămâni de gol interior e ceva ce merită discutat cu cineva specializat. Nu e slăbiciune să ceri ajutor. ⚠️ Aceasta nu este consultanță medicală."
            ),

            new EmotieRegula("Epuizare", true, 3, 7, 14,
                    "3 zile de epuizare. Corpul tău îți trimite un semnal clar. Ce poți scoate din programul tău această săptămână?",
                    "7 zile de epuizare înseamnă că ceva structural trebuie schimbat — nu doar o noapte de somn în plus. Uită-te la ce consumi energia.",
                    "Două săptămâni de epuizare poate fi burnout. Merită o conversație cu un medic sau specialist. ⚠️ Aceasta nu este consultanță medicală."
            ),

            new EmotieRegula("Rușine", true, 5, 10, 14,
                    "5 zile de rușine. Amintește-ți: rușinea spune 'eu sunt greșit(ă)', vinovăția spune 'am greșit'. Sunt lucruri diferite — și ambele pot fi procesate.",
                    "10 zile e mult de purtat. Scrie ce simți — nu ca să îl citești, ci ca să îl scoți din tine.",
                    "Rușinea persistentă poate fi debilitantă. Un terapeut poate ajuta să înțelegi de unde vine și cum să o procesezi. ⚠️ Aceasta nu este consultanță medicală."
            ),

            new EmotieRegula("Vinovăție", true, 5, 10, 14,
                    "5 zile de vinovăție. Există ceva concret pe care îl poți face sau corecta? Dacă da, fă-l. Dacă nu, vinovăția nu mai servește nimănui.",
                    "10 zile de vinovăție poate deveni un cerc vicios. Autocompasiunea nu înseamnă să ignori ce s-a întâmplat — înseamnă să nu te pedepsești la nesfârșit.",
                    "Vinovăția care durează două săptămâni merită explorată cu un specialist — mai ales dacă îți afectează somnul sau relațiile. ⚠️ Aceasta nu este consultanță medicală."
            ),

            // ── RISC MODERAT ──

            new EmotieRegula("Anxietate", true, 5, 10, 21,
                    "5 zile de anxietate. Încearcă tehnica 4-7-8: inspiră 4 secunde, ține 7, expiră 8. Câteva minute pot calma sistemul nervos.",
                    "10 zile de anxietate poate epuiza. Încearcă să identifici ce anume o declanșează — scrie-o pe hârtie. Uneori numirea ei reduce din puterea ei.",
                    "Anxietatea care durează 3 săptămâni merită atenție profesională. Un psiholog te poate ajuta cu tehnici concrete, nu doar sfaturi generale. ⚠️ Aceasta nu este consultanță medicală."
            ),

            new EmotieRegula("Frică", true, 5, 10, 21,
                    "5 zile cu frică. Întreabă-te: frica asta te protejează de ceva real sau îți spune o poveste despre ce s-ar putea întâmpla?",
                    "10 zile de frică poate deveni obositoare. Vorbește cu cineva de încredere despre ce anume te sperie — perspectiva altcuiva ajută enorm.",
                    "Frica care durează 3 săptămâni merită explorată cu un specialist. Nu trebuie să navighezi asta singur(ă). ⚠️ Aceasta nu este consultanță medicală."
            ),

            new EmotieRegula("Furie", true, 4, 7, 14,
                    "4 zile cu furie. Mișcarea fizică poate ajuta enorm — o alergare, o plimbare rapidă, orice care să dea corpului un canal de descărcare.",
                    "7 zile de furie e un semnal că ceva mai adânc are nevoie de atenție. Ce stă în spatele ei — o nedreptate, o limită încălcată, ceva neexprimat?",
                    "Furia care durează două săptămâni poate afecta relațiile și sănătatea. Consideră să vorbești cu cineva specializat. ⚠️ Aceasta nu este consultanță medicală."
            ),

            new EmotieRegula("Singurătate", true, 5, 10, 14,
                    "5 zile de singurătate. Uneori un mesaj trimis cuiva neașteptat poate sparge acea tăcere.",
                    "10 zile de singurătate poate deveni greu de dus. Există comunități, oameni care ar vrea să te cunoască — chiar dacă pare greu de crezut acum.",
                    "Singurătatea prelungită afectează mai mult decât ne dăm seama. Te rugăm să iei în considerare o discuție cu un specialist. ⚠️ Aceasta nu este consultanță medicală."
            ),

            new EmotieRegula("Dezgust", true, 5, 10, 14,
                    "5 zile de dezgust. Încearcă să identifici exact ce îl declanșează — uneori dezgustul e o limită personală care a fost depășită.",
                    "10 zile de dezgust poate semnala că ceva în mediul tău sau în relațiile tale nu mai e în acord cu valorile tale.",
                    "Dezgustul prelungit poate fi epuizant. Dacă simți că nu poți schimba situația singur(ă), o discuție cu un specialist poate ajuta. ⚠️ Aceasta nu este consultanță medicală."
            ),

            // ── SITUAȚIONALE ──

            new EmotieRegula("Confuzie", true, 7, 14, 21,
                    "7 zile de confuzie. Uneori a sta cu întrebarea — fără să forțezi un răspuns — e exact ce ai nevoie.",
                    "Două săptămâni de confuzie poate fi obositoare. Scrie pe hârtie ce știi sigur și ce nu știi — clarificarea parțială ajută.",
                    "Confuzia care durează 3 săptămâni poate afecta deciziile importante. O perspectivă externă — un prieten, un mentor, un specialist — poate fi valoroasă."
            ),

            new EmotieRegula("Dezamăgire", true, 7, 14, 21,
                    "7 zile de dezamăgire. E normală după un eșec sau o așteptare neîmplinită — dar merită să înțelegi ce anume te dezamăgește cel mai mult.",
                    "Două săptămâni de dezamăgire. Ce așteptare stă în spatele ei? Uneori dezamăgirea ne arată ce ne dorim cu adevărat.",
                    "Dezamăgirea care durează 3 săptămâni poate deveni cinism sau retragere. Merită o conversație sinceră cu cineva de încredere."
            ),

            new EmotieRegula("Dor", true, 10, 21, 30,
                    "10 zile de dor. Dorul e o formă de iubire care nu știe unde să se ducă. Dă-i un loc — o amintire, o fotografie, un obiect.",
                    "3 săptămâni de dor poate fi epuizant. Dacă poți, conectează-te cu persoana sau locul de care îți e dor — sau cu cineva care îl cunoaște.",
                    "O lună de dor persistent poate fi o formă de doliu neprocesat. Un specialist poate ajuta să îl înțelegi și să îl integrezi. ⚠️ Aceasta nu este consultanță medicală."
            ),

            // ── POZITIVE ──

            new EmotieRegula("Bucurie", false, 3, 7, 14,
                    "3 zile de bucurie! Ce anume a contribuit? Merită să știi — ca să poți repeta.",
                    "7 zile de bucurie e ceva rar și frumos. Bucuria autentică e contagioasă — poate fără să îți dai seama, ai influențat și oamenii din jurul tău.",
                    "Două săptămâni de bucurie! Savurează fiecare zi. Notează ce a funcționat — vei vrea să îți amintești când vine o perioadă mai dificilă."
            ),

            new EmotieRegula("Pace", false, 3, 7, 14,
                    "3 zile de pace interioară. Asta nu vine de la sine — ceva în viața ta funcționează bine acum.",
                    "7 zile de pace e un semn că ești într-un echilibru real. Ce ai schimbat sau menținut care a contribuit?",
                    "Două săptămâni de pace interioară e extraordinar. Ești un exemplu că echilibrul e posibil."
            ),

            new EmotieRegula("Calm", false, 3, 7, 14,
                    "3 zile de calm. E un fundament solid — din calm poți face față la aproape orice.",
                    "7 zile de calm e o realizare reală. Calmul nu e absența emoțiilor — e capacitatea de a le gestiona.",
                    "Două săptămâni de calm! Ce practici sau obiceiuri au contribuit? Merită să le conștientizezi."
            ),

            new EmotieRegula("Recunoștință", false, 3, 7, 14,
                    "3 zile de recunoștință. Știi deja că asta reorientează creierul spre ce funcționează — continuă.",
                    "7 zile de recunoștință activă. Cercetările arată că asta are efecte reale asupra rezilienței — o simți?",
                    "Două săptămâni de recunoștință! E una dintre cele mai puternice practici psihologice. Ești pe un drum bun."
            ),

            new EmotieRegula("Mândrie", false, 3, 7, 14,
                    "3 zile de mândrie sănătoasă. Ai realizat ceva care merită recunoscut — chiar și de tine însuți/însăți.",
                    "7 zile de mândrie! Lasă asta să se așeze. Uneori suntem atât de concentrați pe ce urmează că uităm să savurăm ce am realizat.",
                    "Două săptămâni de mândrie sănătoasă e un semn că trăiești în acord cu valorile tale. Frumos."
            ),

            new EmotieRegula("Speranță", false, 3, 7, 14,
                    "3 zile de speranță. Speranța nu e naivitate — e o alegere activă de a vedea posibilitatea.",
                    "7 zile de speranță! Asta e combustibilul care te ține în mișcare chiar și în perioade grele.",
                    "Două săptămâni de speranță e un dar real. Ține-o aproape — și amintește-ți de ea când va fi mai greu."
            ),

            new EmotieRegula("Iubire", false, 3, 7, 14,
                    "3 zile în care ai simțit iubire. Fie că e pentru o persoană, un loc sau ce faci — asta e una din cele mai valoroase emoții.",
                    "7 zile de iubire! Conexiunile tale contează mai mult decât crezi — și tu contezi pentru ele.",
                    "Două săptămâni în care iubirea a fost prezentă. Asta e ceea ce, la final, contează cel mai mult."
            ),

            new EmotieRegula("Încredere", false, 3, 7, 14,
                    "3 zile de încredere. Asta se construiește greu și se pierde ușor — savurează-o când e prezentă.",
                    "7 zile de încredere! Ești într-un spațiu de siguranță reală — cu tine sau cu ceilalții.",
                    "Două săptămâni de încredere e un fundament solid. Ce a creat-o? Merită să știi."
            )
    );
}