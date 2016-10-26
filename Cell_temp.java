package sudoku;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Cell_temp implements Comparable<Cell_temp> {

    private final int ligne;
    private final int colonne;
    private final int sousCarre;
 //   private final int value;
    private Set<Integer> valeursPossibles = new HashSet<Integer>();
    private boolean given;
  
    public int getValue() throws Exception{
        if(valeursPossibles.size()==1){
            for(int v: valeursPossibles){
                return v;
            }
        }
        throw new Exception(this + "getValue: Case a plusieurs valeurs possibles");
    }
    

    public boolean isGiven() {
        return given;
    }

    public Cell_temp(int ligne, int colonne, int sousCarre, boolean given) {
        this.ligne = ligne;
        this.colonne = colonne;
        this.sousCarre = sousCarre;
        for (int i = 1; i <= 9; i++) {
            this.valeursPossibles.add(i);
        }
        this.given = given;
    }

    public Cell_temp(int ligne, int colonne, boolean given) {
        this(ligne, colonne, (ligne / 3) * 3 + colonne / 3, given);

    }

    public Cell_temp(int ligne, int colonne) {
        this(ligne, colonne, false);
    }

    public Cell_temp(int ligne, int colonne, int value) {
        this(ligne, colonne, true);
        for (int i = 1; i <= 9; i++) {
            if (i != value) {
                this.interdire(i);
            }
        }
    }

    public void interdire(int n) {
        this.valeursPossibles.remove(n);
    }

    @Override
    public int compareTo(Cell_temp other) {
        return Integer.compare(this.valeursPossibles.size(), other.valeursPossibles.size());
    }

    public Set<Integer> getValeursPossibles() {
        return valeursPossibles;
    }

    public void setValeursPossibles(Set<Integer> valeursPossibles) {
        this.valeursPossibles = valeursPossibles;
    }

    @Override
    public String toString() {
        return "ligne: " + ligne
                + " colonne: " + colonne
                + " sous carre: " + sousCarre
                + "; valeurs possibles: " + valeursPossibles;
    }

}
