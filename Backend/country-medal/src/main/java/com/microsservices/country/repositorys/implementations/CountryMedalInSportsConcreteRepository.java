package com.microsservices.country.repositorys.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import java.sql.Statement;

import com.microsservices.country.enums.MedalType;
import com.microsservices.country.models.Country;
import com.microsservices.country.models.CountryMedalInSports;
import com.microsservices.country.models.Medal;
import com.microsservices.country.models.Sport;

@Repository
public class CountryMedalInSportsConcreteRepository {
    private InstaceDatabase instance = InstaceDatabase.getInstance();
    private Connection conn = instance.getConnection();

    public List<CountryMedalInSports> getAllCountrys(){
        // Connection conn = instance.getConnection();
        String sql = "SELECT cms.id AS cms_id, c.id AS country_id, c.name AS country_name, c.flag AS country_flag, m.id AS medal_id, m.type AS medal_type FROM country_medal_in_sports AS cms RIGHT JOIN countrys AS c ON c.id = cms.id_country LEFT JOIN medals AS m ON m.id = cms.id_medal;";
        Statement statement;
        try {
            List<CountryMedalInSports> countryMedalInSports = new ArrayList<>();
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            
            while(resultSet.next()){
                Long idCountry = resultSet.getLong("country_id");
                String name = resultSet.getString("country_name");
                String flag = resultSet.getString("country_flag");
                Country c = new Country(
                    idCountry,
                    name,
                    flag
                );
                Long idMedal = resultSet.getLong("medal_id");
                String medal = resultSet.getString("medal_type");
                Medal m;
                if(idMedal == 0 || medal == null){
                    m = null;
                }else{
                    m = new Medal(
                        idMedal,
                        MedalType.valueOf(medal)
                    );
                }
                

                CountryMedalInSports cms = new CountryMedalInSports(
                    resultSet.getLong("cms_id"),
                    m,
                    c
                );
                countryMedalInSports.add(cms);
            }
            return countryMedalInSports;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }catch(IllegalArgumentException e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        catch(NullPointerException e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        
    }

    public List<CountryMedalInSports> getCountryById(Long id){
        // Connection conn = instance.getConnection();
        String sql = "select cms.id AS cms_id, c.id AS country_id, c.name AS country_name, c.flag AS country_flag, m.id AS medal_id, m.type AS medal_type, s.id AS sport_id, s.description AS sport_description, s.name AS sport_name from country_medal_in_sports AS cms RIGHT JOIN countrys AS c ON c.id = cms.id_country LEFT JOIN medals AS m ON m.id = cms.id_medal LEFT JOIN sports AS s ON s.id = cms.id_sport where c.id = ?;";
        PreparedStatement prepStatement;
        try {
            prepStatement = conn.prepareStatement(sql);
            prepStatement.setLong(1, id);
            ResultSet resultSet = prepStatement.executeQuery();
            List<CountryMedalInSports> cms = new ArrayList<>();
            while(resultSet.next()){
                Country c = country(resultSet);
                Medal m = medal(resultSet);
                Sport s = sport(resultSet);
                Long idCMS  = resultSet.getLong("cms_id");
                cms.add(new CountryMedalInSports(idCMS, s, m, c));
            }
            return cms;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        
    }

    private Country country(ResultSet resultSet) throws SQLException{
        Long idCountry = resultSet.getLong("country_id");
                String name = resultSet.getString("country_name");
                String flag = resultSet.getString("country_flag");
                Country c = new Country(
                    idCountry,
                    name,
                    flag
                );
        return c;
    }

    private Medal medal(ResultSet resultSet) throws SQLException{
        Long idMedal = resultSet.getLong("medal_id");
                String medal = resultSet.getString("medal_type");
                Medal m;
                if(idMedal == 0 && medal == null){
                    m = null;
                }else{
                    m = new Medal(
                        idMedal,
                        MedalType.valueOf(medal)
                    );
                }
        return m;
    }

    private Sport sport(ResultSet resultSet) throws SQLException{
        Long id = resultSet.getLong("sport_id");
        String name = resultSet.getString("sport_name");
        String description = resultSet.getString("sport_description");
        Sport s = null;
        if(id != 0 && name != null && description != null){
            s = new Sport(id, name, description);
        }
        return s;
    }
}
