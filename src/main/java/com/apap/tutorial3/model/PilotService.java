package com.apap.tutorial3.model;
import java.util.List;

public interface PilotService {
	void addPilot (PilotModel pilot);
	List <PilotModel> getListPilot();
	PilotModel getPilotDetailByLicenseNumber(String licenseNumber);
	PilotModel getPilotDetailById(String id);

}
