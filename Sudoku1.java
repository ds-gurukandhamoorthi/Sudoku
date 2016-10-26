package sudoku;

import java.util.Arrays;

public class Sudoku1 {

    public static void main(String[] args) {

        int[][] a = new int[9][9];
        a = convertir("4_8___93_\n1__24_5__\n97_8__6__\n6_23_____\n_47___21_\n_____64_3\n__3__1_59\n__1_65__2\n_94___1_8");
        boolean b[][] = estDonnee(a);

        
        
       // Sudoku s = new Sudoku("4_8___93_\n1__24_5__\n97_8__6__\n6_23_____\n_47___21_\n_____64_3\n__3__1_59\n__1_65__2\n_94___1_8");
     
        //System.out.println(s.getACompleter());

        /*
         System.out.println(s.getCellPossibMoindre());
         System.out.println(s.getGrille()[0][0]);
         System.out.println(s.getGrille()[0][1]);
        
         s.resoudre();
         */
        //s.forcer(1, 0, 1);
        // s.getGrille()[0][1].interdire(1);
        //  System.out.println(s.getGrille()[0][1]);
        //System.out.println(s.getCellPossibMoindre());

        
        
        
        afficher(a);
        //afficher(b);
        for (int i = 0; i < b.length; i++) {
            boolean[] b1 = b[i];
            System.out.println(Arrays.toString(b1));
        }
        System.out.println(estPossib(a, 0, 1, 8));
        System.out.println((8 / 3) * 3);

        long startTime = System.currentTimeMillis();

        resoudre(a);
        afficher(a);
        long endTime = System.currentTimeMillis();

        System.out.println("Temp pris = " + (endTime - startTime) + " ms");

    }

    public static void afficher(int[][] sudokuMatrix) {
        for (int i = 0; i < sudokuMatrix.length; i++) {
            if (i % 3 == 0) {
                System.out.println("-------------");
            }
            for (int j = 0; j < sudokuMatrix[i].length; j++) {
                if (j % 3 == 0) {
                    System.out.print('|');
                }
                System.out.print(sudokuMatrix[i][j]);
            }
            System.out.println("|");
        }
    }

    public static int[][] convertir(String s) {
        int[][] sud = new int[9][9];
        String[] lignes = s.split("\n");
        if (lignes.length != 9) {
            System.out.println("Erreur: convertir, nb lignes != 9");
            return new int[0][0];
        }
        for (int i = 0; i < lignes.length; i++) {
            if (lignes[i].length() != 9) {
                System.out.println("Erreur: convertir, colonne != 9");
                return new int[0][0];
            }
            String DIGITS = "_123456789";
            for (int j = 0; j < lignes[i].length(); j++) {
                int n = DIGITS.indexOf(lignes[i].charAt(j));
                if (n >= 0) {
                    sud[i][j] = n;
                } else {
                    sud[i][j] = 0;
                }
            }
        }
        return (sud);
    }

    public static boolean[][] estDonnee(int[][] sud) {
        boolean[][] res = new boolean[sud.length][sud[0].length];
        for (int i = 0; i < sud.length; i++) {
            for (int j = 0; j < sud[i].length; j++) {
                res[i][j] = sud[i][j] >= 1;
            }
        }
        return (res);
    }

    public static boolean estPossibHoriz(int[][] sudoku, int li, int co, int val) {
        for (int c = 0; c < 9; c++) {
            if (c != co && sudoku[li][c] == val) {
                return false;
            }
        }
        return true;
    }

    public static boolean estPossibVertic(int[][] sudoku, int li, int co, int val) {
        for (int r = 0; r < 9; r++) {
            if (r != li && sudoku[r][co] == val) {
                return false;
            }
        }
        return true;
    }

    public static boolean estPossibSousCarre(int[][] sudoku, int li, int co, int val) {
        final int ROFF = (li / 3) * 3;
        final int COFF = (co / 3) * 3;
        for (int i = ROFF; i < ROFF + 3; i++) {
            for (int j = COFF; j < COFF + 3; j++) {
                if (i == li && j == co) {
                    continue;
                }
                if (sudoku[i][j] == val) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean estPossib(int[][] sudoku, int li, int co, int val) {
        return estPossibHoriz(sudoku, li, co, val)
                && estPossibVertic(sudoku, li, co, val)
                && estPossibSousCarre(sudoku, li, co, val);
    }

    public static int[][] resoudre(int[][] sudoku) {
        boolean backtrackingMode = false;
        boolean resolu = false;
        boolean resolvable = true;
        boolean given[][] = estDonnee(sudoku);

        int i = 0;

        greatloop:
        while (resolvable && !resolu) {
            int r = i / 9;
            int c = i % 9;
            if (backtrackingMode) {
                if (i <= 0) {  //on est en backtracking mode et on atteint la premiere case
                    resolvable = false;
                    break;
                }
                if (given[r][c]) {  //case avec valeur donnee dans le prob. on va a gauche.
                    i--;
                    continue;
                }
                if (sudoku[r][c] == 0) {// en backtrack mode on recontre une case avec valeur 0. on utilise le fait que case contient 0. Dommage pour l'abstraction.
                    i--;
                    continue;
                }
                if (sudoku[r][c] == 9) {// en backtrack mode on recontre une case avec valeur 9. on a donc essaye toutes les valeurs sur cette case. on va a gauche apres avoir mis 0 dans la case
                    sudoku[r][c] = 0;
                    i--;
                    continue;
                }
                backtrackingMode = false;
            } else {
                if (i >= 81) {
                    resolu = true;
                    break;
                }
                if (given[r][c]) {
                    i++;
                    continue;
                }
                for (int k = sudoku[r][c] + 1; k <= 9; k++) { //utilise le fait que non initialise = 0  ! dommage pour l'abstraction

                    if (estPossib(sudoku, r, c, k)) {
                        sudoku[r][c] = k;
                        i++;
                        continue greatloop;
                    }
                }
                sudoku[r][c] = 0;
                backtrackingMode = true; //on a essaye toutes les valeurs : aucune fonctionne.
            }
        }
        return sudoku;
    }
}
