package com.aizant.vms.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Table;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aizant.vms.model.Audit;
import com.aizant.vms.model.SixMonths;
import com.aizant.vms.model.StatusMaster;
import com.aizant.vms.model.StudyData;
import com.aizant.vms.model.User;
import com.aizant.vms.model.Volunteer;
import com.aizant.vms.model.VolunteerClinicalAssessment;
import com.aizant.vms.model.VolunteerECG;
import com.aizant.vms.model.VolunteerPhysicalExamination;
import com.aizant.vms.model.VolunteerScreeningHistory;
import com.aizant.vms.model.VolunteerStatus;
import com.aizant.vms.model.VolunteerSystemicExamination;
import com.aizant.vms.model.VolunteerXRay;
import com.aizant.vms.repo.AuditRepo;
import com.aizant.vms.repo.StatusMasterRepo;
import com.aizant.vms.repo.StudyDataRepo;
import com.aizant.vms.repo.UserRepository;
import com.aizant.vms.repo.VolunteerClinicalAssessmentRepo;
import com.aizant.vms.repo.VolunteerECGRepo;
import com.aizant.vms.repo.VolunteerPhysicalExaminationRepo;
import com.aizant.vms.repo.VolunteerRepo;
import com.aizant.vms.repo.VolunteerScreeningHistoryRepo;
import com.aizant.vms.repo.VolunteerStatusRepo;
import com.aizant.vms.repo.VolunteerSystemicExaminationRepo;
import com.aizant.vms.repo.VolunteerXRayRepo;
import com.aizant.vms.service.AuditService;
import com.aizant.vms.service.IVolunteerService;
import com.aizant.vms.util.AizantConstants;
import com.aizant.vms.util.AizantUtil;
import com.aizant.vms.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

@Controller
public class VolunteermsrController {

	private static final Logger logger = LoggerFactory.getLogger(VolunteermsrController.class);

	/*
	 * @Autowired StatusMasterRepo statusMasterRepo;
	 */
	
	@Autowired
	StudyDataRepo studyDataRepo;

	@Autowired
	VolunteerScreeningHistoryRepo vshRepo;

	@Autowired
	VolunteerRepo volunteerRepo;

	@Autowired
	UserRepository userRepository;

	@Autowired
	VolunteerClinicalAssessmentRepo clinicalAssessmentRepo;

	@Autowired
	VolunteerPhysicalExaminationRepo volunteerPhysicalExaminationRepo;

	@Autowired
	VolunteerSystemicExaminationRepo volunteerSystemicExaminationRepo;

	@Autowired
	VolunteerXRayRepo volunteerXRayRepo;

	@Autowired
	VolunteerECGRepo volunteerECGRepo;

	@Autowired
	IVolunteerService volunteerService;

	@Autowired
	VolunteerScreeningHistoryRepo volunteerScreeningHistoryRepo;

	@Autowired
	StatusMasterRepo statusMasterRepo;

	@Autowired
	VolunteerStatusRepo volunteerStatusRepo;
	@Autowired
	AuditService auditService;
	@Autowired
	AuditRepo auditRepo;

	Map<String, String> errorMap = new HashMap<>();

	@GetMapping("/volunteer/clinicalassessment/check/{regNum}")
	@ResponseBody
	public Boolean checkClinicalAssessment(@PathVariable String regNum) {
		Volunteer volunteer = volunteerService.getVolunteerById(regNum);
		VolunteerStatus status = volunteerStatusRepo
				.findTop1ByRegistrationNumberOrderByIdDesc(volunteer.getRegistrationNumber());

		if (status == null)
			return false;

		Long statusId = status.getStatusMaster().getId();
		if (statusId.equals(1L))
			return true;

		return false;
	}

	@GetMapping("/volunteer/clinicalassessment/{regNum}")
	@ResponseBody
	public VolunteerClinicalAssessment getVolunteerCA(@PathVariable String regNum) {
		VolunteerStatus volunteerStatus = volunteerStatusRepo.findTop1ByRegistrationNumberOrderByIdDesc(regNum);
		if (volunteerStatus == null)
			return null;

		if (volunteerStatus.getStatusMaster().getId().equals(1L)) {
			VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo
					.findTop1ByVolunteerRegNumOrderByIdDesc(regNum);

			if (screeningHistory == null)
				return null;

			VolunteerClinicalAssessment clinicalAssessment = clinicalAssessmentRepo
					.findByVolunteerScreeningHistory(screeningHistory);
			if (clinicalAssessment != null) {
				Timestamp ts = clinicalAssessment.getRecordedOn();
				if (ts != null)
					clinicalAssessment.setRecordedOnDate(AizantUtil.toDate(ts));
				ts = clinicalAssessment.getReviewedOn();
				if (ts != null)
					clinicalAssessment.setReviewedOnDate(AizantUtil.toDate(ts));
			}
			return clinicalAssessment;
		} else {
			return null;
		}
	}

	/*
	 * @PostMapping("/volunteer/clinicalassessment")
	 * 
	 * @ResponseBody public Boolean createVolunteerCA(@RequestBody
	 * VolunteerClinicalAssessment volunteerClinicalAssessment) {
	 * logger.info("creating volunteer ca data"); try { Volunteer volunteer =
	 * null;// volunteerClinicalAssessment.getVolunteer(); if (volunteer == null)
	 * return null; Volunteer volunteer2 =
	 * volunteerService.getVolunteerById(volunteer.getRegistrationNumber()); if
	 * (volunteer2 == null) return null;
	 * volunteer2.setvStatus(String.valueOf(volunteerClinicalAssessment.
	 * getVolunteerHealthy())); volunteer2 = volunteerRepo.save(volunteer2);
	 * Timestamp ts = new Timestamp(new java.util.Date().getTime()); //
	 * volunteerClinicalAssessment.setVolunteer(volunteer2); //
	 * volunteerClinicalAssessment.setCreatedOn(ts);
	 * volunteerClinicalAssessment.setRecordedOn(ts);
	 * clinicalAssessmentRepo.save(volunteerClinicalAssessment);
	 * 
	 * return true; } catch (Exception e) { logger.error(e.getMessage()); } return
	 * false; }
	 */

	@PostMapping("/volunteer/clinicalassessment/{regNum}")
	@ResponseBody
	public Boolean editVolunteerCA(@PathVariable String regNum, @RequestBody Map<String, Object> map) {
		logger.info("creating/editing volunteer ca data  ");
		try {
			final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
			VolunteerClinicalAssessment volunteerClinicalAssessment = mapper.convertValue(map.get("msr_ca"),
					VolunteerClinicalAssessment.class);
			if (volunteerClinicalAssessment == null)
				return null;

			Timestamp ts = new Timestamp(new java.util.Date().getTime());
			if (volunteerClinicalAssessment.getId() == null) {

				User user = userRepository.findByUsername(volunteerClinicalAssessment.getRecordedBy());
				VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo
						.findTop1ByVolunteerRegNumOrderByIdDesc(regNum);
				screeningHistory.setCreated(new java.sql.Date(new java.util.Date().getTime()));
				screeningHistory.setCreatedBy(user.getUsername());

				screeningHistory.setVolunteerRegNum(regNum);
				volunteerClinicalAssessment.setVolunteerScreeningHistory(screeningHistory);
				clinicalAssessmentRepo.save(volunteerClinicalAssessment);

			} else {
				VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo
						.findTop1ByVolunteerRegNumOrderByIdDesc(regNum);

				User user = userRepository.findByUsername(volunteerClinicalAssessment.getRecordedBy());
				screeningHistory.setUpdatedBy(user.getUsername());
				screeningHistory.setUpdated(new java.sql.Date(new java.util.Date().getTime()));

				if (volunteerClinicalAssessment.getReviewedBy() != null) {

					screeningHistory.setReviewerComments(volunteerClinicalAssessment.getAdditionalComments());
					screeningHistory.setReviewdDate(ts);
					screeningHistory.setReviewedBy(volunteerClinicalAssessment.getReviewedBy());

					Volunteer volunteer = volunteerService.getVolunteerById(regNum);
					if (volunteer != null) {
						VolunteerStatus volunteerStatus = new VolunteerStatus();
						StatusMaster statusMaster = new StatusMaster();
						if (!volunteerClinicalAssessment.getVolunteerHealthy()) {

							if (volunteerClinicalAssessment.getIsRejectedPermanantly()) {
								statusMaster.setId(16L);
								volunteerStatus.setStatusMaster(statusMaster);
								volunteerStatus.setRegistrationNumber(volunteer.getRegistrationNumber());
								volunteerStatus.setIsRejectedPermanantly(
										volunteerClinicalAssessment.getIsRejectedPermanantly());
								volunteerStatus
										.setReasonForRejection(volunteerClinicalAssessment.getReasonForRejection());
								volunteerStatus.setScreeningId(screeningHistory.getId());
								volunteerStatusRepo.save(volunteerStatus);
								volunteer.setCurrentStatus(volunteerStatus.getId());

							} else {
								statusMaster.setId(17L);
								volunteerStatus.setStatusMaster(statusMaster);
								volunteerStatus.setRegistrationNumber(volunteer.getRegistrationNumber());
								volunteerStatus.setRejectedNoOfDays(volunteerClinicalAssessment.getRejectedNoOfDays());
								volunteerStatus.setIsRejectedPermanantly(
										volunteerClinicalAssessment.getIsRejectedPermanantly());
								volunteerStatus
										.setReasonForRejection(volunteerClinicalAssessment.getReasonForRejection());
								volunteerStatus.setScreeningId(screeningHistory.getId());
								volunteerStatus.setRejectedDate(new Date(new java.util.Date().getTime()));
								volunteerStatusRepo.save(volunteerStatus);
								volunteer.setCurrentStatus(volunteerStatus.getId());
							}
						} else {

							volunteerClinicalAssessment.setRecordedOn(ts);
							volunteerScreeningHistoryRepo.save(screeningHistory);

							statusMaster.setId(3L);
							volunteerStatus.setStatusMaster(statusMaster);
							volunteerStatus.setRegistrationNumber(volunteer.getRegistrationNumber());
							volunteerStatus.setScreeningId(screeningHistory.getId());
							volunteerStatusRepo.save(volunteerStatus);
							volunteer.setCurrentStatus(volunteerStatus.getId());

						}
					}

					volunteerRepo.save(volunteer);

					String tableNameOfVolunteer = VolunteerClinicalAssessment.class.getAnnotation(Table.class).name();
					String jsonValue = JsonUtil.convertToJsonForInsert(volunteerClinicalAssessment);
					try {
						auditService.saveAudit(volunteerClinicalAssessment.getCreatedBy(), tableNameOfVolunteer,
								jsonValue, "Clinical Assessment", AizantConstants.CREATE, null);
					} catch (Exception e) {
						logger.error("failed auditing " + e);
					}

				}

				volunteerScreeningHistoryRepo.save(screeningHistory);
				volunteerClinicalAssessment.setVolunteerScreeningHistory(screeningHistory);
				clinicalAssessmentRepo.save(volunteerClinicalAssessment);
				String tableNameOfVolunteer = VolunteerClinicalAssessment.class.getAnnotation(Table.class).name();
				List audit = mapper.convertValue(map.get("audit"), List.class);
				for (int i = 0; i < audit.size(); i++) {
					Audit ad = mapper.convertValue(audit.get(i), Audit.class);
					ad.setActionPerformed(AizantConstants.UPDATE);
					ad.setActionPerformedBy(volunteerClinicalAssessment.getRecordedBy());
					ad.setActionPerformedDate(new Timestamp(new java.util.Date().getTime()));
					ad.setTableName(tableNameOfVolunteer);
					ad.setFormName("Clinical Assessment");
					auditRepo.save(ad);
				}
			}

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	@GetMapping("/volunteer/physicalexam/check/{regNum}")
	@ResponseBody
	public Boolean checkPhysicalexam(@PathVariable String regNum) {
		Volunteer volunteer = volunteerService.getVolunteerById(regNum);
		VolunteerStatus status = volunteerStatusRepo
				.findTop1ByRegistrationNumberOrderByIdDesc(volunteer.getRegistrationNumber());

		if (status == null)
			return false;

		Long statusId = status.getStatusMaster().getId();
		if (statusId.equals(1L))
			return true;

		return false;
	}

	@GetMapping("/volunteer/physicalexam/{regNum}")
	@ResponseBody
	public VolunteerPhysicalExamination getVolunteerPE(@PathVariable String regNum) {
		VolunteerStatus volunteerStatus = volunteerStatusRepo.findTop1ByRegistrationNumberOrderByIdDesc(regNum);
		if (volunteerStatus == null)
			return null;

		if (volunteerStatus.getStatusMaster().getId().equals(1L)) {
			VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo
					.findTop1ByVolunteerRegNumOrderByIdDesc(regNum);

			if (screeningHistory == null)
				return null;

			VolunteerPhysicalExamination physicalExamination = volunteerPhysicalExaminationRepo
					.findByVolunteerScreeningHistory(screeningHistory);
			if (physicalExamination != null) {
				Timestamp ts = physicalExamination.getRecordedOn();
				if (ts != null)
					physicalExamination.setRecordedOnDate(AizantUtil.toDate(ts));
				ts = physicalExamination.getReviewedOn();
				if (ts != null)
					physicalExamination.setReviewedOnDate(AizantUtil.toDate(ts));
			}
			return physicalExamination;
		} else {
			return null;
		}
	}

	@PostMapping("/volunteer/physicalexam/{regNum}")
	@ResponseBody
	public Boolean createVolunteerPE(@PathVariable String regNum, @RequestBody Map<String, Object> map) {
		logger.info("creating/editing  VolunteerPE data  ");
		try {
			final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
			VolunteerPhysicalExamination physicalExamination = mapper.convertValue(map.get("msr_pe1"),
					VolunteerPhysicalExamination.class);
			if (physicalExamination == null)
				return null;

			if (physicalExamination.getId() == null) {

				Timestamp ts = new Timestamp(new java.util.Date().getTime());
				physicalExamination.setRecordedOn(ts);

				VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo
						.findTop1ByVolunteerRegNumOrderByIdDesc(regNum);
				screeningHistory.setCreated(new java.sql.Date(new java.util.Date().getTime()));

				User user = userRepository.findByUsername(physicalExamination.getRecordedBy());
				screeningHistory.setCreatedBy(user.getUsername());
				// screeningHistory.setScreeningDate(new Timestamp(new
				// java.util.Date().getTime()));
				// screeningHistory.setStatusMaster(1L);
				screeningHistory.setVolunteerRegNum(regNum);

				volunteerScreeningHistoryRepo.save(screeningHistory);

				physicalExamination.setVolunteerScreeningHistory(screeningHistory);
				volunteerPhysicalExaminationRepo.save(physicalExamination);

				String tableNameOfVolunteer = VolunteerPhysicalExamination.class.getAnnotation(Table.class).name();
				String jsonValue = JsonUtil.convertToJsonForInsert(physicalExamination);
				try {
					auditService.saveAudit(physicalExamination.getCreatedBy(), tableNameOfVolunteer, jsonValue,
							"General Examination", AizantConstants.CREATE, null);
				} catch (Exception e) {
					logger.error("failed auditing " + e);
				}

			} else {
				VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo
						.findTop1ByVolunteerRegNumOrderByIdDesc(regNum);

				User user = userRepository.findByUsername(physicalExamination.getRecordedBy());
				screeningHistory.setUpdatedBy(user.getUsername());
				screeningHistory.setUpdated(new java.sql.Date(new java.util.Date().getTime()));

				volunteerScreeningHistoryRepo.save(screeningHistory);
				physicalExamination.setVolunteerScreeningHistory(screeningHistory);
				volunteerPhysicalExaminationRepo.save(physicalExamination);

				// VolunteerPhysicalExamination physicalExamination2 =
				// volunteerPhysicalExaminationRepo
				// .findByVolunteerScreeningHistory(screeningHistory);

				String tableNameOfVolunteer = VolunteerPhysicalExamination.class.getAnnotation(Table.class).name();
				/*
				 * String jsonValue = JsonUtil.convertToJsonForUpdate(physicalExamination,
				 * physicalExamination2);
				 * 
				 * if (jsonValue != null) { try {
				 * auditService.saveAudit(physicalExamination.getUpdatedBy(),
				 * tableNameOfVolunteer, jsonValue, tableNameOfVolunteer,
				 * AizantConstants.UPDATE, physicalExamination.getUpdateComments()); } catch
				 * (Exception e) { logger.error("failed auditing " + e); } }
				 */
				List audit = mapper.convertValue(map.get("audit"), List.class);
				for (int i = 0; i < audit.size(); i++) {
					Audit ad = mapper.convertValue(audit.get(i), Audit.class);
					ad.setActionPerformed(AizantConstants.UPDATE);
					ad.setActionPerformedBy(physicalExamination.getRecordedBy());
					ad.setActionPerformedDate(new Timestamp(new java.util.Date().getTime()));
					ad.setTableName(tableNameOfVolunteer);
					ad.setFormName("General Examination");
					auditRepo.save(ad);
				}
			}

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	/*
	 * @PostMapping("/volunteer/physicalexam/{regNum}")
	 * 
	 * @ResponseBody public Boolean editVolunteerPE(@PathVariable String regNum,
	 * 
	 * @RequestBody VolunteerPhysicalExamination physicalExamination) { try {
	 * Volunteer volunteer2 = volunteerService.getVolunteerById(regNum); if
	 * (volunteer2 == null) { return false; } Volunteer v =
	 * volunteerRepo.save(volunteer2);
	 * 
	 * VolunteerPhysicalExamination physicalExamination2 =
	 * volunteerPhysicalExaminationRepo .findbyRegistrationNumber(regNum); if
	 * (physicalExamination2 == null) return null;
	 * 
	 * Long vdId = physicalExamination2.getId();
	 * 
	 * BeanUtils.copyProperties(physicalExamination, physicalExamination2);
	 * physicalExamination2.setId(vdId); physicalExamination2.setVolunteer(v);
	 * Timestamp ts = new Timestamp(new java.util.Date().getTime()); //
	 * physicalExamination2.setUpdatedOn(ts);
	 * physicalExamination2.setReviewedOn(ts);
	 * volunteerPhysicalExaminationRepo.save(physicalExamination2); return true; }
	 * catch (Exception e) { logger.error(e.getMessage()); }
	 * 
	 * return false; }
	 */

	@GetMapping("/volunteer/systemexam/check/{regNum}")

	@ResponseBody
	public Boolean checkSystemexam(@PathVariable String regNum) {
		Volunteer volunteer = volunteerService.getVolunteerById(regNum);
		VolunteerStatus status = volunteerStatusRepo
				.findTop1ByRegistrationNumberOrderByIdDesc(volunteer.getRegistrationNumber());

		if (status == null)
			return false;

		Long statusId = status.getStatusMaster().getId();
		if (statusId.equals(1L))
			return true;

		return false;
	}

	@GetMapping("/volunteer/systemexam/{regNum}")
	@ResponseBody
	public VolunteerSystemicExamination getVolunteerSE(@PathVariable String regNum) {
		VolunteerStatus volunteerStatus = volunteerStatusRepo.findTop1ByRegistrationNumberOrderByIdDesc(regNum);
		if (volunteerStatus == null)
			return null;

		if (volunteerStatus.getStatusMaster().getId().equals(1L)) {
			VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo
					.findTop1ByVolunteerRegNumOrderByIdDesc(regNum);

			if (screeningHistory == null)
				return null;

			VolunteerSystemicExamination volunteerSystemicExamination = volunteerSystemicExaminationRepo
					.findByVolunteerScreeningHistory(screeningHistory);
			if (volunteerSystemicExamination != null) {
				Timestamp ts = volunteerSystemicExamination.getRecordedOn();
				if (ts != null)
					volunteerSystemicExamination.setRecordedOnDate(AizantUtil.toDate(ts));
				ts = volunteerSystemicExamination.getReviewedOn();
				if (ts != null)
					volunteerSystemicExamination.setReviewedOnDate(AizantUtil.toDate(ts));
			}
			return volunteerSystemicExamination;
		} else {
			return null;
		}
	}

	/*
	 * @PostMapping("/volunteer/systemexam")
	 * 
	 * @ResponseBody public Boolean createVolunteerSE(@RequestBody
	 * VolunteerSystemicExamination volunteerSystemicExamination) {
	 * logger.info("creating volunteer system exam data"); try { Volunteer volunteer
	 * = volunteerSystemicExamination.getVolunteer(); if (volunteer == null) return
	 * null; Volunteer volunteer2 =
	 * volunteerService.getVolunteerById(volunteer.getRegistrationNumber()); if
	 * (volunteer2 == null) return null; volunteer2 =
	 * volunteerRepo.save(volunteer2); Timestamp ts = new Timestamp(new
	 * java.util.Date().getTime());
	 * volunteerSystemicExamination.setVolunteer(volunteer2); //
	 * volunteerSystemicExamination.setCreatedOn(ts);
	 * volunteerSystemicExamination.setRecordedOn(ts);
	 * volunteerSystemicExaminationRepo.save(volunteerSystemicExamination); return
	 * true; } catch (Exception e) { logger.error(e.getMessage()); } return false; }
	 */

	@PostMapping("/volunteer/systemexam/{regNum}")
	@ResponseBody
	public Boolean editVolunteerSE(@PathVariable String regNum, @RequestBody Map<String, Object> map) {
		logger.info("creating/editing  VolunteerSE data  ");
		try {
			final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
			VolunteerSystemicExamination volunteerSystemicExamination = mapper.convertValue(map.get("msr_pe2"),
					VolunteerSystemicExamination.class);
			if (volunteerSystemicExamination == null)
				return null;

			if (volunteerSystemicExamination.getId() == null) {

				Timestamp ts = new Timestamp(new java.util.Date().getTime());
				volunteerSystemicExamination.setRecordedOn(ts);

				VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo
						.findTop1ByVolunteerRegNumOrderByIdDesc(regNum);
				screeningHistory.setCreated(new java.sql.Date(new java.util.Date().getTime()));

				User user = userRepository.findByUsername(volunteerSystemicExamination.getRecordedBy());
				screeningHistory.setCreatedBy(user.getUsername());
				// screeningHistory.setScreeningDate(new Timestamp(new
				// java.util.Date().getTime()));
				// screeningHistory.setStatusMaster(1L);
				screeningHistory.setVolunteerRegNum(regNum);

				volunteerScreeningHistoryRepo.save(screeningHistory);

				volunteerSystemicExamination.setVolunteerScreeningHistory(screeningHistory);
				volunteerSystemicExaminationRepo.save(volunteerSystemicExamination);

				String tableNameOfVolunteer = VolunteerSystemicExamination.class.getAnnotation(Table.class).name();
				String jsonValue = JsonUtil.convertToJsonForInsert(volunteerSystemicExamination);
				try {
					auditService.saveAudit(volunteerSystemicExamination.getCreatedBy(), tableNameOfVolunteer, jsonValue,
							"Systemic Examination", AizantConstants.CREATE, null);
				} catch (Exception e) {
					logger.error("failed auditing " + e);
				}

			} else {
				VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo
						.findTop1ByVolunteerRegNumOrderByIdDesc(regNum);

				User user = userRepository.findByUsername(volunteerSystemicExamination.getRecordedBy());
				screeningHistory.setUpdatedBy(user.getUsername());
				screeningHistory.setUpdated(new java.sql.Date(new java.util.Date().getTime()));

				volunteerScreeningHistoryRepo.save(screeningHistory);
				volunteerSystemicExamination.setVolunteerScreeningHistory(screeningHistory);
				volunteerSystemicExaminationRepo.save(volunteerSystemicExamination);

				// VolunteerSystemicExamination volunteerSystemicExamination2 =
				// volunteerSystemicExaminationRepo
				// .findByVolunteerScreeningHistory(screeningHistory);

				String tableNameOfVolunteer = VolunteerSystemicExamination.class.getAnnotation(Table.class).name();
				/*
				 * String jsonValue =
				 * JsonUtil.convertToJsonForUpdate(volunteerSystemicExamination,
				 * volunteerSystemicExamination2);
				 * 
				 * if (jsonValue != null) { try {
				 * auditService.saveAudit(volunteerSystemicExamination.getUpdatedBy(),
				 * tableNameOfVolunteer, jsonValue, tableNameOfVolunteer,
				 * AizantConstants.UPDATE, volunteerSystemicExamination.getUpdateComments()); }
				 * catch (Exception e) { logger.error("failed auditing " + e); } }
				 */
				List audit = mapper.convertValue(map.get("audit"), List.class);
				for (int i = 0; i < audit.size(); i++) {
					Audit ad = mapper.convertValue(audit.get(i), Audit.class);
					ad.setActionPerformed(AizantConstants.UPDATE);
					ad.setActionPerformedBy(volunteerSystemicExamination.getRecordedBy());
					ad.setActionPerformedDate(new Timestamp(new java.util.Date().getTime()));
					ad.setTableName(tableNameOfVolunteer);
					ad.setFormName("Systemic Examination");
					auditRepo.save(ad);
				}
			}

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	/*
	 * @PostMapping(path =
	 * "/volunteer/xray",headers=("content-type={multipart/form-data}"))
	 * 
	 * @ResponseBody public Boolean createVolunteerXray(@RequestParam("xRayFile")
	 * MultipartFile file,@RequestBody VolunteerXRay volunteerXRay) {
	 * logger.info("creating volunteer xray data"); try {
	 * System.out.println(file.getName()); Volunteer volunteer =
	 * volunteerXRay.getVolunteer(); if (volunteer == null) return null; Volunteer
	 * volunteer2 =
	 * volunteerService.getVolunteerById(volunteer.getRegistrationNumber()); if
	 * (volunteer2 == null) return null; volunteer2 =
	 * volunteerRepo.save(volunteer2); Timestamp ts = new Timestamp(new
	 * java.util.Date().getTime()); volunteerXRay.setVolunteer(volunteer2);
	 * volunteerXRay.setCreatedOn(ts); volunteerXRay.setRecordedOn(ts);
	 * volunteerXRayRepo.save(volunteerXRay); return true; } catch (Exception e) {
	 * logger.error(e); } return false; }
	 */

	/*
	 * @PostMapping(path = "/volunteer/xray", consumes = {
	 * MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
	 * 
	 * @ResponseBody public Boolean createVolunteerXray(@RequestParam("xRayFile")
	 * MultipartFile file,
	 * 
	 * @RequestParam("xray") String xray) {
	 * logger.info("creating volunteer xray data"); try { Gson g = new Gson();
	 * VolunteerXRay volunteerXRay = g.fromJson(xray, VolunteerXRay.class);
	 * Timestamp ts = new Timestamp(new java.util.Date().getTime());
	 * volunteerXRay.setPhysicianDate(ts); String type = AizantConstants.X_RAY;
	 * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); java.util.Date
	 * date = (java.util.Date) sdf.parse(volunteerXRay.getTestDate()); java.sql.Date
	 * sqlDate = new java.sql.Date(date.getTime());
	 * volunteerXRay.setXrayDate(sqlDate); if (saveFile(file,
	 * volunteer.getRegistrationNumber(), type, false)) {
	 * volunteerXRay.setxRayFileLocation(AizantConstants.UPLOADED_FOLDER +
	 * volunteer.getRegistrationNumber() + "/" + type + "/" +
	 * file.getOriginalFilename());
	 * 
	 * volunteerXRayRepo.save(volunteerXRay); return true; } } catch (Exception e) {
	 * e.printStackTrace(); logger.error(e.getMessage()); } return false; }
	 */
	@GetMapping("/volunteer/xray/check/{regNum}")
	@ResponseBody
	public ResponseEntity<?> checkXray(@PathVariable String regNum) throws Exception {
		Volunteer volunteer = volunteerService.getVolunteerById(regNum);
		if (volunteer == null) {
			Map<String, String> errorMap = new HashMap<>();
			errorMap.put("message", "Invalid Volunteer Number");

			return ResponseEntity.badRequest().body(errorMap);
		}

		VolunteerXRay volunteerXRay = volunteerXRayRepo.findTop1ByRegNumOrderByIdDesc(regNum);
		if (volunteerXRay != null) {
			Date xrayDate = volunteerXRay.getXrayDate();
			Date currDate = new Date(new java.util.Date().getTime());
			String resutAfterSixmonths = SixMonths.getSixMonths(xrayDate.toString());
			java.util.Date afterSixMonths1 = new SimpleDateFormat("yyyy-MM-dd").parse(resutAfterSixmonths);
			Date afterSixMonths2 = new Date(afterSixMonths1.getTime());
			// int diffInDays = (int) ((currDate.getTime() - xrayDate.getTime()) / (1000 *
			// 60 * 60 * 24));
			if (currDate.after(xrayDate) && currDate.before(afterSixMonths2)) {
				return ResponseEntity.ok(true);
			} else {
				return ResponseEntity.ok(false);
			}

		} else {
			return ResponseEntity.ok(false);
		}

	}

	@GetMapping("/volunteer/xray/{regNum}")
	@ResponseBody
	public VolunteerXRay getVolunteerXRay(@PathVariable String regNum) {

		Volunteer volunteer = volunteerService.getVolunteerById(regNum);
		if (volunteer == null)
			return null;

		VolunteerXRay volunteerXRay = volunteerXRayRepo.findTop1ByRegNumOrderByIdDesc(regNum);
		if (volunteerXRay != null) {
			Date xrayDate = volunteerXRay.getXrayDate();
			Date currDate = new Date(new java.util.Date().getTime());
			int diffInDays = (int) ((currDate.getTime() - xrayDate.getTime()) / (1000 * 60 * 60 * 24));
			if (diffInDays < 180) {
				volunteerXRay.setVolunteerSex(volunteer.getGender());
				volunteerXRay.setVolunteerAge(AizantUtil.getAge(volunteer.getDob()));

				Timestamp ts = volunteerXRay.getPhysicianDate();
				if (ts != null)
					volunteerXRay.setRecordedOnDate(AizantUtil.toDate(ts));
				ts = volunteerXRay.getRadiologistDate();
				if (ts != null)
					volunteerXRay.setReviewedOnDate(AizantUtil.toDate(ts));

				if (volunteerXRay.getxRayFileLocation() != null) {
					File f = new File(volunteerXRay.getxRayFileLocation());
					if (f != null) {
						volunteerXRay.setImageName(f.getName());
					}
				}

				volunteerXRay.setPhysicianDate(null);
				volunteerXRay.setRadiologistDate(null);
			} else {
				return null;
			}
		}
		return volunteerXRay;

	}

	@PostMapping(path = "/volunteer/xray/{regNum}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public Boolean editVolunteerXray(@PathVariable String regNum,
			@RequestPart(name = "xRayFile", required = false) MultipartFile file, @RequestPart("xray") String xray,
			@RequestParam(name = "audit", required = false) String audit) {
		try {

			Volunteer volunteer = volunteerService.getVolunteerById(regNum);

			Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
			VolunteerXRay volunteerXRay = g.fromJson(xray, VolunteerXRay.class);
			List auditData = g.fromJson(audit, List.class);
			if (volunteerXRay != null && volunteerXRay.getId() == null) {
				Timestamp ts = new Timestamp(new java.util.Date().getTime());
				volunteerXRay.setPhysicianDate(ts);
				String type = AizantConstants.X_RAY;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date date = (java.util.Date) sdf.parse(volunteerXRay.getTestDate());
				java.sql.Date sqlDate = new java.sql.Date(date.getTime());
				volunteerXRay.setXrayDate(sqlDate);
				volunteerXRay.setRegNum(regNum);
				if (saveFile(file, regNum, type, false)) {
					volunteerXRay.setxRayFileLocation(
							AizantConstants.UPLOADED_FOLDER + regNum + "/" + type + "/" + file.getOriginalFilename());

					volunteerXRay.setPhysicianDate(ts);

					volunteerXRayRepo.save(volunteerXRay);

				}
				String tableNameOfVolunteer = VolunteerXRay.class.getAnnotation(Table.class).name();
				String jsonValue = JsonUtil.convertToJsonForInsert(volunteerXRay);
				try {
					auditService.saveAudit(volunteerXRay.getCreatedBy(), tableNameOfVolunteer, jsonValue, "X-Ray",
							AizantConstants.CREATE, null);
				} catch (Exception e) {
					logger.error("failed auditing " + e);
				}

			} else {

				volunteerXRay.setVolunteerRegNum(regNum);

				if (file != null) {
					saveFile(file, regNum, AizantConstants.X_RAY, true);
					volunteerXRay.setxRayFileLocation(AizantConstants.UPLOADED_FOLDER + regNum + "/"
							+ AizantConstants.X_RAY + "/" + file.getOriginalFilename());
				}
				volunteerXRayRepo.save(volunteerXRay);

				// VolunteerXRay volunteerXRay2 =
				// volunteerXRayRepo.findTop1ByRegNumOrderByIdDesc(regNum);

				String tableNameOfVolunteer = VolunteerXRay.class.getAnnotation(Table.class).name();
				/*
				 * String jsonValue = JsonUtil.convertToJsonForUpdate(volunteerXRay,
				 * volunteerXRay2);
				 * 
				 * if (jsonValue != null) { try {
				 * auditService.saveAudit(volunteerXRay.getUpdatedBy(), tableNameOfVolunteer,
				 * jsonValue, tableNameOfVolunteer, AizantConstants.UPDATE,
				 * volunteerXRay.getUpdateComments()); } catch (Exception e) {
				 * logger.error("failed auditing " + e); } }
				 */
				for (int i = 0; i < auditData.size(); i++) {
					Audit ad = new Audit();
					LinkedTreeMap<String, String> ltmap = (LinkedTreeMap<String, String>) auditData.get(i);
					ad.setColumnName(ltmap.get("columnName"));
					ad.setOldValue(ltmap.get("oldValue"));
					ad.setNewValue(ltmap.get("newValue"));
					ad.setComments(ltmap.get("comments"));
					ad.setActionPerformed(AizantConstants.UPDATE);
					ad.setActionPerformedBy(volunteerXRay.getRadiologistBy());
					ad.setActionPerformedDate(new Timestamp(new java.util.Date().getTime()));
					ad.setTableName(tableNameOfVolunteer);
					ad.setFormName("X-Ray");

					auditRepo.save(ad);
				}

			}
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return false;

	}

	/*
	 * @PostMapping(path = "/volunteer/ecg", consumes = {
	 * MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
	 * 
	 * @ResponseBody public Boolean createVolunteerEcg(@RequestParam("ecgFile")
	 * MultipartFile file,
	 * 
	 * @RequestParam("ecgdata") String ecgData) {
	 * logger.info("creating volunteer ecg data"); try { Gson g = new Gson();
	 * VolunteerECG volunteerECG = g.fromJson(ecgData, VolunteerECG.class);
	 * Volunteer volunteer = volunteerECG.getVolunteer(); if (volunteer == null)
	 * return null; Volunteer volunteer2 =
	 * volunteerService.getVolunteerById(volunteer.getRegistrationNumber()); if
	 * (volunteer2 == null) return null; volunteer2 =
	 * volunteerRepo.save(volunteer2); Timestamp ts = new Timestamp(new
	 * java.util.Date().getTime()); volunteerECG.setVolunteer(volunteer2); //
	 * volunteerECG.setCreatedOn(ts); if (volunteerECG.getReportConfirmedBy() !=
	 * null) { volunteerECG.setReportConfirmedTime(ts); }
	 * volunteerECG.setRecordedOn(ts); SimpleDateFormat sdf = new
	 * SimpleDateFormat("yyyy-MM-dd"); java.util.Date date = (java.util.Date)
	 * sdf.parse(volunteerECG.getTestDate()); java.sql.Date sqlDate = new
	 * java.sql.Date(date.getTime()); volunteerECG.setEcgDate(sqlDate); String type
	 * = AizantConstants.ECG; if (saveFile(file, volunteer.getRegistrationNumber(),
	 * type, false)) {
	 * volunteerECG.setEcgFileLocation((AizantConstants.UPLOADED_FOLDER +
	 * volunteer.getRegistrationNumber() + "/" + type + "/" +
	 * file.getOriginalFilename())); volunteerECGRepo.save(volunteerECG); return
	 * true; } } catch (Exception e) { logger.error(e.getMessage()); } return false;
	 * }
	 */
	private Boolean saveFile(MultipartFile file, String regNum, String type, Boolean isEdit) {
		boolean fileUploaded = false;
		byte[] bytes = null;
		try {
			if (file != null && !file.isEmpty()) {
				bytes = file.getBytes();
				String folderS = AizantConstants.UPLOADED_FOLDER + regNum + "/" + type + "/";

				Path folderPath = Paths.get(folderS);
				if (isEdit) {
					Path path = Paths.get(folderS);
					// Files.deleteIfExists(path);
					FileUtils.deleteDirectory(new File(path.toString()));
					Files.createDirectories(folderPath);
				}
				if (!Files.exists(folderPath)) {
					Files.createDirectories(folderPath);
				}

				Path path = Paths.get(folderS + file.getOriginalFilename());
				Files.write(path, bytes);
				fileUploaded = true;
			}

		} catch (IOException e) {
			fileUploaded = false;
			logger.error(e.getMessage());
		}

		return fileUploaded;
	}

	@GetMapping("/volunteer/ecg/check/{regNum}")
	@ResponseBody
	public ResponseEntity<?> checkEcg(@PathVariable String regNum) {
		Volunteer volunteer = volunteerService.getVolunteerById(regNum);
		if (volunteer == null) {
			Map<String, String> errorMap = new HashMap<>();
			errorMap.put("message", "Invalid Volunteer Number");

			return ResponseEntity.badRequest().body(errorMap);
		}

		VolunteerECG volunteerECG = volunteerECGRepo.findTop1ByRegNumOrderByIdDesc(regNum);
		if (volunteerECG != null) {
			Date xrayDate = volunteerECG.getEcgDate();
			Date currDate = new Date(new java.util.Date().getTime());
			int diffInDays = (int) ((currDate.getTime() - xrayDate.getTime()) / (1000 * 60 * 60 * 24));
			if (diffInDays < 180) {
				return ResponseEntity.ok(true);
			} else {
				return ResponseEntity.ok(false);
			}

		} else {
			return ResponseEntity.ok(false);
		}

	}

	@GetMapping("/volunteer/ecg/{regNum}")
	@ResponseBody
	public VolunteerECG getVolunteerEcg(@PathVariable String regNum) {

		Volunteer volunteer = volunteerService.getVolunteerById(regNum);
		if (volunteer == null)
			return null;

		VolunteerECG volunteerECG = volunteerECGRepo.findTop1ByRegNumOrderByIdDesc(regNum);
		if (volunteerECG != null) {
			Date xrayDate = volunteerECG.getEcgDate();
			Date currDate = new Date(new java.util.Date().getTime());
			int diffInDays = (int) ((currDate.getTime() - xrayDate.getTime()) / (1000 * 60 * 60 * 24));
			if (diffInDays < 180) {
				String age = AizantUtil.getAge(volunteer.getDob());
				String gender = volunteer.getGender();
				volunteerECG.setRegNum(regNum);
				volunteerECG.setVolunteerSex(volunteer.getGender());
				volunteerECG.setVolunteerAge(AizantUtil.getAge(volunteer.getDob()));
				volunteerECG.setVolunteerRegNum(regNum);

				Timestamp ts = volunteerECG.getRecordedOn();
				if (ts != null)
					volunteerECG.setRecordedOnDate(AizantUtil.toDate(ts));
				ts = volunteerECG.getReviewedOn();
				if (ts != null)
					volunteerECG.setReviewedOnDate(AizantUtil.toDate(ts));

				if (volunteerECG.getEcgFileLocation() != null) {
					File f = new File(volunteerECG.getEcgFileLocation());
					if (f != null) {
						volunteerECG.setImageName(f.getName());
					}
				}
			}
			// volunteerECG.setCreatedOn(null);
			// volunteerECG.setUpdatedOn(null);
			volunteerECG.setRecordedOn(null);
			volunteerECG.setReviewedOn(null);
		}
		return volunteerECG;

	}

	@PostMapping(path = "/volunteer/ecg/{regNum}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public Boolean editVolunteerEcg(@PathVariable String regNum,
			@RequestParam(name = "ecgFile", required = false) MultipartFile file,
			@RequestParam("ecgdata") String ecgData, @RequestParam(name = "audit", required = false) String audit) {
		try {
			Volunteer volunteer = volunteerService.getVolunteerById(regNum);
			if (volunteer == null)
				return null;

			Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
			VolunteerECG volunteerECG = g.fromJson(ecgData, VolunteerECG.class);

			List auditData = g.fromJson(audit, List.class);

			if (volunteerECG == null)
				return false;

			if (volunteerECG.getId() == null) {

				Timestamp ts = new Timestamp(new java.util.Date().getTime());
				volunteerECG.setRegNum(regNum);
				if (volunteerECG.getReportConfirmedBy() != null) {
					volunteerECG.setReportConfirmedTime(ts);
				}
				volunteerECG.setRecordedOn(ts);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date date = (java.util.Date) sdf.parse(volunteerECG.getTestDate());
				java.sql.Date sqlDate = new java.sql.Date(date.getTime());
				volunteerECG.setEcgDate(sqlDate);
				String type = AizantConstants.ECG;
				if (saveFile(file, volunteer.getRegistrationNumber(), type, false)) {
					volunteerECG.setEcgFileLocation((AizantConstants.UPLOADED_FOLDER + volunteer.getRegistrationNumber()
							+ "/" + type + "/" + file.getOriginalFilename()));
					volunteerECGRepo.save(volunteerECG);

					String tableNameOfVolunteer = VolunteerECG.class.getAnnotation(Table.class).name();
					String jsonValue = JsonUtil.convertToJsonForInsert(volunteerECG);
					try {
						auditService.saveAudit(volunteerECG.getCreatedBy(), tableNameOfVolunteer, jsonValue, "Ecg",
								AizantConstants.CREATE, null);
					} catch (Exception e) {
						logger.error("failed auditing " + e);
					}

					return true;
				}
			} else {
				volunteerECG.setVolunteerRegNum(regNum);
				if (file != null) {
					saveFile(file, regNum, AizantConstants.ECG, true);
					volunteerECG.setEcgFileLocation((AizantConstants.UPLOADED_FOLDER + regNum + "/"
							+ AizantConstants.ECG + "/" + file.getOriginalFilename()));

				}

				volunteerECGRepo.save(volunteerECG);
				String tableNameOfVolunteer = VolunteerECG.class.getAnnotation(Table.class).name();

				for (int i = 0; i < auditData.size(); i++) {
					Audit ad = new Audit();
					LinkedTreeMap<String, String> ltmap = (LinkedTreeMap<String, String>) auditData.get(i);
					ad.setColumnName(ltmap.get("columnName"));
					ad.setOldValue(ltmap.get("oldValue"));
					ad.setNewValue(ltmap.get("newValue"));
					ad.setComments(ltmap.get("comments"));
					ad.setActionPerformed(AizantConstants.UPDATE);
					ad.setActionPerformedBy(volunteerECG.getReviewedBy());
					ad.setActionPerformedDate(new Timestamp(new java.util.Date().getTime()));
					ad.setTableName(tableNameOfVolunteer);
					ad.setFormName("Ecg");
					auditRepo.save(ad);
				}
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return false;

	}

	/*
	 * @PostMapping(path = "/volunteer/ecg/{regNum}", consumes = {
	 * MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
	 * 
	 * @ResponseBody public Boolean editVolunteerEcg(@PathVariable String regNum,
	 * 
	 * @RequestParam(name = "ecgFile", required = false) MultipartFile file,
	 * 
	 * @RequestBody Map<String,Object> map) { try { final ObjectMapper mapper = new
	 * ObjectMapper(); // jackson's objectmapper VolunteerECG volunteerECG =
	 * mapper.convertValue(map.get("ecg"), VolunteerECG.class); Volunteer volunteer
	 * = volunteerService.getVolunteerById(regNum); if (volunteer == null) return
	 * null;
	 * 
	 * Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create(); //
	 * VolunteerECG volunteerECG = g.fromJson(ecg, VolunteerECG.class); // List
	 * auditData = g.fromJson(audit, List.class); if (volunteerECG == null) return
	 * false;
	 * 
	 * if (volunteerECG.getId() == null) {
	 * 
	 * Timestamp ts = new Timestamp(new java.util.Date().getTime());
	 * volunteerECG.setRegNum(regNum); if (volunteerECG.getReportConfirmedBy() !=
	 * null) { volunteerECG.setReportConfirmedTime(ts); }
	 * volunteerECG.setRecordedOn(ts); SimpleDateFormat sdf = new
	 * SimpleDateFormat("yyyy-MM-dd"); java.util.Date date = (java.util.Date)
	 * sdf.parse(volunteerECG.getTestDate()); java.sql.Date sqlDate = new
	 * java.sql.Date(date.getTime()); volunteerECG.setEcgDate(sqlDate); String type
	 * = AizantConstants.ECG; if (saveFile(file, volunteer.getRegistrationNumber(),
	 * type, false)) {
	 * volunteerECG.setEcgFileLocation((AizantConstants.UPLOADED_FOLDER +
	 * volunteer.getRegistrationNumber() + "/" + type + "/" +
	 * file.getOriginalFilename())); volunteerECGRepo.save(volunteerECG);
	 * 
	 * 
	 * String tableNameOfVolunteer =
	 * VolunteerECG.class.getAnnotation(Table.class).name(); String jsonValue =
	 * JsonUtil.convertToJsonForInsert(volunteerECG); try {
	 * auditService.saveAudit(volunteerECG.getCreatedBy(), tableNameOfVolunteer,
	 * jsonValue, tableNameOfVolunteer, AizantConstants.CREATE, null); } catch
	 * (Exception e) { logger.error("failed auditing " + e); }
	 * 
	 * return true; } } else { volunteerECG.setVolunteerRegNum(regNum); if (file !=
	 * null) { saveFile(file, regNum, AizantConstants.ECG, true);
	 * volunteerECG.setEcgFileLocation((AizantConstants.UPLOADED_FOLDER + regNum +
	 * "/" + AizantConstants.ECG + "/" + file.getOriginalFilename()));
	 * 
	 * }
	 * 
	 * volunteerECGRepo.save(volunteerECG); String tableNameOfVolunteer =
	 * VolunteerECG.class.getAnnotation(Table.class).name();
	 * 
	 * List audit = mapper.convertValue(map.get("audit"), List.class); for(int
	 * i=0;i<audit.size();i++) { Audit ad = mapper.convertValue(audit.get(i),
	 * Audit.class); ad.setActionPerformed(AizantConstants.UPDATE);
	 * ad.setActionPerformedBy(volunteerECG.getRecordedBy());
	 * ad.setActionPerformedDate(new Timestamp(new java.util.Date().getTime()));
	 * ad.setTableName(tableNameOfVolunteer);
	 * 
	 * auditRepo.save(ad); } return true; }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); logger.error(e.getMessage()); }
	 * 
	 * return false;
	 * 
	 * }
	 */
	@GetMapping(path = "/download/{regNum}/{type}")
	public ResponseEntity<Resource> getFile(@RequestParam String fileName, @PathVariable String regNum,
			@PathVariable String type) {
		String folderS = AizantConstants.UPLOADED_FOLDER + regNum + "/" + type + "/";
		File file = new File(folderS + fileName);
		InputStreamResource resource = null;
		try {
			resource = new InputStreamResource(new FileInputStream(file));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName().toString())
				.contentLength(file.length()).contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(resource);

	}

	@GetMapping("/volunteer/screening/history/{regNum}")
	public ResponseEntity<?> getVolunteerScreeningHistories(@PathVariable String regNum) {
		logger.info("getting volunteer screning history");
		try {
			Volunteer volunteer = volunteerRepo.findByRegistrationNumber(regNum);
			if (volunteer == null) {
				errorMap.put("message", "Invalid Registration Number");
				return ResponseEntity.badRequest().body(errorMap);
			}

			List<VolunteerScreeningHistory> histories = vshRepo
					.findByVolunteerRegNumOrderByIdDesc(volunteer.getRegistrationNumber());

			for (VolunteerScreeningHistory history : histories) {

				VolunteerStatus status = volunteerStatusRepo.findTop1ByScreeningIdOrderByIdDesc(history.getId());
				if (status != null) {
					


					if (status.getStatusMaster() != null)
						if (status.getStatusMaster().getId().equals(17l)) {
							Long rejectedDays = status.getRejectedNoOfDays();
							if (rejectedDays != null) {
								Date rejectedDate = status.getRejectedDate();

								Calendar calendar = Calendar.getInstance();
								calendar.setTime(rejectedDate);
								calendar.add(Calendar.DATE, rejectedDays.intValue());

								java.util.Date dt = calendar.getTime();
								history.setNextEligibleDate(new Date(dt.getTime()));

							}

						}

					if (status.getStatusMaster().getId().equals(3l)) {
						Timestamp screeningDate = history.getScreeningDate();
						Calendar cal = Calendar.getInstance();
						cal.setTime(screeningDate);
						cal.add(Calendar.DATE, 21);
						java.util.Date dt = cal.getTime();
						history.setNextEligibleDate(new Date(dt.getTime()));

					}

					if (status.getStatusMaster().getId().equals(7l)) {
						if (status.getScreeningId() != null) {

							Timestamp screeningDate = history.getScreeningDate();

							Calendar cal = Calendar.getInstance();
							cal.setTime(screeningDate);
							cal.add(Calendar.DATE, 90);
							java.util.Date dt = cal.getTime();
							history.setNextEligibleDate(new Date(dt.getTime()));
						} else {

							StudyData data = studyDataRepo
									.findTop1ByRegistrationNumberOrderByCheckOutDateDesc(volunteer.getRegistrationNumber());
							Date dt = data.getCheckOutDate();
							Calendar cal = Calendar.getInstance();
							cal.setTime(dt);
							cal.add(Calendar.DATE, 90);
							java.util.Date dt1 = cal.getTime();
							history.setNextEligibleDate(new Date(dt1.getTime()));

						}

					}
				
					
					
					
				} else {

					Timestamp screeningDate = history.getScreeningDate();
					Calendar cal = Calendar.getInstance();
					cal.setTime(screeningDate);
					cal.add(Calendar.DATE, 21);
					java.util.Date dt = cal.getTime();
					history.setNextEligibleDate(new Date(dt.getTime()));
				}
			}

			/*
			 * if (histories != null && !histories.isEmpty()) { for
			 * (VolunteerScreeningHistory history : histories) { if
			 * (history.getStatusMaster() != null) { StatusMaster master =
			 * statusMasterRepo.findOne(history.getStatusMaster()); if (master != null)
			 * history.setStatusValue(master.getStatusDescription()); } } }
			 */
			return ResponseEntity.ok(histories);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
