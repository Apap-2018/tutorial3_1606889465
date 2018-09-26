package com.apap.tutorial3.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial3.model.PilotModel;
import com.apap.tutorial3.model.PilotService;

@Controller
public class PilotController {

	@Autowired
	private PilotService pilotService;

	@RequestMapping("/pilot/add")
	public String add(@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "licenseNumber", required = true) String licenseNumber,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "flyHour", required = true) int flyHour) {

		PilotModel pilot = new PilotModel(id, licenseNumber, name, flyHour);
		pilotService.addPilot(pilot);
		return "add";
	}

	@RequestMapping("/pilot/view")
	public String view(@RequestParam("licenseNumber") String licenseNumber, Model model) {
		PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);

		model.addAttribute("pilot", archive);
		return "view-pilot";
	}

	@RequestMapping("/pilot/viewall")
	public String viewall(Model model) {
		List<PilotModel> archive = pilotService.getListPilot();

		model.addAttribute("listPilot", archive);
		return "viewall-pilot";
	}

	@RequestMapping(value = { "/pilot/view/license-number/", "/pilot/view/license-number/{licenseNumber}" })
	public String viewPath(@PathVariable Optional<String> licenseNumber, Model model) {
		String halaman = "error";

		if (licenseNumber.isPresent()) {
			PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber.get());
			if (archive == null) {
				model.addAttribute("erorinfo", "License Number Tidak ditemukkan");
			}

			else {
				model.addAttribute("pilot", archive);
				halaman = "view-pilot";
			}
		} 
		
		else {
			model.addAttribute("erorinfo", "License Number Tidak dimasukkan");
		}
		return halaman;
	}
	

	@RequestMapping(value = {"/pilot/update/license-number/fly-hour/{flyHour}", "/pilot/update/license-number/{licenseNumber}/fly-hour/{flyHour}"})
	public String updatePath (@PathVariable Optional<String>licenseNumber, 
							@PathVariable Optional<Integer>flyHour,Model model) {
		if (licenseNumber.isPresent() & flyHour.isPresent()) {
			PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber.get());
			if (archive == null) {
				model.addAttribute("updateinfo", "License Number Tidak ditemukkan, Data Batal diupdate !");
			}

			else {
				archive.setFlyHour(flyHour.get());
				model.addAttribute("updateinfo", "Data Berhasil diupdate");
			}
		} 
		
		else {
			model.addAttribute("updateinfo", "License Number Tidak dimasukkan, Data Batal diupdate !");
		}
		
		return "infoupdate";
	}
	
	@RequestMapping(value = {"/pilot/delete/id/{id}", "/pilot/delete/id/"})
	public String deletePath (@PathVariable Optional<String> id , Model model) {
		List<PilotModel> archive = pilotService.getListPilot();
		if (id.isPresent()) {
			PilotModel  objekData= pilotService.getPilotDetailById(id.get());
			if (objekData == null) {
				model.addAttribute("infodelete", "Id Tidak ditemukkan, Data Batal diHapus!");
			}

			else {
				archive.remove(objekData);
				model.addAttribute("infodelete", "Data Berhasil dihapus");
			}
		} 
		
		else {
			model.addAttribute("infodelete", "Id Tidak dimasukkan, Data Batal diHapus !");
		}
		
		return "deleteinfo";
	}
}
