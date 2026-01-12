package org.example.combat;

public class Warden extends enemy {

    public Warden() {
        set();
    }

    public void set()
    {

        this.health = 200;
        this.name = "Warden of the Pyre";

        this.can_use_shield = true;

        this.abilities_dropped.put("Hellfire Cataclysm", 50);


        this.abilities.put("Hellfire Cataclysm", 50);
        this.abilities.put("Magma Cleave", 35);
        this.abilities.put("Searing Scorch", 20);

        info[0] = "Wields three devastating attacks: Hellfire, Magma Cleave, and Scorch.";
        info[1] = "His attacks deal massive damage; blocking is essential.";
        info[2] = "The heat is intense—prolonged battles will exhaust you.";
        info[3] = "He embodies the Eternal Pyre. Expect no mercy.";

        dialogue[0] = "I am flame incarnate. You will burn before you earn.";
        dialogue[1] = "The Eternal Pyre does not give freely. It demands pain.";
        dialogue[2] = "Heal yourself if you can. I will scorch your soul.";
    }
}