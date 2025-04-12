package net.fuffo.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reign.Reign;
import reign.Terrain;
import reign.network.Client;
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
}