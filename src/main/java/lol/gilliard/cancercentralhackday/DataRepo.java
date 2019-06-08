package lol.gilliard.cancercentralhackday;

import lol.gilliard.cancercentralhackday.DataObjects.CancerType;
import lol.gilliard.cancercentralhackday.DataObjects.Category;
import lol.gilliard.cancercentralhackday.DataObjects.CcProvider;
import lol.gilliard.cancercentralhackday.DataObjects.Keyword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
public class DataRepo implements Data {

    private JdbcTemplate jdbc;

    @Autowired
    public DataRepo(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Category> getCategories() {
        // Iterable<String> categories= (Iterable<String>) jdbc.query("SELECT distinct movie_genre FROM tblMovies ", );
        return jdbc.query("SELECT  id, description FROM category", new RowMapper<Category>() {
            public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Category(
                    rs.getInt("id"),
                    rs.getString("description"),
                    "https://placekitten.com/200/300");
            }
        });
    }

    @Override
    public List<CancerType> getCancerType() {

        return jdbc.query("SELECT  id, description FROM cancer_type", new RowMapper<CancerType>() {
            public CancerType mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new CancerType(
                    rs.getInt("id"),
                    rs.getString("description"));
            }
        });
    }

    @Override
    public List<Keyword> getKeywords() {
        return jdbc.query("select * from keyword where id<=5", new RowMapper<Keyword>() {
            public Keyword mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Keyword(
                    rs.getInt("id"),
                    rs.getString("description"));
            }
        });
    }

    public static final double R = 6372.8; // In kilometers

    public static double haversine(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }

    private double distance(CcProvider ccp, Double lat, Double lon) {
        return haversine(ccp.lat, ccp.lon, lat, lon);
    }

    @Override
    public List<CcProvider> doProviderSearch(final Double lat, final Double lon) {
        List<CcProvider> providers = allProviders();


        // Todo: this sorts by distance but doesn't return the distances in km or anything.
        providers.sort(Comparator.comparingDouble(cc -> distance(cc, lat, lon)));

        for (CcProvider provider : providers) {
            provider.setDistance(distance(provider, lat, lon));
        }

        return providers;

    }


    private List<String> categoriesForProvider(int providerId) {

        List<String> categories = new ArrayList<>();

        jdbc.query("select p.provider_name, provider_id as id, category_id, c.description as name from provider_category pc inner join provider p on pc.provider_id=p.id inner join category c on pc.category_id=c.id", new RowMapper<String>() {
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                if (rs.getInt("id") == providerId){
                     categories.add(rs.getString("name"));
                }
                return null;
            }
        });
        return categories;
    }

    private List<String> keywordsForProvider(int providerId) {

        List<String> categories = new ArrayList<>();

        jdbc.query("select p.provider_name, provider_id as id, keyword_id, k.description as name from provider_keyword pk inner join provider p on pk.provider_id=p.id inner join keyword k on pk.keyword_id=k.id", new RowMapper<String>() {
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                if (rs.getInt("id") == providerId){
                     categories.add(rs.getString("name"));
                }
                return null;
            }
        });
        return categories;
    }

    private List<String> cancerTypesForProvider(int providerId) {

        List<String> categories = new ArrayList<>();

        jdbc.query("select p.provider_name, provider_id as id, cancer_type_id, ct.description as name from provider_cancer_type pct inner join provider p on pct.provider_id=p.id inner join cancer_type ct on pct.cancer_type_id=ct.id", new RowMapper<String>() {
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                if (rs.getInt("id") == providerId){
                     categories.add(rs.getString("name"));
                }
                return null;
            }
        });
        return categories;
    }

    private List<CcProvider> allProviders() {

        return jdbc.query("select distinct p.id as id, p.provider_name as name, p.proposition as description, a.latitude as lat, a.longitude as lon from provider p INNER JOIN address a on a.provider_id=p.id;\n;", new RowMapper<CcProvider>() {
            public CcProvider mapRow(ResultSet rs, int rowNum) throws SQLException {
                CcProvider ccProvider = new CcProvider(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("lat"),
                    rs.getDouble("lon"));

                ccProvider.categories.addAll(categoriesForProvider(rs.getInt("id")));
                ccProvider.keywords.addAll(keywordsForProvider(rs.getInt("id")));
                ccProvider.cancerTypes.addAll(cancerTypesForProvider(rs.getInt("id")));

                return ccProvider;
            }
        });
    }

}
