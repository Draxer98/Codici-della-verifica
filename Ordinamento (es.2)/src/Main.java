import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner keyboard = new Scanner(System.in);
        int[] numeri = new int[10]; //Array di numeri
        int lunghezza = numeri.length; //Lunghezza dell'array "numeri"
        int numero; //intero per assegnare il numero alla cella dell'array
        boolean verifica; //boolean per verifica

        //Funzione per assegnare i numeri scelti dall'utente all'interno dell'array "numeri"
        for(int i = 0; i < lunghezza; i++) {
            do {
                verifica = false;
                System.out.printf("Inserisci il %d° numero: ", (i + 1));
                numero = keyboard.nextInt();

                if (numero < 0 || numero >30) {
                    System.out.println("Il numero è negativo, riprova.");
                    verifica = true;
                } else {
                    // Controlla se il numero è già presente nell'array
                    for(int j = 0; j < i; j++) {
                        if(numeri[j] == numero) {
                            System.out.println("Il numero è già presente, riprova.");
                            verifica = true;
                            break;
                        }
                    }
                }
            } while (verifica);

            numeri[i] = numero;
        }

        //Richiamo il metodo per ordinare in base alla consegna
        Ordinamento(numeri, lunghezza);

        //Funzione per stampare l'array "numeri"
        for(int i = 0; i < lunghezza; i++){
            System.out.println(numeri[i]);
        }
    }

    //Funzione per ordinare i numeri in base alla consegna
    //Metodo di ordinamento: Selection Sort
    private static void Ordinamento(int[] numeri, int lunghezza){
        int temp;
        for(int i = 0; i < lunghezza - 1; i++){
            if(numeri[i] % 2 == 0){
                for(int j = i + 1; j < lunghezza; j++){
                    if(numeri[j] % 2 == 0 && numeri[j] < numeri[i]){
                        temp = numeri[i];
                        numeri[i] = numeri[j];
                        numeri[j] = temp;
                    }
                }
            }
        }
    }
}