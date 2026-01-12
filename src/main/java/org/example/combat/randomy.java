package org.example.combat;
import java.util.Random;
public class randomy {
    private static Random rand;

    public randomy(){
        rand=new Random();
    }



    public static int pick(int min, int max){
        return rand.nextInt((max-min)+1)+min;
    }

}
