package net.fuffo.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reign.Building;
import reign.Reign;
import reign.Resource;
import reign.Terrain;
import reign.types.BuildingType;
import reign.types.ResourceType;
import reign.types.TerrainType;

@RestController
public class ReignController {
    Reign test;

    @GetMapping("/reign")
    public String getReignInfo() {
        if(test == null) {
            test = new Reign("test_name", 1234);
            Terrain t = Terrain.create(TerrainType.FIELD);
            t.build(new Building(BuildingType.FIELD, new Resource(ResourceType.VEGETABLE, 0)));
            test.addTerrain(t);
        }
        return test.toJSON();
    }
}