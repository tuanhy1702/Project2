package com.javaweb.repository.impl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.utils.ConnectionJDBCUtil;
import com.javaweb.utils.NumberUtil;
import com.javaweb.utils.StringUtil;
@Repository
@PropertySource("classpath:application.properties")
public class JDBCBuildingRepositoryImpl implements BuildingRepository{
	
	@Value("${spring.datasource.url}")
	private String url;
	
	@Value("${spring.datasource.username}")
	private String username;
	
	@Value("${spring.datasource.password}")
	private  String password;
	
	@PersistenceContext
	private EntityManager entityManager;
    
    public static void joinTable(BuildingSearchBuilder buildingSearchBuilder, StringBuilder sql) {
    	Long staffId = buildingSearchBuilder.getStaffId();
    	if(staffId != null) {
    		sql.append("INNER JOIN assignmentbuilding ON b.id = assignmentbuilding.buildingid ");
    	}
    	List<String>typeCode = buildingSearchBuilder.getTypeCode();
    	if(typeCode != null && typeCode.size() != 0) {
    		sql.append("INNER JOIN buildingrenttype ON b.id = buildingrenttype.buildingid ");
    		sql.append("INNER JOIN renttype ON renttype.id = buildingrenttype.renttypeid ");
    	}
    }
    
    public static void queryNomal(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where) {
    	try {
    		Field[] fields = BuildingSearchBuilder.class.getDeclaredFields();
    		for(Field item : fields) {
    			item.setAccessible(true);
    			String 	fieldName = item.getName();
    			if(!fieldName.equals("staffId") && !fieldName.equals("typeCode")&&!fieldName.startsWith("area") && !fieldName.startsWith("rentPrice")) {
    				Object value = item.get(buildingSearchBuilder); 
    				if(value != null) {
        				if(item.getType().getName().equals("java.lang.Long") || item.getType().getName().equals("java.lang.Integer")) {
        					where.append(" AND b." + fieldName + " = " + value);
        				}else if(item.getType().getName().equals("java.lang.String")){
        					where.append(" AND b." + fieldName + " LIKE '%" + value +"%' ");
        				}
        			}
    			}
    		}
    	}catch(Exception ex){
    		ex.printStackTrace();
    		
    		
    	}
    }
    
    public static void querySpecial(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where) {
    	Long staffId = buildingSearchBuilder.getStaffId();
    	if(staffId != null) {
    		where.append(" AND assignmentbuilding.staffId = " + staffId);
    	}
    	
    	Long rentAreaFrom = buildingSearchBuilder.getAreaFrom();
    	Long rentAreaTo = buildingSearchBuilder.getAreaTo();
    	if(rentAreaTo != null || rentAreaFrom != null ) {
    		where.append(" AND EXISTS (SELECT * FROM rentarea r WHERE b.id = r.buildingid ");
    		if(rentAreaFrom != null) {
    			where.append(" AND r.value >=" + rentAreaFrom);
    		}
    		if(rentAreaTo != null) {
    			where.append(" AND r.value <=" + rentAreaTo);
    		}
    		where.append(") ");
    	}
    	
    	Long rentPriceFrom = buildingSearchBuilder.getRentPriceFrom();
    	Long rentPriceTo = buildingSearchBuilder.getRentPriceTo();
    	if(rentPriceTo != null || rentPriceFrom != null) {
    		if(rentPriceFrom != null) {
    			where.append(" AND b.rentprice >=" + rentPriceFrom);
    		}
    		if(rentPriceTo != null) {
    			where.append(" AND b.rentprice <=" + rentPriceTo);
    		}
    	}
    	// java7
//    	if(typeCode != null && typeCode.size() != 0) {
//    		List<String> code = new ArrayList<>();
//    		for(String item : typeCode) {
//    			code.add("'" + item + "'");
//    		}
//    		//String.join là để nối các phần tử của mảng xâu typeCode bằng dấu ,
//    		where.append(" AND renttype.code IN(" + String.join(",", code) + ") ");
//    	}
    	// java 8
    	List<String> typeCode = buildingSearchBuilder.getTypeCode();
    	if(typeCode != null && typeCode.size() != 0) {
    		where.append(" AND(");
    		String sql = typeCode.stream().map(it -> "renttype.code LIKE" + "'%" + it +"%'").collect(Collectors.joining(" OR "));
    		where.append(sql);
    		where.append(" ) ");
    	}
    	
    }
	@Override
	public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder) {
		StringBuilder sql = new StringBuilder("SELECT * FROM building b ");
		joinTable(buildingSearchBuilder, sql);
		StringBuilder where = new StringBuilder(" WHERE 1=1 ");
		queryNomal(buildingSearchBuilder, where);
		querySpecial(buildingSearchBuilder, where);
		where.append(" GROUP BY b.id;");
		sql.append(where);
		Query query = entityManager.createNativeQuery(sql.toString(), BuildingEntity.class);
		return query.getResultList();
	}
}
