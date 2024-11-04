package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
@Repository
public class BuildingRepositoryImpl implements BuildingRepository{
	private static String url = "jdbc:mysql://localhost:3306/estatebasic";
    private static String username = "root";
    private static String password = "123456789";
	@Override
	public List<BuildingEntity> findAll(Map<String, Object> params) {
		StringBuilder sql = new StringBuilder("SELECT * FROM building WHERE 1 = 1 ");
		List<BuildingEntity> result = new ArrayList<>();
		try(Connection con = DriverManager.getConnection(url,username, password);
				Statement stm = con.createStatement();
				ResultSet res = stm.executeQuery(sql.toString());){
			while(res.next()) {
				BuildingEntity building = new BuildingEntity();
				building.setName(res.getString("name"));
				building.setStreet(res.getString("street"));
				building.setWard(res.getNString("ward"));
				building.setNumberOfBasement(res.getInt("numberofbasement"));
				result.add(building);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
