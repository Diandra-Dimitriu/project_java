package org.example.combat;

public class Strigoi extends enemy {

    public Strigoi() {
        set();
    }

    public void set()
    {
        this.health = 300;
        this.name = "Strigoi of Hollowdeep";

        this.can_use_shield = true;

        this.abilities_dropped.put("Obsidian Crush", 70);

        this.abilities.put("Obsidian Crush", 70);
        this.abilities.put("Vampiric Bite", 45);
        this.abilities.put("Echoing Scream", 35);


        info[0] = "Lurks in the dark using Obsidian Crush and Vampiric Bite.";
        info[1] = "His scream disorients prey in the tight tunnels.";
        info[2] = "He feeds on the life of intruders to sustain his undeath.";
        info[3] = "The cursed ore has hardened his skin like stone.";

        dialogue[0] = "You want the cursed ore? Then fight like the damned.";
        dialogue[1] = "This cavern has swallowed stronger men than you.";
        dialogue[2] = "Your bones will shore up these tunnels.";
    }
}