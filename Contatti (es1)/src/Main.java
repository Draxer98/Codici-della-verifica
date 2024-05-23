import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static tools.utility.Wait;
import static tools.utility.menu;

public class Main {
    public static void main(String[] args) {
        //Definizione del menu delle operazioni disponibili
        String[] operazioni = {"RUBRICA",
                "[1] Inserimento",
                "[2] Visualizza contatti pubblici",
                "[3] Visualizza contatti privati",
                "[4] Ricerca",
                "[5] Modifica contatto",
                "[6] cancellazione",
                "[7] Carica saldo telefonico",
                "[8] Telefona",
                "[9] Visualizza ultime chiamate",
                "[10] cambia password",
                "[11] Salva file",
                "[12] Carica file",
                "[13] Fine"};

        boolean fine = true; //Variabile di controllo del ciclo principale
        boolean Sitel = true;  //Variabile di controllo per la lettura dei dati
        final int nMax = 3;    //Numero massimo di contatti gestiti
        int contatoreContatti = 0; //Contatore dei contatti venduti
        int posizione = 0; //Variabile per tenere traccia della posizione dell'array
        int contatorePassword=0; //Variabile per capire se è la prima volta che modifica la password
        String password="0000"; //Variabile per gestire la password (0000 è la password generica)
        String passwordVera=password; //Variabile per salvare la password
        Contatto[] UltimeChiamate = new Contatto[4]; //Array delle ultime chiamate (contatti)
        Contatto[] gestore = new Contatto[nMax]; //Array di contatti

        Scanner keyboard = new Scanner(System.in); // Scanner per la lettura degli input

        do {
            //Switch case per la gestione delle diverse opzioni del menu
            switch (menu(operazioni, keyboard)) {
                case 1:
                    //Inserimento di un nuovo contatto
                    if (contatoreContatti < nMax) {
                        gestore[contatoreContatti] = leggiPersona(Sitel, keyboard);
                        contatoreContatti++;
                    } else {
                        System.out.println("Non ci sono più contratti da vendere");
                        Wait(2);
                    }
                    break;

                case 2:
                    //Visualizzazione dei contatti pubblici
                    if (contatoreContatti != 0) {
                        visualizza(gestore, contatoreContatti);
                        Wait(2);
                    } else {
                        System.out.println("Non ci sono contratti\n");
                        Wait(2);
                    }
                    break;

                case 3: {
                    //Visualizzazione dei contatti privati
                    if (contatoreContatti != 0) {
                        System.out.println("Inserisci password");
                        password=keyboard.nextLine();
                        if(password.equals(passwordVera)) {
                            visualizzaPrivati(gestore, contatoreContatti);
                            Wait(2);
                        } else {
                            System.out.println("Password errata");
                        }
                    } else {
                        System.out.println("Non ci sono contratti\n");
                        Wait(2);
                    }
                    break;
                }
                case 4: {
                    //Ricerca di un contatto
                    int verifica=0;
                    if (contatoreContatti != 0) {
                        posizione=RicercaIndex(gestore, leggiPersona(false, keyboard), contatoreContatti);
                        if (posizione!=-1) {
                            //Possibilità di rendere privato il contatto
                            System.out.println("Vuoi renderlo privato (Si = 1): ");
                            verifica=keyboard.nextInt();
                            if(verifica==1){
                                gestore[posizione].privato=true;
                            }
                            Wait(2);
                        } else {
                            System.out.println("Il contatto non esiste");
                            Wait(2);
                        }
                    } else {
                        System.out.println("Non sono ancora presenti contratti venduti");
                        Wait(2);
                    }
                    keyboard.nextLine();
                    break;
                }

                case 5:
                    //Modifica del numero telefonico di un contatto
                    Contatto numero = new Contatto();
                    int scelta;
                    if (contatoreContatti != 0) {
                        posizione = RicercaIndex(gestore, leggiPersona(false, keyboard), contatoreContatti);
                        if (posizione != -1) {
                            System.out.println("Vuoi modificare il numero telefonico (si = 1 | no = 0): ");
                            scelta = keyboard.nextInt();
                            keyboard.nextLine();
                            if (scelta == 1) {
                                System.out.println("Modifica numero telefonico: ");
                                numero.telefono = keyboard.nextLine();
                                gestore[posizione].telefono = numero.telefono;
                            } else {
                                System.out.println("Numero telefonico non modificato");
                                Wait(2);
                            }
                        } else {
                            System.out.println("Contatto inesistente");
                            Wait(2);
                        }
                    } else {
                        System.out.println("Non sono ancora presenti contratti venduti");
                        Wait(2);
                    }
                    break;
                case 6:
                    //Cancellazione di un contatto
                    if (contatoreContatti != 0) {
                        posizione = RicercaIndex(gestore, leggiPersona(false, keyboard), contatoreContatti);
                        if (posizione != -1) {
                            contatoreContatti = cancellazione(gestore, posizione, contatoreContatti);
                        } else {
                            System.out.println("Contatto inesistente");
                            Wait(2);
                        }
                    } else {
                        System.out.println("Non sono ancora presenti contratti venduti");
                        Wait(2);
                    }
                    break;

                case 7:
                    //Aggiunta di saldo telefonico a un contatto
                    AggiuntaSaldo(gestore, keyboard, contatoreContatti);
                    break;

                case 8:
                    //Simulazione di una telefonata
                    if (contatoreContatti != 0) {
                        posizione = Telefona(gestore, keyboard, contatoreContatti);
                        //Gestione delle ultime chiamate
                        if (posizione != -1) {
                            for (int i = UltimeChiamate.length - 1; i > 0; i--) {
                                UltimeChiamate[i] = UltimeChiamate[i - 1];
                            }
                            UltimeChiamate[0] = gestore[posizione];
                        } else {
                            System.out.println("Contatto non trovato");
                            Wait(2);
                        }
                    } else {
                        System.out.println("Non ci sono contratti venduti");
                        Wait(2);
                    }
                    break;

                case 9:
                    //Visualizzazione delle ultime chiamate
                        visualizzaUltimeChiamate(UltimeChiamate);
                        Wait(2);
                    break;

                case 10:
                    //Funzione per cambiare la password
                    password=CambiaPassword(keyboard, password, contatorePassword);
                    contatorePassword++;
                    passwordVera=password;
                    break;

                case 11:
                    //Salvataggio dei contatti su file
                    try {
                        ScriviFile("archivio.csv", gestore, contatoreContatti);
                    } catch (IOException ex) {
                        System.out.println(ex.toString());
                        break;
                    }
                    break;

                case 12:
                    //Lettura dei contatti su file
                    try {
                        int contElementi=LeggiFile("archivio.csv", gestore);
                    } catch (IOException ex) {
                        System.out.println(ex.toString());
                        break;
                    }
                    contatoreContatti++;
                    // Caricamento dei contatti da file (da implementare)
                    break;
                default:
                    //Uscita dal programma
                    fine = false;
                    break;
            }
        } while (fine);
    }

    // Funzione per leggere i dati di un contatto
    private static Contatto leggiPersona(boolean Sitel, Scanner keyboard) {
        String[] tipoC = {"Telefono", "1]abitazione", "2]cellulare", "3]aziendale"};
        Contatto persona = new Contatto();
        System.out.println("\nInserisci il nome: ");
        persona.nome=keyboard.nextLine();
        System.out.println("\nInserisci il cognome: ");
        persona.cognome = keyboard.nextLine();

        //Se Sitel è true allora inserirà anche il numero più il tipo
        if (Sitel) {
            System.out.println("\nInserisci il numero di telefono: ");
            persona.telefono = keyboard.nextLine();
            switch (menu(tipoC, keyboard)) {
                case 1 -> persona.tipo = tipoContratto.abitazione;
                case 2 -> persona.tipo = tipoContratto.cellulare;
                default -> persona.tipo = tipoContratto.aziendale;
            }
        }
        return persona;
    }

    //Funzione per ottenere l'indice di un contatto nell'array
    private static int RicercaIndex(Contatto[] gestore, Contatto ricerca, int contrattiVenduti) {
        int indice = -1;
        for (int i = 0; i < contrattiVenduti; i++) {
            if (ricerca.nome.equals(gestore[i].nome) && ricerca.cognome.equals(gestore[i].cognome)) {
                indice = i;
                break;
            }
        }
        return indice;
    }

    //Funzione per visualizzare tutti i contatti pubblici
    private static void visualizza(Contatto[] gestore, int contrattiVenduti) {
        for (int i = 0; i < contrattiVenduti; i++) {
            if(gestore[i].privato!=true){
                System.out.println(gestore[i].toString());
            }
        }
    }

    //Funzione per visualizzare tutti i contatti privati
    private static void visualizzaPrivati(Contatto[] gestore, int contrattiVenduti){
        for (int i = 0; i < contrattiVenduti; i++) {
            if(gestore[i].privato==true){
                System.out.println(gestore[i].toString());
            }
        }
    }

    //Funzione per visualizzare le ultime 5 chiamate
    private static void visualizzaUltimeChiamate(Contatto[] UltimeChiamate){
        for (int i = 0; i < UltimeChiamate.length; i++) {
            if(UltimeChiamate[i]!=null) {
                System.out.println(UltimeChiamate[i].toString2());
            }
        }
    }

    //Funzione per cancellare un contatto dall'array
    public static int cancellazione(Contatto[] gestore, int posizione, int contrattiVenduti) {
        if (posizione != gestore.length - 1) {
            for (int i = posizione; i < contrattiVenduti - 1; i++) {
                gestore[i] = gestore[i + 1];
            }
        }
        contrattiVenduti--;
        return contrattiVenduti;
    }

    //Funzione per aggiungere saldo a un contatto
    public static void AggiuntaSaldo(Contatto[] gestore, Scanner keyboard, int contrattiVenduti) {
        Contatto ricarica = new Contatto();
        int posizione;
        if (contrattiVenduti != 0) {
            posizione = RicercaIndex(gestore, leggiPersona(false, keyboard), contrattiVenduti);
            if (posizione != -1) {
                System.out.println("Inserire la ricarica");
                ricarica.saldo = Integer.parseInt(keyboard.nextLine());
                gestore[posizione].saldo += ricarica.saldo;
            } else {
                System.out.println("Contatto non trovato");
                Wait(2);
            }
        } else {
            System.out.println("Non sono ancora presenti contratti venduti");
            Wait(2);
        }
    }

    //Funzione per simulare una telefonata
    public static int Telefona(Contatto[] gestore, Scanner keyboard, int contrattiVenduti) {
        int locazione = 0;
        if (contrattiVenduti != 0) {
            locazione = RicercaIndex(gestore, leggiPersona(false, keyboard), contrattiVenduti);
            if (locazione != -1) {
                System.out.println("Telefonata in corso");
                Wait(1);
                System.out.println(".");
                Wait(1);
                System.out.println(".");
                Wait(1);
                System.out.println(".");
                Wait(1);
                System.out.println("Telefonata terminata");
                Wait(2);
                gestore[locazione].saldo--;
            } else {
                System.out.println("Contatto non trovato");
                Wait(2);
            }
        } else {
            System.out.println("Non sono ancora presenti contratti venduti");
            Wait(2);
        }
        return locazione;
    }

    //Funzione per cambiare la password
    private static String CambiaPassword(Scanner keyboard, String password, int contatorePassword){
        String cambioPassword;
        //Se è la prima volta basterà cambiare la password
        if(contatorePassword==0){
            System.out.println("Password preimpostata: "+password);
            System.out.print("Cambia password: ");
            cambioPassword=keyboard.nextLine();
            Wait(2);
        } else {
            //Dalla seconda volta in poi chiederà prima la password attuale e poi la cambia
            System.out.println("Password Attuale: ");
            cambioPassword=keyboard.nextLine();
            Wait(2);
            if(cambioPassword.equals(password)){
                System.out.println("Nuova password: ");
                cambioPassword=keyboard.nextLine();
                Wait(2);
            } else {
                System.out.println("Password errata");
                Wait(2);
            }
        }
        return cambioPassword;
    }

    //Funzione per scrivere i contatti su file
    public static void ScriviFile(String fileName, Contatto[] gestore, int contatoreContatti) throws IOException {
        FileWriter writer = new FileWriter(fileName);
        for (int i = 0; i < contatoreContatti; i++) {
            writer.write(gestore[i].toString() + "\r\n");
        }
        writer.flush();
        writer.close();

    }
    //Funzione per leggere i contatti su file
    private static int LeggiFile(String fileName, Contatto[] gestore) throws IOException {
        FileReader reader = new FileReader(fileName);
        Scanner input = new Scanner(reader);
        String lineIn;
        String[] vetAttributi;
        int contaElementi = 0;
        while (input.hasNextLine() && (contaElementi < gestore.length)) {
            lineIn = input.nextLine();
            vetAttributi = lineIn.split(",");
            Contatto persona = new Contatto();
            persona.nome = vetAttributi[0];
            persona.cognome = vetAttributi[1];
            persona.telefono = vetAttributi[2];
            switch (vetAttributi[3]) {
                case "abitazione":
                    persona.tipo = tipoContratto.abitazione;
                    break;
                case "cellulare":
                    persona.tipo = tipoContratto.cellulare;
                    break;
                case "aziendale":
                    persona.tipo = tipoContratto.aziendale;
                    break;
            }
            persona.saldo = Double.parseDouble(vetAttributi[4]);
            gestore[contaElementi++] = persona;

        }
        reader.close();
        return contaElementi;
    }
}