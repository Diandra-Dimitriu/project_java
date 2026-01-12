package org.example.combat;
public class Guardian extends enemy {

    public Guardian() {
        set();
    }

     public void set()
    {
        this.health = 100;
        this.name = "Guardian of Whispers";
        this.can_use_shield= false;

        this.abilities_dropped.put("Hammer", 35);

        this.abilities.put("Hammer",35 );
        this.abilities.put("Slash",20);
        this.abilities.put("Stomp", 10);

        info[0]="Has 3 abilities: Hammer, Slash and Stomp";
        info[1]="Shields are useless against him";
        info[2]="Has lost the ability to block";
        info[3]="Don't waste away your turns!";

        dialogue[0]="You dare disturb the silence of my woods?";
        dialogue[1]="Strike if you must, but know that every tree watches you.";
        dialogue[2]="Only those who respect the forest may wield its strength.";
    }
}
