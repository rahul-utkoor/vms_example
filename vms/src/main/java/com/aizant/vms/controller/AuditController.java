package com.aizant.vms.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aizant.vms.model.Audit;
import com.aizant.vms.model.UserLoginLogoutAudit;
import com.aizant.vms.repo.AuditRepo;
import com.aizant.vms.repo.UserLoginLogoutAuditRepo;

@Controller
public class AuditController {
	private static final Logger logger = LoggerFactory.getLogger(AuditController.class);

	@Autowired
	AuditRepo auditRepo;
	@Autowired
	UserLoginLogoutAuditRepo userLoginLogoutAuditRepo;
	@Autowired
	EntityManager entityManager;

	@GetMapping("/audit")
	@ResponseBody
	public ResponseEntity<?> getAuditDetails(@RequestParam(required = false) String userName,
			@RequestParam(required = false) Date date1, @RequestParam(required = false) Date date2,
			@RequestParam(required = false) String activity) {
		logger.info("getting audit details for : " + userName + " : " + date1 + " :  " + date2 + " : " + activity);
		List<Audit> auditList = new ArrayList<>();
		try {
			Timestamp timestamp = null;

			if (date1 != null && date2 != null) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date2);
				calendar.add(Calendar.HOUR, 23);
				calendar.add(Calendar.MINUTE, 59);
				calendar.add(Calendar.SECOND, 59);
				calendar.add(Calendar.MILLISECOND, 999);

				java.util.Date utildate2 = calendar.getTime();
				date2 = new Date(utildate2.getTime());

				timestamp = new Timestamp(utildate2.getTime());
			}

			// no filters
			if (userName == null && date1 == null && date2 == null && activity == null) {
				auditList = auditRepo.findAll();
			}
			// all 3 filters
			if (userName != null && date1 != null && date2 != null && activity != null) {
				auditList = auditRepo.findByActionPerformedDateBetweenAndActionPerformedByAndActionPerformed(date1,
						timestamp, userName, activity);
			}
			// date and username
			if (userName != null && date1 != null && date2 != null && activity == null) {
				auditList = auditRepo.findByActionPerformedDateBetweenAndActionPerformedBy(date1, timestamp, userName);
			}
			// user and activity
			if (userName != null && (date1 == null && date2 == null) && activity != null) {
				auditList = auditRepo.findByActionPerformedByAndActionPerformed(userName, activity);
			}
			// date and activity
			if (userName == null && date1 != null && date2 != null && activity != null) {
				auditList = auditRepo.findByActionPerformedDateBetweenAndActionPerformed(date1, timestamp, activity);
			}

			// only username
			if (userName != null && date1 == null && date2 == null && activity == null) {
				auditList = auditRepo.findByActionPerformedBy(userName);
			}
			// only date
			if (userName == null && date1 != null && date2 != null && activity == null) {
				auditList = auditRepo.findByActionPerformedDateBetween(date1, timestamp);
			}
			// only activity
			if (userName == null && date1 == null && date2 == null && activity != null) {
				auditList = auditRepo.findByActionPerformed(activity);
			}

			return ResponseEntity.ok(auditList);

		} catch (Exception e) {
			logger.error("failed while getting audit details");
		}

		return null;
	}

	@GetMapping("/audit/{regNum}")
	@ResponseBody
	public ResponseEntity<?> getAuditDetails(@PathVariable String regNum) {
		logger.info("getting audit details for : " + regNum);
		List<Audit> auditList = new ArrayList<>();
		try {

			auditList = auditRepo.findByValueContains(regNum);

			return ResponseEntity.ok(auditList);

		} catch (Exception e) {
			logger.error("failed while getting audit details");
		}

		return null;
	}
	@GetMapping("/audit/user/loginlogout")
	@ResponseBody
	public ResponseEntity<?> getLoginLogoutAuditDetails(@RequestParam(required = false) String userName,
			@RequestParam(required = false) Date date1, @RequestParam(required = false) Date date2) {
		logger.info("getting login logout audit details for : " + userName + " : " + date1 + " :  " + date2 );
		List<UserLoginLogoutAudit> auditList = new ArrayList<>();
		try {
			Timestamp timestamp1 = null;
			Timestamp timestamp2 = null;

			if (date1 != null && date2 != null) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date2);
				calendar.add(Calendar.HOUR, 23);
				calendar.add(Calendar.MINUTE, 59);
				calendar.add(Calendar.SECOND, 59);
				calendar.add(Calendar.MILLISECOND, 999);
				Calendar calendar1 = Calendar.getInstance();
				calendar1.setTime(date1);
				calendar1.add(Calendar.HOUR, 00);
				calendar1.add(Calendar.MINUTE, 00);
				calendar1.add(Calendar.SECOND, 00);
				calendar1.add(Calendar.MILLISECOND, 000);

				java.util.Date utildate2 = calendar.getTime();
				date2 = new Date(utildate2.getTime());
				java.util.Date utildate1 = calendar1.getTime();
				date1 = new Date(utildate1.getTime());

				timestamp2 = new Timestamp(utildate2.getTime());
				timestamp1 = new Timestamp(utildate1.getTime());
			}

			// no filters
			if (userName == null && date1 == null && date2 == null) {
				auditList = userLoginLogoutAuditRepo.findAll();
			}
			// all 3 filters
			if (userName != null && date1 != null && date2 != null) {
				auditList = userLoginLogoutAuditRepo.findbyUserAndDate(userName, timestamp1, timestamp2);
			}
			// date 
			if(userName == null && date1 != null && date2 != null) {
				auditList = userLoginLogoutAuditRepo.findByLastLoginBetween(timestamp1, timestamp2);
			}
			// User Name
			if(userName != null && date1 == null && date2 == null) {
				auditList = userLoginLogoutAuditRepo.findbyUser(userName);
			}

			return ResponseEntity.ok(auditList);

		} catch (Exception e) {
			logger.error("failed while getting audit details");
		}

		return null;
	}

}
