package org.example.combat;

public class main_combat {
    public static character chara;
    public static void main(int g) {
        if (chara == null) chara = new character();
        battle battleD = new battle();
        switch (g) {
            case 1:
                battleD.fight_guardian(chara);
                break;
            case 2:
                battleD.fight_warden(chara);
                break;
            case 3:
                battleD.fight_strigoi(chara);
                break;
        }
    }
}
