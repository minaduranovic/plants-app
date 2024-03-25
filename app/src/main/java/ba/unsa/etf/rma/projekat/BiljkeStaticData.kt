package ba.unsa.etf.rma.projekat

val biljke = listOf(
    Biljka(
        naziv = "Bosiljak (Ocimum basilicum)",
        porodica = "Lamiaceae (usnate)",
        medicinskoUpozorenje = "Može iritati kožu osjetljivu na sunce. Preporučuje se oprezna upotreba pri korištenju ulja bosiljka.",
        medicinskeKoristi = listOf(
            MedicinskaKorist.SMIRENJE,
            MedicinskaKorist.REGULACIJAPROBAVE
        ),
        profilOkusa = ProfilOkusaBiljke.BEZUKUSNO,
        jela = listOf("Salata od paradajza", "Punjene tikvice"),
        klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUBTROPSKA),
        zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.ILOVACA)
    ),
    Biljka(
        naziv = "Nana (Mentha spicata)",
        porodica = "Lamiaceae (metvice)",
        medicinskoUpozorenje = "Nije preporučljivo za trudnice, dojilje i djecu mlađu od 3 godine .",
        medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.PROTIVBOLOVA),
        profilOkusa = ProfilOkusaBiljke.MENTA,
        jela = listOf("Jogurt sa voćem", "Gulaš"),
        klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.UMJERENA),
        zemljisniTipovi = listOf(Zemljiste.GLINENO, Zemljiste.CRNICA)
    ),
    Biljka(
        naziv = "Kamilica (Matricaria chamomilla)", porodica = "Asteraceae (glavočike)",
        medicinskoUpozorenje = "Može uzrokovati alergijske reakcije kod osjetljivih osoba.",
        medicinskeKoristi = listOf(
            MedicinskaKorist.SMIRENJE,
            MedicinskaKorist.PROTUUPALNO
        ),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = listOf("Čaj od kamilice"),
        klimatskiTipovi = listOf(KlimatskiTip.UMJERENA, KlimatskiTip.SUBTROPSKA),
        zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.KRECNJACKO)
    ),
    Biljka(
        naziv = "Ružmarin (Rosmarinus officinalis)",
        porodica = "Lamiaceae (metvice)",
        medicinskoUpozorenje = "Treba ga koristiti umjereno i konsultovati se sa ljekarom pri dugotrajnoj upotrebi ili upotrebi u većim količinama.",
        medicinskeKoristi = listOf(
            MedicinskaKorist.PROTUUPALNO,
            MedicinskaKorist.REGULACIJAPRITISKA
        ),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = listOf("Pečeno pile", "Grah", "Gulaš"),
        klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUHA),
        zemljisniTipovi = listOf(Zemljiste.SLJUNKOVITO, Zemljiste.KRECNJACKO)
    ),
    Biljka(
        naziv = "Lavanda (Lavandula angustifolia)",
        porodica = "Lamiaceae (metvice)",
        medicinskoUpozorenje = "Nije preporučljivo za trudnice, dojilje i djecu mlađu od 3 godine. Također, treba izbjegavati kontakt lavanda ulja sa očima.",
        medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.PODRSKAIMUNITETU),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = listOf("Jogurt sa voćem"),
        klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUHA),
        zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.KRECNJACKO)
    ),
    Biljka(
        naziv = "Peršun (Petroselinum crispum)",
        porodica = "Apiaceae (štitarka)",
        medicinskoUpozorenje = "Osobe koje uzimaju lijekove za razrjeđivanje krvi trebaju izbjegavati konzumaciju peršuna u velikim količinama.",
        medicinskeKoristi = listOf(
            MedicinskaKorist.PODRSKAIMUNITETU,
            MedicinskaKorist.PROTIVBOLOVA
        ),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = listOf("Čaj od peršuna", "Supa sa peršunom"),
        klimatskiTipovi = listOf(KlimatskiTip.UMJERENA, KlimatskiTip.SUHA),
        zemljisniTipovi = listOf(Zemljiste.ILOVACA, Zemljiste.KRECNJACKO)
    ),
    Biljka(
        naziv = "Majčina dušica (Thymus vulgaris)",
        porodica = "Lamiaceae (metvice)",
        medicinskoUpozorenje = "Osobe koje pate od bolesti štitnjače trebaju izbjegavati konzumaciju velikih količina majčine dušice.",
        medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.PROTUUPALNO),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = listOf("Čaj od majčine dušice", "Povrće sa začinima"),
        klimatskiTipovi = listOf(KlimatskiTip.UMJERENA, KlimatskiTip.SUHA),
        zemljisniTipovi = listOf(Zemljiste.KRECNJACKO, Zemljiste.PJESKOVITO)
    ),
    Biljka(
        naziv = "Origano (Origanum vulgare)",
        porodica = "Lamiaceae (metvice)",
        medicinskoUpozorenje = "Osobe koje uzimaju lijekove za razrjeđivanje krvi trebaju izbjegavati konzumaciju origana u velikim količinama.",
        medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = listOf("Pica", "Piletina s origanom"),
        klimatskiTipovi = listOf(KlimatskiTip.UMJERENA, KlimatskiTip.SUHA),
        zemljisniTipovi = listOf(Zemljiste.KRECNJACKO, Zemljiste.PJESKOVITO)
    ),
    Biljka(
        naziv = "Kopriva (Urtica dioica)",
        porodica = "Urticaceae (grmovi)",
        medicinskoUpozorenje = "Može izazvati iritaciju kože kod dodira s dlakama na biljci.",
        medicinskeKoristi = listOf(MedicinskaKorist.PODRSKAIMUNITETU, MedicinskaKorist.PROTUUPALNO),
        profilOkusa = ProfilOkusaBiljke.GORKO,
        jela = listOf("Pita od koprive"),
        klimatskiTipovi = listOf(KlimatskiTip.UMJERENA, KlimatskiTip.SUHA),
        zemljisniTipovi = listOf(Zemljiste.GLINENO)
    ),
    Biljka(
        naziv = "Šafran (Crocus sativus)",
        porodica = "Iridaceae (perunike)",
        medicinskoUpozorenje = "Prekomjerna konzumacija može izazvati nuspojave kod nekih ljudi.",
        medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.PROTUUPALNO),
        profilOkusa = ProfilOkusaBiljke.GORKO,
        jela = listOf("Rižoto od šafrana","Čaj od šafrana" ),
        klimatskiTipovi = listOf(KlimatskiTip.UMJERENA, KlimatskiTip.SUHA),
        zemljisniTipovi = listOf(Zemljiste.PJESKOVITO)
    )


)

fun getBiljkeList(): List<Biljka> {
    return biljke;
}