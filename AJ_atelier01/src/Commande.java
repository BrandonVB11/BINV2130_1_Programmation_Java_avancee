import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Iterator;

public class Commande {
    private static int numeroSuivant = 1;
    private int numero;
    private Client client;
    private LocalDateTime date;
    private ArrayList<LigneDeCommande> ligneDeCommandes;

    public Commande(Client client) {
        if(client.getCommandeEnCours() != null){
            throw new IllegalArgumentException("impossible de créer une commande pour un client ayant encore une commande en cours");
        }
        this.client = client;
        this.date = LocalDateTime.now();
        this.ligneDeCommandes = new ArrayList<>();
        this.numero = numeroSuivant;
        numeroSuivant++;
    }

    public int getNumero() {
        return numero;
    }

    public Client getClient() {
        return client;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Iterator<LigneDeCommande> iterator() {
        return ligneDeCommandes.iterator();
    }

    public boolean ajouter(Pizza pizza,int quantite){
        if(client.getCommandeEnCours() != this){
            return false;
        }
        if(ligneDeCommandes.contains(pizza)){
            LigneDeCommande ligne = ligneDeCommandes.get(ligneDeCommandes.indexOf(pizza));
            ligne.setQuantite(ligne.getQuantite()+quantite);
            return true;
        }
        else{
            ligneDeCommandes.add(new LigneDeCommande(pizza,quantite));
            return true;
        }
    }

    public boolean ajouter(Pizza pizza){
        if(client.getCommandeEnCours() != this){
            return false;
        }
        if(ligneDeCommandes.contains(pizza)){
            return false;
        }
        LigneDeCommande l = new LigneDeCommande(pizza,0);
        ligneDeCommandes.add(l);
        return true;
    }

    public double calculerMontantTotal(){
        double total = 0.0;
        for (LigneDeCommande l:ligneDeCommandes) {
            total += l.calculerPrixTotal();
        }
        return total;
    }

    public String detailler(){
        StringBuilder sb = new StringBuilder();
        for (LigneDeCommande l:ligneDeCommandes) {
            sb.append(l.getPizza().getTitre()).append(" x").append(l.getQuantite()).append(" : ").append(l.calculerPrixTotal()).append("\n");
        }
        return sb.toString();
    }

    public String toString() {
        DateTimeFormatter formater = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        String encours = "";
        if (client.getCommandeEnCours() == this)
            encours = " (en cours)";
        return "Commande n° " + numero + encours + " du " + client + "\ndate : " + formater.format(date);
    }
}
