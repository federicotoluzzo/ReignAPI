package reign;

import reign.types.ResourceType;

public class Resource {
    private ResourceType type;
    private int quantity;

    public Resource(ResourceType type, int quantity) {
        this.type = type;
        this.quantity = quantity;
    }

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String toString(){
        return String.format("{\"type\": \"%s\", \"quantity\": %d}", type.toString(), quantity);
    }
}
