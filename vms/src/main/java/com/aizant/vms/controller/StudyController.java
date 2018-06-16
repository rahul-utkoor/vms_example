package com.aizant.vms.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aizant.vms.model.StudyData;
import com.aizant.vms.repo.StudyDataRepo;

@Controller
public class StudyController {

	private static final Logger logger = LoggerFactory.getLogger(StudyController.class);
	
	
	@Autowired
	StudyDataRepo studyDataRepo;
	
	@GetMapping("/study/details/{regNum}")
	@ResponseBody
	public ResponseEntity<?> getStudyDetails(@PathVariable String regNum){
		
		logger.info("getting study details");
		try {
			List<StudyData> data = studyDataRepo.findByRegistrationNumberOrderByCheckOutDateDesc(regNum);
			return ResponseEntity.ok(data);
		}catch (Exception e) {
			logger.error("error while getting study details");
		}
		
		
		return null;
		
	}
	
}
