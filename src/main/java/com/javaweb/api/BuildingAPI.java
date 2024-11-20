package com.javaweb.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.Model.BuildingDTO;
import com.javaweb.Model.ErrorResponseDTO;
import com.javaweb.service.BuildingService;

import customException.FieldRequiredException;

@RestController
@PropertySource("classpath:application.properties")
public class BuildingAPI {
	
	@Autowired // biến để khởi tạo interface , tìm các class
	private BuildingService buildingService;
	
	@Value("${dev.khuc}")
	private String data;
	
	@GetMapping(value = "/api/building/")
	public List<BuildingDTO> gettBuilding(@RequestParam Map<String, Object> params,
										  @RequestParam(name = "typeCode", required = false) List<String> typeCode) {
		List<BuildingDTO> result = buildingService.findAll(params, typeCode);
		return result;
	}
		
//	@DeleteMapping(value = "/api/building/{id}/{name}")
//	public void deleteBuilding(@PathVariable Integer id, @PathVariable String name) {
//		System.out.print("da xoa toa nha co id la " + id + " ten la " + name);
//	}
	
	@DeleteMapping(value = "/api/building/{id}")
	public void deleteBuilding(@PathVariable Integer id) {
		System.out.print(data);
	}
	
	 
	
}












