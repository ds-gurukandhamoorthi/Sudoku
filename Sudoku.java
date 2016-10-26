package sudoku;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Sudoku {

    private Cell[][] grille = new Cell[9][9];
    private Set<Cell> aCompleter = new HashSet<>(); //CAUTION: Don't use TreeSet here...

    public void forcer(int li, int co, int val) {
        for (Cell c : aCompleter) {
            if (c.memeSousCarreQue(li, co) || c.memeColonneQue(co) || c.memeLigneQue(li)) {
                if (!c.equals(li, co)) {
                    c.interdire(val);
                    //aCompleter.remove(c);
                }
            }
        }
    }

    public void resoudre() {
        setACompleter();
        contraindre();
        Cell cellContraignant = this.getCellPossibMoindre();
        //System.out.println("cell contraignant" + cellContraignant);
        if (cellContraignant.getValeursPossibles().isEmpty()) {
            System.out.println("Erreur: pas de solutions");
        } else if (cellContraignant.getValeursPossibles().size() == 1) {
            try {
                forcer(cellContraignant.getLigne(), cellContraignant.getColonne(), cellContraignant.getValue());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            contraindre();
        } else {

            Set<Integer> valeursATester = new HashSet<Integer>();
            for (int v : cellContraignant.getValeursPossibles()) {
                //Sudoku s = new Sudoku(grille);
                //Sudoku s=this;
                //grille[cellContraignant.getLigne()][cellContraignant.getColonne()].setValue(v);
                //  s.resoudre();
                //     System.out.println(s);

                valeursATester.add(v);
            }
            for (int v : valeursATester) {
                Sudoku s = new Sudoku(grille);
                //Sudoku s=this;
                //grille[cellContraignant.getLigne()][cellContraignant.getColonne()].setValue(v);
                s.resoudre();
                System.out.println(s);
            }
        }
    }

    public void contraindre() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Cell c = grille[i][j];
                if (c.getValeursPossibles().size() == 1) {
                    try {
                        forcer(i, j, c.getValue());
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        }
    }

    public Cell[][] getGrille() {
        return grille;
    }

    public void setGrille(Cell[][] grille) {
        this.grille = grille;
    }

    public Sudoku(Cell[][] grille) {
        this.grille = grille;
        setACompleter();
    }

    public Sudoku(String sudokuRepresentation) {
        String[] lignes = sudokuRepresentation.split("\n");
        if (lignes.length != 9) {
            System.out.println("Erreur: convertir, nb lignes != 9");
            return;
        }
        for (int i = 0; i < lignes.length; i++) {
            if (lignes[i].length() != 9) {
                System.out.println("Erreur: convertir, colonne != 9");
                return;
            }
            String DIGITS = "_123456789";
            for (int j = 0; j < lignes[i].length(); j++) {
                int n = DIGITS.indexOf(lignes[i].charAt(j));
                if (n > 0) {
                    grille[i][j] = new Cell(i, j, n);
                } else {
                    grille[i][j] = new Cell(i, j);
                }
            }
        }
        setACompleter();
        contraindre();
    }

    private void setACompleter() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                Cell c = grille[i][j];
                
                if (c.getValeursPossibles().size() >= 2) {
                    //System.out.println(c);
                    //System.out.println("dans if: setACompleter " + aCompleter.size());
                    aCompleter.add(c);
                    //System.out.println("dans if: setACompleter " + aCompleter.size());

                }
            }
        }

    }

    public Set<Cell> getACompleter() {
        return aCompleter;
    }

    public Cell getCellPossibMoindre() {
        return Collections.min(aCompleter, null);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        //sb.append("aCompleter.size()==" + aCompleter.size() + '\n');
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Cell c = grille[i][j];
                if (c.getValeursPossibles().size() == 1) {
                    try {
                        sb.append(c.getValue());
                    } catch (Exception ex) {
                        System.out.println("Erreur:\n\n" + ex.getMessage());
                    }
                } else {
                    sb.append('_');
                }
            }
            sb.append('\n');
        }
        return sb+""; //+ "Sudoku{" + "aCompleter=" + aCompleter + '}';
    }

}
