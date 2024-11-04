package com.javaweb.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
	// lấy thông tin từ server qua client = param
//	@RequestMapping(value = "/api/building/", method = RequestMethod.GET)
//	public void getBuilding(@RequestParam(value = "name", required = false) String name,
//			@RequestParam(value = "numberOfBasement", required = false) Integer numberOfBasement,
//			@RequestParam(value = "ward", required = false) String ward) {
//		System.out.print(name + " " + numberOfBasement + " " + ward);
//	}

	// lấy thông tin từ server qua client = body
//	@RequestMapping(value = "/api/building/", method = RequestMethod.POST)
//	public void getBuilding(@RequestBody BuildingDTO buildingDTO) {
//		System.out.print("ok");
//	}
	
	// lấy thông tin từ server qua client = param và trả về client dạng JSON
//	@GetMapping(value = "/api/building/")
//	public BuildingDTO getBuilding(@RequestParam(value = "name", required = false) String name,
//			@RequestParam(value = "numberOfBasement", required = false) Integer numberOfBasement,
//			@RequestParam(value = "ward", required = false) String ward) {
//		// after processing the DB
//		BuildingDTO result = new BuildingDTO();
//		result.setName(name);
//		result.setNumberOfBasement(numberOfBasement);
//		result.setWard(ward);
//		return result;
//	}
	
	// lấy thông tin từ server qua client = body và trả về client có thông tin list dạng JSON
//	@GetMapping(value = "/api/building/")
//	public List<BuildingDTO> getBuilding (@RequestBody BuildingDTO building) {
//		//processing the database is done
//		List<BuildingDTO> listBuildings = new ArrayList<>();
//		BuildingDTO buildingDT01 = new BuildingDTO();
//		buildingDT01.setName("ABC Building");
//		buildingDT01.setNumberOfBasement(3);
//		buildingDT01.setWard("Tan Mai");
//		BuildingDTO buildingDTO2 = new BuildingDTO();
//		buildingDTO2.setName("ACM Tower"); 
//		buildingDTO2.setNumberOfBasement(2);
//		buildingDTO2.setWard("Da Cao");
//		listBuildings.add(buildingDT01);
//		listBuildings.add(buildingDTO2);
//		return listBuildings;
//	}
	
	// try catch lỗi báo về không chia cho 0 về BE
//	@GetMapping(value = "/api/building/")
//	public Object getBuilding(@RequestBody BuildingDTO building) {
//		// kiểm tra lỗi
//		try {
//			System.out.print(5/0);
//		}catch(Exception e) {
//			ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
//			errorResponseDTO.setError(e.getMessage());
//			List<String> details = new ArrayList<>();
//			details.add("không chia được cho 0");
//			errorResponseDTO.setDetail(details);
//			return errorResponseDTO;
//		}
//		BuildingDTO result = new BuildingDTO();
//		result.setName(name);
//		result.setNumberOfBasement(numberOfBasement);
//		result.setWard(ward);
//		return result;
//	}	
	
	
	// try catch kiểm tra thông tin nhập vào có đủ không và thông báo lại bằng cách tạo exception về BE
//	@PostMapping(value = "/api/building/")
//	public Object getBuilding (@RequestBody BuildingDTO building) {
//		try {
//			valiDate(building);
//		}catch(FieldRequiredException e) {
//			ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
//			errorResponseDTO.setError(e.getMessage());
//			List<String> details = new ArrayList<>();
//			details.add("check lại name hoặc NumberOfBasement đi vì bị null đó!");
//			errorResponseDTO.setDetail(details);
//			return errorResponseDTO;
//		}	
//		return null;
//	}
	// báo đúng lỗi nhập null không cần try catch
//		@PostMapping(value = "/api/building/")
//		public Object postBuilding (@RequestBody BuildingDTO building) {	
//			valiDate(building);
//			return null;
//		}
//	public void valiDate(BuildingDTO buildingDTO){
//		if(buildingDTO.getName() == null || buildingDTO.getName().equals("")|| buildingDTO.getNumberOfBasement()== null) {
//			throw new FieldRequiredException("name or NumberOfBasement is null");
//		}
//	}
//	// báo đúng lỗi 
//	@PostMapping(value = "/api/building/")
//	public Object postBuilding (@RequestBody BuildingDTO building) {	
//		System.out.print(5/0);
//		return null;
//	}
	
	// ghi dữ liệu dạng list từ client và trả về BuildingDTO
//	@PostMapping(value = "/api/building/")
//	public BuildingDTO getBuilding1(@RequestBody BuildingDTO building) {
//		return building;
//	}

//	// ghi dữ liệu dạng list từ client và trả về String
//	@PostMapping(value = "/api/building/")
//	public String putBuilding(@RequestBody List<BuildingDTO> building) {
//		return "da nhap thanh cong";
//		
//	}
    
//	@DeleteMapping(value = "/api/building/{id}/{name}")
//	public void deleteBuilding(@PathVariable Integer id, @PathVariable String name) {
//		System.out.print("da xoa toa nha co id la " + id + " ten la " + name);
//	}
	
	 //tìm kiếm toà nhà nào có chữ name trong tên
	@Autowired // biến để khởi tạo interface , tìm các class
	private BuildingService buildingService;
	@GetMapping(value = "/api/building/")
	public List<BuildingDTO> gettBuilding(@RequestParam(name="name", required = false) String name,
										  @RequestParam(name="districtId", required = false) Long districtId,
										  @RequestParam(name="typeCode", required = false) List<String> typeCode) {
		List<BuildingDTO> result = buildingService.findAll(name, districtId);
		return result;
	}
	
}












