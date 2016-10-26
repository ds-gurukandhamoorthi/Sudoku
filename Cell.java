package sudoku;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Cell implements Comparable<Cell> {

    private final int ligne;
    private final int colonne;
    // private final int sousCarre;
    //   private final int value;
    private Set<Integer> valeursPossibles = new HashSet<Integer>();
    //private boolean given;

    public int getValue() throws Exception {
        if (valeursPossibles.size() == 1) {
            for (int v : valeursPossibles) {
                return v;
            }
        }
        throw new Exception(this + "getValue: Case a plusieurs valeurs possibles");
    }

    public void setValue(int val) {
        for (Iterator<Integer> itr = valeursPossibles.iterator(); itr.hasNext();) {
            Integer next = itr.next();
            if (next != val) {
                itr.remove();
            }
        }
    }

    public int getLigne() {
        return ligne;
    }

    public int getColonne() {
        return colonne;
    }

    public int getSousCarre() {
        return (ligne / 3) * 3 + colonne / 3;
    }

    public Cell(int ligne, int colonne) { // seules lignes et colonnes sont donnes; donc toutes les valeurs 1-9 possibles.
        this.ligne = ligne;
        this.colonne = colonne;
        // this.sousCarre = (ligne / 3) * 3 + colonne / 3;
        for (int i = 1; i <= 9; i++) {
            this.valeursPossibles.add(i);
        }
    }

    public Cell(int ligne, int colonne, int val) {
        this(ligne, colonne);
        this.setValue(val);
    }

    public void interdire(int n) {
        this.valeursPossibles.remove(n);
    }

    @Override
    public int compareTo(Cell other) {
        return Integer.compare(this.valeursPossibles.size(), other.valeursPossibles.size());
    }

    public Set<Integer> getValeursPossibles() {
        return valeursPossibles;
    }

    public void setValeursPossibles(Set<Integer> valeursPossibles) {
        this.valeursPossibles = valeursPossibles;
    }

    public boolean memeColonneQue(Cell cell) {
        return memeColonneQue(cell.getColonne());
    }

    public boolean memeColonneQue(int co) {
        return this.getColonne() == co;
    }

    public boolean memeLigneQue(Cell cell) {
        return memeLigneQue(cell.getLigne());
    }

    public boolean memeLigneQue(int li) {
        return this.getLigne() == li;
    }

    public boolean memeSousCarreQue(Cell cell) {
        return memeColonneQue(cell.getSousCarre());
    }

    public boolean memeSousCarreQue(int sc) {
        return this.getSousCarre() == sc;
    }

    public boolean memeSousCarreQue(int ligne, int colonne) {
        return this.getSousCarre() == (ligne / 3) * 3 + colonne / 3;
    }

    @Override
    public int hashCode() {
        return this.ligne;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cell other = (Cell) obj;
        if (this.ligne != other.ligne) {
            return false;
        }
        if (this.colonne != other.colonne) {
            return false;
        }
        return true;
    }

    public boolean equals(int ligne, int colonne) {
        return this.getLigne() == ligne && this.getColonne() == colonne;
    }

    @Override
    public String toString() {
        return "ligne: " + ligne
                + " colonne: " + colonne
                + " sous carre: " + getSousCarre()
                + "; valeurs possibles: " + valeursPossibles;
    }

}
