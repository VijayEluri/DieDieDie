package org.diediedie.level;

import java.util.Properties;

import org.diediedie.Entity;
import org.diediedie.level.Level.ObjectType;
import org.diediedie.level.objects.Elevator;
import org.diediedie.level.objects.Radio;
import org.diediedie.level.objects.Switch;

public class LevelObjectFactory
{
    /*
     * Creates a LevelObject based on the properties object.
     * 
     * Some objects are added to specific lists so they can
     * be queried quickly during the game.
     * 
     * Fuck Yeah Polymorphism.
     */
    public static Entity createObject(Properties props) 
    {
        System.out.println("\nLOF.createObject -> " + props);
        
        String type = (String) props.get("type");
        
        if(type.equals(ObjectType.Radio.name()))
        {
            Radio r = new Radio(props);
            System.out.println("\t^ LevelObjectFactory found a radio");
            return r; 
        }
        else if(type .equals(ObjectType.Elevator.name()))
        {
            Elevator e = new Elevator(props);
            System.out.println("\t^ LevelObjectFactory found am Elevator");
            return e;
        }
        else if(type.equals(ObjectType.Switch.name()))
        {
            Switch s = new Switch(props);
            return s;
        }
        else if(type.equals(ObjectType.Drone.name()))
        {
            System.out.println("Found a drone : " 
                    + "");
            // TODO
            
            // why aren't any drones found???
        }
        System.out.println("Couldn't determine type of LevelObject.");
        
        return null;
    }
}
