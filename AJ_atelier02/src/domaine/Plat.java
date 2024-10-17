package domaine;

import java.time.Duration;
import java.util.*;

public class Plat {
    private String nom;
    private int nbPersonnes;
    private Difficulte niveauDeDifficulte;
    private Cout cout;
    private Duration dureeEnMinutes;
    private List<Instruction> recette;
    private Set<IngredientQuantifie> ingredients;

    public Plat(String nom,int nbrPersonnes,Difficulte niveau,Cout cout){
        this.nom = nom;
        this.nbPersonnes = nbrPersonnes;
        this.niveauDeDifficulte = niveau;
        this.cout = cout;
        this.dureeEnMinutes = Duration.ZERO;
        //initialisation de la liste d'instruction
        recette = new ArrayList<>();
        ingredients = new HashSet<>();
    }

    public String getNom() {
        return nom;
    }

    public int getNbrPersonnes() {
        return nbPersonnes;
    }

    public Difficulte getNiveauDeDifficulte() {
        return niveauDeDifficulte;
    }

    public Cout getCout() {
        return cout;
    }

    public Duration getDureeEnMinutes() {
        return dureeEnMinutes;
    }

    public void insererInstruction(int position, Instruction instruction){
        //insère l’instruction à la position précisée, position commençant à 1.
        if(position <= 0){
            throw new IllegalArgumentException("Position passée en parametre est inferieur ou egal à 0");
        }
        if(position > recette.size()){
            throw new IllegalArgumentException("Position passée en parametre est superieur à la taille de la liste");
        }
        this.dureeEnMinutes = this.dureeEnMinutes.plus(instruction.getDureeEnMinutes());
        recette.add(position-1,instruction);
    }

    public void ajouterInstruction (Instruction instruction){
        //ajoute l’instruction en dernier.
        this.dureeEnMinutes = this.dureeEnMinutes.plus(instruction.getDureeEnMinutes());
        recette.addLast(instruction);
    }

    public Instruction remplacerInstruction (int position, Instruction instruction){
        //remplace l’instruction à la position précisée par celle en paramètre.
        //renvoie l’instruction qui a été remplacée
        if(position <= 0){
            throw new IllegalArgumentException("Position passée en parametre est inferieur ou egal à 0");
        }
        Instruction remplace = recette.get(position-1);
        this.dureeEnMinutes = this.dureeEnMinutes.minus(remplace.getDureeEnMinutes()).plus(instruction.getDureeEnMinutes());
        recette.set(position-1,instruction);
        return remplace;
    }

    public Instruction supprimerInstruction (int position){
        //supprimer l’instruction à la position précisée
        //renvoie l’instruction qui a été supprimée
        if(position <= 0){
            throw new IllegalArgumentException("Position passée en parametre est inferieur ou egal à 0");
        }
        Instruction supprime = recette.get(position-1);
        this.dureeEnMinutes = this.dureeEnMinutes.minus(supprime.getDureeEnMinutes());
        recette.remove(position-1);
        return supprime;
    }

    public List<Instruction> instructions(){
        // fournit une collection non-modifiable contenant les instructions du plat
        //considéré.
        return List.copyOf(recette);
    }

    public SortedSet<Ingredient> ingredients(){
        // renvoie l’ensemble des ingrédients, utilisés dans le plat, triés par nom
        //d’ingrédient.
        SortedSet<Ingredient> sortedSet;
        sortedSet = new TreeSet<>();
        for(IngredientQuantifie ing : ingredients){
            sortedSet.add(ing.getIngredient());
        }
        return sortedSet;
    }


    @Override
    public String toString() {
        String hms = String.format("%d h %02d m", dureeEnMinutes.toHours(), dureeEnMinutes.toMinutes()%60);
        String res = this.nom + "\n\n";
        res += "Pour " + this.nbPersonnes + " personnes\n";
        res += "Difficulté : " + this.niveauDeDifficulte + "\n";
        res += "Coût : " + this.cout + "\n";
        res += "Durée : " + hms + " \n\n";
        res += "Ingrédients :\n";
        for (IngredientQuantifie ing : this.ingredients) {
            res += ing + "\n";
        }
        int i = 1;
        res += "\n";
        for (Instruction instruction : this.recette) {
            res += i++ + ". " + instruction + "\n";
        }
        return res;
    }


    public enum Difficulte {
        X, XX, XXX, XXXX, XXXXX;
        @Override
        public String toString() {
            return "*".repeat(ordinal() + 1);
        }
    }

    public enum Cout{
        $, $$, $$$, $$$$,$$$$$;
        public String toString() {
            return "€".repeat(ordinal()+1);
        }
    }
}
