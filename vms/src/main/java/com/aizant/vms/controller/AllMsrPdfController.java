package com.aizant.vms.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;

import com.aizant.vms.model.AllMsrPdf;
import com.aizant.vms.model.Audit;
import com.aizant.vms.model.User;
import com.aizant.vms.model.UserLoginLogoutAudit;
import com.aizant.vms.model.Volunteer;
import com.aizant.vms.model.VolunteerClinicalAssessment;
import com.aizant.vms.model.VolunteerDemographicProfile;
import com.aizant.vms.model.VolunteerFamilyHistory;
import com.aizant.vms.model.VolunteerMenstrualHistory;
import com.aizant.vms.model.VolunteerPastHistory;
import com.aizant.vms.model.VolunteerPersonalHistory;
import com.aizant.vms.model.VolunteerPhysicalExamination;
import com.aizant.vms.model.VolunteerRestrictionCompliance;
import com.aizant.vms.model.VolunteerStatus;
import com.aizant.vms.model.VolunteerSystemicExamination;
import com.aizant.vms.model.VolunteerVitalSigns;
import com.aizant.vms.repo.AuditRepo;
import com.aizant.vms.repo.SopFormNumbersRepo;
import com.aizant.vms.repo.UserLoginLogoutAuditRepo;
import com.aizant.vms.repo.UserRepository;
import com.aizant.vms.repo.VolunteerClinicalAssessmentRepo;
import com.aizant.vms.repo.VolunteerDemographicProfileRepo;
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
import com.aizant.vms.util.AizantConstants;
import com.aizant.vms.util.AizantUtil;

@Controller
public class AllMsrPdfController {
	private static final Logger logger = LoggerFactory.getLogger(AllMsrPdfController.class);

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
	SopFormNumbersRepo sopFormNumbersRepo;
	@Autowired
	VolunteerStatusRepo volunteerStatusRepo; 
	@Autowired
	AuditRepo auditRepo;
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserLoginLogoutAuditRepo userLoginLogoutAuditRepo;
	@RequestMapping(path = "/allmsrpdf/{regNum}/{screeingId}", method = RequestMethod.GET)
	public ModelAndView allmsrpdf(@PathVariable Long screeingId, @PathVariable String regNum, ModelAndView modelAndView,
			ModelMap model) {
		String type = "pdf";
		JasperReportsMultiFormatView view = new JasperReportsMultiFormatView();
		view.setUrl("classpath:reports/AllMsrPdf.jrxml");
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
			VolunteerStatus volunteerStatus =volunteerStatusRepo.findTop1ByScreeningIdOrderByIdDesc(screeingId);
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
					.findByVolunteerScreeningHistoryId(screeingId);
			User userdm = userRepository.findByUsername(volunteerDemographicProfile.getRecordedBy()); 
			AllMsrPdf amp = new AllMsrPdf();
			amp.setScreeningICF(volunteerDemographicProfile.getScreeningICF());
			amp.setScreeningicfDate(volunteerDemographicProfile.getScreeningicfDate());
			amp.setVolunteerHeight(volunteer.getHeight());
			amp.setVolunteerWeight(volunteer.getWeight());
			amp.setDietStatus(volunteer.getDiet());
			amp.setVolunteerIntials(volunteer.getIntials());
			amp.setDmrecordedBy(userdm.getFirstname()+" "+userdm.getLastname());
			amp.setVolunteerRead(volunteer.getLangRead());
			amp.setVolunteerWrite(volunteer.getLangWrite());
			amp.setVolunteerGender(volunteer.getGender());
			amp.setVolunteerAge(volunteer.getAge());
			amp.setVolunteerRace(volunteer.getRace());
			amp.setVolunteerBmi(volunteer.getBmi());
			amp.setVolunteerRegNum(volunteer.getRegistrationNumber());
			amp.setNoOfDaysCompletedFromLastPartcipation(
					volunteerDemographicProfile.getNoOfDaysCompletedFromLastPartcipation());
			amp.setLastParticipationDate(volunteerDemographicProfile.getLastParticipationDate());
			amp.setHistoryOfBloodDonationDate(volunteerDemographicProfile.getHistoryOfBloodDonationDate());
			amp.setProceedForScreening(volunteerDemographicProfile.getProceedForScreening());
			amp.setFormNumber(volunteerDemographicProfile.getFormNumbers().getFormNumber());
			amp.setSopNumber(volunteerDemographicProfile.getFormNumbers().getSopNumber());
			VolunteerRestrictionCompliance volunteerRestrictionCompliance = volunteerRestrictionComplianceRepo
					.findByVolunteerScreeningHistoryId(screeingId);
			User userrc = userRepository.findByUsername(volunteerRestrictionCompliance.getRecordedBy()); 
			amp.setBevarageConsumption(volunteerRestrictionCompliance.getBevarageConsumption());
			amp.setAlcoholConsumption(volunteerRestrictionCompliance.getAlcoholConsumption());
			amp.setTobaccoConsumption(volunteerRestrictionCompliance.getTobaccoConsumption());
			amp.setComplyWithCompliance(volunteerRestrictionCompliance.getComplyWithCompliance());
			amp.setInstructedNotToConsume(volunteerRestrictionCompliance.getInstructedNotToConsume());
			amp.setRcrecordedBy(userrc.getFirstname()+" "+userrc.getLastname());
			VolunteerVitalSigns volunteerVitalSigns = volunteerVitalSignsRepo
					.findByVolunteerScreeningHistoryId(screeingId);
			User uservs = userRepository.findByUsername(volunteerVitalSigns.getRecordedBy());
			amp.setMedicalExamStartDate(volunteerVitalSigns.getMedicalExamStartDate());
			amp.setMedicalExamStartTime(volunteerVitalSigns.getMedicalExamStartTime());
			amp.setRadialPulseRate(volunteerVitalSigns.getRadialPulseRate());
			amp.setSittingBloodPressure(volunteerVitalSigns.getSittingBloodPressure());
			amp.setBodyTemparature(volunteerVitalSigns.getBodyTemparature());
			amp.setRespiratoryRate(volunteerVitalSigns.getRespiratoryRate());
			amp.setVsrecordedBy(uservs.getFirstname()+" "+uservs.getLastname());
			VolunteerPersonalHistory volunteerpersonalhistory = volunteerPersonalHistoryRepo
					.findByVolunteerScreeningHistoryId(screeingId);
			User userph1 = userRepository.findByUsername(volunteerpersonalhistory.getRecordedBy());
			amp.setMaritalStatus(volunteerpersonalhistory.getMaritalStatus());
			amp.setChildrenCount(volunteerpersonalhistory.getChildrenCount());
			amp.setSmokerDetails(volunteerpersonalhistory.getSmokerDetails());
			amp.setTobaccoType(volunteerpersonalhistory.getTobaccoType());
			amp.setTobaccoStoppedDuration(volunteerpersonalhistory.getTobaccoStoppedDuration());
			amp.setTobaccoStoppedDate(volunteerpersonalhistory.getTobaccoStoppedDate());
			amp.setAlcoholConsumerType(volunteerpersonalhistory.getAlcoholConsumerType());
			amp.setNoOfUnitsPerDay(volunteerpersonalhistory.getNoOfUnitsPerDay());
			amp.setTypeOfDrink(volunteerpersonalhistory.getTypeOfDrink());
			amp.setDateOfLastConsumption(volunteerpersonalhistory.getDateOfLastConsumption());
			amp.setSubstaanceOfAbuse(volunteerpersonalhistory.getSubstaanceOfAbuse());
			amp.setDrugDuration(volunteerpersonalhistory.getDrugDuration());
			amp.setDrugStoppedDate(volunteerpersonalhistory.getDrugStoppedDate());
			amp.setAppetiteDetails(volunteerpersonalhistory.getAppetiteDetails());
			amp.setSpecailDietDetails(volunteerpersonalhistory.getSpecailDietDetails());
			amp.setAllergyType(volunteerpersonalhistory.getAllergyType());
			amp.setAllergySymtoms(volunteerpersonalhistory.getAllergySymtoms());
			amp.setAllergyDate(volunteerpersonalhistory.getAllergyDate());
			amp.setAllergicToDrug(volunteerpersonalhistory.getAllergicToDrug());
			amp.setStudySpecificAllergy(volunteerpersonalhistory.getStudySpecificAllergy());
			amp.setNoOfCigarPerDay(volunteerpersonalhistory.getNoOfCigarPerDay());
			amp.setSmokeStoppedDuration(volunteerpersonalhistory.getSmokeStoppedDuration());
			amp.setDateOfStopping(volunteerpersonalhistory.getDateOfStopping());
			amp.setChewerDetails(volunteerpersonalhistory.getChewerDetails());
			amp.setAlcoholConsumerDetails(volunteerpersonalhistory.getAlcoholConsumerDetails());
			amp.setDrugAbuserDetails(volunteerpersonalhistory.getDrugAbuserDetails());
			amp.setAppetite(volunteerpersonalhistory.getAppetite());
			amp.setOnSpecialDiet(volunteerpersonalhistory.getOnSpecialDiet());
			amp.setAllergies(volunteerpersonalhistory.getAllergies());
			amp.setDiffFacedDuringDrugAdmin(volunteerpersonalhistory.getDiffFacedDuringDrugAdmin());
			amp.setDiffFacedDuringDrugAdminDetails(volunteerpersonalhistory.getDiffFacedDuringDrugAdminDetails());
			amp.setPh1recordedBy(userph1.getFirstname()+" "+userph1.getLastname());
			VolunteerPastHistory volunteerpasthistory = volunteerPastHistoryRepo
					.findByVolunteerScreeningHistoryId(screeingId);
			User userph = userRepository.findByUsername(volunteerpasthistory.getRecordedBy());
			amp.setMedicalHistory(volunteerpasthistory.getMedicalHistory());
			amp.setTypeOfIllness(volunteerpasthistory.getTypeOfIllness());
			amp.setDateOfDetection(volunteerpasthistory.getDateOfDetection());
			amp.setHospitalized(volunteerpasthistory.getHospitalized());
			amp.setTreatmentTaken(volunteerpasthistory.getTreatmentTaken());
			amp.setSurgeryName(volunteerpasthistory.getSurgeryName());
			amp.setSurgeryReason(volunteerpasthistory.getSurgeryReason());
			amp.setSurgeryDate(volunteerpasthistory.getSurgeryDate());
			amp.setFamilyPlanningSelf(volunteerpasthistory.getFamilyPlanningSelf());
			amp.setFamilyPlanningSelfComments(volunteerpasthistory.getFamilyPlanningSelfComments());
			amp.setFamilyPlannningSelfDate(volunteerpasthistory.getFamilyPlannningSelfDate());
			amp.setFamilyPlanningSouseDate(volunteerpasthistory.getFamilyPlanningSouseDate());
			amp.setFamilyPlanningSpouse(volunteerpasthistory.getFamilyPlanningSpouse());
			amp.setFamilyPlanningSpouseComments(volunteerpasthistory.getFamilyPlanningSpouseComments());
			amp.setSurgicalHistory(volunteerpasthistory.getSurgicalHistory());
			amp.setFamilyPlanning(volunteerpasthistory.getFamilyPlanning());
			amp.setTreatmentHistory(volunteerpasthistory.getTreatmentHistory());
			amp.setTreatmentDetails(volunteerpasthistory.getTreatmentDetails());
			amp.setPhrecordedBy(userph.getFirstname()+" "+userph.getLastname());
			VolunteerFamilyHistory volunteerfamilyhistory = volunteerFamilyHistoryRepo
					.findByVolunteerScreeningHistoryId(screeingId);
			User userfh = userRepository.findByUsername(volunteerfamilyhistory.getRecordedBy());
			amp.setCoronaryArteryDisease(volunteerfamilyhistory.getCoronaryArteryDisease());
			amp.setCoronaryArteryDiseaseVolunteer(volunteerfamilyhistory.getCoronaryArteryDiseaseVolunteer());
			amp.setCoronaryArteryDiseaseComments(volunteerfamilyhistory.getCoronaryArteryDiseaseComments());
			amp.setHypertension(volunteerfamilyhistory.getHypertension());
			amp.setHypertensionRelationWithVolunteer(volunteerfamilyhistory.getHypertensionRelationWithVolunteer());
			amp.setHypertensionComments(volunteerfamilyhistory.getHypertensionComments());
			amp.setAsthma(volunteerfamilyhistory.getAsthma());
			amp.setAsthmaRelationWithVolunteer(volunteerfamilyhistory.getAsthmaRelationWithVolunteer());
			amp.setAsthmaComments(volunteerfamilyhistory.getAsthmaComments());
			amp.setMalignancy(volunteerfamilyhistory.getMalignancy());
			amp.setMalignancyRelationWithVolunteer(volunteerfamilyhistory.getAsthmaRelationWithVolunteer());
			amp.setMalignancyComments(volunteerfamilyhistory.getMalignancyComments());
			amp.setEpilepsy(volunteerfamilyhistory.getEpilepsy());
			amp.setEpilepsyRelationWithVolunteer(volunteerfamilyhistory.getEpilepsyRelationWithVolunteer());
			amp.setEpilepsyComments(volunteerfamilyhistory.getEpilepsyComments());
			amp.setRenalDisorder(volunteerfamilyhistory.getRenalDisorder());
			amp.setRenalDisorderRelationWithVolunteer(volunteerfamilyhistory.getRenalDisorderRelationWithVolunteer());
			amp.setRenalDisorderComments(volunteerfamilyhistory.getRenalDisorderComments());
			amp.setDiabetesMellitus(volunteerfamilyhistory.getDiabetesMellitus());
			amp.setDiabetesMellitusComments(volunteerfamilyhistory.getDiabetesMellitusComments());
			amp.setDiabetesMellitusRelationWithVolunteer(
					volunteerfamilyhistory.getDiabetesMellitusRelationWithVolunteer());
			amp.setTuberculosis(volunteerfamilyhistory.getTuberculosis());
			amp.setTuberculosisRelationWithVolunteer(volunteerfamilyhistory.getTuberculosisRelationWithVolunteer());
			amp.setTuberculosisComments(volunteerfamilyhistory.getTuberculosisComments());
			amp.setNeurological(volunteerfamilyhistory.getNeurological());
			amp.setNeurologicalRelationWithVolunteer(volunteerfamilyhistory.getNeurologicalRelationWithVolunteer());
			amp.setNeurologicalComments(volunteerfamilyhistory.getNeurologicalComments());
			amp.setFhrecordedBy(userfh.getFirstname()+" "+userfh.getLastname());
			if (volunteer.getGender().equals("Female")) {
				VolunteerMenstrualHistory volunteerMenstrualHistory = volunteerMenstrualHistoryRepo
						.findByVolunteerScreeningHistoryId(screeingId);
				if(volunteerMenstrualHistory != null) {
				User usermh = userRepository.findByUsername(volunteerMenstrualHistory.getRecordedBy());
				amp.setLmpDetails(volunteerMenstrualHistory.getLmpDetails());
				amp.setLmpComments(volunteerMenstrualHistory.getLmpComments());
				amp.setDurtionOfFlowDetails(volunteerMenstrualHistory.getDurtionOfFlowDetails());
				amp.setDurtionOfFlowComments(volunteerMenstrualHistory.getDurtionOfFlowComments());
				amp.setRegularDetails(volunteerMenstrualHistory.getRegularDetails());
				amp.setRegularComments(volunteerMenstrualHistory.getRegularComments());
				amp.setLengthOfCycleDetails(volunteerMenstrualHistory.getLengthOfCycleDetails());
				amp.setLengthOfCycleComments(volunteerMenstrualHistory.getLengthOfCycleComments());
				amp.setHoDysmenorrhoeaDetails(volunteerMenstrualHistory.getHoDysmenorrhoeaDetails());
				amp.setHoDysmenorrhoeaComments(volunteerMenstrualHistory.getHoDysmenorrhoeaComments());
				amp.setHoAmenorrhoeaDetails(volunteerMenstrualHistory.getHoAmenorrhoeaDetails());
				amp.setHoAmenorrhoeaComments(volunteerMenstrualHistory.getHoAmenorrhoeaComments());
				amp.setHoLeucorrhoeaDetails(volunteerMenstrualHistory.getHoLeucorrhoeaDetails());
				amp.setHoLeucorrhoeaComments(volunteerMenstrualHistory.getHoLeucorrhoeaComments());
				amp.setObestricAge(volunteerMenstrualHistory.getObestricAge());
				amp.setObestricMethodOfDelivery(volunteerMenstrualHistory.getObestricMethodOfDelivery());
				amp.setObestricGender(volunteerMenstrualHistory.getObestricGender());
				amp.setObestricHealthStatus(volunteerMenstrualHistory.getObestricHealthStatus());
				amp.setObestricComments(volunteerMenstrualHistory.getObestricComments());
				amp.setAbortionHistoryMonthYear(volunteerMenstrualHistory.getAbortionHistoryMonthYear());
				amp.setAbortionCorrespondingAge(volunteerMenstrualHistory.getAbortionCorrespondingAge());
				amp.setBreastFeedingCurrently(volunteerMenstrualHistory.getBreastFeedingCurrently());
				amp.setHormonalTreatment(volunteerMenstrualHistory.getHormonalTreatment());
				amp.setHormonalTreatmentComments(volunteerMenstrualHistory.getHormonalTreatmentComments());
				amp.setGynaecologicalExam(volunteerMenstrualHistory.getGynaecologicalExam());
				amp.setGynaecologicalExamComments(volunteerMenstrualHistory.getGynaecologicalExamComments());
				amp.setMhrecordedBy(usermh.getFirstname()+" "+usermh.getLastname());
				}
		    }
			VolunteerPhysicalExamination volunteerPhysicalExamination = volunteerPhysicalExaminationRepo
					.findByVolunteerScreeningHistoryId(screeingId);
			User userpe = userRepository.findByUsername(volunteerPhysicalExamination.getRecordedBy());
			amp.setGenAppStatus(volunteerPhysicalExamination.getGenAppStatus());
			amp.setGenAppComments(volunteerPhysicalExamination.getGenAppComments());
			amp.setHeadStatus(volunteerPhysicalExamination.getHeadStatus());
			amp.setHeadComments(volunteerPhysicalExamination.getHeadComments());
			amp.setEyesStatus(volunteerPhysicalExamination.getEyesStatus());
			amp.setEyesComments(volunteerPhysicalExamination.getEyesComments());
			amp.setEarsStatus(volunteerPhysicalExamination.getEarsStatus());
			amp.setEarsComments(volunteerPhysicalExamination.getEarsComments());
			amp.setNoseStatus(volunteerPhysicalExamination.getNoseStatus());
			amp.setNoseComments(volunteerPhysicalExamination.getNoseComments());
			amp.setOropharynxStatus(volunteerPhysicalExamination.getOropharynxStatus());
			amp.setOropharynxComments(volunteerPhysicalExamination.getOropharynxComments());
			amp.setNeckStatus(volunteerPhysicalExamination.getNeckStatus());
			amp.setNeckComments(volunteerPhysicalExamination.getNeckComments());
			amp.setMusculoskeletalStatus(volunteerPhysicalExamination.getMusculoskeletalStatus());
			amp.setMusculoskeletalComments(volunteerPhysicalExamination.getMusculoskeletalComments());
			amp.setExtremitiesStatus(volunteerPhysicalExamination.getExtremitiesStatus());
			amp.setExtremitiesComments(volunteerPhysicalExamination.getExtremitiesComments());
			amp.setSkinStatus(volunteerPhysicalExamination.getSkinStatus());
			amp.setSkinComments(volunteerPhysicalExamination.getSkinComments());
			amp.setOverAllComments(volunteerPhysicalExamination.getOverAllComments());
			amp.setGerecordedBy(userpe.getFirstname()+" "+userpe.getLastname());
			VolunteerSystemicExamination volunteerSystemicExamination = volunteerSystemicExaminationRepo
					.findByVolunteerScreeningHistoryId(screeingId);
			User userse = userRepository.findByUsername(volunteerSystemicExamination.getRecordedBy());
			amp.setCardiovascularSystemStatus(volunteerSystemicExamination.getCardiovascularSystemStatus());
			amp.setCardiovascularSystemComments(volunteerSystemicExamination.getCardiovascularSystemComments());
			amp.setRespiratorySystemStatus(volunteerSystemicExamination.getRespiratorySystemStatus());
			amp.setRespiratorySystemComments(volunteerSystemicExamination.getRespiratorySystemComments());
			amp.setAbdomenStatus(volunteerSystemicExamination.getAbdomenStatus());
			amp.setAbdomenComments(volunteerSystemicExamination.getAbdomenComments());
			amp.setCentralNervousSystemStatus(volunteerSystemicExamination.getCentralNervousSystemStatus());
			amp.setCentralNervousSystemComments(volunteerSystemicExamination.getCentralNervousSystemComments());
			amp.setOverallComments(volunteerSystemicExamination.getOverallComments());
			amp.setCommentsOnPhysicalExamination(volunteerSystemicExamination.getCommentsOnPhysicalExamination());
			amp.setExaminationEndTime(volunteerSystemicExamination.getExaminationEndTime());
			amp.setSerecordedBy(userse.getFirstname()+" "+userse.getLastname());
			VolunteerClinicalAssessment volunteerClinicalAssessment = clinicalAssessmentRepo
					.findByVolunteerScreeningHistoryId(screeingId);
			User userca = userRepository.findByUsername(volunteerClinicalAssessment.getRecordedBy());
			User userca1 = userRepository.findByUsername(volunteerClinicalAssessment.getReviewedBy());
			amp.setHematologyStatus(volunteerClinicalAssessment.getHematologyStatus());
			amp.setHematologyComments(volunteerClinicalAssessment.getHematologyComments());
			amp.setBiochemistryStatus(volunteerClinicalAssessment.getBiochemistryStatus());
			amp.setBiochemistryComments(volunteerClinicalAssessment.getBiochemistryComments());
			amp.setSerologyStatus(volunteerClinicalAssessment.getSerologyStatus());
			amp.setSerologyComments(volunteerClinicalAssessment.getSerologyComments());
			amp.setUrineAnalysisStatus(volunteerClinicalAssessment.getUrineAnalysisStatus());
			amp.setUrineAnalysisComments(volunteerClinicalAssessment.getUrineAnalysisComments());
			amp.setEcgStatus(volunteerClinicalAssessment.getEcgStatus());
			amp.setEcgComments(volunteerClinicalAssessment.getEcgComments());
			amp.setChestXRayStatus(volunteerClinicalAssessment.getChestXRayStatus());
			amp.setChestXRayComments(volunteerClinicalAssessment.getChestXRayComments());
			amp.setOthersStatus(volunteerClinicalAssessment.getOthersStatus());
			amp.setOthersComments(volunteerClinicalAssessment.getOthersComments());
			amp.setBmiDetails(volunteerClinicalAssessment.getBmiDetails());
			amp.setBmiComments(volunteerClinicalAssessment.getBmiComments());
			amp.setAdditionalComments(volunteerClinicalAssessment.getAdditionalComments());
			amp.setVolunteerHealthy(volunteerClinicalAssessment.getVolunteerHealthy());
			amp.setCarecordedBy(userca.getFirstname()+" "+userca.getLastname());
			amp.setTemporaryorpermanent(volunteerStatus.getIsRejectedPermanantly());
			amp.setNoofdays(volunteerStatus.getRejectedNoOfDays());
			amp.setReasonForRejection(volunteerStatus.getReasonForRejection());
			amp.setCheckedBy(userca1.getFirstname()+" "+userca1.getLastname());
			java.util.List<AllMsrPdf> ampList = new ArrayList<AllMsrPdf>();
			ampList.add(amp);
			model.addAttribute("datasource", ampList);
			model.addAttribute("format", type);
			return new ModelAndView(view, model);

		} else {

			return null;

		}
	}
	
	// Audit Reports
	@RequestMapping("/audit/report/{type}")
	public ModelAndView auditReports(@PathVariable String type,
			ModelAndView modelAndView, ModelMap model,@RequestParam(required = false) String userName,
			@RequestParam(required = false) Date date1, @RequestParam(required = false) Date date2,
			@RequestParam(required = false) String activity) {
		JasperReportsMultiFormatView view = new JasperReportsMultiFormatView();
		view.setUrl("classpath:reports/AuditResults.jrxml");
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
			List<Audit> auditList = new ArrayList<>();
			
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

			model.addAttribute("datasource", auditList);
			model.addAttribute("format", type);
			return new ModelAndView(view, model);

		} else {

			return null;

		}


	}
	
	@RequestMapping("/user/loginlogout/report/{type}")
	public ModelAndView auditReports(@PathVariable String type,
			ModelAndView modelAndView, ModelMap model,@RequestParam(required = false) String userName,
			@RequestParam(required = false) Date date1, @RequestParam(required = false) Date date2) {
		JasperReportsMultiFormatView view = new JasperReportsMultiFormatView();
		view.setUrl("classpath:reports/UserAudit.jrxml");
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
			List<UserLoginLogoutAudit> auditList = new ArrayList<>();
			
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

			model.addAttribute("datasource", auditList);
			model.addAttribute("format", type);
			return new ModelAndView(view, model);

		} else {

			return null;

		}


	}

}