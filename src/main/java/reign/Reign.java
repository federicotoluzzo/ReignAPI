package reign;

import java.util.Arrays;

public class Reign {
    private Warehouse warehouse;
    private String name;
    private int gold;
    private Terrain[] map;

    public Reign(String name, int gold) {
        this.name = name;
        this.gold = gold;

        map = new Terrain[5];
        warehouse = new Warehouse(map);
    }

    public void addTerrain(Terrain terrain) {
        for (int i = 0; i < map.length; i++) {
            if(map[i] == null) {
                map[i] = terrain;
                return;
            }
        }
        throw new IndexOutOfBoundsException("Too many terrains.");
    }

    public void printWarehouse() {
        System.out.println(warehouse);
    }

    public Terrain getTerrain(int index){
        return map[index];
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public String getName() {
        return name;
    }

    public int getGold() {
        return gold;
    }

    public Terrain[] getMap() {
        return map;
    }

    public String toJSON() {
        String ret = String.format("{\"name\":\"%s\",\"gold\":%d,\"warehouse\":%s,\"map\":%s}", name, gold, warehouse.toJSON(), Arrays.toString(map));
        return ret;
    }

    public void save(){

    }

    public void setTerrains(Terrain[] terrains) {
        map = Arrays.copyOf(terrains, terrains.length);
    }
}
