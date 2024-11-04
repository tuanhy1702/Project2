package com.javaweb.service;

import java.util.List;

import com.javaweb.Model.BuildingDTO;

public interface BuildingService {
	List<BuildingDTO> findAll(String name, Long districtId);
}
