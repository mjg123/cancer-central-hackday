package lol.gilliard.cancercentralhackday;

import lol.gilliard.cancercentralhackday.DataObjects.CancerType;
import lol.gilliard.cancercentralhackday.DataObjects.Category;
import lol.gilliard.cancercentralhackday.DataObjects.CcProvider;
import lol.gilliard.cancercentralhackday.DataObjects.Keyword;

import java.util.List;

public interface Data {

    List<Category> getCategories();

    List<CancerType> getCancerType();

    List<Keyword> getKeywords();

    List<CcProvider> doProviderSearch(Double lat, Double lon);
}
