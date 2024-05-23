enum tipoContratto{abitazione,cellulare,aziendale};

public class Contatto {
    public String nome;
    public String cognome;
    public String telefono;
    public tipoContratto tipo;
    public double saldo;
    public boolean privato;

    //Metodi per stampare i contatti
    @Override
    public String toString()
    {
        return String.format("Nome: %s Cognome: %s Telefono: %s, tipo: %s, saldo: %s", nome, cognome, telefono, tipo.toString(), saldo);
    }
    public String toString2()
    {
        return String.format("Nome: %s Cognome: %s Telefono: %s", nome, cognome, telefono);
    }

}