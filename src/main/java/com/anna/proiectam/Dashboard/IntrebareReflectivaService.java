package com.anna.proiectam.Dashboard;

import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class IntrebareReflectivaService {

    private static final List<String> INTREBARI = List.of(
        "Când ai simțit că respiri mai ușor azi?",
        "Ce moment din zi ai fi vrut să dureze mai mult?",
        "Există ceva pe care l-ai evitat azi? De ce?",
        "Cum ai știut că ai nevoie de o pauză?",
        "Ce ai fi avut nevoie să auzi azi?",
        "Dacă ziua de azi ar fi un anotimp, care ar fi?",
        "Ce ți-a cerut cel mai mult energie azi?",
        "A existat un moment în care te-ai simțit cu adevărat prezent(ă)?",
        "Ce ai lăsat neterminat — și cum te simți în legătură cu asta?",
        "Există cineva la care te-ai gândit azi fără să îi spui?",
        "Ce parte din tine a vorbit cel mai tare azi?",
        "Dacă corpul tău ar putea vorbi, ce ar spune acum?",
        "Ce lucru mic ți-a adus un strop de bine azi?",
        "Există ceva ce ai spus azi și pe care ai fi vrut să îl spui altfel?",
        "Unde în corp simți cel mai mult tensiunea în acest moment?",
        "Ce ai face diferit dacă ai trăi ziua de azi din nou?",
        "Care a fost cel mai sincer moment al zilei?",
        "Ce emoție ai ignorat cel mai mult azi?",
        "Există o limită pe care ai simțit că o depășești azi?",
        "Ce ți-a amintit azi de cine ești cu adevărat?",
        "Dacă ai putea da deoparte un gând din ziua de azi, care ar fi?",
        "Ce ai învățat despre tine azi, chiar și ceva mic?",
        "Există ceva ce ai așteptat să se întâmple și nu s-a întâmplat?",
        "Cum arată îngrijirea de sine pentru tine în ziua de azi?",
        "Ce conversație din ziua de azi a rămas cu tine?",
        "Există ceva ce ți-e greu să recunoști despre ziua de azi?",
        "Ce ai fi vrut să faci azi și nu ai făcut?",
        "Cum te-ai tratat pe tine însuți/însăți azi?",
        "Ce parte din ziua de azi a fost cu adevărat a ta?",
        "Dacă ziua de azi ar fi un mesaj, ce ți-ar spune?"
    );

    public String getIntrebareZilnica() {
        int ziuaAnului = LocalDate.now().getDayOfYear();
        int index = ziuaAnului % INTREBARI.size();
        return INTREBARI.get(index);
    }
}
