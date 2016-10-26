/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author cdi415
 */
public class Essai {

    public static void main(String[] args) {
        Set<Integer> valeursPossibles = new HashSet<Integer>();
        valeursPossibles.add(2);
        valeursPossibles.add(6);
        System.out.println("valeursPossible = " + valeursPossibles);
        Set<Integer> valeursImposs = new HashSet<Integer>();
        valeursImposs.add(5);
        valeursImposs.add(8);
        valeursImposs.add(6);
        valeursPossibles.removeAll(valeursImposs);
        System.out.println("valeursImposs = " + valeursImposs);
        System.out.println("valeursPossible = " + valeursPossibles);

        System.out.println("Ea".hashCode());
        System.out.println("FB".hashCode());
        String wor = "F";
        String word = wor + "B";
        switch (word) {
            case "Ea":
                System.out.println("Ea");
                break;
            case "FB":
                System.out.println("fb");
                break;
        }

    }
}
