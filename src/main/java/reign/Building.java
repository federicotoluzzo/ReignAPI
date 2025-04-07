package reign;

import reign.types.BuildingType;
import reign.types.ResourceType;

import java.util.concurrent.TimeUnit;

public class Building implements Runnable {
    private BuildingType type;
    private ResourceType resource;
    private Resource production;
    private int productionRate;

    public Building(BuildingType type, Resource production) {
        this.type = type;
        this.production = production;
        this.productionRate = switch (type) {
            case QUARRY -> 3;
            case FARM -> 20;
            case FIELD -> 50;
            case HUT -> 10;
            case MINE -> 1;
        };
        this.resource = switch (type){
            case QUARRY -> ResourceType.STONE;
            case FARM -> ResourceType.MEAT;
            case FIELD -> ResourceType.VEGETABLE;
            case HUT -> ResourceType.WOOD;
            case MINE -> ResourceType.IRON;
        };
    }

    public BuildingType getType() {
        return type;
    }

    public Resource collect(){
        Resource collected = production;
        production = new Resource(resource, 0);
        return collected;
    }

    public void run(){
        while(true){
            try {
                TimeUnit.SECONDS.sleep(
                    switch (production.getType()) {
                        case IRON -> 3;
                        case WOOD -> 5;
                        case MEAT -> 30;
                        case STONE -> 2;
                        case VEGETABLE -> 15;
                    }
                );
                production.setQuantity(production.getQuantity() + productionRate);
            } catch (InterruptedException chissene) {}
        }
    }

    public ResourceType getResource() {
        return resource;
    }

    @Override
    public String toString() {
        return String.format("{\"type\":\"%s\", \"resource\":\"%s\", \"production\":%s, \"productionRate\":\"%s\"}", type.toString(), resource.toString(), production, productionRate);
    }
}
