package reign;

import reign.types.BuildingType;
import reign.types.TerrainType;

import java.util.HashSet;

public class Terrain {
    protected TerrainType type;
    protected HashSet<BuildingType> options;
    protected Building building;

    public Terrain(TerrainType type, HashSet<BuildingType> options) {
        this.type = type;
        this.options = options;
    }

    public TerrainType getType() {
        return type;
    }

    public HashSet<BuildingType> getOptions() {
        return options;
    }

    public Building getBuilding() {
        return building;
    }

    public boolean build(Building building) {
        System.out.println(options.toString());
        if(options.contains(building.getType())) {
            this.building = building;
            Thread t = new Thread(this.building);
            t.start();
            return true;
        }
        return false;
    }

    public static Terrain create(TerrainType type) {
        switch(type){
            case FIELD -> {
                HashSet<BuildingType> options = new HashSet<>();
                options.add(BuildingType.FIELD_FARM);
                options.add(BuildingType.PIG_FARM);
                return new Terrain(type, options);
            }
            case MOUNTAIN -> {
                HashSet<BuildingType> options = new HashSet<>();
                options.add(BuildingType.QUARRY);
                return new Terrain(type, options);
            }
            case FOREST -> {
                HashSet<BuildingType> options = new HashSet<>();
                options.add(BuildingType.LUMBERJACK_HUT);
                return new Terrain(type, options);
            }
        };
        return null;
    }

    @Override
    public String toString(){
        String ret = "{"
                + "\"type\":\"" + type.toString() + "\","
                + "\"options\":\"" + options.toString() + "\","
                + "\"building\":" + building.toString() + "}";
        return ret;
    }
}
