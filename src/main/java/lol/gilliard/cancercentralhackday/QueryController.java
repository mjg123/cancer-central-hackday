package lol.gilliard.cancercentralhackday;

import lol.gilliard.cancercentralhackday.DataObjects.CancerType;
import lol.gilliard.cancercentralhackday.DataObjects.Category;
import lol.gilliard.cancercentralhackday.DataObjects.CcProvider;
import lol.gilliard.cancercentralhackday.DataObjects.Keyword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
public class QueryController {

    @Autowired
    public Data data;

    @CrossOrigin
    @GetMapping(path="/call", produces="application/json")
    @ResponseBody
    public List<String> doQuery(){
        return Arrays.asList("One", "Two", "Three");
    }

    @CrossOrigin
    @GetMapping(path="/categories", produces="application/json")
    @ResponseBody
    public List<Category> getCategories(){
        return data.getCategories();
    }

    @CrossOrigin
    @GetMapping(path="/CancerType", produces="application/json")
    @ResponseBody
    public List<CancerType> getCancerType(){
        return data.getCancerType();
    }

    @CrossOrigin
    @GetMapping(path="/Keyword", produces="application/json")
    @ResponseBody
    public List<Keyword> getKeyword(){
        return data.getKeywords();
    }

    @CrossOrigin
    @GetMapping(path="/query", produces="application/json")
    @ResponseBody
    public List<CcProvider> providerSearch(@RequestParam double lat,
                                           @RequestParam double lon){
        return data.doProviderSearch(lat, lon);
    }
}
