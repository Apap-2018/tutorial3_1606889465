package com.apap.tutorial3.model;
import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Service;

@Service
public class PilotInMemoryService implements PilotService {
	private List<PilotModel> archivePilot;
	
	public PilotInMemoryService() {
		archivePilot = new ArrayList<>();
	}
	
	@Override
	public void addPilot(PilotModel pilot) {
		archivePilot.add(pilot);

	}
	
	@Override
	public List<PilotModel> getListPilot(){
		return archivePilot;
	}
	
	@Override
	public PilotModel getPilotDetailByLicenseNumber(String licenseNumber) {
		for (PilotModel temp : archivePilot) {
			if (temp.getLicenseNumber().equals(licenseNumber)) {
				return temp;
			}
			
		}
		return null;
	}
	
	@Override
	public PilotModel getPilotDetailById(String id) {
		for (PilotModel temp : archivePilot) {
			if (temp.getId().equals(id)) {
				return temp;
			}
			
		}
		return null;
	}
}
