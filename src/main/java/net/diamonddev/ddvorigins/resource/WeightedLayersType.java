package net.diamonddev.ddvorigins.resource;

import com.google.gson.annotations.SerializedName;
import io.github.apace100.origins.origin.OriginLayer;
import net.diamonddev.ddvorigins.DDVOrigins;
import net.diamonddev.ddvorigins.util.WeightedList;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionResourceType;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class WeightedLayersType implements CognitionResourceType {

    public static final String WEIGHTS = "weights";
    public static WeightedList<OriginLayer> WEIGHTMAP = new WeightedList<>();

    @Override
    public Identifier getId() {
        return DDVOrigins.id("weighted_layers");
    }

    @Override
    public void addJsonKeys(ArrayList<String> keys) {
        keys.add(WEIGHTS);
    }

    public static class Serialized {
        @SerializedName(WEIGHTS) public ArrayList<Weight> weights;

        public static class Weight {
            @SerializedName("key") public String key;
            @SerializedName("weight") public int weight;
        }
    }
}
