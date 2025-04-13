package net.fuffo.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reign.Building;
import reign.Reign;
import reign.Resource;
import reign.Terrain;
import reign.network.Client;
import reign.types.BuildingType;
import reign.types.ResourceType;
import reign.types.TerrainType;

import java.util.ArrayList;

@RestController
public class ReignController {
    Reign reign;

    @GetMapping("/reign")
    public Reign getReignInfo() {
        if(reign == null) {
            return null;
        }
        return reign;
    }

    @GetMapping("/get_peers")
    public ArrayList<String> getPeers() {
        return Client.getReigns();
    }

    @GetMapping("/create/{name}")
    public Reign getReignInfo(@PathVariable String name) {
        ArrayList<String> data = Client.create(name);
        if(data != null && data.size() == 7){
            reign = new Reign(name, Integer.parseInt(data.get(0)));

            reign.setTerrains(new Terrain[Integer.parseInt(data.get(1))]);

            for(int i = 2; i < data.size(); i++){
                reign.addTerrain(Terrain.create(
                        switch (data.get(i)){
                            case "FIELD" -> TerrainType.FIELD;
                            case "MOUNTAIN" -> TerrainType.MOUNTAIN;
                            case "FOREST" -> TerrainType.FOREST;
                            default -> TerrainType.FIELD;
                        }
                ));
                reign.getWarehouse().addTerrain(reign.getTerrain(i - 2));
            }

            return reign;
        }
        return null;
    }

    @GetMapping("/test")
    public Reign getTest() {
        Reign test = new Reign("test", 1337);
        test.addTerrain(Terrain.create(TerrainType.FOREST));

        return test;
    }

    @GetMapping("/build/{terrainType}+{buildingType}")
    public boolean build(@PathVariable String terrainType, @PathVariable String buildingType) {
        boolean result = Client.build(TerrainType.valueOf(terrainType), BuildingType.valueOf(buildingType));
        if (result) {
            for (Terrain terrain : reign.getMap()){
                if(terrain.getType() == TerrainType.valueOf(terrainType) && terrain.getBuilding() == null){
                    Resource resource = switch (buildingType){
                        case "LUMBERJACK_HUT" -> new Resource(ResourceType.WOOD, 0);
                        case "QUARRY" -> new Resource(ResourceType.STONE, 0);
                        case "FIELD_FARM" -> new Resource(ResourceType.VEGETABLE, 0);
                        case "PIG_FARM" -> new Resource(ResourceType.MEAT, 0);
                        default -> throw new IllegalStateException("Unexpected value: " + buildingType);
                    };
                    terrain.build(new Building(BuildingType.valueOf(buildingType), resource));
                    return true;
                }
            }
        }
        return false;
    }
}