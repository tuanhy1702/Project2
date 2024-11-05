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
import com.javaweb.utils.NumberUtil;
import com.javaweb.utils.StringUtil;
@Repository
public class BuildingRepositoryImpl implements BuildingRepository{
	private static String url = "jdbc:mysql://localhost:3306/estatebasic";
    private static String username = "root";
    private static String password = "123456789";
    
    public static void joinTable(Map<String, Object> params, List<String> typeCode, StringBuilder sql) {
    	String staffId = (String)params.get("staffId");
    	if(StringUtil.checkString(staffId)) {
    		sql.append("INNER JOIN assignmentbuilding ON b.id = assignmentbuilding.buildingid ");
    	}
    	if(typeCode != null && typeCode.size() != 0) {
    		sql.append("INNER JOIN buildingrenttype ON b.id = buildingrenttype.buildingid ");
    		sql.append("INNER JOIN renttype ON renttype.id = buildingrenttype.renttypeid ");
    	}
    	String rentAreaFrom = (String)params.get("areaFrom");
    	String rentAreaTo = (String)params.get("areaTo");
    	if(StringUtil.checkString(rentAreaTo) || StringUtil.checkString(rentAreaFrom)) {
    		sql.append("INNER JOIN rentarea ON b.id = rentarea.buildingid ");
    	}
    }
    
    public static void queryNomal(Map<String, Object> params, StringBuilder where) {
    	for(Map.Entry<String, Object> it : params.entrySet()) {
    		if(!it.getKey().equals("staffId") && !it.getKey().equals("typeCode")&&!it.getKey().startsWith("area") && !it.getKey().startsWith("rentPrice")) {
    			String value = it.getValue().toString();
    			if(StringUtil.checkString(value)) {
    				if(NumberUtil.isNumber(value) == true) {
    					where.append(" AND b." + it.getKey() + " = " + value);
    				}else {
    					where.append(" AND b." + it.getKey() + " LIKE '%" + value +"%' ");
    				}
    			}
    		}
    	}
    }
    
    public static void querySpecial(Map<String, Object> params, List<String> typeCode, StringBuilder where) {
    	String staffId = (String)params.get("staffId");
    	if(StringUtil.checkString(staffId)) {
    		where.append(" AND assignmentbuilding.staffId = " + staffId);
    	}
    	
    	String rentAreaFrom = (String)params.get("areaFrom");
    	String rentAreaTo = (String)params.get("areaTo");
    	if(StringUtil.checkString(rentAreaTo) || StringUtil.checkString(rentAreaFrom)) {
    		if(StringUtil.checkString(rentAreaFrom)) {
    			where.append(" AND rentarea.value >=" + rentAreaFrom);
    		}
    		if(StringUtil.checkString(rentAreaTo)) {
    			where.append(" AND rentarea.value <=" + rentAreaTo);
    		}
    	}
    	
    	String rentPriceFrom = (String)params.get("rentPriceFrom");
    	String rentPriceTo = (String)params.get("rentPriceTo");
    	if(StringUtil.checkString(rentPriceTo) || StringUtil.checkString(rentPriceFrom)) {
    		if(StringUtil.checkString(rentPriceFrom)) {
    			where.append(" AND b.rentprice >=" + rentPriceFrom);
    		}
    		if(StringUtil.checkString(rentPriceTo)) {
    			where.append(" AND b.rentprice <=" + rentPriceTo);
    		}
    	}
    	
    	if(typeCode != null && typeCode.size() != 0) {
    		List<String> code = new ArrayList<>();
    		for(String item : typeCode) {
    			code.add("'" + item + "'");
    		}
    		//String.join là để nối các phần tử của mảng xâu typeCode bằng dấu ,
    		where.append(" AND renttype.code IN(" + String.join(",", code) + ") ");
    	}
    	
    }
	@Override
	public List<BuildingEntity> findAll(Map<String, Object> params, List<String> typeCode) {
		StringBuilder sql = new StringBuilder("SELECT b.id, b.name, b.districtid, b.street, b.ward, b.numberofbasement, b.floorarea, b.rentprice,"
				+ "b.managername, b.managerphonenumber, b.servicefee, b.brokeragefee FROM building b ");
		joinTable(params, typeCode, sql);
		StringBuilder where = new StringBuilder(" WHERE 1=1 ");
		queryNomal(params, where);
		querySpecial(params, typeCode, where);
		where.append(" GROUP BY b.id;");
		sql.append(where);
		
		List<BuildingEntity> result = new ArrayList<>();
		try(Connection con = DriverManager.getConnection(url,username, password);
				Statement stm = con.createStatement();
				ResultSet rs = stm.executeQuery(sql.toString());){
			while(rs.next()) {
				BuildingEntity buildingEntity = new BuildingEntity();
				buildingEntity.setId(rs.getLong("b.id"));
				buildingEntity.setName(rs.getString("b.name"));
				buildingEntity.setWard(rs.getString("b.ward"));
				buildingEntity.setDistrictid(rs.getLong("b.districtid"));
				buildingEntity.setStreet(rs.getString("b.street"));
				buildingEntity.setNumberOfBasement(rs.getLong("b.numberofbasement"));
				buildingEntity.setFloorArea(rs.getLong("b.floorarea"));
				buildingEntity.setRentPrice(rs.getLong("b.rentprice"));
				buildingEntity.setServiceFee(rs.getString("b.servicefee"));
				buildingEntity.setBrokerageFee(rs.getLong("b.brokeragefee"));
				buildingEntity.setManagerName(rs.getString("b.managername"));
				buildingEntity.setManagerPhoneNumber(rs.getString("b.managerphonenumber"));
				result.add(buildingEntity);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
