package com.aizant.vms.controller;

import static org.springframework.data.jpa.domain.Specifications.where;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;

import com.aizant.vms.model.SopFormNumbers;
import com.aizant.vms.model.StatusMaster;
import com.aizant.vms.model.StudyData;
import com.aizant.vms.model.User;
import com.aizant.vms.model.Volunteer;
import com.aizant.vms.model.VolunteerClinicalAssessment;
import com.aizant.vms.model.VolunteerDemographicProfile;
import com.aizant.vms.model.VolunteerECG;
import com.aizant.vms.model.VolunteerFamilyHistory;
import com.aizant.vms.model.VolunteerMenstrualHistory;
import com.aizant.vms.model.VolunteerPastHistory;
import com.aizant.vms.model.VolunteerPersonalHistory;
import com.aizant.vms.model.VolunteerPhysicalExamination;
import com.aizant.vms.model.VolunteerRestrictionCompliance;
import com.aizant.vms.model.VolunteerScreeningHistory;
import com.aizant.vms.model.VolunteerStatus;
import com.aizant.vms.model.VolunteerSystemicExamination;
import com.aizant.vms.model.VolunteerVitalSigns;
import com.aizant.vms.model.VolunteerXRay;
import com.aizant.vms.repo.SopFormNumbersRepo;
import com.aizant.vms.repo.StudyDataRepo;
import com.aizant.vms.repo.UserRepository;
import com.aizant.vms.repo.VolunteerClinicalAssessmentRepo;
import com.aizant.vms.repo.VolunteerDemographicProfileRepo;
import com.aizant.vms.repo.VolunteerECGRepo;
import com.aizant.vms.repo.VolunteerFamilyHistoryRepo;
import com.aizant.vms.repo.VolunteerMenstrualHistoryRepo;
import com.aizant.vms.repo.VolunteerPastHistoryRepo;
import com.aizant.vms.repo.VolunteerPersonalHistoryRepo;
import com.aizant.vms.repo.VolunteerPhysicalExaminationRepo;
import com.aizant.vms.repo.VolunteerRepo;
import com.aizant.vms.repo.VolunteerRestrictionComplianceRepo;
import com.aizant.vms.repo.VolunteerScreeningHistoryRepo;
import com.aizant.vms.repo.VolunteerStatusRepo;
import com.aizant.vms.repo.VolunteerSystemicExaminationRepo;
import com.aizant.vms.repo.VolunteerVitalSignsRepo;
import com.aizant.vms.repo.VolunteerXRayRepo;
import com.aizant.vms.service.VolunteerSpecs;
import com.aizant.vms.util.AizantConstants;
import com.aizant.vms.util.AizantUtil;

@Controller
public class JasperReportsController {
	private static final Logger logger = LoggerFactory.getLogger(JasperReportsController.class);

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	VolunteerRepo volunteerRepo;

	@Autowired
	VolunteerScreeningHistoryRepo volunteerScreeningHistoryRepo;

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
	SopFormNumbersRepo sopFormNumbersRepo;

	@Autowired
	VolunteerStatusRepo volunteerStatusRepo;

	@Autowired
	UserRepository userRepository;

	@Autowired
	VolunteerController volunteerController;
	@Autowired
	StudyDataRepo studyDataRepo;

	@RequestMapping(path = "/dmprofile/{type}/{regNum}", method = RequestMethod.GET)
	public ModelAndView dmprofileReport(@PathVariable String type, @PathVariable String regNum,
			ModelAndView modelAndView, ModelMap model) {

		JasperReportsMultiFormatView view = new JasperReportsMultiFormatView();
		view.setUrl("classpath:reports/DemographicProfile.jrxml");
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

			VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo
					.findTop1ByVolunteerRegNumOrderByIdDesc(regNum);
			Volunteer volunteer = volunteerRepo.findByRegistrationNumber(regNum);
			if (volunteer != null) {
				String age = AizantUtil.getAge(volunteer.getDob());
				volunteer.setAge(age);
			}
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
			VolunteerDemographicProfile volunteerDemographicProfile = volunteerDemographicProfileRepo
					.findByVolunteerScreeningHistory(screeningHistory);
			User user = userRepository.findByUsername(volunteerDemographicProfile.getRecordedBy());
			volunteerDemographicProfile.setVolunteerRegNum(volunteer.getRegistrationNumber());
			volunteerDemographicProfile.setVolunteerIntials(volunteer.getIntials());
			volunteerDemographicProfile.setLangRead(volunteer.getLangRead());
			volunteerDemographicProfile.setLangWrite(volunteer.getLangWrite());
			volunteerDemographicProfile.setVolunteerGender(volunteer.getGender());
			volunteerDemographicProfile.setVolunteerAge(volunteer.getAge());
			volunteerDemographicProfile.setVolunteerRace(volunteer.getRace());
			volunteerDemographicProfile.setVolunteerBmi(volunteer.getBmi());
			volunteerDemographicProfile
					.setNoOfDaysCompletedFromLastPartcipation(volunteer.getNoOfDaysCompletedFromLastPartcipation());
			volunteerDemographicProfile.setProceedForScreening(volunteer.getProceedForScreening());
			volunteerDemographicProfile.setFormNumber(volunteerDemographicProfile.getFormNumbers().getFormNumber());
			volunteerDemographicProfile.setSopNumber(volunteerDemographicProfile.getFormNumbers().getSopNumber());
			volunteerDemographicProfile.setRecordedBy(user.getFirstname() + " " + user.getLastname());
			java.util.List<VolunteerDemographicProfile> dpList = new ArrayList<VolunteerDemographicProfile>();
			dpList.add(volunteerDemographicProfile);
			model.addAttribute("datasource", dpList);
			model.addAttribute("format", type);
			return new ModelAndView(view, model);

		} else {

			return null;

		}
	}

	@RequestMapping(path = "/rcomp/{type}/{regNum}", method = RequestMethod.GET)
	public ModelAndView rcompReport(@PathVariable String type, @PathVariable String regNum, ModelAndView modelAndView,
			ModelMap model) {

		JasperReportsMultiFormatView view = new JasperReportsMultiFormatView();
		view.setUrl("classpath:reports/RestrictionCompliance.jrxml");
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

			VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo
					.findTop1ByVolunteerRegNumOrderByIdDesc(regNum);
			Volunteer volunteer = volunteerRepo.findByRegistrationNumber(regNum);
			VolunteerRestrictionCompliance volunteerRestrictionCompliance = volunteerRestrictionComplianceRepo
					.findByVolunteerScreeningHistory(screeningHistory);
			User user = userRepository.findByUsername(volunteerRestrictionCompliance.getRecordedBy());
			volunteerRestrictionCompliance.setVolunteerRegNum(volunteer.getRegistrationNumber());
			volunteerRestrictionCompliance.setVolunteerIntials(volunteer.getIntials());
			volunteerRestrictionCompliance.setRecordedBy(user.getFirstname() + " " + user.getLastname());
			volunteerRestrictionCompliance
					.setFormNumber(volunteerRestrictionCompliance.getFormNumbers().getFormNumber());
			volunteerRestrictionCompliance.setSopNumber(volunteerRestrictionCompliance.getFormNumbers().getSopNumber());
			java.util.List<VolunteerRestrictionCompliance> rcList = new ArrayList<VolunteerRestrictionCompliance>();
			rcList.add(volunteerRestrictionCompliance);
			model.addAttribute("datasource", rcList);
			model.addAttribute("format", type);
			return new ModelAndView(view, model);

		} else {

			return null;

		}
	}

	@RequestMapping(path = "/vitalsigns/{type}/{regNum}", method = RequestMethod.GET)
	public ModelAndView vitalsignsReport(@PathVariable String type, @PathVariable String regNum,
			ModelAndView modelAndView, ModelMap model) {

		JasperReportsMultiFormatView view = new JasperReportsMultiFormatView();
		view.setUrl("classpath:reports/VitalSigns.jrxml");
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
			VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo
					.findTop1ByVolunteerRegNumOrderByIdDesc(regNum);
			Volunteer volunteer = volunteerRepo.findByRegistrationNumber(regNum);
			VolunteerVitalSigns volunteerVitalSigns = volunteerVitalSignsRepo
					.findByVolunteerScreeningHistory(screeningHistory);
			User user = userRepository.findByUsername(volunteerVitalSigns.getRecordedBy());
			volunteerVitalSigns.setVolunteerRegNum(volunteer.getRegistrationNumber());
			volunteerVitalSigns.setFormNumber(volunteerVitalSigns.getFormNumbers().getFormNumber());
			volunteerVitalSigns.setSopNumber(volunteerVitalSigns.getFormNumbers().getSopNumber());
			volunteerVitalSigns.setRecordedBy(user.getFirstname() + " " + user.getLastname());
			java.util.List<VolunteerVitalSigns> vsList = new ArrayList<VolunteerVitalSigns>();
			vsList.add(volunteerVitalSigns);
			model.addAttribute("datasource", vsList);
			model.addAttribute("format", type);
			return new ModelAndView(view, model);

		} else {

			return null;

		}
	}

	@RequestMapping(path = "/personalhistory/{type}/{regNum}", method = RequestMethod.GET)
	public ModelAndView personalhistoryReport(@PathVariable String type, @PathVariable String regNum,
			ModelAndView modelAndView, ModelMap model) {

		JasperReportsMultiFormatView view = new JasperReportsMultiFormatView();
		view.setUrl("classpath:reports/PersonalHistory.jrxml");
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
			VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo
					.findTop1ByVolunteerRegNumOrderByIdDesc(regNum);
			Volunteer volunteer = volunteerRepo.findByRegistrationNumber(regNum);
			VolunteerPersonalHistory volunteerPersonalHistory = volunteerPersonalHistoryRepo
					.findByVolunteerScreeningHistory(screeningHistory);
			User user = userRepository.findByUsername(volunteerPersonalHistory.getRecordedBy());
			volunteerPersonalHistory.setVolunteerRegNum(volunteer.getRegistrationNumber());
			volunteerPersonalHistory.setFormNumber(volunteerPersonalHistory.getFormNumbers().getFormNumber());
			volunteerPersonalHistory.setSopNumber(volunteerPersonalHistory.getFormNumbers().getSopNumber());
			volunteerPersonalHistory.setRecordedBy(user.getFirstname() + " " + user.getLastname());
			java.util.List<VolunteerPersonalHistory> phList = new ArrayList<VolunteerPersonalHistory>();
			phList.add(volunteerPersonalHistory);
			model.addAttribute("datasource", phList);
			model.addAttribute("format", type);
			return new ModelAndView(view, model);

		} else {

			return null;

		}
	}

	@RequestMapping(path = "/pasthistory/{type}/{regNum}", method = RequestMethod.GET)
	public ModelAndView pasthistoryReport(@PathVariable String type, @PathVariable String regNum,
			ModelAndView modelAndView, ModelMap model) {

		JasperReportsMultiFormatView view = new JasperReportsMultiFormatView();
		view.setUrl("classpath:reports/PastHistory.jrxml");
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

			VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo
					.findTop1ByVolunteerRegNumOrderByIdDesc(regNum);
			Volunteer volunteer = volunteerRepo.findByRegistrationNumber(regNum);
			VolunteerPastHistory volunteerPastHistory = volunteerPastHistoryRepo
					.findByVolunteerScreeningHistory(screeningHistory);
			User user = userRepository.findByUsername(volunteerPastHistory.getRecordedBy());
			volunteerPastHistory.setVolunteerRegNum(volunteer.getRegistrationNumber());
			volunteerPastHistory.setFormNumber(volunteerPastHistory.getFormNumbers().getFormNumber());
			volunteerPastHistory.setSopNumber(volunteerPastHistory.getFormNumbers().getSopNumber());
			volunteerPastHistory.setRecordedBy(user.getFirstname() + " " + user.getLastname());
			java.util.List<VolunteerPastHistory> past1List = new ArrayList<VolunteerPastHistory>();
			past1List.add(volunteerPastHistory);
			model.addAttribute("datasource", past1List);
			model.addAttribute("format", type);
			return new ModelAndView(view, model);

		} else {

			return null;

		}
	}

	@RequestMapping(path = "/familyhistory/{type}/{regNum}", method = RequestMethod.GET)
	public ModelAndView familyhistoryReport(@PathVariable String type, @PathVariable String regNum,
			ModelAndView modelAndView, ModelMap model) {

		JasperReportsMultiFormatView view = new JasperReportsMultiFormatView();
		view.setUrl("classpath:reports/FamilyHistory.jrxml");
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
			VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo
					.findTop1ByVolunteerRegNumOrderByIdDesc(regNum);
			Volunteer volunteer = volunteerRepo.findByRegistrationNumber(regNum);
			VolunteerFamilyHistory volunteerFamilyHistory = volunteerFamilyHistoryRepo
					.findByVolunteerScreeningHistory(screeningHistory);
			User user = userRepository.findByUsername(volunteerFamilyHistory.getRecordedBy());
			volunteerFamilyHistory.setVolunteerRegNum(volunteer.getRegistrationNumber());
			volunteerFamilyHistory.setFormNumber(volunteerFamilyHistory.getFormNumbers().getFormNumber());
			volunteerFamilyHistory.setSopNumber(volunteerFamilyHistory.getFormNumbers().getSopNumber());
			volunteerFamilyHistory.setRecordedBy(user.getFirstname() + " " + user.getLastname());
			java.util.List<VolunteerFamilyHistory> past2List = new ArrayList<VolunteerFamilyHistory>();
			past2List.add(volunteerFamilyHistory);
			model.addAttribute("datasource", past2List);
			model.addAttribute("format", type);
			return new ModelAndView(view, model);

		} else {

			return null;

		}
	}

	@RequestMapping(path = "/menshistory/{type}/{regNum}", method = RequestMethod.GET)
	public ModelAndView menshistoryReport(@PathVariable String type, @PathVariable String regNum,
			ModelAndView modelAndView, ModelMap model) {

		JasperReportsMultiFormatView view = new JasperReportsMultiFormatView();
		view.setUrl("classpath:reports/MenstrualHistory.jrxml");
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
			VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo
					.findTop1ByVolunteerRegNumOrderByIdDesc(regNum);
			Volunteer volunteer = volunteerRepo.findByRegistrationNumber(regNum);
			VolunteerMenstrualHistory volunteerMenstrualHistory = volunteerMenstrualHistoryRepo
					.findByVolunteerScreeningHistory(screeningHistory);
			User user = userRepository.findByUsername(volunteerMenstrualHistory.getRecordedBy());
			volunteerMenstrualHistory.setVolunteerRegNum(volunteer.getRegistrationNumber());
			volunteerMenstrualHistory.setFormNumber(volunteerMenstrualHistory.getFormNumbers().getFormNumber());
			volunteerMenstrualHistory.setSopNumber(volunteerMenstrualHistory.getFormNumbers().getSopNumber());
			volunteerMenstrualHistory.setRecordedBy(user.getFirstname() + " " + user.getLastname());
			java.util.List<VolunteerMenstrualHistory> past3List = new ArrayList<VolunteerMenstrualHistory>();
			past3List.add(volunteerMenstrualHistory);
			model.addAttribute("datasource", past3List);
			model.addAttribute("format", type);
			return new ModelAndView(view, model);

		} else {

			return null;

		}
	}

	@RequestMapping(path = "/physicalexam/{type}/{regNum}", method = RequestMethod.GET)
	public ModelAndView physicalexamReport(@PathVariable String type, @PathVariable String regNum,
			ModelAndView modelAndView, ModelMap model) {

		JasperReportsMultiFormatView view = new JasperReportsMultiFormatView();
		view.setUrl("classpath:reports/PhysicalExamination.jrxml");
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
			VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo
					.findTop1ByVolunteerRegNumOrderByIdDesc(regNum);
			Volunteer volunteer = volunteerRepo.findByRegistrationNumber(regNum);
			VolunteerPhysicalExamination volunteerPhysicalExamination = volunteerPhysicalExaminationRepo
					.findByVolunteerScreeningHistory(screeningHistory);
			User user = userRepository.findByUsername(volunteerPhysicalExamination.getRecordedBy());
			volunteerPhysicalExamination.setVolunteerRegNum(volunteer.getRegistrationNumber());
			volunteerPhysicalExamination.setFormNumber(volunteerPhysicalExamination.getFormNumbers().getFormNumber());
			volunteerPhysicalExamination.setSopNumber(volunteerPhysicalExamination.getFormNumbers().getSopNumber());
			volunteerPhysicalExamination.setRecordedBy(user.getFirstname() + " " + user.getLastname());
			java.util.List<VolunteerPhysicalExamination> past3List = new ArrayList<VolunteerPhysicalExamination>();
			past3List.add(volunteerPhysicalExamination);
			model.addAttribute("datasource", past3List);
			model.addAttribute("format", type);
			return new ModelAndView(view, model);

		} else {

			return null;

		}
	}

	@RequestMapping(path = "/systemexam/{type}/{regNum}", method = RequestMethod.GET)
	public ModelAndView systemexamReport(@PathVariable String type, @PathVariable String regNum,
			ModelAndView modelAndView, ModelMap model) {

		JasperReportsMultiFormatView view = new JasperReportsMultiFormatView();
		view.setUrl("classpath:reports/SystemicExamination.jrxml");
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
			VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo
					.findTop1ByVolunteerRegNumOrderByIdDesc(regNum);
			Volunteer volunteer = volunteerRepo.findByRegistrationNumber(regNum);
			VolunteerSystemicExamination volunteerSystemicExamination = volunteerSystemicExaminationRepo
					.findByVolunteerScreeningHistory(screeningHistory);
			User user = userRepository.findByUsername(volunteerSystemicExamination.getRecordedBy());
			volunteerSystemicExamination.setVolunteerRegNum(volunteer.getRegistrationNumber());
			volunteerSystemicExamination.setFormNumber(volunteerSystemicExamination.getFormNumbers().getFormNumber());
			volunteerSystemicExamination.setSopNumber(volunteerSystemicExamination.getFormNumbers().getSopNumber());
			volunteerSystemicExamination.setRecordedBy(user.getFirstname() + " " + user.getLastname());
			java.util.List<VolunteerSystemicExamination> past3List = new ArrayList<VolunteerSystemicExamination>();
			past3List.add(volunteerSystemicExamination);
			model.addAttribute("datasource", past3List);
			model.addAttribute("format", type);
			return new ModelAndView(view, model);

		} else {

			return null;

		}
	}

	@RequestMapping(path = "/clinicalassessment/{type}/{regNum}", method = RequestMethod.GET)
	public ModelAndView clinicalassessmentReport(@PathVariable String type, @PathVariable String regNum,
			ModelAndView modelAndView, ModelMap model) {

		JasperReportsMultiFormatView view = new JasperReportsMultiFormatView();
		view.setUrl("classpath:reports/ClinicalAssessment.jrxml");
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

			VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo
					.findTop1ByVolunteerRegNumOrderByIdDesc(regNum);
			Volunteer volunteer = volunteerRepo.findByRegistrationNumber(regNum);
			VolunteerStatus volunteerStatus = volunteerStatusRepo.findOne(volunteer.getCurrentStatus());
			VolunteerClinicalAssessment volunteerClinicalAssessment = clinicalAssessmentRepo
					.findByVolunteerScreeningHistory(screeningHistory);
			User user1 = userRepository.findByUsername(volunteerClinicalAssessment.getRecordedBy());
			User user2 = userRepository.findByUsername(volunteerClinicalAssessment.getReviewedBy());
			volunteerClinicalAssessment.setVolunteerRegNum(volunteer.getRegistrationNumber());
			volunteerClinicalAssessment.setFormNumber(volunteerClinicalAssessment.getFormNumbers().getFormNumber());
			volunteerClinicalAssessment.setSopNumber(volunteerClinicalAssessment.getFormNumbers().getSopNumber());
			volunteerClinicalAssessment.setRejectedNoOfDays(volunteerStatus.getRejectedNoOfDays());
			volunteerClinicalAssessment.setReasonForRejection(volunteerStatus.getReasonForRejection());
			volunteerClinicalAssessment.setIsRejectedPermanantly(volunteerStatus.getIsRejectedPermanantly());
			volunteerClinicalAssessment.setRecordedBy(user1.getFirstname() + " " + user1.getLastname());
			volunteerClinicalAssessment.setReviewedBy(user2.getFirstname() + " " + user2.getLastname());
			java.util.List<VolunteerClinicalAssessment> caList = new ArrayList<VolunteerClinicalAssessment>();
			caList.add(volunteerClinicalAssessment);
			model.addAttribute("datasource", caList);
			model.addAttribute("format", type);
			return new ModelAndView(view, model);

		} else {

			return null;

		}
	}

	@RequestMapping(path = "/xray/{type}/{regNum}", method = RequestMethod.GET)
	public ModelAndView xrayReport(@PathVariable String type, @PathVariable String regNum, ModelAndView modelAndView,
			ModelMap model) {

		Volunteer volunteer = volunteerRepo.findByRegistrationNumber(regNum);
		if (volunteer == null)
			return null;

		JasperReportsMultiFormatView view = new JasperReportsMultiFormatView();
		view.setUrl("classpath:reports/xray.jrxml");
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

			VolunteerXRay volunteerXRay = volunteerXRayRepo.findTop1ByRegNumOrderByIdDesc(regNum);
			User user1 = userRepository.findByUsername(volunteerXRay.getPhysicianBy());
			User user2 = userRepository.findByUsername(volunteerXRay.getRadiologistBy());
			volunteerXRay.setVolunteerRegNum(regNum);
			volunteerXRay.setVolunteerAge(AizantUtil.getAge(volunteer.getDob()));
			volunteerXRay.setVolunteerSex(volunteer.getGender());
			volunteerXRay.setRadiologistBy(volunteerXRay.getRadiologistBy());
			volunteerXRay.setPhysicianBy(user1.getFirstname() + " " + user2.getLastname());
			volunteerXRay.setRadiologistBy(user2.getFirstname() + " " + user2.getLastname());
			java.util.List<VolunteerXRay> past3List = new ArrayList<VolunteerXRay>();
			past3List.add(volunteerXRay);
			model.addAttribute("datasource", past3List);
			model.addAttribute("format", type);
			return new ModelAndView(view, model);

		} else {

			return null;

		}
	}

	@RequestMapping(path = "/ecg/{type}/{regNum}", method = RequestMethod.GET)
	public ModelAndView ecgReport(@PathVariable String type, @PathVariable String regNum, ModelAndView modelAndView,
			ModelMap model) {

		Volunteer volunteer = volunteerRepo.findByRegistrationNumber(regNum);
		if (volunteer == null)
			return null;

		JasperReportsMultiFormatView view = new JasperReportsMultiFormatView();
		view.setUrl("classpath:reports/ECG.jrxml");
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
			VolunteerECG volunteerECG = volunteerECGRepo.findTop1ByRegNumOrderByIdDesc(regNum);
			User user1 = userRepository.findByUsername(volunteerECG.getRecordedBy());
			User user2 = userRepository.findByUsername(volunteerECG.getReviewedBy());
			volunteerECG.setVolunteerRegNum(regNum);
			volunteerECG.setVolunteerAge(AizantUtil.getAge(volunteer.getDob()));
			volunteerECG.setVolunteerSex(volunteer.getGender());
			volunteerECG.setRecordedBy(user1.getFirstname() + " " + user1.getLastname());
			volunteerECG.setReviewedBy(user2.getFirstname() + " " + user2.getLastname());
			java.util.List<VolunteerECG> ecg = new ArrayList<VolunteerECG>();
			ecg.add(volunteerECG);
			model.addAttribute("datasource", ecg);
			model.addAttribute("format", type);
			return new ModelAndView(view, model);

		} else {

			return null;

		}
	}

	@GetMapping("/volunteer/msr/history/{msrId}")
	@ResponseBody
	public ModelAndView getVolunteerMSRHistory(@PathVariable Long msrId, ModelAndView modelAndView, ModelMap model) {
		try {

			VolunteerScreeningHistory screeningHistory = volunteerScreeningHistoryRepo.findOne(msrId);

			VolunteerDemographicProfile demographicProfile = volunteerDemographicProfileRepo
					.findByVolunteerScreeningHistory(screeningHistory);
			VolunteerRestrictionCompliance compliance = volunteerRestrictionComplianceRepo
					.findByVolunteerScreeningHistory(screeningHistory);

			JasperReportsMultiFormatView view = new JasperReportsMultiFormatView();
			// view.setUrl("classpath:reports/RestrictionCompliance.jrxml");
			view.setApplicationContext(appContext);
			Properties headers = new Properties();
			view.setHeaders(headers);
			Boolean returnError = false;
			headers.put("Content-Disposition", "inline; filename=Report_" + new java.util.Date().getTime() + ".pdf");

			view.setHeaders(headers);

			java.util.List<Object> past3List = new ArrayList<Object>();

			past3List.add(demographicProfile);
			past3List.add(compliance);
			model.addAttribute("datasource", past3List);
			model.addAttribute("format", "pdf");
			return new ModelAndView(view, model);

		} catch (Exception e) {
			logger.error("error while getting msr history" + e);
		}

		return null;

	}

	@RequestMapping(path = "/idcard/pdf/{regNum}/{issuedby}", method = RequestMethod.GET)
	public ModelAndView volunteeridcard(@PathVariable String regNum, @PathVariable String issuedby,
			ModelAndView modelAndView, ModelMap model) {
		String type = "pdf";
		JasperReportsMultiFormatView view = new JasperReportsMultiFormatView();
		view.setUrl("classpath:reports/VolunteerIdCard.jrxml");
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

			Volunteer volunteer = volunteerRepo.findByRegistrationNumber(regNum);
			String age = AizantUtil.getAge(volunteer.getDob());
			volunteer.setAge(age);
			SopFormNumbers sopFormNumbers = sopFormNumbersRepo.findTop1ByOrderByIdDesc();
			User user = userRepository.findByUsername(issuedby);
			volunteer.setFormNumber(sopFormNumbers.getFormNumber());
			volunteer.setSopNumber(sopFormNumbers.getSopNumber());
			volunteer.setIdCardIssuedBy(user.getFirstname() + " " + user.getLastname());
			volunteer.setIdCardIssuedDate(new java.sql.Date(new java.util.Date().getTime()));
			java.util.List<Volunteer> vol = new ArrayList<Volunteer>();

			vol.add(volunteer);
			model.addAttribute("datasource", vol);
			model.addAttribute("format", type);
			return new ModelAndView(view, model);

		} else {

			return null;

		}
	}

	@RequestMapping(path = "/screeningactivity/pdf/{regNum}", method = RequestMethod.GET)
	public ModelAndView screeningactivity(@PathVariable String regNum, ModelAndView modelAndView, ModelMap model) {
		String type = "pdf";
		JasperReportsMultiFormatView view = new JasperReportsMultiFormatView();
		view.setUrl("classpath:reports/ScreeningActivity.jrxml");
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

			Volunteer volunteer = volunteerRepo.findByRegistrationNumber(regNum);
			String age = AizantUtil.getAge(volunteer.getDob());
			volunteer.setAge(age);
			volunteer.setScreeningActivityDate(new java.sql.Date(new java.util.Date().getTime()));
			java.util.List<Volunteer> vol = new ArrayList<Volunteer>();
			vol.add(volunteer);
			model.addAttribute("datasource", vol);
			model.addAttribute("format", type);
			return new ModelAndView(view, model);

		} else {

			return null;

		}
	}

	@RequestMapping(path = "/allvolunteers/{type}", method = RequestMethod.GET)
	public ModelAndView allVolunteers(@PathVariable String type, ModelAndView modelAndView, ModelMap model,
			Pageable pageable) {

		JasperReportsMultiFormatView view = new JasperReportsMultiFormatView();
		view.setUrl("classpath:reports/AllVolunteers.jrxml");
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
			// List<Volunteer> vol = volunteerRepo.findAll();
			Page<Volunteer> allvol = volunteerController.getAllVolunteers(pageable);
			for (int i = 0; i < allvol.getContent().size(); i++) {
				if (allvol.getContent() != null) {
					if (allvol.getContent().get(i).getCurrentStatus() != null) {
						VolunteerStatus volStatus = volunteerStatusRepo
								.findOne(allvol.getContent().get(i).getCurrentStatus());
						if (volStatus.getStatusMaster().getStatusDescription() != null) {
							allvol.getContent().get(i)
									.setStatusValue(volStatus.getStatusMaster().getStatusDescription());
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
			List<Volunteer> vol = allvol.getContent();
			model.addAttribute("datasource", vol);
			model.addAttribute("format", type);
			return new ModelAndView(view, model);

		} else {

			return null;

		}
	}

	@RequestMapping("/volunteers/21days/above/report/{type}")
	public ModelAndView above21DaysVolunteers(@PathVariable String type, ModelAndView modelAndView, ModelMap model,Pageable pageable) {
		JasperReportsMultiFormatView view = new JasperReportsMultiFormatView();
		view.setUrl("classpath:reports/Above21Days.jrxml");
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
			StatusMaster master = new StatusMaster();

			String statusMasterId = "12,116";
		/*	if (q.equals("below")) {
				master.setId(3L);
				statusMasterId = "3";
			}
			if (q.equals("above")) {
				master.setId(116L);
				statusMasterId = "12,116";
			}*/

			List<VolunteerStatus> status = new ArrayList<VolunteerStatus>();
			int limit = 0;
			for (int i = 0; i < 25; i++) {
				List<VolunteerStatus> vss = new ArrayList<VolunteerStatus>();
				List list = volunteerController.getVolunteerStatuses(statusMasterId, limit);
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
			Page<Volunteer> aboveVolunteers =  volunteerRepo.findAll(where(VolunteerSpecs.registrationNumberIn(registrationNumbers)), pageable);
			List<Volunteer> eligibleForScreening = aboveVolunteers.getContent();
			
			model.addAttribute("datasource", eligibleForScreening);
			model.addAttribute("format", type);
			return new ModelAndView(view, model);

		} else {

			return null;

		}

	}

	@RequestMapping("/volunteers/21days/below/report/{type}")
	public ModelAndView below21DaysVolunteers(@PathVariable String type, ModelAndView modelAndView, ModelMap model,Pageable pageable) {
		JasperReportsMultiFormatView view = new JasperReportsMultiFormatView();
		view.setUrl("classpath:reports/Below21Days.jrxml");
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
			StatusMaster master = new StatusMaster();

			String statusMasterId = "3";
			List<VolunteerStatus> status = new ArrayList<VolunteerStatus>();
			int limit = 0;
			for (int i = 0; i < 25; i++) {
				List<VolunteerStatus> vss = new ArrayList<VolunteerStatus>();
				List list = volunteerController.getVolunteerStatuses(statusMasterId, limit);
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
			Page<Volunteer> belowVolunteers =  volunteerRepo.findAll(where(VolunteerSpecs.registrationNumberIn(registrationNumbers)), pageable);
			List<Volunteer> eligibleForStudy = belowVolunteers.getContent();
			model.addAttribute("datasource", eligibleForStudy);
			model.addAttribute("format", type);
			return new ModelAndView(view, model);

		} else {

			return null;

		}

	}
}