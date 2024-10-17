import java.util.ArrayList;
import java.util.Iterator;

public abstract class Pizza{
    public static final double PRIX_BASE = 5.0;
    private String titre;
    private String description;
    private ArrayList<Ingredient> ingredients;

    public Pizza(String titre, String description) {
        this.titre = titre;
        this.description = description;
    }

    public Pizza(String titre, String description, ArrayList<Ingredient> ingredients) {
        this.titre = titre;
        this.description = description;
       this.ingredients = ingredients;
       ingredients = new ArrayList<>();
        for (Ingredient i:ingredients) {
            if(ingredients.contains(i)){
                throw new IllegalArgumentException();
            }
            ingredients.add(i);
        }
    }

    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public Iterator<Ingredient> iterator() {
        return ingredients.iterator();
    }

    public boolean ajouter(Ingredient ingredient){
        if(ingredients.contains(ingredient)){
            return false;
        }
        ingredients.add(ingredient);
        return true;
    }

    public boolean supprimer(Ingredient ingredient){
        if(!ingredients.contains(ingredient)){
            return false;
        }
        ingredients.remove(ingredient);
        return true;
    }

    public double calculerPrix(){
        double total = 0.0;
        for (Ingredient i:ingredients) {
            total += i.getPrix();
        }
        return total + PRIX_BASE;
    }


    @Override
    public String toString() {
        String infos = titre + "\n" + description + "\nIngrédients : ";
        for (Ingredient ingredient : ingredients){
            infos +="\n" + ingredient.getNom();
        }
        infos +="\nprix : " + calculerPrix() + " euros";
        return infos;
    }

}
