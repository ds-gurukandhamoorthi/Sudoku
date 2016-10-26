package essai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import sudoku.Cell;
import sudoku.Sudoku;

public class CellEssai {

    public static void main(String[] args) {
//        for (int i = 0; i < 9; i++) {
//            for (int j = 0; j < 9; j++) {
//                Cell c = new Cell(i, j);
//                c.interdire(5);
//                System.out.println(c);
//            }
//
//        }
//       

//        int tab = new int[10];
//        int a = 15;
//        System.out.println("a = " + a);
//        String string = new String();
//        StringBuffer stringBuffer = new StringBuffer();
//
        /* <-
         Cell a = new Cell(5, 6);
         a.setValue(9);
         a.interdire(9);
         System.out.println(a);
         Cell b = new Cell(7, 7);
         b.interdire(5);
         b.interdire(8);
         System.out.println(a.compareTo(b));
         List<Cell> li = new ArrayList<Cell>();
         li.add(b);
         li.add(a);
         li.sort(null);
         System.out.println(li);
         //*/
        Sudoku s = new Sudoku("4_8___93_\n1__24_5__\n97_8__6__\n6_23_____\n_47___21_\n_____64_3\n__3__1_59\n__1_65__2\n_94___1_8");
        Sudoku[] ss = new Sudoku[1000];
        for (int i = 0; i < ss.length; i++) {
            ss[i] = s;

        }
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
        System.out.println(s);
        //Cell[][] gr = s.getGrille();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < ss.length; i++) {
            ss[i].resoudre();

        }
       // s.resoudre();
        //System.out.println(s);

        long endTime = System.currentTimeMillis();

        System.out.println("Temp pris = " + (endTime - startTime) + " ms");
    }

}
