package reign;

import reign.types.ResourceType;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Warehouse implements Runnable {
    private Resource[] warehouse;
    private Terrain[] terrains;

    public Warehouse(Terrain[] terrains) {
        initWarehouse();
        this.terrains = terrains;
        Thread t = new Thread(this);
        t.start();
    }

    private void initWarehouse() {
        warehouse = new Resource[ResourceType.values().length];
        for (int i = 0; i < warehouse.length; i++) {
            warehouse[i] = new Resource(ResourceType.values()[i], 0);
        }
    }

    @Override
    public void run() {
        while (true){
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for (int i = 0; i < terrains.length; i++) {
                if(terrains[i] != null){
                    if(terrains[i].getBuilding() != null){
                        for (Resource r : warehouse) {
                            if (r.getType() == terrains[i].getBuilding().getResource()){
                                r.setQuantity(r.getQuantity() + terrains[i].getBuilding().collect().getQuantity());
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public String toString(){
        String ret = "";
        ret += "federicotoluzzo.Warehouse: \n";
        for (int i = 0; i < warehouse.length; i++) {
            Resource r = warehouse[i];
            ret += (i == warehouse.length - 1 ? "\u2514" : "\u251c") + r.toString() + "\n";
        }
        return ret;
    }

    public String toJSON(){
        String ret = "{\"" + warehouse[0].getType() + "\":" + warehouse[0].getQuantity() + "";
        for (int i = 1; i < warehouse.length; i++) {
            Resource r = warehouse[i];
            ret += ",\"" + r.getType() + "\":";
            ret += "\"" + r.getQuantity() + "\"";
        }
        ret += "}";
        return ret;
    }

    public Resource[] getWarehouse() {
        return warehouse;
    }

    public void addTerrain(Terrain t){
        for (int i = 0; i < terrains.length; i++) {
            if(terrains[i] == null){
                terrains[i] = t;
                return;
            }
        }
    }
}
