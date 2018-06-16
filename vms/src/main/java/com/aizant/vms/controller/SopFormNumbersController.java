package com.aizant.vms.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aizant.vms.model.Audit;
import com.aizant.vms.model.SopFormNumbers;
import com.aizant.vms.repo.AuditRepo;
import com.aizant.vms.repo.SopFormNumbersRepo;
import com.aizant.vms.repo.UserRepository;
import com.aizant.vms.util.AizantConstants;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class SopFormNumbersController {
	
	private static final Logger logger = LoggerFactory.getLogger(SopFormNumbersController.class);
	
	@Autowired
	SopFormNumbersRepo sopFormNumbersRepo; 
	@Autowired
	UserRepository userRepo;
	@Autowired
	AuditRepo auditRepo;
	@GetMapping("/sopformnumber")
	@ResponseBody
	public SopFormNumbers getSopFormNumber() {
		SopFormNumbers sopFormNumbers = null;
		try {
			Date currDate = new Date(new java.util.Date().getTime());
			sopFormNumbers = sopFormNumbersRepo.findTop1ByStartingDateLessThanEqualOrderByStartingDateDesc(currDate);
			return sopFormNumbers;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return sopFormNumbers;
	}

	@GetMapping("/allsopformnumbers")
	@ResponseBody
	public List<SopFormNumbers> getAllSopFormNumbers() {
		List<SopFormNumbers> allsopFormNumbers = null;
		try {
			allsopFormNumbers = sopFormNumbersRepo.findAllByOrderByIdDesc();
			return allsopFormNumbers;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return allsopFormNumbers;
	}
	@PostMapping("/sopformnumber")
	@ResponseBody
	public Boolean addSopFormNumber(@RequestBody SopFormNumbers sopFormNumbers) {
		try {
			sopFormNumbers.setCreated(new java.sql.Date(new java.util.Date().getTime()));
			sopFormNumbersRepo.save(sopFormNumbers);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	@PostMapping("/sopformnumber/{id}")
	@ResponseBody
	public Boolean updateSopFormNumber(@PathVariable Long id,@RequestBody Map<String,Object> map) {
		try {
			final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
			SopFormNumbers sopFormNumbers = mapper.convertValue(map.get("sopForm"), SopFormNumbers.class);
			sopFormNumbers.setUpdated(new java.sql.Date(new java.util.Date().getTime()));
			sopFormNumbersRepo.save(sopFormNumbers);
			String tableNameOfVolunteer = SopFormNumbers.class.getAnnotation(Table.class).name();
			List audit = mapper.convertValue(map.get("audit"), List.class);
			for(int i=0;i<audit.size();i++) {
				Audit ad = mapper.convertValue(audit.get(i), Audit.class);
				ad.setActionPerformed(AizantConstants.UPDATE);
				ad.setActionPerformedBy(sopFormNumbers.getUpdatedBy());
				ad.setActionPerformedDate(new Timestamp(new java.util.Date().getTime()));
				ad.setTableName(tableNameOfVolunteer);
				ad.setFormName("SOP Form Numbers");
				auditRepo.save(ad);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

	@GetMapping("/sopformnumber/{id}")
	@ResponseBody
	public SopFormNumbers getSingleSopFormNumber(@PathVariable Long id) {
		SopFormNumbers sopFormNumbers = null;
		try {
			sopFormNumbers = sopFormNumbersRepo.findOne(id);
			return sopFormNumbers;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return sopFormNumbers;
	}
	
}
