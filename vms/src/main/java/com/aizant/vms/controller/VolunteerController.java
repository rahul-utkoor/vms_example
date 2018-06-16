
package com.aizant.vms.controller;

import static org.springframework.data.jpa.domain.Specifications.where;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Table;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsPdfView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsXlsView;

import com.aizant.vms.model.Audit;
import com.aizant.vms.model.StatusMaster;
import com.aizant.vms.model.StudyData;
import com.aizant.vms.model.User;
import com.aizant.vms.model.Volunteer;
import com.aizant.vms.model.VolunteerDemographicProfile;
import com.aizant.vms.model.VolunteerFamilyHistory;
import com.aizant.vms.model.VolunteerFingerDtls;
import com.aizant.vms.model.VolunteerImageDtls;
import com.aizant.vms.model.VolunteerMaster;
import com.aizant.vms.model.VolunteerMenstrualHistory;
import com.aizant.vms.model.VolunteerPastHistory;
import com.aizant.vms.model.VolunteerPersonalHistory;
import com.aizant.vms.model.VolunteerRestrictionCompliance;
import com.aizant.vms.model.VolunteerScreeningHistory;
import com.aizant.vms.model.VolunteerStatus;
import com.aizant.vms.model.VolunteerVitalSigns;
import com.aizant.vms.repo.AuditRepo;
import com.aizant.vms.repo.StatusMasterRepo;
import com.aizant.vms.repo.StudyDataRepo;
import com.aizant.vms.repo.UserRepository;
import com.aizant.vms.repo.VolunteerDemographicProfileRepo;
import com.aizant.vms.repo.VolunteerFamilyHistoryRepo;
import com.aizant.vms.repo.VolunteerFingerDtlsRepo;
import com.aizant.vms.repo.VolunteerImageDtlsRepo;
import com.aizant.vms.repo.VolunteerMasterRepo;
import com.aizant.vms.repo.VolunteerMenstrualHistoryRepo;
import com.aizant.vms.repo.VolunteerPastHistoryRepo;
import com.aizant.vms.repo.VolunteerPersonalHistoryRepo;
import com.aizant.vms.repo.VolunteerRepo;
import com.aizant.vms.repo.VolunteerRestrictionComplianceRepo;
import com.aizant.vms.repo.VolunteerScreeningHistoryRepo;
import com.aizant.vms.repo.VolunteerStatusRepo;
import com.aizant.vms.repo.VolunteerVitalSignsRepo;
import com.aizant.vms.service.AuditService;
import com.aizant.vms.service.IVolunteerService;
import com.aizant.vms.service.VolunteerSpecs;
import com.aizant.vms.util.AizantConstants;
import com.aizant.vms.util.AizantUtil;
import com.aizant.vms.util.JdbcUtil;
import com.aizant.vms.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller

public class VolunteerController {

	private static final Logger logger = LoggerFactory.getLogger(VolunteerController.class);

	@Autowired
	StudyDataRepo studyDataRepo;

	@Autowired
	StatusMasterRepo statusMasterRepo;

	@Autowired
	VolunteerScreeningHistoryRepo volunteerScreeningHistoryRepo;

	@PersistenceContext
	EntityManager em;

	@Autowired
	AuditService auditService;

	@Autowired
	VolunteerMasterRepo volunteerMasterRepo;

	@Autowired
	UserRepository userRepository;

	@Autowired
	AuditRepo auditRepo;

	@Autowired
	private ApplicationContext appContext;

	// @Autowired
	// VolunteerScreeningHistoryRepo volunteerScreeningHistoryRepo;

	@Autowired
	IVolunteerService volunteerService;

	@Autowired
	VolunteerRepo volunteerRepo;

	@Autowired
	VolunteerImageDtlsRepo volunteerImageDtlsRepo;

	@Autowired
	VolunteerFingerDtlsRepo volunteerFingerDtlsRepo;

	@Autowired
	VolunteerDemographicProfileRepo volunteerDemographicProfileRepo;

	@Autowired
	VolunteerRestrictionComplianceRepo volunteerRestrictionComplianceRepo;

	@Autowired
	VolunteerVitalSignsRepo volunteerVitalSignsRepo;

	@Autowired
	VolunteerPersonalHistoryRepo volunteerPersonalHistoryRepo;

	@Autowired
	VolunteerFamilyHistoryRepo volunteerFamilyHistoryRepo;

	@Autowired
	VolunteerMenstrualHistoryRepo volunteerMenstrualHistoryRepo;

	@Autowired
	VolunteerPastHistoryRepo volunteerPastHistoryRepo;

	@Autowired
	VolunteerStatusRepo volunteerStatusRepo;

	/**
	 * This method gets all volunteers data
	 * 
	 * @return List<Volunteer>
	 */
	/*
	 * @RequestMapping(value = "/volunteers", method = RequestMethod.GET)
	 * // @Secured
	 * 
	 * @ResponseBody public List<Volunteer> getAllVolunteers() {
	 * logger.info("Getting all volunteers "); try { return
	 * volunteerService.getVolunteers(); } catch (Exception e) {
	 * logger.error(e.getMessage()); } return null; }
	 */

	@RequestMapping(value = "/volunteers", method = RequestMethod.GET)
	@ResponseBody
	public Page<Volunteer> getAllVolunteers(Pageable pageable) {
		try {
		Page<Volunteer> allvol = volunteerRepo.findAll(pageable);
		for (int i = 0; i < allvol.getContent().size(); i++) {
			if (allvol.getContent() != null) {
				if (allvol.getContent().get(i).getCurrentStatus() != null) {
					VolunteerStatus volStatus = volunteerStatusRepo
							.findOne(allvol.getContent().get(i).getCurrentStatus());
					if (volStatus.getStatusMaster().getStatusDescription() != null) {
						allvol.getContent().get(i).setStatusValue(volStatus.getStatusMaster().getStatusDescription());
						allvol.getContent().get(i).setStatusId(volStatus.getStatusMaster().getId());
						allvol.getContent().get(i).setReasonForRejection(volStatus.getReasonForRejection());
					}
				}
			}
		}
		for (int i = 0; i < allvol.getContent().size(); i++) {
			if(allvol.getContent() != null) {
			if (allvol.getContent().get(i).getRegistrationNumber() != null) {
				StudyData studyData = studyDataRepo.findTop1ByRegistrationNumberOrderByCheckOutDateDesc(
						allvol.getContent().get(i).getRegistrationNumber());
				if (studyData != null) {
					allvol.getContent().get(i).setLatestStudyNumber(studyData.getStudyName());
				}

			}
		}
		}

		// Page<Volunteer> volunteers = (Page<Volunteer>) allvol.getContent();
		return allvol;
		}catch (Exception e) {
		e.printStackTrace();
		}
		return null;
	}

	@GetMapping("/latestregistrationnumber")
	@ResponseBody
	public Volunteer getLatestregNum() {
		logger.info("Getting latest registration number");
		Volunteer latestRegnum = null;
		try {
			latestRegnum = volunteerRepo.findFirstByOrderByIdDesc();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return latestRegnum;
	}

	/**
	 * This method creates volunteer
	 * 
	 * @param volunteer
	 * @return boolean
	 */
	@RequestMapping(value = "/volunteers", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<?> createVolunteer(@RequestBody Volunteer volunteer) {
		try {
			Volunteer volunteer2 = volunteerRepo.findFirstByOrderByIdDesc();

			if (volunteer.getRegisteredBy() != null && volunteer.getRegisteredOn() == null) {
				volunteer.setRegisteredOn(new java.sql.Timestamp(new java.util.Date().getTime()));
			}
			if (checkRegNum(volunteer.getRegistrationNumber())) {

				String tableNameOfVolunteer = Volunteer.class.getAnnotation(Table.class).name();
				String jsonValue = JsonUtil.convertToJsonForInsert(volunteer);
				try {
					auditService.saveAudit(volunteer.getRegisteredBy(), tableNameOfVolunteer, jsonValue,
							tableNameOfVolunteer, AizantConstants.CREATE, null);
				} catch (Exception e) {
					// e.printStackTrace();
					logger.error("failed auditing " + e);
				}
				VolunteerStatus status = new VolunteerStatus();

				StatusMaster statusMaster = new StatusMaster();
				statusMaster.setId(12L);
				status.setStatusMaster(statusMaster);
				status.setRegistrationNumber(volunteer.getRegistrationNumber());
				status.setCreatedBy(volunteer.getCreatedBy());

				status = volunteerStatusRepo.save(status);

				volunteer.setCurrentStatus(status.getId());
				volunteer.setVolunteerStatus(status);
				volunteerRepo.save(volunteer);
				return ResponseEntity.ok(volunteer);
			} else {
				return ResponseEntity.badRequest().body("Reg Num already exist!");
			}
		} catch (Exception e) {

			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return ResponseEntity.badRequest().body(null);

	}

	@RequestMapping(value = "/check/regnum", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> checkRegNumExist(@RequestParam String regNum) {
		try {
			Volunteer volunteer = volunteerRepo.findByRegistrationNumber(regNum);
			if (volunteer == null) {
				return ResponseEntity.ok(true);
			} else {
				return ResponseEntity.ok(false);
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return ResponseEntity.badRequest().body(null);

	}

	private Boolean checkRegNum(String registrationNumber) {
		Volunteer volunteer = volunteerRepo.findByRegistrationNumber(registrationNumber);
		if (volunteer == null) {
			return true;
		}

		return false;

	}

	/**
	 * This method gets the volunteer details for the selected param
	 * 
	 * @param regNum
	 * @return volunteer
	 */

	@PostMapping("/volunteers/{regNum}")
	@ResponseBody
	public ResponseEntity<?> editVolunteer(@PathVariable String regNum, @RequestBody Map<String, Object> map) {
		logger.info("Editing volunteer");
		try {

			final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
			Volunteer volunteer = mapper.convertValue(map.get("volunteer"), Volunteer.class);

			if (volunteer.getRegisteredBy() != null && volunteer.getRegisteredOn() == null) {
				volunteer.setRegisteredOn(new java.sql.Timestamp(new java.util.Date().getTime()));
			}

			String tableNameOfVolunteer = Volunteer.class.getAnnotation(Table.class).name();

			/*
			 * String jsonValue = JsonUtil.convertToJsonForUpdate(volunteer, volunteer2);
			 * 
			 * if (jsonValue != null) { try {
			 * auditService.saveAudit(volunteer.getUpdatedBy(), tableNameOfVolunteer,
			 * jsonValue, tableNameOfVolunteer, AizantConstants.UPDATE,
			 * volunteer.getUpdateComments()); } catch (Exception e) {
			 * logger.error("failed auditing " + e); } }
			 * 
			 * copyNonNullProperties(volunteer, volunteer2);
			 */
			volunteerRepo.save(volunteer);

			List audit = mapper.convertValue(map.get("audit"), List.class);
			for (int i = 0; i < audit.size(); i++) {
				Audit ad = mapper.convertValue(audit.get(i), Audit.class);
				ad.setActionPerformed(AizantConstants.UPDATE);
				ad.setActionPerformedBy(volunteer.getRegisteredBy());
				ad.setActionPerformedDate(new Timestamp(new java.util.Date().getTime()));
				ad.setTableName(tableNameOfVolunteer);
				ad.setFormName(tableNameOfVolunteer);
				auditRepo.save(ad);
			}

			return ResponseEntity.ok(volunteer);
		} catch (Exception e) {
			// logger.error(e);
			e.printStackTrace();
		}
		return ResponseEntity.badRequest().body(null);

	}

	public static void copyNonNullProperties(Object src, Object target) {
		BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
	}

	public static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<String>();
		for (java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null)
				emptyNames.add(pd.getName());
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}

	@RequestMapping(value = "/volunteers/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Volunteer getVolunteerById(@PathVariable("id") String id) {
		logger.info("Getting volunteer by reg num");
		try {
			Volunteer volunteer = volunteerRepo.findByRegistrationNumber(id);
			if (volunteer == null) {
				return null;
			}
			String age = AizantUtil.getAge(volunteer.getDob());
			volunteer.setAge(age);

			if (volunteer.getHeight() != null && volunteer.getWeight() != null) {
				String bmi = AizantUtil.calculateBmi(volunteer.getHeight(), volunteer.getWeight());
				volunteer.setBmi(bmi);
			}

			Date lastDate = volunteer.getLastParticipationDate();
			if (lastDate != null) {
				Date currDate = new Date(new java.util.Date().getTime());

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate date1 = LocalDate.parse(lastDate.toString(), formatter);
				LocalDate date2 = LocalDate.parse(currDate.toString(), formatter);
				Long days = ChronoUnit.DAYS.between(date1, date2);
				volunteer.setNoOfDaysCompletedFromLastPartcipation(days);

				if (days < 90) {
					volunteer.setProceedForScreening(false);

				} else {
					volunteer.setProceedForScreening(true);

				}
			}

			
			VolunteerStatus status = volunteerStatusRepo
					.findTop1ByRegistrationNumberOrderByIdDesc(volunteer.getRegistrationNumber());
			if (status != null) {
				volunteer.setStatusId(status.getStatusMaster().getId());
				volunteer.setStatusValue(status.getStatusMaster().getStatusDescription());
				volunteer.setReasonForRejection(status.getReasonForRejection());

				if (status.getStatusMaster() != null)
					if (status.getStatusMaster().getId().equals(17l)) {
						Long rejectedDays = status.getRejectedNoOfDays();
						if (rejectedDays != null) {
							Date rejectedDate = status.getRejectedDate();

							Calendar calendar = Calendar.getInstance();
							calendar.setTime(rejectedDate);
							calendar.add(Calendar.DATE, rejectedDays.intValue());

							java.util.Date dt = calendar.getTime();
							volunteer.setNextEligibleDate(new Date(dt.getTime()));

						}

					}

				if (status.getStatusMaster().getId().equals(3L)) {
					VolunteerScreeningHistory history = volunteerScreeningHistoryRepo.findOne(status.getScreeningId());

					Timestamp screeningDate = history.getScreeningDate();

					Calendar cal = Calendar.getInstance();
					cal.setTime(screeningDate);
					cal.add(Calendar.DATE, 21);
					java.util.Date dt = cal.getTime();
					volunteer.setNextEligibleDate(new Date(dt.getTime()));

				}

				if (status.getStatusMaster().getId().equals(7L)) {
					if (status.getScreeningId() != null) {
						VolunteerScreeningHistory history = volunteerScreeningHistoryRepo
								.findOne(status.getScreeningId());

						Timestamp screeningDate = history.getScreeningDate();

						Calendar cal = Calendar.getInstance();
						cal.setTime(screeningDate);
						cal.add(Calendar.DATE, 90);
						java.util.Date dt = cal.getTime();
						volunteer.setNextEligibleDate(new Date(dt.getTime()));
					} else {

						StudyData data = studyDataRepo
								.findTop1ByRegistrationNumberOrderByCheckOutDateDesc(volunteer.getRegistrationNumber());
						Date dt = data.getCheckOutDate();
						Calendar cal = Calendar.getInstance();
						cal.setTime(dt);
						cal.add(Calendar.DATE, 90);
						java.util.Date dt1 = cal.getTime();
						volunteer.setNextEligibleDate(new Date(dt1.getTime()));

					}

				}
			}

			VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo
					.findTop1ByVolunteerRegNumOrderByIdDesc(volunteer.getRegistrationNumber());
			if (screeningHistory != null)
				volunteer.setLastScreeningDate(new Date(screeningHistory.getScreeningDate().getTime()));
			StudyData studyData = studyDataRepo.findTop1ByRegistrationNumberOrderByCheckOutDateDesc(
					volunteer.getRegistrationNumber());
			if(studyData != null) {
				volunteer.setLatestStudyNumber(studyData.getStudyName());
			}
			return volunteer;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;

	}

	@RequestMapping(value = "/volunteers/searchByName", method = RequestMethod.GET)
	@ResponseBody
	public List<Volunteer> searchVolunteerByName(@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "fatherName", required = false) String fatherName,
			@RequestParam(value = "phone", required = false) String phone,
			@RequestParam(value = "dob", required = false) Date dob) {
		// return volunteerService.searchVolunteerByName(name);
		return volunteerRepo.findAll(where(VolunteerSpecs.nameLike(name)).and(VolunteerSpecs.fatherNameLike(fatherName))
				.and(VolunteerSpecs.phoneEquals(phone)).and(VolunteerSpecs.dobEquals(dob)));
	}

	/*
	 * @GetMapping("/volunteers/21days/below")
	 * 
	 * @ResponseBody public List<Volunteer> getAllVolunteersbelow21days() {
	 * 
	 * // LastScreeningDate java.sql.Date sqlDate = new java.sql.Date(new
	 * java.util.Date().getTime()); Calendar cal = Calendar.getInstance();
	 * cal.setTime(sqlDate); cal.add(Calendar.DATE, -21); java.util.Date
	 * dateBefore21Days = cal.getTime(); java.sql.Date sqlDate1 = new
	 * Date(dateBefore21Days.getTime()); return
	 * volunteerRepo.findByLastScreeningDateBetweenandIsNUll(sqlDate1, sqlDate);
	 * 
	 * StatusMaster master = new StatusMaster(); master.setId(3L);
	 * 
	 * List<VolunteerStatus> status =
	 * volunteerStatusRepo.findByStatusMaster(master); Set<Volunteer> list = new
	 * HashSet<>(); if (status != null && !status.isEmpty()) { for (VolunteerStatus
	 * status2 : status) { Volunteer volunteer =
	 * volunteerRepo.findByRegistrationNumber(status2.getRegistrationNumber());
	 * list.add(volunteer); } }
	 * 
	 * return new ArrayList<>(list);
	 * 
	 * }
	 */

	/*
	 * @GetMapping("/volunteers/21days/above")
	 * 
	 * @ResponseBody public List<Volunteer> getAllVolunteersabove21days() {
	 * StatusMaster master = new StatusMaster(); master.setId(116L);
	 * List<VolunteerStatus> status =
	 * volunteerStatusRepo.findByStatusMaster(master); Set<Volunteer> list = new
	 * HashSet<>(); if (status != null && !status.isEmpty()) { for (VolunteerStatus
	 * status2 : status) { Volunteer volunteer =
	 * volunteerRepo.findByRegistrationNumber(status2.getRegistrationNumber());
	 * list.add(volunteer); } }
	 * 
	 * return new ArrayList<>(list);
	 * 
	 * }
	 */

	/*
	 * @GetMapping("/volunteers/21days/{q}")
	 * 
	 * @ResponseBody public Page<Volunteer>
	 * getAllVolunteersbelow21days(@PathVariable String q, Pageable pageable) {
	 * StatusMaster master = new StatusMaster(); List<VolunteerStatus> status =null;
	 * String statusMasterId = ""; if(q.equals("below")){ master.setId(3L);
	 * statusMasterId = "3"; status = volunteerStatusRepo.volunteersbelow21(); }
	 * if(q.equals("above")){ master.setId(116L); statusMasterId = "12,116"; status
	 * = volunteerStatusRepo.volunteersabove21(); }
	 * 
	 * 
	 * List<VolunteerStatus> status = new ArrayList<VolunteerStatus>(); int limit =
	 * 0; for(int i=0; i<25; i++){ List<VolunteerStatus> vss = new
	 * ArrayList<VolunteerStatus>(); List list =
	 * getVolunteerStatuses(statusMasterId, limit); vss.addAll(list); if(vss.size()
	 * > 0){ if(vss.size() < 3000){ break; } limit = limit + vss.size(); } }
	 * 
	 * Set<String> registrationNumbers = new HashSet<String>(); for(VolunteerStatus
	 * vs : status){ registrationNumbers.add(vs.getRegistrationNumber()); }
	 * System.out.println("Size = "+registrationNumbers.size()); return
	 * volunteerRepo.findAll(where(VolunteerSpecs.registrationNumberIn(
	 * registrationNumbers)), pageable); }
	 */

	@GetMapping("/volunteers/21days/{q}")
	@ResponseBody
	public Page<Volunteer> getAllVolunteersbelow21days(@PathVariable String q, Pageable pageable) {
		StatusMaster master = new StatusMaster();

		String statusMasterId = "";
		if (q.equals("below")) {
			master.setId(3L);
			statusMasterId = "3";
		}
		if (q.equals("above")) {
			master.setId(116L);
			statusMasterId = "12,116";
		}

		List<VolunteerStatus> status = new ArrayList<VolunteerStatus>();
		int limit = 0;
		for (int i = 0; i < 25; i++) {
			List<VolunteerStatus> vss = new ArrayList<VolunteerStatus>();
			List list = getVolunteerStatuses(statusMasterId, limit);
			vss.addAll(list);
			status.addAll(list);
			if (vss.size() > 0) {
				if (vss.size() < 3000) {
					break;
				}
				limit = limit + vss.size();
			}
		}

		Set<String> registrationNumbers = new HashSet<String>();
		for (VolunteerStatus vs : status) {
			registrationNumbers.add(vs.getRegistrationNumber());
		}
		System.out.println("Size = " + registrationNumbers.size());
		return volunteerRepo.findAll(where(VolunteerSpecs.registrationNumberIn(registrationNumbers)), pageable);
	}

	@GetMapping("/volunteers/90days/above")
	public ResponseEntity<List<Volunteer>> getAllVolunteersabove90days() {
		Set<Volunteer> list = volunteerService.getVolunteersAbove90Days();
		return new ResponseEntity(list, HttpStatus.OK);
	}

	@GetMapping("/volunteers/90days/below")
	@ResponseBody
	public Set<Volunteer> getAllVolunteersbelow90days() {
		StatusMaster master = new StatusMaster();
		master.setId(7L);

		List<VolunteerStatus> status = volunteerStatusRepo.findByStatusMasterid(master.getId());
		Set<Volunteer> list = new HashSet<>();
		if (status != null && !status.isEmpty()) {
			for (VolunteerStatus status2 : status) {
				Volunteer volunteer = volunteerRepo.findByRegistrationNumber(status2.getRegistrationNumber());
				list.add(volunteer);
			}
		}

		return list;
	}

	@GetMapping("/volunteers/dashboard")
	@ResponseBody
	public ResponseEntity<?> getDashBoardData() {
		try {
			java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
			Calendar cal = Calendar.getInstance();
			cal.setTime(sqlDate);
			cal.add(Calendar.DATE, -21);
			java.util.Date dateBefore21Days = cal.getTime();
			java.sql.Date sqlDate1 = new Date(dateBefore21Days.getTime());
			List<Volunteer> volunteerAbove21 = new ArrayList<>();
			List<Volunteer> volunteerBelow21 = new ArrayList<>();
			Set<Volunteer> volunteerAbove90 = new HashSet();
			Set<Volunteer> volunteerBelow90 = new HashSet();
			List<Volunteer> rejectedVolunteersWithoutReason = new ArrayList<>();
			// List<Volunteer> volunteerBelow21 =
			// volunteerRepo.findByLastScreeningDateBetweenandIsNUll(sqlDate1, sqlDate);
			// List<Volunteer> volunteerAbove21 =
			// volunteerRepo.findByLastScreeningDateBefore(sqlDate1);
			// Set<Volunteer> volunteerAbove90 =
			// volunteerService.getVolunteersAbove90Days();
			// Set<Volunteer> volunteerBelow90 =
			// volunteerService.getAllVolunteersbelow90days();
			// List<Volunteer> rejectedVolunteersWithoutReason =
			// volunteerRepo.rejectedVolunteers();
			List<Volunteer> allVolunteers = volunteerService.getVolunteers();
			rejectedVolunteersWithoutReason = volunteerRepo.rejectedVolunteers();
			volunteerBelow21 = volunteerRepo.volunteersbelow21();
			volunteerAbove21 = volunteerRepo.volunteersabove21();

			/*
			 * for (int i = 0; i < allVolunteers.size(); i++) { VolunteerStatus status =
			 * volunteerStatusRepo
			 * .findTop1ByRegistrationNumberOrderByIdDesc(allVolunteers.get(i).
			 * getRegistrationNumber()); if (status != null) {
			 * allVolunteers.get(i).setStatusId(status.getStatusMaster().getId());
			 * allVolunteers.get(i).setStatusValue(status.getStatusMaster().
			 * getStatusDescription());
			 * allVolunteers.get(i).setReasonForRejection(status.getReasonForRejection()); }
			 * if (allVolunteers.get(i).getStatusId() != null && status != null) { //
			 * System.out.println("Count"+allVolunteers.get(i).getRegistrationNumber()); if
			 * (allVolunteers.get(i).getStatusId() == 12L ||
			 * allVolunteers.get(i).getStatusId() == 116L) {
			 * volunteerAbove21.add(allVolunteers.get(i)); } if
			 * (allVolunteers.get(i).getStatusId() == 3L) {
			 * volunteerBelow21.add(allVolunteers.get(i)); } if
			 * (allVolunteers.get(i).getStatusId() == 7L) {
			 * volunteerAbove90.add(allVolunteers.get(i)); } if
			 * (allVolunteers.get(i).getStatusId() == 6L) {
			 * volunteerBelow90.add(allVolunteers.get(i)); } if
			 * (allVolunteers.get(i).getStatusId() == 16L ||
			 * allVolunteers.get(i).getStatusId() == 17L) {
			 * rejectedVolunteersWithoutReason.add(allVolunteers.get(i)); } } }
			 */
			HashMap<String, String> vd = new HashMap<String, String>();
			if (allVolunteers != null)
				vd.put("allVolunteers", "" + allVolunteers.size());
			if (volunteerBelow21 != null)
				vd.put("volunteerBelow21", "" + volunteerBelow21.size());
			if (volunteerAbove21 != null)
				vd.put("volunteerAbove21", "" + volunteerAbove21.size());
			if (volunteerAbove90 != null)
				vd.put("volunteerAbove90", "" + volunteerAbove90.size());
			if (volunteerBelow90 != null)
				vd.put("volunteerBelow90", "" + volunteerBelow90.size());
			if (rejectedVolunteersWithoutReason != null)
				vd.put("rejectedVolunteers", "" + rejectedVolunteersWithoutReason.size());
			return ResponseEntity.ok(vd);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;

	}

	@GetMapping("/rejectedvolunteers")
	@ResponseBody
	public List<Volunteer> getRejectedVolunteers() {
		List<Volunteer> rejectedVolunteersWithoutReason = volunteerRepo.rejectedVolunteers();
		List<Volunteer> rejectedVolunteersWithReason = new ArrayList<>();
		for (Volunteer vr : rejectedVolunteersWithoutReason) {
			if (vr.getRegistrationNumber() != null) {

				VolunteerStatus status = volunteerStatusRepo
						.findTop1ByRegistrationNumberOrderByIdDesc(vr.getRegistrationNumber());
				if (status != null) {
					vr.setStatusId(status.getStatusMaster().getId());
					vr.setStatusValue(status.getStatusMaster().getStatusDescription());
					vr.setReasonForRejection(status.getReasonForRejection());
				}
				rejectedVolunteersWithReason.add(vr);

			}
		}
		return rejectedVolunteersWithReason;

	}

	@GetMapping("/volunteer/dmprofile/check/{regNum}")
	@ResponseBody
	public Boolean checkDmProfileExist(@PathVariable String regNum) {

		Volunteer volunteer = volunteerService.getVolunteerById(regNum);
		VolunteerStatus status = volunteerStatusRepo
				.findTop1ByRegistrationNumberOrderByIdDesc(volunteer.getRegistrationNumber());

		if (status == null) {
			return false;

		} else {

			Long statusId = status.getStatusMaster().getId();
			if (statusId.equals(1L) || statusId.equals(12L) || statusId.equals(116L))
				return true;
		}

		return false;
	}

	/*	*//**
			 * This method is responsible to create volunteer demographic profile
			 * 
			 * @param volunteerDemographicProfile
			 * @return
			 *//*
				 * @PostMapping("/volunteer/dmprofile/")
				 * 
				 * @ResponseBody public Boolean createVDMProfile(@RequestBody
				 * VolunteerDemographicProfile volunteerDemographicProfile) {
				 * logger.info("creating volunteer dm profile"); try { Volunteer volunteer =
				 * null;// volunteerDemographicProfile.getVolunteer(); if (volunteer == null)
				 * return false; Volunteer volunteer2 =
				 * volunteerService.getVolunteerById(volunteer.getRegistrationNumber()); if
				 * (volunteer2 == null) return false;
				 * volunteer2.setLastScreeningDate(volunteerDemographicProfile.
				 * getScreeningicfDate()); volunteer2 = volunteerRepo.save(volunteer2);
				 * Timestamp ts = new Timestamp(new java.util.Date().getTime()); //
				 * volunteerDemographicProfile.setCreatedOn(ts);
				 * volunteerDemographicProfile.setRecordedOn(ts); //
				 * volunteerDemographicProfile.setVolunteer(volunteer2);
				 * volunteerDemographicProfileRepo.save(volunteerDemographicProfile);
				 * 
				 * return true; } catch (Exception e) { logger.error(e.getMessage()); } return
				 * false; }
				 */

	/**
	 * This method is responsible for updating the volunteer demographic profile
	 * 
	 * @param regNum
	 * @param volunteerDemographicProfile
	 * @return
	 */
	@PostMapping("/volunteer/dmprofile/{regNum}")
	@ResponseBody
	public Boolean editVDMProfile(@PathVariable String regNum, @RequestBody Map<String, Object> map) {
		logger.info("creating/editing volunteer DMProfile data  ");
		try {
			final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
			VolunteerDemographicProfile volunteerDemographicProfile = mapper.convertValue(map.get("msr_dm"),
					VolunteerDemographicProfile.class);
			Volunteer volunteer = volunteerRepo.findByRegistrationNumber(regNum);
			if (volunteer == null)
				return false;

			if (volunteerDemographicProfile == null)
				return false;

			if (volunteerDemographicProfile.getId() == null) {

				Timestamp ts = new Timestamp(new java.util.Date().getTime());
				volunteerDemographicProfile.setRecordedOn(ts);

				VolunteerScreeningHistory screeningHistory = new VolunteerScreeningHistory();
				screeningHistory.setCreated(new java.sql.Date(new java.util.Date().getTime()));

				User user = userRepository.findByUsername(volunteerDemographicProfile.getRecordedBy());
				screeningHistory.setCreatedBy(user.getUsername());
				screeningHistory.setScreeningDate(new Timestamp(new java.util.Date().getTime()));
				// //screeningHistory.setStatusMaster(1L);
				screeningHistory.setVolunteerRegNum(regNum);

				volunteerScreeningHistoryRepo.save(screeningHistory);

				volunteerDemographicProfile.setVolunteerScreeningHistory(screeningHistory);
				volunteerDemographicProfileRepo.save(volunteerDemographicProfile);

				VolunteerStatus volunteerStatus = new VolunteerStatus();
				StatusMaster statusMaster = new StatusMaster();
				statusMaster.setId(1L);
				volunteerStatus.setStatusMaster(statusMaster);
				volunteerStatus.setRegistrationNumber(volunteer.getRegistrationNumber());
				volunteerStatus.setScreeningId(screeningHistory.getId());
				volunteerStatusRepo.save(volunteerStatus);

				volunteer.setCurrentStatus(volunteerStatus.getId());
				volunteerRepo.save(volunteer);

				String tableNameOfVolunteer = VolunteerDemographicProfile.class.getAnnotation(Table.class).name();
				String jsonValue = JsonUtil.convertToJsonForInsert(volunteerDemographicProfile);
				try {
					auditService.saveAudit(volunteerDemographicProfile.getCreatedBy(), tableNameOfVolunteer, jsonValue,
							"Demographic Profile", AizantConstants.CREATE, null);
				} catch (Exception e) {
					logger.error("failed auditing " + e);
				}

			} else {
				VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo
						.findTop1ByVolunteerRegNumOrderByIdDesc(regNum);

				User user = userRepository.findByUsername(volunteerDemographicProfile.getRecordedBy());
				screeningHistory.setUpdatedBy(user.getUsername());
				screeningHistory.setUpdated(new java.sql.Date(new java.util.Date().getTime()));

				volunteerScreeningHistoryRepo.save(screeningHistory);
				volunteerDemographicProfile.setVolunteerScreeningHistory(screeningHistory);
				volunteerDemographicProfileRepo.save(volunteerDemographicProfile);

				String tableNameOfVolunteer = VolunteerDemographicProfile.class.getAnnotation(Table.class).name();

				List audit = mapper.convertValue(map.get("audit"), List.class);
				for (int i = 0; i < audit.size(); i++) {
					Audit ad = mapper.convertValue(audit.get(i), Audit.class);
					ad.setActionPerformed(AizantConstants.UPDATE);
					ad.setActionPerformedBy(volunteerDemographicProfile.getRecordedBy());
					ad.setActionPerformedDate(new Timestamp(new java.util.Date().getTime()));
					ad.setTableName(tableNameOfVolunteer);
					ad.setFormName("Demographic Profile");
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

	/**
	 * This method is responsible for getting volunteer demographic profile
	 * 
	 * @param regNum
	 * @return
	 */
	@GetMapping("/volunteer/dmprofile/{regNum}")
	@ResponseBody
	public VolunteerDemographicProfile getVDMProfile(@PathVariable String regNum) {
		try {

			VolunteerStatus volunteerStatus = volunteerStatusRepo.findTop1ByRegistrationNumberOrderByIdDesc(regNum);
			if (volunteerStatus == null)
				return null;

			if (volunteerStatus.getStatusMaster().getId().equals(1L)) {

				VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo
						.findTop1ByVolunteerRegNumOrderByIdDesc(regNum);

				if (screeningHistory == null)
					return null;

				VolunteerDemographicProfile volunteerDemographicProfile = volunteerDemographicProfileRepo
						.findByVolunteerScreeningHistory(screeningHistory);
				if (volunteerDemographicProfile != null) {
					Timestamp ts = volunteerDemographicProfile.getRecordedOn();
					if (ts != null)
						volunteerDemographicProfile.setRecordedOnDate(AizantUtil.toDate(ts));
					ts = volunteerDemographicProfile.getReviewedOn();
					if (ts != null)
						volunteerDemographicProfile.setReviewedOnDate(AizantUtil.toDate(ts));
				}

				Volunteer volunteer2 = volunteerService.getVolunteerById(regNum);
				if (volunteer2 == null)
					return null;
				volunteerDemographicProfile.setVolunteerIntials(volunteer2.getIntials());

				String age = AizantUtil.getAge(volunteer2.getDob());
				volunteerDemographicProfile.setVolunteerAge(age);

				if (volunteerDemographicProfile.getHeight() != null
						&& volunteerDemographicProfile.getWeight() != null) {
					String bmi = AizantUtil.calculateBmi(String.valueOf(volunteerDemographicProfile.getHeight()),
							String.valueOf(volunteerDemographicProfile.getWeight()));
					volunteerDemographicProfile.setVolunteerBmi(bmi);
				}

				Timestamp ts = volunteerDemographicProfile.getRecordedOn();
				if (ts != null)
					volunteerDemographicProfile.setRecordedOnDate(AizantUtil.toDate(ts));

				ts = volunteerDemographicProfile.getReviewedOn();
				if (ts != null)
					volunteerDemographicProfile.setReviewedOnDate(AizantUtil.toDate(ts));

				Date lastDate = volunteer2.getLastParticipationDate();
				if (lastDate != null) {
					Date currDate = new Date(new java.util.Date().getTime());

					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDate date1 = LocalDate.parse(lastDate.toString(), formatter);
					LocalDate date2 = LocalDate.parse(currDate.toString(), formatter);
					Long days = ChronoUnit.DAYS.between(date1, date2);
					volunteerDemographicProfile.setNoOfDaysCompletedFromLastPartcipation(days);

					if (days < 90) {
						volunteerDemographicProfile.setProceedForScreening(false);
					} else {
						volunteerDemographicProfile.setProceedForScreening(true);
					}
				}

				if (volunteerDemographicProfile.getLangRead() != null
						&& !volunteerDemographicProfile.getLangRead().isEmpty()) {
					List<String> items = Arrays
							.asList(volunteerDemographicProfile.getLangRead().split(AizantConstants.SPLIT_BY_COMMA));
					volunteerDemographicProfile.setLangReadList(items);
				} else {
					volunteerDemographicProfile.setLangReadList(null);
				}

				if (volunteerDemographicProfile.getLangWrite() != null
						&& !volunteerDemographicProfile.getLangWrite().isEmpty()) {
					List<String> items = Arrays
							.asList(volunteerDemographicProfile.getLangWrite().split(AizantConstants.SPLIT_BY_COMMA));
					volunteerDemographicProfile.setLangWriteList(items);
				} else {
					volunteerDemographicProfile.setLangWriteList(null);
				}

				if (volunteerDemographicProfile.getDietStatus() != null
						&& !volunteerDemographicProfile.getDietStatus().isEmpty()) {
					List<String> items = Arrays
							.asList(volunteerDemographicProfile.getDietStatus().split(AizantConstants.SPLIT_BY_COMMA));
					volunteerDemographicProfile.setDietList(items);
				} else {
					volunteerDemographicProfile.setDietList(null);
				}
				return volunteerDemographicProfile;
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return null;
	}

	@GetMapping("/volunteer/rcomp/check/{regNum}")
	@ResponseBody
	public Boolean checkRestrictionCompl(@PathVariable String regNum) {
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

	@GetMapping("/volunteer/rcomp/{regNum}")
	@ResponseBody
	public VolunteerRestrictionCompliance getVolunteerRestrictionComp(@PathVariable String regNum) {
		VolunteerStatus volunteerStatus = volunteerStatusRepo.findTop1ByRegistrationNumberOrderByIdDesc(regNum);
		if (volunteerStatus == null)
			return null;

		if (volunteerStatus.getStatusMaster().getId().equals(1L)) {
			VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo
					.findTop1ByVolunteerRegNumOrderByIdDesc(regNum);

			if (screeningHistory == null)
				return null;

			VolunteerRestrictionCompliance volunteerRestrictionCompliance = volunteerRestrictionComplianceRepo
					.findByVolunteerScreeningHistory(screeningHistory);
			if (volunteerRestrictionCompliance != null) {
				Timestamp ts = volunteerRestrictionCompliance.getRecordedOn();
				if (ts != null)
					volunteerRestrictionCompliance.setRecordedOnDate(AizantUtil.toDate(ts));
				ts = volunteerRestrictionCompliance.getReviewedOn();
				if (ts != null)
					volunteerRestrictionCompliance.setReviewedOnDate(AizantUtil.toDate(ts));
			}

			return volunteerRestrictionCompliance;
		} else {
			return null;
		}

	}

	/*
	 * @PostMapping("/volunteer/rcomp")
	 * 
	 * @ResponseBody public Boolean createVolunteerRComp(@RequestBody
	 * VolunteerRestrictionCompliance volunteerRestrictionCompliance) { Volunteer
	 * volunteer2 = volunteerService
	 * .getVolunteerById(volunteerRestrictionCompliance.getVolunteer().
	 * getRegistrationNumber()); if (volunteer2 == null) return false; Volunteer v2
	 * = volunteerRepo.save(volunteer2); Timestamp ts = new Timestamp(new
	 * java.util.Date().getTime()); //
	 * volunteerRestrictionCompliance.setCreatedOn(ts);
	 * volunteerRestrictionCompliance.setRecordedOn(ts);
	 * volunteerRestrictionCompliance.setVolunteer(v2);
	 * volunteerRestrictionComplianceRepo.save(volunteerRestrictionCompliance);
	 * 
	 * return true; }
	 */

	@PostMapping("/volunteer/rcomp/{regNum}")
	@ResponseBody
	public Boolean updateVolunteerRComp(@PathVariable String regNum, @RequestBody Map<String, Object> map) {
		logger.info("creating/editing volunteer rcomp data  ");
		try {
			final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
			VolunteerRestrictionCompliance volunteerRestrictionCompliance = mapper.convertValue(map.get("msr_rc"),
					VolunteerRestrictionCompliance.class);
			if (volunteerRestrictionCompliance == null)
				return null;

			if (volunteerRestrictionCompliance.getId() == null) {

				Timestamp ts = new Timestamp(new java.util.Date().getTime());
				volunteerRestrictionCompliance.setRecordedOn(ts);

				VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo
						.findTop1ByVolunteerRegNumOrderByIdDesc(regNum.trim());
				screeningHistory.setCreated(new java.sql.Date(new java.util.Date().getTime()));

				User user = userRepository.findByUsername(volunteerRestrictionCompliance.getRecordedBy());
				screeningHistory.setCreatedBy(user.getUsername());
				// screeningHistory.setScreeningDate(new Timestamp(new
				// java.util.Date().getTime()));
				//// screeningHistory.setStatusMaster(1L);
				screeningHistory.setVolunteerRegNum(regNum);

				volunteerScreeningHistoryRepo.save(screeningHistory);

				volunteerRestrictionCompliance.setVolunteerScreeningHistory(screeningHistory);
				volunteerRestrictionComplianceRepo.save(volunteerRestrictionCompliance);

				String tableNameOfVolunteer = VolunteerRestrictionCompliance.class.getAnnotation(Table.class).name();
				String jsonValue = JsonUtil.convertToJsonForInsert(volunteerRestrictionCompliance);
				try {
					auditService.saveAudit(volunteerRestrictionCompliance.getCreatedBy(), tableNameOfVolunteer,
							jsonValue, "Restriction Complaince", AizantConstants.CREATE, null);
				} catch (Exception e) {
					logger.error("failed auditing " + e);
				}
			} else {
				VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo
						.findTop1ByVolunteerRegNumOrderByIdDesc(regNum);

				User user = userRepository.findByUsername(volunteerRestrictionCompliance.getRecordedBy());
				screeningHistory.setUpdatedBy(user.getUsername());
				screeningHistory.setUpdated(new java.sql.Date(new java.util.Date().getTime()));

				volunteerScreeningHistoryRepo.save(screeningHistory);
				volunteerRestrictionCompliance.setVolunteerScreeningHistory(screeningHistory);
				volunteerRestrictionComplianceRepo.save(volunteerRestrictionCompliance);

				// VolunteerRestrictionCompliance volunteerRestrictionCompliance2 =
				// volunteerRestrictionComplianceRepo
				// .findByVolunteerScreeningHistory(screeningHistory);

				String tableNameOfVolunteer = VolunteerRestrictionCompliance.class.getAnnotation(Table.class).name();
				/*
				 * String jsonValue =
				 * JsonUtil.convertToJsonForUpdate(volunteerRestrictionCompliance,
				 * volunteerRestrictionCompliance2);
				 * 
				 * if (jsonValue != null) { try {
				 * auditService.saveAudit(volunteerRestrictionCompliance.getUpdatedBy(),
				 * tableNameOfVolunteer, jsonValue, tableNameOfVolunteer,
				 * AizantConstants.UPDATE, volunteerRestrictionCompliance.getUpdateComments());
				 * } catch (Exception e) { logger.error("failed auditing " + e); } }
				 */
				List audit = mapper.convertValue(map.get("audit"), List.class);
				for (int i = 0; i < audit.size(); i++) {
					Audit ad = mapper.convertValue(audit.get(i), Audit.class);
					ad.setActionPerformed(AizantConstants.UPDATE);
					ad.setActionPerformedBy(volunteerRestrictionCompliance.getRecordedBy());
					ad.setActionPerformedDate(new Timestamp(new java.util.Date().getTime()));
					ad.setTableName(tableNameOfVolunteer);
					ad.setFormName(tableNameOfVolunteer);
					ad.setFormName("Restriction Complaince");
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

	@GetMapping("/volunteer/vitalsigns/check/{regNum}")
	@ResponseBody
	public Boolean checkVitalSigns(@PathVariable String regNum) {
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

	@GetMapping("/volunteer/vitalsigns/{regNum}")
	@ResponseBody
	public VolunteerVitalSigns getVolunteerVitalSigns(@PathVariable String regNum) {
		VolunteerStatus volunteerStatus = volunteerStatusRepo.findTop1ByRegistrationNumberOrderByIdDesc(regNum);
		if (volunteerStatus == null)
			return null;

		if (volunteerStatus.getStatusMaster().getId().equals(1L)) {
			VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo
					.findTop1ByVolunteerRegNumOrderByIdDesc(regNum);

			if (screeningHistory == null)
				return null;

			VolunteerVitalSigns volunteerVitalSigns = volunteerVitalSignsRepo
					.findByVolunteerScreeningHistory(screeningHistory);
			if (volunteerVitalSigns != null) {
				Timestamp ts = volunteerVitalSigns.getRecordedOn();
				if (ts != null)
					volunteerVitalSigns.setRecordedOnDate(AizantUtil.toDate(ts));
				ts = volunteerVitalSigns.getReviewedOn();
				if (ts != null)
					volunteerVitalSigns.setReviewedOnDate(AizantUtil.toDate(ts));
			}
			return volunteerVitalSigns;
		} else {
			return null;
		}
	}

	/*
	 * @PostMapping("/volunteer/vitalsigns")
	 * 
	 * @ResponseBody public Boolean createVolunteerVitalSigns(@RequestBody
	 * VolunteerVitalSigns volunteerVitalSigns) { Volunteer volunteer2 =
	 * volunteerService
	 * .getVolunteerById(volunteerVitalSigns.getVolunteer().getRegistrationNumber())
	 * ; volunteer2 = volunteerRepo.save(volunteer2); Timestamp ts = new
	 * Timestamp(new java.util.Date().getTime()); //
	 * volunteerVitalSigns.setCreatedOn(ts); volunteerVitalSigns.setRecordedOn(ts);
	 * volunteerVitalSigns.setVolunteer(volunteer2);
	 * volunteerVitalSignsRepo.save(volunteerVitalSigns); return true; }
	 */

	@PostMapping("/volunteer/vitalsigns/{regNum}")
	@ResponseBody
	public Boolean updateVolunteerVitalSigns(@PathVariable String regNum, @RequestBody Map<String, Object> map) {
		logger.info("creating/editing volunteer vital signs data  ");
		try {
			final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
			VolunteerVitalSigns volunteerVitalSigns = mapper.convertValue(map.get("msr_vs"), VolunteerVitalSigns.class);
			if (volunteerVitalSigns == null)
				return null;

			if (volunteerVitalSigns.getId() == null) {

				Timestamp ts = new Timestamp(new java.util.Date().getTime());
				volunteerVitalSigns.setRecordedOn(ts);

				VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo
						.findTop1ByVolunteerRegNumOrderByIdDesc(regNum);
				screeningHistory.setCreated(new java.sql.Date(new java.util.Date().getTime()));

				User user = userRepository.findByUsername(volunteerVitalSigns.getRecordedBy());
				screeningHistory.setCreatedBy(user.getUsername());
				// screeningHistory.setScreeningDate(new Timestamp(new
				// java.util.Date().getTime()));
				// //screeningHistory.setStatusMaster(1L);
				screeningHistory.setVolunteerRegNum(regNum);

				volunteerScreeningHistoryRepo.save(screeningHistory);

				volunteerVitalSigns.setVolunteerScreeningHistory(screeningHistory);
				volunteerVitalSignsRepo.save(volunteerVitalSigns);

				String tableNameOfVolunteer = VolunteerVitalSigns.class.getAnnotation(Table.class).name();
				String jsonValue = JsonUtil.convertToJsonForInsert(volunteerVitalSigns);
				try {
					auditService.saveAudit(volunteerVitalSigns.getCreatedBy(), tableNameOfVolunteer, jsonValue,
							"Vital Signs", AizantConstants.CREATE, null);
				} catch (Exception e) {
					logger.error("failed auditing " + e);
				}
			} else {
				VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo
						.findTop1ByVolunteerRegNumOrderByIdDesc(regNum);

				User user = userRepository.findByUsername(volunteerVitalSigns.getRecordedBy());
				screeningHistory.setUpdatedBy(user.getUsername());
				screeningHistory.setUpdated(new java.sql.Date(new java.util.Date().getTime()));

				volunteerScreeningHistoryRepo.save(screeningHistory);
				volunteerVitalSigns.setVolunteerScreeningHistory(screeningHistory);
				volunteerVitalSignsRepo.save(volunteerVitalSigns);

				// VolunteerVitalSigns volunteerVitalSigns2 = volunteerVitalSignsRepo
				// .findByVolunteerScreeningHistory(screeningHistory);

				String tableNameOfVolunteer = VolunteerVitalSigns.class.getAnnotation(Table.class).name();
				/*
				 * String jsonValue = JsonUtil.convertToJsonForUpdate(volunteerVitalSigns,
				 * volunteerVitalSigns2);
				 * 
				 * if (jsonValue != null) { try {
				 * auditService.saveAudit(volunteerVitalSigns.getUpdatedBy(),
				 * tableNameOfVolunteer, jsonValue, tableNameOfVolunteer,
				 * AizantConstants.UPDATE, volunteerVitalSigns.getUpdateComments()); } catch
				 * (Exception e) { logger.error("failed auditing " + e); } }
				 */
				List audit = mapper.convertValue(map.get("audit"), List.class);
				for (int i = 0; i < audit.size(); i++) {
					Audit ad = mapper.convertValue(audit.get(i), Audit.class);
					ad.setActionPerformed(AizantConstants.UPDATE);
					ad.setActionPerformedBy(volunteerVitalSigns.getRecordedBy());
					ad.setActionPerformedDate(new Timestamp(new java.util.Date().getTime()));
					ad.setTableName(tableNameOfVolunteer);
					ad.setFormName("Vital Signs");
					auditRepo.save(ad);
				}
			}

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	@GetMapping("/volunteer/personalhistory/check/{regNum}")
	@ResponseBody
	public Boolean checkPersonalHistory(@PathVariable String regNum) {
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

	@GetMapping("/volunteer/personalhistory/{regNum}")
	@ResponseBody
	public VolunteerPersonalHistory getVolunteerPH(@PathVariable String regNum) {
		VolunteerStatus volunteerStatus = volunteerStatusRepo.findTop1ByRegistrationNumberOrderByIdDesc(regNum);
		if (volunteerStatus == null)
			return null;

		if (volunteerStatus.getStatusMaster().getId().equals(1L)) {
			VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo
					.findTop1ByVolunteerRegNumOrderByIdDesc(regNum);

			if (screeningHistory == null)
				return null;

			VolunteerPersonalHistory volunteerPersonalHistory = volunteerPersonalHistoryRepo
					.findByVolunteerScreeningHistory(screeningHistory);
			if (volunteerPersonalHistory != null) {
				Timestamp ts = volunteerPersonalHistory.getRecordedOn();
				if (ts != null)
					volunteerPersonalHistory.setRecordedOnDate(AizantUtil.toDate(ts));
				ts = volunteerPersonalHistory.getReviewedOn();
				if (ts != null)
					volunteerPersonalHistory.setReviewedOnDate(AizantUtil.toDate(ts));
			}
			return volunteerPersonalHistory;
		} else {
			return null;
		}
	}

	/*
	 * @PostMapping("/volunteer/personalhistory")
	 * 
	 * @ResponseBody public Boolean createVolunteerPH(@RequestBody
	 * VolunteerPersonalHistory volunteerPersonalHistory) { Volunteer volunteer2 =
	 * volunteerService .getVolunteerById(volunteerPersonalHistory.getVolunteer().
	 * getRegistrationNumber()); Volunteer volunteer =
	 * volunteerRepo.save(volunteer2); Timestamp ts = new Timestamp(new
	 * java.util.Date().getTime()); // volunteerPersonalHistory.setCreatedOn(ts);
	 * volunteerPersonalHistory.setRecordedOn(ts);
	 * volunteerPersonalHistory.setVolunteer(volunteer);
	 * volunteerPersonalHistoryRepo.save(volunteerPersonalHistory); return true; }
	 */

	@PostMapping("/volunteer/personalhistory/{regNum}")
	@ResponseBody
	public Boolean editVolunteerPH(@PathVariable String regNum, @RequestBody Map<String, Object> map) {
		logger.info("creating/editing volunteer personal history data  ");
		try {
			final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
			VolunteerPersonalHistory volunteerPersonalHistory = mapper.convertValue(map.get("msr_ph"),
					VolunteerPersonalHistory.class);
			if (volunteerPersonalHistory == null)
				return null;

			if (volunteerPersonalHistory.getId() == null) {

				Timestamp ts = new Timestamp(new java.util.Date().getTime());
				volunteerPersonalHistory.setRecordedOn(ts);

				VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo
						.findTop1ByVolunteerRegNumOrderByIdDesc(regNum);
				screeningHistory.setCreated(new java.sql.Date(new java.util.Date().getTime()));

				User user = userRepository.findByUsername(volunteerPersonalHistory.getRecordedBy());
				screeningHistory.setCreatedBy(user.getUsername());
				// screeningHistory.setScreeningDate(new Timestamp(new
				// java.util.Date().getTime()));
				// screeningHistory.setStatusMaster(1L);
				screeningHistory.setVolunteerRegNum(regNum);

				volunteerScreeningHistoryRepo.save(screeningHistory);

				volunteerPersonalHistory.setVolunteerScreeningHistory(screeningHistory);
				volunteerPersonalHistoryRepo.save(volunteerPersonalHistory);

				String tableNameOfVolunteer = VolunteerPersonalHistory.class.getAnnotation(Table.class).name();
				String jsonValue = JsonUtil.convertToJsonForInsert(volunteerPersonalHistory);
				try {
					auditService.saveAudit(volunteerPersonalHistory.getCreatedBy(), tableNameOfVolunteer, jsonValue,
							"Personal History", AizantConstants.CREATE, null);
				} catch (Exception e) {
					logger.error("failed auditing " + e);
				}
			} else {
				VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo
						.findTop1ByVolunteerRegNumOrderByIdDesc(regNum);

				User user = userRepository.findByUsername(volunteerPersonalHistory.getRecordedBy());
				screeningHistory.setUpdatedBy(user.getUsername());
				screeningHistory.setUpdated(new java.sql.Date(new java.util.Date().getTime()));

				volunteerScreeningHistoryRepo.save(screeningHistory);
				volunteerPersonalHistory.setVolunteerScreeningHistory(screeningHistory);
				volunteerPersonalHistoryRepo.save(volunteerPersonalHistory);
				// VolunteerPersonalHistory volunteerPersonalHistory2 =
				// volunteerPersonalHistoryRepo
				// .findByVolunteerScreeningHistory(screeningHistory);

				String tableNameOfVolunteer = VolunteerPersonalHistory.class.getAnnotation(Table.class).name();

				/*
				 * String jsonValue = JsonUtil.convertToJsonForUpdate(volunteerPersonalHistory,
				 * volunteerPersonalHistory2);
				 * 
				 * 
				 * if (jsonValue != null) { try {
				 * auditService.saveAudit(volunteerPersonalHistory.getUpdatedBy(),
				 * tableNameOfVolunteer, jsonValue, tableNameOfVolunteer,
				 * AizantConstants.UPDATE, volunteerPersonalHistory.getUpdateComments()); }
				 * catch (Exception e) { logger.error("failed auditing " + e); } }
				 */
				List audit = mapper.convertValue(map.get("audit"), List.class);
				for (int i = 0; i < audit.size(); i++) {
					Audit ad = mapper.convertValue(audit.get(i), Audit.class);
					ad.setActionPerformed(AizantConstants.UPDATE);
					ad.setActionPerformedBy(volunteerPersonalHistory.getRecordedBy());
					ad.setActionPerformedDate(new Timestamp(new java.util.Date().getTime()));
					ad.setTableName(tableNameOfVolunteer);
					ad.setFormName("Personal History");
					auditRepo.save(ad);
				}
			}

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	@GetMapping("/volunteer/familyhistory/check/{regNum}")
	@ResponseBody
	public Boolean checkFamilyHistory(@PathVariable String regNum) {
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

	@GetMapping("/volunteer/familyhistory/{regNum}")
	@ResponseBody
	public VolunteerFamilyHistory getVolunteerFh(@PathVariable String regNum) {
		VolunteerStatus volunteerStatus = volunteerStatusRepo.findTop1ByRegistrationNumberOrderByIdDesc(regNum);
		if (volunteerStatus == null)
			return null;

		if (volunteerStatus.getStatusMaster().getId().equals(1L)) {
			VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo
					.findTop1ByVolunteerRegNumOrderByIdDesc(regNum);

			if (screeningHistory == null)
				return null;

			VolunteerFamilyHistory volunteerFamilyHistory = volunteerFamilyHistoryRepo
					.findByVolunteerScreeningHistory(screeningHistory);
			if (volunteerFamilyHistory != null) {
				Timestamp ts = volunteerFamilyHistory.getRecordedOn();
				if (ts != null)
					volunteerFamilyHistory.setRecordedOnDate(AizantUtil.toDate(ts));
				ts = volunteerFamilyHistory.getReviewedOn();
				if (ts != null)
					volunteerFamilyHistory.setReviewedOnDate(AizantUtil.toDate(ts));
			}
			return volunteerFamilyHistory;
		} else {
			return null;
		}
	}

	/*
	 * @PostMapping("/volunteer/familyhistory")
	 * 
	 * @ResponseBody public Boolean createVolunteerFH(@RequestBody
	 * VolunteerFamilyHistory familyHistory) { Volunteer v =
	 * volunteerService.getVolunteerById(familyHistory.getVolunteer().
	 * getRegistrationNumber()); Volunteer v2 = volunteerRepo.save(v);
	 * 
	 * Timestamp ts = new Timestamp(new java.util.Date().getTime()); //
	 * familyHistory.setCreatedOn(ts); familyHistory.setRecordedOn(ts);
	 * 
	 * familyHistory.setVolunteer(v2);
	 * volunteerFamilyHistoryRepo.save(familyHistory);
	 * 
	 * return true; }
	 */
	@PostMapping("/volunteer/familyhistory/{regNum}")
	@ResponseBody
	public Boolean editVolunteerFH(@PathVariable String regNum, @RequestBody Map<String, Object> map) {
		logger.info("creating/editing volunteer family history data  ");
		try {
			final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
			VolunteerFamilyHistory familyHistory = mapper.convertValue(map.get("msr_past2"),
					VolunteerFamilyHistory.class);
			if (familyHistory == null)
				return null;

			if (familyHistory.getId() == null) {

				Timestamp ts = new Timestamp(new java.util.Date().getTime());
				familyHistory.setRecordedOn(ts);

				VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo
						.findTop1ByVolunteerRegNumOrderByIdDesc(regNum);
				screeningHistory.setCreated(new java.sql.Date(new java.util.Date().getTime()));

				User user = userRepository.findByUsername(familyHistory.getRecordedBy());
				screeningHistory.setCreatedBy(user.getUsername());
				// screeningHistory.setScreeningDate(new Timestamp(new
				// java.util.Date().getTime()));
				// screeningHistory.setStatusMaster(1L);
				screeningHistory.setVolunteerRegNum(regNum);

				volunteerScreeningHistoryRepo.save(screeningHistory);

				familyHistory.setVolunteerScreeningHistory(screeningHistory);
				volunteerFamilyHistoryRepo.save(familyHistory);
				String tableNameOfVolunteer = VolunteerFamilyHistory.class.getAnnotation(Table.class).name();
				String jsonValue = JsonUtil.convertToJsonForInsert(familyHistory);
				try {
					auditService.saveAudit(familyHistory.getCreatedBy(), tableNameOfVolunteer, jsonValue,
							"Family History", AizantConstants.CREATE, null);
				} catch (Exception e) {
					logger.error("failed auditing " + e);
				}
			} else {
				VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo
						.findTop1ByVolunteerRegNumOrderByIdDesc(regNum);

				User user = userRepository.findByUsername(familyHistory.getRecordedBy());
				screeningHistory.setUpdatedBy(user.getUsername());
				screeningHistory.setUpdated(new java.sql.Date(new java.util.Date().getTime()));

				volunteerScreeningHistoryRepo.save(screeningHistory);
				familyHistory.setVolunteerScreeningHistory(screeningHistory);
				volunteerFamilyHistoryRepo.save(familyHistory);

				// VolunteerFamilyHistory familyHistory2 = volunteerFamilyHistoryRepo
				// .findByVolunteerScreeningHistory(screeningHistory);

				String tableNameOfVolunteer = VolunteerFamilyHistory.class.getAnnotation(Table.class).name();
				/*
				 * String jsonValue = JsonUtil.convertToJsonForUpdate(familyHistory,
				 * familyHistory2);
				 * 
				 * 
				 * if (jsonValue != null) { try {
				 * auditService.saveAudit(familyHistory.getUpdatedBy(), tableNameOfVolunteer,
				 * jsonValue, tableNameOfVolunteer, AizantConstants.UPDATE,
				 * familyHistory.getUpdateComments()); } catch (Exception e) {
				 * logger.error("failed auditing " + e); } }
				 */
				List audit = mapper.convertValue(map.get("audit"), List.class);
				for (int i = 0; i < audit.size(); i++) {
					Audit ad = mapper.convertValue(audit.get(i), Audit.class);
					ad.setActionPerformed(AizantConstants.UPDATE);
					ad.setActionPerformedBy(familyHistory.getRecordedBy());
					ad.setActionPerformedDate(new Timestamp(new java.util.Date().getTime()));
					ad.setTableName(tableNameOfVolunteer);
					ad.setFormName("Family History");
					auditRepo.save(ad);
				}
			}

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	@GetMapping("/volunteer/menshistory/check/{regNum}")
	@ResponseBody
	public Boolean checkMensHistory(@PathVariable String regNum) {

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

	@GetMapping("/volunteer/menshistory/{regNum}")
	@ResponseBody
	public VolunteerMenstrualHistory getVolunteerMH(@PathVariable String regNum) {
		VolunteerStatus volunteerStatus = volunteerStatusRepo.findTop1ByRegistrationNumberOrderByIdDesc(regNum);
		if (volunteerStatus == null)
			return null;

		if (volunteerStatus.getStatusMaster().getId().equals(1L)) {
			VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo
					.findTop1ByVolunteerRegNumOrderByIdDesc(regNum);

			if (screeningHistory == null)
				return null;

			VolunteerMenstrualHistory volunteerMenstrualHistory = volunteerMenstrualHistoryRepo
					.findByVolunteerScreeningHistory(screeningHistory);
			if (volunteerMenstrualHistory != null) {
				Timestamp ts = volunteerMenstrualHistory.getRecordedOn();
				if (ts != null)
					volunteerMenstrualHistory.setRecordedOnDate(AizantUtil.toDate(ts));
				ts = volunteerMenstrualHistory.getReviewedOn();
				if (ts != null)
					volunteerMenstrualHistory.setReviewedOnDate(AizantUtil.toDate(ts));
			}
			return volunteerMenstrualHistory;
		} else {
			return null;
		}
	}

	/*
	 * @PostMapping("/volunteer/menshistory")
	 * 
	 * @ResponseBody public Boolean createVolunteerMH(@RequestBody
	 * VolunteerMenstrualHistory volunteerMenstrualHistory) { Volunteer v =
	 * volunteerService .getVolunteerById(volunteerMenstrualHistory.getVolunteer().
	 * getRegistrationNumber()); if (v == null) return false; Volunteer v2 =
	 * volunteerRepo.save(v);
	 * 
	 * Timestamp ts = new Timestamp(new java.util.Date().getTime()); //
	 * volunteerMenstrualHistory.setCreatedOn(ts);
	 * volunteerMenstrualHistory.setRecordedOn(ts);
	 * 
	 * volunteerMenstrualHistory.setVolunteer(v2);
	 * volunteerMenstrualHistoryRepo.save(volunteerMenstrualHistory); return true; }
	 */

	@PostMapping("/volunteer/menshistory/{regNum}")
	@ResponseBody
	public Boolean editVolunteerMH(@PathVariable String regNum, @RequestBody Map<String, Object> map) {
		logger.info("creating/editing volunteer mens history data  ");
		try {
			final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
			VolunteerMenstrualHistory volunteerMenstrualHistory = mapper.convertValue(map.get("msr_past3"),
					VolunteerMenstrualHistory.class);
			if (volunteerMenstrualHistory == null)
				return null;

			if (volunteerMenstrualHistory.getId() == null) {

				Timestamp ts = new Timestamp(new java.util.Date().getTime());
				volunteerMenstrualHistory.setRecordedOn(ts);

				VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo
						.findTop1ByVolunteerRegNumOrderByIdDesc(regNum);
				screeningHistory.setCreated(new java.sql.Date(new java.util.Date().getTime()));

				User user = userRepository.findByUsername(volunteerMenstrualHistory.getRecordedBy());
				screeningHistory.setCreatedBy(user.getUsername());
				// screeningHistory.setScreeningDate(new Timestamp(new
				// java.util.Date().getTime()));
				// screeningHistory.setStatusMaster(1L);
				screeningHistory.setVolunteerRegNum(regNum);

				volunteerScreeningHistoryRepo.save(screeningHistory);

				volunteerMenstrualHistory.setVolunteerScreeningHistory(screeningHistory);
				volunteerMenstrualHistoryRepo.save(volunteerMenstrualHistory);

				String tableNameOfVolunteer = VolunteerMenstrualHistory.class.getAnnotation(Table.class).name();
				String jsonValue = JsonUtil.convertToJsonForInsert(volunteerMenstrualHistory);
				try {
					auditService.saveAudit(volunteerMenstrualHistory.getCreatedBy(), tableNameOfVolunteer, jsonValue,
							"Menstrual History", AizantConstants.CREATE, null);
				} catch (Exception e) {
					logger.error("failed auditing " + e);
				}
			} else {
				VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo
						.findTop1ByVolunteerRegNumOrderByIdDesc(regNum);

				User user = userRepository.findByUsername(volunteerMenstrualHistory.getRecordedBy());
				screeningHistory.setUpdatedBy(user.getUsername());
				screeningHistory.setUpdated(new java.sql.Date(new java.util.Date().getTime()));

				volunteerScreeningHistoryRepo.save(screeningHistory);
				volunteerMenstrualHistory.setVolunteerScreeningHistory(screeningHistory);
				volunteerMenstrualHistoryRepo.save(volunteerMenstrualHistory);

				// VolunteerMenstrualHistory volunteerMenstrualHistory2 =
				// volunteerMenstrualHistoryRepo
				// .findByVolunteerScreeningHistory(screeningHistory);

				String tableNameOfVolunteer = VolunteerRestrictionCompliance.class.getAnnotation(Table.class).name();
				/*
				 * String jsonValue = JsonUtil.convertToJsonForUpdate(volunteerMenstrualHistory,
				 * volunteerMenstrualHistory2);
				 * 
				 * if (jsonValue != null) { try {
				 * auditService.saveAudit(volunteerMenstrualHistory.getUpdatedBy(),
				 * tableNameOfVolunteer, jsonValue, tableNameOfVolunteer,
				 * AizantConstants.UPDATE, volunteerMenstrualHistory.getUpdateComments()); }
				 * catch (Exception e) { logger.error("failed auditing " + e); } }
				 */
				List audit = mapper.convertValue(map.get("audit"), List.class);
				for (int i = 0; i < audit.size(); i++) {
					Audit ad = mapper.convertValue(audit.get(i), Audit.class);
					ad.setActionPerformed(AizantConstants.UPDATE);
					ad.setActionPerformedBy(volunteerMenstrualHistory.getRecordedBy());
					ad.setActionPerformedDate(new Timestamp(new java.util.Date().getTime()));
					ad.setTableName(tableNameOfVolunteer);
					ad.setFormName("Menstrual History");
					auditRepo.save(ad);
				}
			}

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	@GetMapping("/volunteer/pasthistory/check/{regNum}")
	@ResponseBody
	public Boolean checkPastHistory(@PathVariable String regNum) {
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

	@GetMapping("/volunteer/pasthistory/{regNum}")
	@ResponseBody
	public VolunteerPastHistory getVolunteerPastHistory(@PathVariable String regNum) {
		VolunteerStatus volunteerStatus = volunteerStatusRepo.findTop1ByRegistrationNumberOrderByIdDesc(regNum);
		if (volunteerStatus == null)
			return null;

		if (volunteerStatus.getStatusMaster().getId().equals(1L)) {
			VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo
					.findTop1ByVolunteerRegNumOrderByIdDesc(regNum);

			if (screeningHistory == null)
				return null;

			VolunteerPastHistory volunteerPastHistory = volunteerPastHistoryRepo
					.findByVolunteerScreeningHistory(screeningHistory);
			if (volunteerPastHistory != null) {
				Timestamp ts = volunteerPastHistory.getRecordedOn();
				if (ts != null)
					volunteerPastHistory.setRecordedOnDate(AizantUtil.toDate(ts));
				ts = volunteerPastHistory.getReviewedOn();
				if (ts != null)
					volunteerPastHistory.setReviewedOnDate(AizantUtil.toDate(ts));
			}
			return volunteerPastHistory;
		} else {
			return null;
		}
	}

	/*
	 * @PostMapping("/volunteer/pasthistory")
	 * 
	 * @ResponseBody public Boolean createVolunteerPasthistory(@RequestBody
	 * VolunteerPastHistory volunteerPastHistory) { Volunteer volunteer2 =
	 * volunteerService
	 * .getVolunteerById(volunteerPastHistory.getVolunteer().getRegistrationNumber()
	 * ); if (volunteer2 == null) return false; Volunteer v2 =
	 * volunteerRepo.save(volunteer2); Timestamp ts = new Timestamp(new
	 * java.util.Date().getTime()); // volunteerPastHistory.setCreatedOn(ts);
	 * volunteerPastHistory.setRecordedOn(ts);
	 * volunteerPastHistory.setVolunteer(v2);
	 * volunteerPastHistoryRepo.save(volunteerPastHistory);
	 * 
	 * return true; }
	 */

	@PostMapping("/volunteer/pasthistory/{regNum}")
	@ResponseBody
	public Boolean updateVolunteerPasthistory(@PathVariable String regNum, @RequestBody Map<String, Object> map) {
		logger.info("creating/editing volunteer past history data  ");
		try {

			final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
			VolunteerPastHistory volunteerPastHistory = mapper.convertValue(map.get("msr_past1"),
					VolunteerPastHistory.class);
			if (volunteerPastHistory == null)
				return null;

			if (volunteerPastHistory.getId() == null) {

				Timestamp ts = new Timestamp(new java.util.Date().getTime());
				volunteerPastHistory.setRecordedOn(ts);

				VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo
						.findTop1ByVolunteerRegNumOrderByIdDesc(regNum);
				screeningHistory.setCreated(new java.sql.Date(new java.util.Date().getTime()));

				User user = userRepository.findByUsername(volunteerPastHistory.getRecordedBy());
				screeningHistory.setCreatedBy(user.getUsername());
				// screeningHistory.setScreeningDate(new Timestamp(new
				// java.util.Date().getTime()));
				// screeningHistory.setStatusMaster(1L);
				screeningHistory.setVolunteerRegNum(regNum);

				volunteerScreeningHistoryRepo.save(screeningHistory);

				volunteerPastHistory.setVolunteerScreeningHistory(screeningHistory);
				volunteerPastHistoryRepo.save(volunteerPastHistory);

				String tableNameOfVolunteer = VolunteerPastHistory.class.getAnnotation(Table.class).name();
				String jsonValue = JsonUtil.convertToJsonForInsert(volunteerPastHistory);
				try {
					auditService.saveAudit(volunteerPastHistory.getCreatedBy(), tableNameOfVolunteer, jsonValue,
							"Past History", AizantConstants.CREATE, null);
				} catch (Exception e) {
					logger.error("failed auditing " + e);
				}

			} else {
				VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo
						.findTop1ByVolunteerRegNumOrderByIdDesc(regNum);

				User user = userRepository.findByUsername(volunteerPastHistory.getRecordedBy());
				screeningHistory.setUpdatedBy(user.getUsername());
				screeningHistory.setUpdated(new java.sql.Date(new java.util.Date().getTime()));

				volunteerScreeningHistoryRepo.save(screeningHistory);
				volunteerPastHistory.setVolunteerScreeningHistory(screeningHistory);
				volunteerPastHistoryRepo.save(volunteerPastHistory);

				// VolunteerPastHistory volunteerPastHistory2 = volunteerPastHistoryRepo
				// .findByVolunteerScreeningHistory(screeningHistory);

				String tableNameOfVolunteer = VolunteerRestrictionCompliance.class.getAnnotation(Table.class).name();
				/*
				 * String jsonValue = JsonUtil.convertToJsonForUpdate(volunteerPastHistory,
				 * volunteerPastHistory2);
				 * 
				 * 
				 * if (jsonValue != null) { try {
				 * auditService.saveAudit(volunteerPastHistory.getUpdatedBy(),
				 * tableNameOfVolunteer, jsonValue, tableNameOfVolunteer,
				 * AizantConstants.UPDATE, volunteerPastHistory.getUpdateComments()); } catch
				 * (Exception e) { logger.error("failed auditing " + e); } }
				 */
				List audit = mapper.convertValue(map.get("audit"), List.class);
				for (int i = 0; i < audit.size(); i++) {
					Audit ad = mapper.convertValue(audit.get(i), Audit.class);
					ad.setActionPerformed(AizantConstants.UPDATE);
					ad.setActionPerformedBy(volunteerPastHistory.getRecordedBy());
					ad.setActionPerformedDate(new Timestamp(new java.util.Date().getTime()));
					ad.setTableName(tableNameOfVolunteer);
					ad.setFormName("Past History");
					auditRepo.save(ad);
				}
			}

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	@PostMapping(path = "/volunteer/image", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public Boolean saveImage(@RequestParam("image") String imageBase64, @RequestParam("regNum") String regNum) {
		try {

			Volunteer volunteer2 = volunteerService.getVolunteerById(regNum);
			byte[] decodedImg = Base64.getDecoder().decode(imageBase64.getBytes());
			String folderS = AizantConstants.UPLOADED_FOLDER + "/" + regNum + "/" + AizantConstants.V_IMAGE + "/";
			Path folderPath = Paths.get(folderS);
			String imageName = regNum.toUpperCase() + "_photo.jpg";
			Path destinationFile = null;
			logger.info("saving image ==============" + regNum + "==imageBase64===" + imageBase64);
			if (!folderPath.toFile().exists()) {
				logger.info("image already exists ----overwriting   : " + folderPath);
				Files.createDirectories(folderPath);
				destinationFile = Paths.get(folderS, imageName);
				logger.info("destinationFile  : " + destinationFile);
				Files.write(destinationFile, decodedImg);
			} else {
				logger.info("image not exists in " + folderPath);
				destinationFile = Paths.get(folderS);
				FileUtils.deleteDirectory(new File(destinationFile.toString()));
				Files.createDirectories(folderPath);
				destinationFile = Paths.get(folderS, imageName);
				logger.info("destinationFile  : " + destinationFile);
				Files.write(destinationFile, decodedImg);
			}
			volunteer2.setImageName(imageName);
			volunteer2.setImageLocation(destinationFile.toString());
			volunteerRepo.save(volunteer2);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return true;
	}

	// @GetMapping("/copy")
	//
	// @ResponseBody
	// public void copyData() {
	//
	// String regn = null;
	// try {
	//
	// List<VolunteerMaster> aizantVolunteers = volunteerMasterRepo.findAll();
	// Integer i = 0;
	//
	// for (VolunteerMaster aizantVolunteers2 : aizantVolunteers) {
	// Boolean ins = false;
	// List<Volunteer> list = volunteerRepo.findAll();
	//
	// if (list == null || list.isEmpty()) {
	// ins = true;
	// } else {
	//
	// for (Volunteer volunteer1 : list) {
	// if (aizantVolunteers2.getVolunteerstrid() != null
	// &&
	// aizantVolunteers2.getVolunteerstrid().equals(volunteer1.getRegistrationNumber()))
	// {
	// ins = false;
	// break;
	// } else {
	// ins = true;
	// }
	// }
	// }
	//
	// if (ins) {
	//
	// Volunteer volunteer = null;
	// /*
	// * if (aizantVolunteers2.getImage_id() != null)
	// * volunteer.setImageId(Long.valueOf(aizantVolunteers2.getImage_id()));
	// *
	// * if (aizantVolunteers2.getFinger_id() != null)
	// * volunteer.setFingerId(Long.valueOf(aizantVolunteers2.getFinger_id()));
	// *
	// * StringBuilder address = new
	// * StringBuilder(aizantVolunteers2.getAddressfirst());
	// * address.append(aizantVolunteers2.getStreet()).append(" , ");
	// * address.append(aizantVolunteers2.getVillage()).append(" , ");
	// * address.append(aizantVolunteers2.getLocality()).append(" , ");
	// * address.append(aizantVolunteers2.getCityfirst()).append(" , ");
	// * address.append(aizantVolunteers2.getState()).append(" , ");
	// * address.append(aizantVolunteers2.getCountry()).append(" , ");
	// * address.append(aizantVolunteers2.getPincode());
	// *
	// * volunteer.setAddress(address.toString());
	// * volunteer.setCreatedOn(aizantVolunteers2.getReg_date());
	// * volunteer.setRegisteredOn(aizantVolunteers2.getReg_date());
	// *
	// * volunteer.setLangRead(aizantVolunteers2.getMothertongue());
	// * volunteer.setLangSpeak(aizantVolunteers2.getMothertongue());
	// * volunteer.setLangWrite(aizantVolunteers2.getMothertongue());
	// * volunteer.setApprovalStatus(aizantVolunteers2.getApprovalStatus());
	// *
	// * volunteer.setDiet(aizantVolunteers2.getDiet());
	// *
	// * Connection connection = JdbcUtil.getConnection();
	// *
	// * Statement st = connection.createStatement(); String str =
	// * "select birthDate from volunteer_master where volunteerstrid='" +
	// * aizantVolunteers2.getVolunteerstrid() + "'"; ResultSet rs =
	// * st.executeQuery(str); while (rs.next()) { volunteer.setDob(rs.getDate(1));
	// }
	// *
	// * str = "select approvalStatus from volunteer_master where volunteerstrid='"
	// +
	// * aizantVolunteers2.getVolunteerstrid() + "'"; rs = st.executeQuery(str);
	// while
	// * (rs.next()) { volunteer.setApprovalStatus((rs.getLong(1))); }
	// *
	// * // volunteer.setDob(aizantVolunteers2.getBirthDate()); // StringBuilder
	// * StringBuilder address2 = new
	// * StringBuilder(aizantVolunteers2.getPaddressfirst());
	// * address2.append(aizantVolunteers2.getPstreet()).append(" , ");
	// * address2.append(aizantVolunteers2.getPvillage()).append(" , ");
	// * address2.append(aizantVolunteers2.getPlocality()).append(" , ");
	// * address2.append(aizantVolunteers2.getPcityfirst()).append(" , ");
	// * address2.append(aizantVolunteers2.getPstate()).append(" , ");
	// * address2.append(aizantVolunteers2.getPcountry()).append(" , ");
	// * address2.append(aizantVolunteers2.getPpincode());
	// *
	// * volunteer.seteAddress(address2.toString());
	// *
	// * volunteer.setHeight(aizantVolunteers2.getHeight());
	// * volunteer.setWeight(aizantVolunteers2.getWeight());
	// *
	// * volunteer.setePhone(aizantVolunteers2.getPcontact()); //
	// * volunteer.seteName(aizantVolunteers2.getPlandline_contact()); // // // //
	// * volunteer.setRelationVol(aizantVolunteers2.get);
	// * volunteer.setFirstName(aizantVolunteers2.getFirstname()); String ch =
	// * aizantVolunteers2.getGender(); if (ch != null) { if
	// * (ch.equalsIgnoreCase("f")) { volunteer.setGender("FEMALE"); } else if
	// * (ch.equalsIgnoreCase("M")) { volunteer.setGender("MALE"); } else
	// * volunteer.setGender(ch); }
	// * volunteer.setIdMark1(aizantVolunteers2.getIdentification_marks()); // // //
	// * volunteer.setIdMark2(aizantVolunteers2.getIdentification_marks_2()); // //
	// //
	// * volunteer.setIntials(aizantVolunteers2.getINITIALS());
	// * volunteer.setLastName(aizantVolunteers2.getLastname()); // // //
	// * volunteer.setNationality(aizantVolunteers2.getra);
	// * volunteer.setOccupation(aizantVolunteers2.getOccupation());
	// * volunteer.setPhone(aizantVolunteers2.getContact());
	// * volunteer.setQualification(aizantVolunteers2.getEducationstatus());
	// * volunteer.setRegistrationNumber(aizantVolunteers2.getVolunteerstrid());
	// *
	// * // volunteer.setCreatedBy(aizantVolunteers2.getUpdatedby()); //
	// * volunteer.setUpdatedBy(aizantVolunteers2.getUpdatedby());
	// * volunteer.setComments(aizantVolunteers2.getReason());
	// */
	//
	// // regn = volunteer.getRegistrationNumber();
	//
	// volunteer = volunteerRepo.findByRegistrationNumber(regn);
	// if (volunteer != null)
	// volunteer.setFatherName(aizantVolunteers2.getF_first_name());
	// volunteerRepo.save(volunteer);
	//
	// } else {
	// System.out.println("insert skipped " + regn);
	// }
	//
	// }
	//
	// } catch (Exception e) {
	// System.out.println("failed : " + regn);
	// // logger.error(e.get);
	// }
	// }

	@GetMapping("/volunteer/getimage/{regNum}")
	@ResponseBody
	public ResponseEntity<String> getvolunteerImage(@PathVariable String regNum) {
		Volunteer volunteer = volunteerRepo.findByRegistrationNumber(regNum);
		String base64Encoded = null;
		byte[] imageAr = null;
		if (volunteer != null) {
			Long imageId = volunteer.getImageId();
			if (imageId != null) {
				VolunteerImageDtls volunteerImageDtls = volunteerImageDtlsRepo.findOne(imageId);
				if (volunteerImageDtls.getPhoto() != null) {
					imageAr = volunteerImageDtls.getPhoto();
					base64Encoded = Base64.getEncoder().encodeToString(imageAr);
					return ResponseEntity.ok(base64Encoded);
				}
			}
		}
		return ResponseEntity.badRequest().body(null);
	}

	@GetMapping("/volunteer/getfptemplate/{regNum}")
	@ResponseBody
	public ResponseEntity<?> getvolunteerFPTemplate(@PathVariable String regNum) {
		Volunteer volunteer = volunteerRepo.findByRegistrationNumber(regNum);
		if (volunteer != null && volunteer.getFingerId() != null) {
			VolunteerFingerDtls volunteerFingerDtls = volunteerFingerDtlsRepo.findOne(volunteer.getFingerId());

			byte[] fpTemplate = volunteerFingerDtls.getTemplate();
			if (volunteerFingerDtls != null && fpTemplate != null) {

			}

		}

		return null;

	}

	@RequestMapping(path = "/pdf", method = RequestMethod.GET)
	public ModelAndView report() {

		JasperReportsPdfView view = new JasperReportsPdfView();
		view.setUrl("classpath:sample.jrxml");
		view.setApplicationContext(appContext);

		Map<String, Object> params = new HashMap<>();
		params.put("datasource", volunteerRepo.findAll());

		return new ModelAndView(view, params);
	}

	@RequestMapping(path = "/report/{type}", method = RequestMethod.GET)
	public ModelAndView reportExcel(@PathVariable String type, ModelAndView modelAndView, ModelMap model) {

		JasperReportsMultiFormatView view = new JasperReportsMultiFormatView();
		view.setUrl("classpath:sample34.jrxml");
		view.setApplicationContext(appContext);
		Properties headers = new Properties();
		view.setHeaders(headers);
		Boolean returnError = false;
		if (type != null) {
			if (AizantConstants.EXCEL_REPORT.equals(type)) {
				headers.put("Content-Disposition",
						"attachment; filename=Report_" + new java.util.Date().getTime() + ".xls");
			} else if (AizantConstants.PDF_REPORT.equals(type)) {
				headers.put("Content-Disposition",
						"inline; filename=Report_" + new java.util.Date().getTime() + ".pdf");
			} else if (AizantConstants.CSV_REPORT.equals(type)) {
				headers.put("Content-Disposition",
						"attachment; filename=Report_" + new java.util.Date().getTime() + ".csv");
			} else {
				returnError = true;
			}
		} else {
			returnError = true;
		}
		if (!returnError) {
			view.setHeaders(headers);

			model.addAttribute("datasource", volunteerRepo.findByregistrationNumber("R10928"));
			model.addAttribute("format", type);

			return new ModelAndView(view, model);
		} else {
			return null;
		}
	}

	@RequestMapping(path = "/export", method = RequestMethod.GET)
	public ResponseEntity<?> exportRepors() {

		return ResponseEntity.ok(true);
	}

	@GetMapping("/volunteers/21days/above/report/excel")
	@ResponseBody
	public ModelAndView getAllVolunteersabove21daysExcel() {
		// LastScreeningDate
		java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());

		Calendar cal = Calendar.getInstance();
		cal.setTime(sqlDate);
		cal.add(Calendar.DATE, -21);
		java.util.Date dateBefore21Days = cal.getTime();
		java.sql.Date sqlDate1 = new Date(dateBefore21Days.getTime());
		List<Volunteer> list = volunteerRepo.findByLastScreeningDateBefore(sqlDate1);
		JasperReportsXlsView view = new JasperReportsXlsView();
		view.setUrl("classpath:excel.jrxml");
		view.setApplicationContext(appContext);

		Map<String, Object> params = new HashMap<>();
		params.put("datasource", list);

		return new ModelAndView(view, params);

	}

	/*
	 * @GetMapping("/volunteers/21days/above/report/pdf")
	 * 
	 * @ResponseBody public ModelAndView
	 * getAllVolunteersabove21daysPdf(HttpServletResponse response) { //
	 * LastScreeningDate java.sql.Date sqlDate = new java.sql.Date(new
	 * java.util.Date().getTime());
	 * 
	 * Calendar cal = Calendar.getInstance(); cal.setTime(sqlDate);
	 * cal.add(Calendar.DATE, -21); java.util.Date dateBefore21Days = cal.getTime();
	 * java.sql.Date sqlDate1 = new Date(dateBefore21Days.getTime());
	 * List<Volunteer> list = volunteerRepo.findByLastScreeningDateBefore(sqlDate1);
	 * JasperReportsPdfView view = new JasperReportsPdfView();
	 * view.setUrl("classpath:excel.jrxml"); view.setApplicationContext(appContext);
	 * 
	 * Map<String, Object> params = new HashMap<>(); params.put("datasource", list);
	 * // response.setHeader("Content-disposition", "inline; filename=report1.pdf");
	 * return new ModelAndView(view, params);
	 * 
	 * }
	 */

	/*
	 * @RequestMapping(path = "/sample/report1/{type}/{regNum}", method =
	 * RequestMethod.GET)
	 * 
	 * public ModelAndView report1Excel(@PathVariable String type, @PathVariable
	 * String regNum, ModelAndView modelAndView, ModelMap model) {
	 * 
	 * JasperReportsMultiFormatView view = new JasperReportsMultiFormatView();
	 * 
	 * view.setUrl("classpath:sample.jrxml");
	 * 
	 * view.setApplicationContext(appContext);
	 * 
	 * Properties headers = new Properties();
	 * 
	 * view.setHeaders(headers);
	 * 
	 * Boolean returnError = false;
	 * 
	 * if (type != null) {
	 * 
	 * if (AizantConstants.EXCEL_REPORT.equals(type)) {
	 * 
	 * headers.put("Content-Disposition",
	 * 
	 * "attachment; filename=Report_" + new java.util.Date().getTime() + ".xls");
	 * 
	 * } else if (AizantConstants.PDF_REPORT.equals(type)) {
	 * 
	 * headers.put("Content-Disposition",
	 * 
	 * "inline; filename=Report_" + new java.util.Date().getTime() + ".pdf");
	 * 
	 * } else if (AizantConstants.CSV_REPORT.equals(type)) {
	 * 
	 * headers.put("Content-Disposition",
	 * 
	 * "attachment; filename=Report_" + new java.util.Date().getTime() + ".csv");
	 * 
	 * } else {
	 * 
	 * returnError = true;
	 * 
	 * }
	 * 
	 * } else {
	 * 
	 * returnError = true;
	 * 
	 * }
	 * 
	 * if (!returnError) {
	 * 
	 * view.setHeaders(headers);
	 * 
	 * java.util.List<VolunteerRestrictionCompliance> nodeList = new
	 * ArrayList<VolunteerRestrictionCompliance>();
	 * nodeList.add(volunteerRestrictionComplianceRepo.findbyRegistrationNumber(
	 * regNum));
	 * 
	 * JRDataSource JRdataSource = new JRBeanCollectionDataSource(nodeList);
	 * 
	 * model.addAttribute("datasource", JRdataSource); model.addAttribute("format",
	 * type); return new ModelAndView(view, model);
	 * 
	 * } else {
	 * 
	 * return null;
	 * 
	 * }
	 * 
	 * }
	 */

	/*
	 * @GetMapping("/saveimage")
	 * 
	 * @ResponseBody public ResponseEntity<String> savevolunteerImage() {
	 * List<Volunteer> volunteers = volunteerRepo.findAll(); Connection connection =
	 * null; Statement st = null; FileOutputStream fos = null; try { if (volunteers
	 * != null) { for (Volunteer v : volunteers) { if (v.getId() > 12077) { Long
	 * imageId = v.getImageId(); if (imageId != null) { connection =
	 * JdbcUtil.getConnection(); st = connection.createStatement(); String str =
	 * "select photo from vol_image_dtls where image_id=" + imageId + ""; String
	 * base64Encoded = null; ResultSet rs = st.executeQuery(str); while (rs.next())
	 * {
	 * 
	 * byte[] buffer = new byte[1]; InputStream is = rs.getBinaryStream(1); if (is
	 * != null) {
	 * 
	 * String folderS = AizantConstants.UPLOADED_FOLDER + "/" +
	 * v.getRegistrationNumber().trim() + "/" + AizantConstants.V_IMAGE + "/"; Path
	 * folderPath = Paths.get(folderS); String imageName =
	 * v.getRegistrationNumber().trim() + "_photo.jpg";
	 * 
	 * Files.createDirectories(folderPath); fos = new FileOutputStream(folderS +
	 * imageName); while (is.read(buffer) > 0) { fos.write(buffer); } fos.close();
	 * v.setImage(imageName); v.setImageLocation(folderS + imageName);
	 * volunteerRepo.save(v); }
	 * 
	 * } st.close(); connection.close(); } } } } } catch (Exception e) {
	 * e.printStackTrace(); } finally { try { st.close(); connection.close();
	 * fos.close(); } catch (SQLException | IOException e) { e.printStackTrace(); }
	 * 
	 * } return ResponseEntity.badRequest().body(null); }
	 */

	@GetMapping("/copy/study")
	@ResponseBody
	public void copyexccel() {

		String SAMPLE_XLSX_FILE_PATH = "E:\\docs\\Studytt.xlsx";

		// Creating a Workbook from an Excel file (.xls or .xlsx)
		Workbook workbook;
		try {
			workbook = WorkbookFactory.create(new File(SAMPLE_XLSX_FILE_PATH));

			// Retrieving the number of sheets in the Workbook
			System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

			// Getting the Sheet at index zero
			Sheet sheet = workbook.getSheetAt(0);

			// Create a DataFormatter to format and get each cell's value as String
			DataFormatter dataFormatter = new DataFormatter();

			String projectNum = null;
			java.sql.Date chkoutDate = null;
			java.sql.Date checkindate = null;
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
			// 2. Or you can use a for-each loop to iterate over the rows and columns
			System.out.println("\n\nIterating over Rows and Columns using for-each loop\n");
			for (Row row : sheet) {
				for (Cell cell : row) {
					String cellValue = dataFormatter.formatCellValue(cell);
					// System.out.println("======cellValue====" + cellValue);
					if (cellValue.startsWith("Project no") || cellValue.startsWith("PROJECT NO:")
							|| cellValue.startsWith("PROJECT NO :") || cellValue.startsWith("PROJECT NO ")) {
						cellValue = StringUtils.removeStartIgnoreCase(cellValue, "Project no:");
						cellValue = StringUtils.removeStartIgnoreCase(cellValue, "PROJECT NO:");
						cellValue = StringUtils.removeStartIgnoreCase(cellValue, "PROJECT NO :");
						cellValue = StringUtils.removeStartIgnoreCase(cellValue, "PROJECT NO ");
						if (cellValue.contains("(")) {
							int index = cellValue.indexOf("(");
							cellValue = cellValue.substring(0, index);
						}
						projectNum = cellValue;
					}

					try {
						if (cellValue.startsWith("vmscheckin")) {
							// System.out.println(cellValue);
							String timecell = cellValue;

							System.out.println("timecell=============" + timecell);

							String[] stra = timecell.split(":");
							System.out.println("stra= ============" + stra);
							java.util.Date date = null;

							date = sdf1.parse(stra[1]);

							checkindate = new java.sql.Date(date.getTime());

							// System.out.println(checkindate);

						}

						if (cellValue.startsWith("vmscheckout")) {

							// System.out.println(cellValue);
							String timecell = cellValue;
							System.out.println("timecell= 2============" + timecell);
							String[] stra = timecell.split(":");
							System.out.println("stra= 2============" + stra);
							java.util.Date date = null;

							date = sdf1.parse(stra[1]);

							chkoutDate = new java.sql.Date(date.getTime());

							// System.out.println(chkoutDate);

						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					if (cellValue.startsWith("R") && !cellValue.startsWith("R.") && !cellValue.startsWith("Re")
							&& !cellValue.startsWith("RA") && !cellValue.startsWith("RE") && !cellValue.startsWith("RY")
							&& !cellValue.startsWith("RO") && !cellValue.startsWith("RI")
							&& !cellValue.startsWith("RU")) {

						StudyData data = new StudyData();
						data.setCheckInDate(checkindate);
						data.setCheckOutDate(chkoutDate);
						data.setRegistrationNumber(cellValue);
						data.setStudyName(projectNum);

						studyDataRepo.save(data);

						// System.out.println(data);

						/*
						 * System.out.println(projectNum + " : " + cellValue);
						 * 
						 * BufferedWriter writer = new BufferedWriter(new
						 * FileWriter("E:\\docs\\studyd.txt", true)); writer.append('\n');
						 * writer.append(projectNum + " : " + cellValue);
						 * 
						 * writer.close();
						 */
					}
				}
				System.out.println();
			}
		} catch (org.apache.poi.openxml4j.exceptions.InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List getVolunteerStatuses(String statusMasterId, Integer limit) {
		String hql = "from VolunteerStatus v where (v.registrationNumber, v.id) "
				+ "IN (select registrationNumber, max(id) as id "
				+ "from VolunteerStatus group by registrationNumber) and v.statusMaster In (" + statusMasterId
				+ ") Order by id desc";
		System.out.println("Q = " + hql);
		Query query = em.createQuery(hql);
		query.setFirstResult(limit);
		query.setMaxResults(3000);
		return query.getResultList();
	}

	/*
	 * @GetMapping("/volunteers/21days/{q}")
	 * 
	 * @ResponseBody public Page<Volunteer>
	 * getAllVolunteersbelow21days2(@PathVariable String q, Pageable pageable) {
	 * StatusMaster master = new StatusMaster(); if (q.equals("below")) {
	 * master.setId(3L); } if (q.equals("above")) { master.setId(116L); }
	 * 
	 * 
	 * List<VolunteerStatus> status =
	 * volunteerStatusRepo.findByStatusMasterid(master.getId()); Set<String>
	 * registrationNumbers = new HashSet<String>(); for (VolunteerStatus vs :
	 * status) {
	 * 
	 * registrationNumbers.add(vs.getRegistrationNumber()); } return
	 * volunteerRepo.findAll(where(VolunteerSpecs.registrationNumberIn(
	 * registrationNumbers)), pageable); }
	 */

	@GetMapping("/copy")
	@ResponseBody
	public String copyData() {

		String regn = null;
		try {

			List<VolunteerMaster> aizantVolunteers = volunteerMasterRepo.findAll();
			Integer i = 0;

			for (VolunteerMaster aizantVolunteers2 : aizantVolunteers) {
				Boolean ins = false;
				List<Volunteer> list = volunteerRepo.findAll();

				if (list == null || list.isEmpty()) {
					ins = true;
				} else {

					for (Volunteer volunteer1 : list) {
						if (aizantVolunteers2.getVolunteerstrid() != null
								&& aizantVolunteers2.getVolunteerstrid().equals(volunteer1.getRegistrationNumber())) {
							ins = false;
							break;
						} else {
							ins = true;
						}
					}
				}

				if (ins) {

					Volunteer volunteer = new Volunteer();

					if (aizantVolunteers2.getImage_id() != null)
						volunteer.setImageId(Long.valueOf(aizantVolunteers2.getImage_id()));

					if (aizantVolunteers2.getFinger_id() != null)
						volunteer.setFingerId(Long.valueOf(aizantVolunteers2.getFinger_id()));

					StringBuilder address = new StringBuilder(aizantVolunteers2.getAddressfirst());

					if (aizantVolunteers2.getStreet() != null)
						address.append(aizantVolunteers2.getStreet()).append(" , ");

					if (aizantVolunteers2.getVillage() != null)
						address.append(aizantVolunteers2.getVillage()).append(" , ");

					if (aizantVolunteers2.getLocality() != null)
						address.append(aizantVolunteers2.getLocality()).append(" , ");

					if (aizantVolunteers2.getCityfirst() != null)
						address.append(aizantVolunteers2.getCityfirst()).append(" , ");

					if (aizantVolunteers2.getState() != null)
						address.append(aizantVolunteers2.getState()).append(" , ");

					if (aizantVolunteers2.getCountry() != null)
						address.append(aizantVolunteers2.getCountry()).append(" , ");

					if (aizantVolunteers2.getPincode() != null)
						address.append(aizantVolunteers2.getPincode());

					volunteer.setAddress(address.toString());
					volunteer.setCreatedOn(aizantVolunteers2.getReg_date());
					volunteer.setRegisteredOn(aizantVolunteers2.getReg_date());

					volunteer.setLangRead(aizantVolunteers2.getMothertongue());
					volunteer.setLangSpeak(aizantVolunteers2.getMothertongue());
					volunteer.setLangWrite(aizantVolunteers2.getMothertongue());
					volunteer.setApprovalStatus(aizantVolunteers2.getApprovalStatus());

					volunteer.setDiet(aizantVolunteers2.getDiet());

					Connection connection = JdbcUtil.getConnection();

					Timestamp dobTs = null;
					Statement st = connection.createStatement();
					String str = "select birthDate from volunteer_master where volunteerstrid='"
							+ aizantVolunteers2.getVolunteerstrid() + "'";
					ResultSet rs = st.executeQuery(str);
					while (rs.next()) {
						dobTs = rs.getTimestamp(1);
					}

					rs.close();
					st.close();
					connection.close();

					if (dobTs != null) {
						volunteer.setDob(new java.sql.Date(dobTs.getTime()));

					}

					/*
					 * Connection connection = JdbcUtil.getConnection();
					 * 
					 * Statement st = connection.createStatement(); String str =
					 * "select birthDate from volunteer_master where volunteerstrid='" +
					 * aizantVolunteers2.getVolunteerstrid() + "'"; ResultSet rs =
					 * st.executeQuery(str); while (rs.next()) { volunteer.setDob(rs.getDate(1)); }
					 * 
					 * str = "select approvalStatus from volunteer_master where volunteerstrid='" +
					 * aizantVolunteers2.getVolunteerstrid() + "'"; rs = st.executeQuery(str); while
					 * (rs.next()) { volunteer.setApprovalStatus((rs.getLong(1))); }
					 */

					/*
					 * if (aizantVolunteers2.getBirthDate() != null) { volunteer.setDob(new
					 * java.sql.Date(aizantVolunteers2.getBirthDate().getTime())); // StringBuilder
					 * }
					 */
					StringBuilder address2 = new StringBuilder(aizantVolunteers2.getPaddressfirst());

					if (aizantVolunteers2.getPstreet() != null)
						address2.append(aizantVolunteers2.getPstreet()).append(" , ");

					if (aizantVolunteers2.getPvillage() != null)
						address2.append(aizantVolunteers2.getPvillage()).append(" , ");

					if (aizantVolunteers2.getPlocality() != null)
						address2.append(aizantVolunteers2.getPlocality()).append(" , ");

					if (aizantVolunteers2.getPcityfirst() != null)
						address2.append(aizantVolunteers2.getPcityfirst()).append(" , ");

					if (aizantVolunteers2.getPstate() != null)
						address2.append(aizantVolunteers2.getPstate()).append(" , ");

					if (aizantVolunteers2.getPcountry() != null)
						address2.append(aizantVolunteers2.getPcountry()).append(" , ");

					if (aizantVolunteers2.getPpincode() != null)
						address2.append(aizantVolunteers2.getPpincode());

					volunteer.seteAddress(address2.toString());

					volunteer.setHeight(aizantVolunteers2.getHeight());
					volunteer.setWeight(aizantVolunteers2.getWeight());

					volunteer.setePhone(aizantVolunteers2.getPcontact()); //
					volunteer.seteName(aizantVolunteers2.getPlandline_contact()); // // // //
					// volunteer.setRelationVol(aizantVolunteers2.get);
					volunteer.setFirstName(aizantVolunteers2.getFirstname());
					String ch = aizantVolunteers2.getGender();
					if (ch != null) {
						if (ch.equalsIgnoreCase("f")) {
							volunteer.setGender("FEMALE");
						} else if (ch.equalsIgnoreCase("M")) {
							volunteer.setGender("MALE");
						} else
							volunteer.setGender(ch);
					}
					volunteer.setIdMark1(aizantVolunteers2.getIdentification_marks()); // // //
					// volunteer.setIdMark2(aizantVolunteers2.getIdentification_marks_2()); // //

					volunteer.setIntials(aizantVolunteers2.getMiddlename());
					volunteer.setLastName(aizantVolunteers2.getLastname()); // // //
					volunteer.setNationality(aizantVolunteers2.getRace());
					volunteer.setOccupation(aizantVolunteers2.getOccupation());
					volunteer.setPhone(aizantVolunteers2.getContact());
					volunteer.setQualification(aizantVolunteers2.getEducationstatus());
					volunteer.setRegistrationNumber(aizantVolunteers2.getVolunteerstrid());

					volunteer.setCreatedBy(aizantVolunteers2.getUsername());
					volunteer.setUpdatedBy(aizantVolunteers2.getUsername());

					volunteer.setCenterId(1l);

					volunteer.setCreated(aizantVolunteers2.getReg_date());
					volunteer.setUpdated(aizantVolunteers2.getReg_date());
					volunteer.setComments(aizantVolunteers2.getReason());

					volunteer.setFatherName(aizantVolunteers2.getF_first_name());
					regn = volunteer.getRegistrationNumber();

					// volunteer = volunteerRepo.findByRegistrationNumber(regn);
					volunteerRepo.save(volunteer);

				} else {
					System.out.println("insert skipped " + regn);
				}

			}

			return "sucess";

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "fail";
	}
}