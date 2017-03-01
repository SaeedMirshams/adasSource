/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mir.game.adas;

/**
 *
 * @author 8062439
 */
import java.util.ArrayList;

/**
 * Contains getter and setter method for varialbles
 */
public class AnimalList {

    private ArrayList<Animal> animals = new ArrayList<Animal>();

    public void AddAnimal(Animal animal) {
        this.animals.add(animal);
    }

    public int size() {
        return animals.size();
    }

    Animal get(int index) {
        return animals.get(index);
    }
}
