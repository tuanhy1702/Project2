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
public class BuildingAPI {
	
	@DeleteMapping(value = "/api/building/{id}/{name}")
	public void deleteBuilding(@PathVariable Integer id, @PathVariable String name) {
		System.out.print("da xoa toa nha co id la " + id + " ten la " + name);
	}
	
	 //tìm kiếm toà nhà nào có chữ name trong tên
	@Autowired // biến để khởi tạo interface , tìm các class
	private BuildingService buildingService;
	@GetMapping(value = "/api/building/")
	public List<BuildingDTO> gettBuilding(@RequestParam Map<String, Object> params) {
		List<BuildingDTO> result = buildingService.findAll(params);
		return result;
	}
	
}












