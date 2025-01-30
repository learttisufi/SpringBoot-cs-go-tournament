package com.example.ninth_assigment;


import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



@RestController
public class DBController {

    private static final String url = "jdbc:sqlite:src/main/resources/DB/ninth_assigment.db";

    @RequestMapping("/allgroups")
    public List<CsGoTournament> selectAllGroups() {
        String selectSQL = "Select * from Tournament";
        List<CsGoTournament> csGoTournaments = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectSQL);
            while (resultSet.next()) {
                CsGoTournament csGoTournament = new CsGoTournament();
                csGoTournament.setId(resultSet.getInt("Id"));
                csGoTournament.setGroup(resultSet.getString("Group"));
                csGoTournament.setLocation(resultSet.getString("Location"));
                csGoTournament.setCaptain(resultSet.getString("Captain"));
                csGoTournament.setRank(resultSet.getInt("Rank"));
                csGoTournaments.add(csGoTournament);
            }
            statement.close();
            connection.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return csGoTournaments;


    }

    @GetMapping("/csgo-groups/{id}")
    public CsGoTournament getTournament(@PathVariable Integer id) {

        String selectSQL = "Select * From Tournament Where Id = ?";
        CsGoTournament csGoTournament = new CsGoTournament();

        try {
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                csGoTournament.setId(resultSet.getInt("Id"));
                csGoTournament.setGroup(resultSet.getString("Group"));
                csGoTournament.setLocation(resultSet.getString("Location"));
                csGoTournament.setCaptain(resultSet.getString("Captain"));
                csGoTournament.setRank(resultSet.getInt("Rank"));
            }
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return csGoTournament;
    }

    @PostMapping("/newgroup")
    public CsGoTournament insertGroup(@RequestBody CsGoTournament csGoTournament) {
        String group = csGoTournament.getGroup();
        String location = csGoTournament.getLocation();
        String captain = csGoTournament.getCaptain();
        int rank = csGoTournament.getRank();
        String insertSQL = "Insert into Tournament('Group',Location,"
                + "Captain,Rank)values(?,?,?,?)";

        try {
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1,group);
            preparedStatement.setString(2,location);
            preparedStatement.setString(3,captain);
            preparedStatement.setInt(4,rank);
            int count = preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            if (count==1) {
                return csGoTournament;
            }
            else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


        @DeleteMapping ("/deletegroup/{id}")
        public CsGoTournament deleteGroup(@PathVariable Integer id) {
        String deleteSQL = "Delete From Tournament Where Id = ?";
        CsGoTournament csGoTournament = new CsGoTournament();

            try {
                Connection connection = DriverManager.getConnection(url);
                PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
                preparedStatement.setInt(1,id);

                int count = preparedStatement.executeUpdate();
                preparedStatement.close();
                connection.close();

                if (count==1) {
                    return csGoTournament;

                }
                else {
                    return null;
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

        @PutMapping ("/updategroup/{id}")
        CsGoTournament updateGroup(@PathVariable Integer id, @RequestBody CsGoTournament csGoTournament) {
        String updateSQL = "Update Tournament Set Rank = ?, Captain = ? Where Id = ?";

            try {
                Connection connection = DriverManager.getConnection(url);
                PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
                int updatedValue = csGoTournament.getRank();
                String updatedValue1=csGoTournament.getCaptain();
                preparedStatement.setInt(1,updatedValue);
                preparedStatement.setString(2,updatedValue1);
                preparedStatement.setInt(3,id);
                int count = preparedStatement.executeUpdate();

                if (count == 1) {
                    return csGoTournament;
                }
                else {
                    return null;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

}

