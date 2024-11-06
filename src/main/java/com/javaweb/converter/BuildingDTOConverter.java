package com.javaweb.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.javaweb.Model.BuildingDTO;
import com.javaweb.repository.DistrictRepository;
import com.javaweb.repository.RentAreaRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.DistrictEntity;
import com.javaweb.repository.entity.RentAreaEntity;

@Component// đánh dấu class này là bean
public class BuildingDTOConverter {
	@Autowired
	private DistrictRepository districtRepository;
	@Autowired
	private RentAreaRepository rentAreaRepository;
	@Autowired
	private ModelMapper modelMapper;
	
	public BuildingDTO toBuildingDTO(BuildingEntity item) {
		BuildingDTO building = modelMapper.map(item, BuildingDTO.class);
		// modelMapper sẽ tự set các field có cùng tên và kiểu dữ liệu
		
		DistrictEntity districtEntity = districtRepository.findNameById(item.getDistrictid()) ;
		building.setAddress(item.getStreet() + "," + item.getWard()+","+districtEntity.getName());
		List<RentAreaEntity> rentAreas = rentAreaRepository.getValueByBuildingId(item.getId()); 
		String areaResult = rentAreas.stream().map(it -> it.getValue().toString()).collect(Collectors.joining(","));
		building.setRentArea(areaResult);
		
//		building.setNumberOfBasement(item.getNumberOfBasement());
//		building.setManagerName(item.getManagerName());
//		building.setManagerPhoneNumber(item.getManagerPhoneNumber());
//		building.setRentPrice(item.getRentPrice());
//		building.setBrokerageFee(item.getBrokerageFee());
//		building.setFloorArea(item.getFloorArea());
//		building.setName(item.getName());

		return building;
	}
}
