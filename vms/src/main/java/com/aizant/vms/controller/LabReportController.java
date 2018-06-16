package com.aizant.vms.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;

import com.aizant.vms.dto.AllLabDetails;
import com.aizant.vms.dto.LabDetailsDTO;
import com.aizant.vms.model.Volunteer;
import com.aizant.vms.model.labreport.Biochemistry;
import com.aizant.vms.model.labreport.CompleteUrineExamination;
import com.aizant.vms.model.labreport.Haematology;
import com.aizant.vms.model.labreport.LabDetails;
import com.aizant.vms.model.labreport.Serology;
import com.aizant.vms.repo.labreport.BiochemistryRepo;
import com.aizant.vms.repo.labreport.CompleteUrineExaminationRepo;
import com.aizant.vms.repo.labreport.HaematologyRepo;
import com.aizant.vms.repo.labreport.LabDetailsRepo;
import com.aizant.vms.repo.labreport.SerologyRepo;
import com.aizant.vms.util.AizantConstants;
import com.aizant.vms.util.AizantUtil;
import com.aizant.vms.util.MSSqlUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class LabReportController {

	private static final Logger logger = LoggerFactory.getLogger(LabReportController.class);

	Map<String, String> errorMap = new HashMap<>();

	@Autowired
	LabDetailsRepo detailsRepo;

	@Autowired
	HaematologyRepo haematologyRepo;

	@Autowired
	BiochemistryRepo biochemistryRepo;

	@Autowired
	SerologyRepo serologyRepo;

	@Autowired
	CompleteUrineExaminationRepo completeUrineExaminationRepo;
	@Autowired
	private ApplicationContext appContext;

	@GetMapping("/haematology/{regNum}")
	@ResponseBody
	public ResponseEntity<?> getHaemotologyDetails(@PathVariable String regNum) {
		logger.info("Getting haemotology details");

		LabDetailsDTO detailsDTO = new LabDetailsDTO();
		try {
			System.out.println(regNum);
			LabDetails labDetails = detailsRepo.findTop1ByRegNumOrderByIdDesc(regNum);
			Haematology haematology = null;
			if (labDetails != null) {
				haematology = haematologyRepo.findByLabDetails(labDetails);
			}
			if (haematology != null) {
				detailsDTO.setDetails(labDetails);
				detailsDTO.setObject(haematology);
			}

			return ResponseEntity.ok(detailsDTO);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in getHaemotologyDetails : " + e);
		}

		return null;
	}

	@PostMapping("/haematology")
	@ResponseBody
	public ResponseEntity<?> addHaemotologyDetails(@RequestBody LabDetailsDTO detailsDTO) {
		logger.info("adding haemotology details");
		if (detailsDTO == null || detailsDTO.getDetails() == null || detailsDTO.getObject() == null) {
			errorMap.put("message", "details cannot be null");
			return ResponseEntity.badRequest().body(errorMap);
		}
		try {
			LabDetails details = detailsDTO.getDetails();

			details.setReportedOn(new Timestamp(new java.util.Date().getTime()));
			details.setSamplecollectedOn(new Timestamp(new java.util.Date().getTime()));
			details.setSampleReceivedOn(new Timestamp(new java.util.Date().getTime()));

			LinkedHashMap<String, String> linkedHashMap = (LinkedHashMap<String, String>) detailsDTO.getObject();
			String json = new ObjectMapper().writeValueAsString(linkedHashMap);
			ObjectMapper mapper = new ObjectMapper();
			Haematology haematology = mapper.readValue(json, Haematology.class);

			details = detailsRepo.save(details);
			if (haematology.getPreparedBy() != null && haematology.getPreparedOn() == null) {
				haematology.setPreparedOn(new Timestamp(new java.util.Date().getTime()));
			}
			if (haematology.getReviewedBy() != null && haematology.getReviewedOn() == null) {
				haematology.setReviewedOn(new Timestamp(new java.util.Date().getTime()));
			}
			if (haematology.getAuthorizedBy() != null && haematology.getAuthorizedOn() == null) {
				haematology.setAuthorizedOn(new Timestamp(new java.util.Date().getTime()));
			}
			haematology.setLabDetails(details);
			haematologyRepo.save(haematology);

			return ResponseEntity.ok(detailsDTO);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in adding haemotology : " + e);
		}
		return null;
	}

	@GetMapping("/biochemistry/{regNum}")
	@ResponseBody
	public ResponseEntity<?> getBiochemistryDetails(@PathVariable String regNum) {
		logger.info("Getting biochemistry details");

		LabDetailsDTO detailsDTO = new LabDetailsDTO();
		try {
			LabDetails labDetails = detailsRepo.findTop1ByRegNumOrderByIdDesc(regNum);
			Biochemistry biochemistry = null;
			if (labDetails != null) {
				biochemistry = biochemistryRepo.findByLabDetails(labDetails);
			}
			if (biochemistry != null) {
				detailsDTO.setDetails(labDetails);
				detailsDTO.setObject(biochemistry);
			}

			return ResponseEntity.ok(detailsDTO);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in getBiochemistryDetails : " + e);
		}

		return null;
	}

	@PostMapping("/biochemistry")
	@ResponseBody
	public ResponseEntity<?> addBiochemistryDetails(@RequestBody LabDetailsDTO detailsDTO) {
		logger.info("adding biochemistry details");
		if (detailsDTO == null || detailsDTO.getDetails() == null || detailsDTO.getObject() == null) {
			errorMap.put("message", "details cannot be null");
			return ResponseEntity.badRequest().body(errorMap);
		}
		try {
			LabDetails details = detailsDTO.getDetails();

			details.setReportedOn(new Timestamp(new java.util.Date().getTime()));
			details.setSamplecollectedOn(new Timestamp(new java.util.Date().getTime()));
			details.setSampleReceivedOn(new Timestamp(new java.util.Date().getTime()));

			LinkedHashMap<String, String> linkedHashMap = (LinkedHashMap<String, String>) detailsDTO.getObject();
			String json = new ObjectMapper().writeValueAsString(linkedHashMap);
			ObjectMapper mapper = new ObjectMapper();
			Biochemistry biochemistry = mapper.readValue(json, Biochemistry.class);

			details = detailsRepo.save(details);
			if (biochemistry.getPreparedBy() != null && biochemistry.getPreparedOn() == null) {
				biochemistry.setPreparedOn(new Timestamp(new java.util.Date().getTime()));
			}
			if (biochemistry.getReviewedBy() != null && biochemistry.getReviewedOn() == null) {
				biochemistry.setReviewedOn(new Timestamp(new java.util.Date().getTime()));
			}
			if (biochemistry.getAuthorizedBy() != null && biochemistry.getAuthorizedOn() == null) {
				biochemistry.setAuthorizedOn(new Timestamp(new java.util.Date().getTime()));
			}
			biochemistry.setLabDetails(details);
			biochemistryRepo.save(biochemistry);

			return ResponseEntity.ok(detailsDTO);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in adding biochemistry : " + e);
		}
		return null;
	}

	@GetMapping("/serology/{regNum}")
	@ResponseBody
	public ResponseEntity<?> getSerologyDetails(@PathVariable String regNum) {
		logger.info("Getting serology details");

		LabDetailsDTO detailsDTO = new LabDetailsDTO();
		try {
			LabDetails labDetails = detailsRepo.findTop1ByRegNumOrderByIdDesc(regNum);
			Serology serology = null;
			if (labDetails != null) {
				serology = serologyRepo.findByLabDetails(labDetails);
			}
			if (serology != null) {
				detailsDTO.setDetails(labDetails);
				detailsDTO.setObject(serology);
			}

			return ResponseEntity.ok(detailsDTO);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in getSerologyDetails : " + e);
		}

		return null;
	}

	@PostMapping("/serology")
	@ResponseBody
	public ResponseEntity<?> addSerologyDetails(@RequestBody LabDetailsDTO detailsDTO) {
		logger.info("adding serology details");
		if (detailsDTO == null || detailsDTO.getDetails() == null || detailsDTO.getObject() == null) {
			errorMap.put("message", "details cannot be null");
			return ResponseEntity.badRequest().body(errorMap);
		}
		try {
			LabDetails details = detailsDTO.getDetails();

			details.setReportedOn(new Timestamp(new java.util.Date().getTime()));
			details.setSamplecollectedOn(new Timestamp(new java.util.Date().getTime()));
			details.setSampleReceivedOn(new Timestamp(new java.util.Date().getTime()));

			LinkedHashMap<String, String> linkedHashMap = (LinkedHashMap<String, String>) detailsDTO.getObject();
			String json = new ObjectMapper().writeValueAsString(linkedHashMap);
			ObjectMapper mapper = new ObjectMapper();
			Serology serology = mapper.readValue(json, Serology.class);

			details = detailsRepo.save(details);
			if (serology.getPreparedBy() != null && serology.getPreparedOn() == null) {
				serology.setPreparedOn(new Timestamp(new java.util.Date().getTime()));
			}
			if (serology.getReviewedBy() != null && serology.getReviewedOn() == null) {
				serology.setReviewedOn(new Timestamp(new java.util.Date().getTime()));
			}
			if (serology.getAuthorizedBy() != null && serology.getAuthorizedOn() == null) {
				serology.setAuthorizedOn(new Timestamp(new java.util.Date().getTime()));
			}
			serology.setLabDetails(details);
			serologyRepo.save(serology);

			return ResponseEntity.ok(detailsDTO);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in adding serology : " + e);
		}
		return null;
	}

	@GetMapping("/completeUrineExamination/{regNum}")
	@ResponseBody
	public ResponseEntity<?> getCompleteUrineExaminationDetails(@PathVariable String regNum) {
		logger.info("Getting completeUrineExamination details");

		LabDetailsDTO detailsDTO = new LabDetailsDTO();
		try {
			LabDetails labDetails = detailsRepo.findTop1ByRegNumOrderByIdDesc(regNum);
			CompleteUrineExamination completeUrineExamination = null;
			if (labDetails != null) {
				completeUrineExamination = completeUrineExaminationRepo.findByLabDetails(labDetails);
			}
			if (completeUrineExamination != null) {
				detailsDTO.setDetails(labDetails);
				detailsDTO.setObject(completeUrineExamination);
			}

			return ResponseEntity.ok(detailsDTO);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in getCompleteUrineExaminationDetails : " + e);
		}

		return null;
	}

	@PostMapping("/completeUrineExamination")
	@ResponseBody
	public ResponseEntity<?> addCompleteUrineExaminationDetails(@RequestBody LabDetailsDTO detailsDTO) {
		logger.info("adding completeUrineExamination details");
		if (detailsDTO == null || detailsDTO.getDetails() == null || detailsDTO.getObject() == null) {
			errorMap.put("message", "details cannot be null");
			return ResponseEntity.badRequest().body(errorMap);
		}
		try {
			LabDetails details = detailsDTO.getDetails();

			details.setReportedOn(new Timestamp(new java.util.Date().getTime()));
			details.setSamplecollectedOn(new Timestamp(new java.util.Date().getTime()));
			details.setSampleReceivedOn(new Timestamp(new java.util.Date().getTime()));

			LinkedHashMap<String, String> linkedHashMap = (LinkedHashMap<String, String>) detailsDTO.getObject();
			String json = new ObjectMapper().writeValueAsString(linkedHashMap);
			ObjectMapper mapper = new ObjectMapper();
			CompleteUrineExamination completeUrineExamination = mapper.readValue(json, CompleteUrineExamination.class);

			details = detailsRepo.save(details);
			if (completeUrineExamination.getPreparedBy() != null && completeUrineExamination.getPreparedOn() == null) {
				completeUrineExamination.setPreparedOn(new Timestamp(new java.util.Date().getTime()));
			}
			if (completeUrineExamination.getReviewedBy() != null && completeUrineExamination.getReviewedOn() == null) {
				completeUrineExamination.setReviewedOn(new Timestamp(new java.util.Date().getTime()));
			}
			if (completeUrineExamination.getAuthorizedBy() != null
					&& completeUrineExamination.getAuthorizedOn() == null) {
				completeUrineExamination.setAuthorizedOn(new Timestamp(new java.util.Date().getTime()));
			}
			completeUrineExamination.setLabDetails(details);
			completeUrineExaminationRepo.save(completeUrineExamination);

			return ResponseEntity.ok(detailsDTO);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in adding completeUrineExamination : " + e);
		}
		return null;
	}

	@GetMapping("/lab/details/{regNum}")
	@ResponseBody
	public ResponseEntity<?> getAllLabDetails(@PathVariable String regNum) {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		PreparedStatement patTestResultSt = null;
		PreparedStatement doctorPreSt = null;
		ResultSet docotrRs = null;
		PreparedStatement unitMasterSt = null;
		ResultSet unitRs = null;

		ResultSet patTestResultRs = null;
		try {
			con = MSSqlUtil.getConnection();

			String patienMaster = " select Top 1 id,mcc_code,name,age,gender,sample_date,sample_time,status,mobile_number from tbl_med_mcc_patient_master where name like ? order by sample_date desc";

			preparedStatement = con.prepareStatement(patienMaster);
			preparedStatement.setString(1, "%" + regNum);
			AllLabDetails details = new AllLabDetails();
			rs = preparedStatement.executeQuery();
			if (rs.next() == false) {
				System.out.println("ResultSet is empty");
			} else {

				do {
					details = new AllLabDetails();
					details.setLabId(rs.getString(1));
					details.setAge(rs.getString(4));
					details.setGender(rs.getString(5));
					details.setSubjectInitials(rs.getString(9));
					details.setRegNum(rs.getString(3));
					details.setSampleReceivedOn(rs.getTimestamp(6));
					details.setSamplecollectedOn(rs.getTimestamp(7));

					Long id = rs.getLong(1);
					String mccCode = rs.getString(2);
					String status = rs.getString(8);

					String patTestResultStr = "select vailid,testname,value,testnormal_range,testunit from tbl_med_mcc_patient_test_result where patientid=?";
					patTestResultSt = con.prepareStatement(patTestResultStr);
					patTestResultSt.setLong(1, id);

					patTestResultRs = patTestResultSt.executeQuery();
					List<Map<String, String>> hDetails = new ArrayList<>();
					List<Map<String, String>> bDetails = new ArrayList<>();
					List<Map<String, String>> sDetails = new ArrayList<>();
					if (patTestResultRs.next() == false) {
						System.out.println("patTestResultRs is empty");
					} else {

						do {
							String labId = patTestResultRs.getString(1);
							
							
							if (null != patTestResultRs.getString(3) && !(patTestResultRs.getString(3).isEmpty())) {
								if (StringUtils.contains(labId, "W")) {
									Map<String, String> map = new HashMap<>();
									map.put("testname", patTestResultRs.getString(2));
									// map.put("range", patTestResultRs.getString(4));
									map.put("value", patTestResultRs.getString(3));
									if (null != patTestResultRs.getString(5) && !StringUtils.contains(patTestResultRs.getString(4), patTestResultRs.getString(5))) {
										map.put("range", patTestResultRs.getString(4) + patTestResultRs.getString(5));
									} else {
										map.put("range", patTestResultRs.getString(4));
									}
									hDetails.add(map);
								}

								if (StringUtils.contains(labId, "B") || StringUtils.contains(labId, "P")) {
									Map<String, String> map = new HashMap<>();
									map.put("testname", patTestResultRs.getString(2));
									map.put("value", patTestResultRs.getString(3));
									// map.put("range", patTestResultRs.getString(4));
									if (null != patTestResultRs.getString(5) && !StringUtils.contains(patTestResultRs.getString(4), patTestResultRs.getString(5))) {
										map.put("range", patTestResultRs.getString(4) + patTestResultRs.getString(5));
									} else {
										map.put("range", patTestResultRs.getString(4));
									}
									bDetails.add(map);
								}

								if (StringUtils.contains(labId, "S")) {
									Map<String, String> map = new HashMap<>();
									map.put("testname", patTestResultRs.getString(2));
									map.put("value", patTestResultRs.getString(3));
									if (null != patTestResultRs.getString(5) && !StringUtils.contains(patTestResultRs.getString(4), patTestResultRs.getString(5))) {
										map.put("range", patTestResultRs.getString(4) + patTestResultRs.getString(5));
									} else {
										map.put("range", patTestResultRs.getString(4));
									}
									sDetails.add(map);
								}
							}
							
							if(labId!=null && labId.length()>0)
								labId = labId.substring(0, labId.length() - 1);
								details.setLabId(labId);

						} while (patTestResultRs.next());
					}

					details.setHaematologyDetails(hDetails);
					details.setBioDetails(bDetails);
					details.setSerologyDetails(sDetails);
					String doctorsMasterStr = "select doctor_name from tbl_med_mcc_doctors where doctor_code=?";

					doctorPreSt = con.prepareStatement(doctorsMasterStr);
					doctorPreSt.setInt(1, Integer.valueOf(status));
					docotrRs = doctorPreSt.executeQuery();
					if (docotrRs.next() == false) {
						System.out.println("ResultSet is empty");
					} else {

						do {
							details.setReferredBy(docotrRs.getString(1));
						} while (docotrRs.next());
					}

					String unitMasterStr = "select MCCUnitName from tbl_med_mcc_unit_master where id=?";
					unitMasterSt = con.prepareStatement(unitMasterStr);
					unitMasterSt.setInt(1, Integer.valueOf(mccCode));
					unitRs = unitMasterSt.executeQuery();

					if (unitRs.next() == false) {
						System.out.println("ResultSet is empty");
					} else {

						do {
							details.setSamplecollectedAt(unitRs.getString(1));
						} while (unitRs.next());
					}

					System.out.println(details);

				} while (rs.next());
			}

			return ResponseEntity.ok(details);
		} catch (Exception e) {
			logger.error("error while getting lab details " + e);
		} finally {
			try {
				if (con != null)

					con.close();
				if (preparedStatement != null)
					preparedStatement.close();
				if (rs != null)
					rs.close();
				if (patTestResultSt != null)
					patTestResultSt.close();
				if (doctorPreSt != null)
					doctorPreSt.close();
				if (docotrRs != null)
					docotrRs.close();
				if (unitMasterSt != null)
					unitMasterSt.close();
				if (unitRs != null)
					unitRs.close();

			} catch (SQLException e) {
				logger.error("error while closing" + e);
			}
		}

		return null;

	}

	@RequestMapping(path = "/labreports/details/{type}/{regNum}", method = RequestMethod.GET)
	public ModelAndView screeningactivity(@PathVariable String type, @PathVariable String regNum,
			ModelAndView modelAndView, ModelMap model) {

		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		PreparedStatement patTestResultSt = null;
		PreparedStatement doctorPreSt = null;
		ResultSet docotrRs = null;
		PreparedStatement unitMasterSt = null;
		ResultSet unitRs = null;

		ResultSet patTestResultRs = null;

		JasperReportsMultiFormatView view = new JasperReportsMultiFormatView();
		view.setUrl("classpath:reports/LabReports.jrxml");
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
			AllLabDetails details = new AllLabDetails();
			try {
				con = MSSqlUtil.getConnection();

				String patienMaster = " select Top 1 id,mcc_code,name,age,gender,sample_date,sample_time,status,mobile_number from tbl_med_mcc_patient_master where name like ? order by sample_date desc";

				preparedStatement = con.prepareStatement(patienMaster);
				preparedStatement.setString(1, "%" + regNum);

				rs = preparedStatement.executeQuery();
				if (rs.next() == false) {
					System.out.println("ResultSet is empty");
				} else {

					do {
						details = new AllLabDetails();
						details.setLabId(rs.getString(1));
						details.setAge(rs.getString(4));
						details.setGender(rs.getString(5));
						details.setSubjectInitials(rs.getString(9));
						details.setRegNum(rs.getString(3));
						details.setSampleReceivedOn(rs.getTimestamp(6));
						details.setSamplecollectedOn(rs.getTimestamp(7));

						Long id = rs.getLong(1);
						String mccCode = rs.getString(2);
						String status = rs.getString(8);

						String patTestResultStr = "select vailid,testname,value,testnormal_range,testunit from tbl_med_mcc_patient_test_result where patientid=?";
						patTestResultSt = con.prepareStatement(patTestResultStr);
						patTestResultSt.setLong(1, id);

						patTestResultRs = patTestResultSt.executeQuery();
						List<Map<String, String>> hDetails = new ArrayList<>();
						List<Map<String, String>> bDetails = new ArrayList<>();
						List<Map<String, String>> sDetails = new ArrayList<>();
						if (patTestResultRs.next() == false) {
							System.out.println("patTestResultRs is empty");
						} else {

							do {
								String labId = patTestResultRs.getString(1);
								
								
								if (null != patTestResultRs.getString(3) && !(patTestResultRs.getString(3).isEmpty())) {
									if (StringUtils.contains(labId, "W")) {
										Map<String, String> map = new HashMap<>();
										map.put("testname", patTestResultRs.getString(2));
										// map.put("range", patTestResultRs.getString(4));
										map.put("value", patTestResultRs.getString(3));
										if (null != patTestResultRs.getString(5) && !StringUtils.contains(patTestResultRs.getString(4), patTestResultRs.getString(5))) {
											map.put("range",
													patTestResultRs.getString(4) + patTestResultRs.getString(5));
										} else {
											map.put("range", patTestResultRs.getString(4));
										}
										hDetails.add(map);
									}

									if (StringUtils.contains(labId, "B") || StringUtils.contains(labId, "P")) {
										Map<String, String> map = new HashMap<>();
										map.put("testname", patTestResultRs.getString(2));
										map.put("value", patTestResultRs.getString(3));
										// map.put("range", patTestResultRs.getString(4));
										if (null != patTestResultRs.getString(5) && !StringUtils.contains(patTestResultRs.getString(4), patTestResultRs.getString(5))) {
											map.put("range",
													patTestResultRs.getString(4) + patTestResultRs.getString(5));
										} else {
											map.put("range", patTestResultRs.getString(4));
										}
										bDetails.add(map);
									}

									if (StringUtils.contains(labId, "S")) {
										Map<String, String> map = new HashMap<>();
										map.put("testname", patTestResultRs.getString(2));
										map.put("value", patTestResultRs.getString(3));
										if (null != patTestResultRs.getString(5) && !StringUtils.contains(patTestResultRs.getString(4), patTestResultRs.getString(5))) {
											map.put("range",
													patTestResultRs.getString(4) + patTestResultRs.getString(5));
										} else {
											map.put("range", patTestResultRs.getString(4));
										}
										sDetails.add(map);
									}
								}
								
								if(labId!=null && labId.length()>0)
									labId = labId.substring(0, labId.length() - 1);
									details.setLabId(labId);

							} while (patTestResultRs.next());
						}

						details.setHaematologyDetails(hDetails);
						details.setBioDetails(bDetails);
						details.setSerologyDetails(sDetails);
						String doctorsMasterStr = "select doctor_name from tbl_med_mcc_doctors where doctor_code=?";

						doctorPreSt = con.prepareStatement(doctorsMasterStr);
						doctorPreSt.setInt(1, Integer.valueOf(status));
						docotrRs = doctorPreSt.executeQuery();
						if (docotrRs.next() == false) {
							System.out.println("ResultSet is empty");
						} else {

							do {
								details.setReferredBy(docotrRs.getString(1));
							} while (docotrRs.next());
						}

						String unitMasterStr = "select MCCUnitName from tbl_med_mcc_unit_master where id=?";
						unitMasterSt = con.prepareStatement(unitMasterStr);
						unitMasterSt.setInt(1, Integer.valueOf(mccCode));
						unitRs = unitMasterSt.executeQuery();

						if (unitRs.next() == false) {
							System.out.println("ResultSet is empty");
						} else {

							do {
								details.setSamplecollectedAt(unitRs.getString(1));
							} while (unitRs.next());
						}

						System.out.println(details);

					} while (rs.next());
				}

			} catch (Exception e) {
				logger.error("error while getting lab details " + e);
			} finally {
				try {
					if (con != null)
						con.close();
					if (preparedStatement != null)
						preparedStatement.close();
					if (rs != null)
						rs.close();
					if (patTestResultSt != null)
						patTestResultSt.close();
					if (doctorPreSt != null)
						doctorPreSt.close();
					if (docotrRs != null)
						docotrRs.close();
					if (unitMasterSt != null)
						unitMasterSt.close();
					if (unitRs != null)
						unitRs.close();

				} catch (SQLException e) {
					logger.error("error while closing" + e);
				}
			}

			java.util.List<AllLabDetails> labdetails = new ArrayList<AllLabDetails>();
			labdetails.add(details);
			model.addAttribute("datasource", labdetails);
			model.addAttribute("format", type);
			return new ModelAndView(view, model);

		} else {

			return null;

		}
	}

	@GetMapping("/lab/details/history/{regNum}")
	@ResponseBody
	public ResponseEntity<?> getHistoryLabDetails(@PathVariable String regNum) {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		PreparedStatement patTestResultSt = null;
		ResultSet patTestResultRs = null;
		try {
			con = MSSqlUtil.getConnection();

			String patienMaster = " select id,mcc_code,name,age,gender,sample_date,sample_time,status,mobile_number from tbl_med_mcc_patient_master where name like ? order by sample_date desc";

			preparedStatement = con.prepareStatement(patienMaster);
			preparedStatement.setString(1, "%" + regNum);
			List<AllLabDetails> allLabDetails = new ArrayList<>();
			AllLabDetails details = new AllLabDetails();
			rs = preparedStatement.executeQuery();
			if (rs.next() == false) {
				return null;
			} else {

				do {
					details = new AllLabDetails();
					details.setLabId(rs.getString(1));
					details.setAge(rs.getString(4));
					details.setGender(rs.getString(5));
					details.setSubjectInitials(rs.getString(9));
					details.setRegNum(rs.getString(3));
					details.setSampleReceivedOn(rs.getTimestamp(6));
					details.setSamplecollectedOn(rs.getTimestamp(7));

					Long id = rs.getLong(1);
					String mccCode = rs.getString(2);
					String status = rs.getString(8);

					String patTestResultStr = "select vailid,testname,value,testnormal_range,testunit from tbl_med_mcc_patient_test_result where patientid=?";
					patTestResultSt = con.prepareStatement(patTestResultStr);
					patTestResultSt.setLong(1, id);

					patTestResultRs = patTestResultSt.executeQuery();
					if (patTestResultRs.next() == false) {
						System.out.println("patTestResultRs is empty");
						return null;
					} else {
						do {
							String labId = patTestResultRs.getString(1);
							if(labId!=null && labId.length()>0)
							labId = labId.substring(0, labId.length() - 1);
							details.setLabId(labId);
						} while (patTestResultRs.next());
						
						
						allLabDetails.add(details);
					}
				} while (rs.next());
			}

			return ResponseEntity.ok(allLabDetails);
		} catch (Exception e) {
			logger.error("error while getting history lims in getHistoryLabDetails" + e);
		} finally {
			try {
				if (con != null)
					con.close();
				if (preparedStatement != null)
					preparedStatement.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				logger.error("error while closing in getHistoryLabDetails " + e);
			}

		}
		return null;
	}
	
	@GetMapping("/lab/details/id")
	@ResponseBody
	public ResponseEntity<?> getLabDetailsByLabId(@RequestParam String labId,@RequestParam String regNum){
		
		logger.info("getting lab details by lab id");
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		PreparedStatement patTestResultSt = null;
		PreparedStatement doctorPreSt = null;
		ResultSet docotrRs = null;
		PreparedStatement unitMasterSt = null;
		ResultSet unitRs = null;

		ResultSet patTestResultRs = null;
		try {
			con = MSSqlUtil.getConnection();

			String patienMaster = " select Top 1 id,mcc_code,name,age,gender,sample_date,sample_time,status,mobile_number from tbl_med_mcc_patient_master where name like ? order by sample_date desc";

			preparedStatement = con.prepareStatement(patienMaster);
			preparedStatement.setString(1, "%" + regNum);
			AllLabDetails details = new AllLabDetails();
			rs = preparedStatement.executeQuery();
			if (rs.next() == false) {
				System.out.println("ResultSet is empty");
			} else {

				do {
					details = new AllLabDetails();
					details.setLabId(rs.getString(1));
					details.setAge(rs.getString(4));
					details.setGender(rs.getString(5));
					details.setSubjectInitials(rs.getString(9));
					details.setRegNum(rs.getString(3));
					details.setSampleReceivedOn(rs.getTimestamp(6));
					details.setSamplecollectedOn(rs.getTimestamp(7));

					Long id = rs.getLong(1);
					String mccCode = rs.getString(2);
					String status = rs.getString(8);

					String patTestResultStr = "select vailid,testname,value,testnormal_range,testunit from tbl_med_mcc_patient_test_result where vailid like ?";
					patTestResultSt = con.prepareStatement(patTestResultStr);
					patTestResultSt.setString(1, "%"+labId+"%");

					patTestResultRs = patTestResultSt.executeQuery();
					List<Map<String, String>> hDetails = new ArrayList<>();
					List<Map<String, String>> bDetails = new ArrayList<>();
					List<Map<String, String>> sDetails = new ArrayList<>();
					if (patTestResultRs.next() == false) {
						System.out.println("patTestResultRs is empty");
					} else {

						do {
							labId = patTestResultRs.getString(1);
							if (null != patTestResultRs.getString(3) && !(patTestResultRs.getString(3).isEmpty())) {
								if (StringUtils.contains(labId, "W")) {
									Map<String, String> map = new HashMap<>();
									map.put("testname", patTestResultRs.getString(2));
									// map.put("range", patTestResultRs.getString(4));
									map.put("value", patTestResultRs.getString(3));
									if (null != patTestResultRs.getString(5) && !StringUtils.contains(patTestResultRs.getString(4), patTestResultRs.getString(5))) {
										map.put("range", patTestResultRs.getString(4) + patTestResultRs.getString(5));
									} else {
										map.put("range", patTestResultRs.getString(4));
									}
									hDetails.add(map);
								}

								if (StringUtils.contains(labId, "B") || StringUtils.contains(labId, "P")) {
									Map<String, String> map = new HashMap<>();
									map.put("testname", patTestResultRs.getString(2));
									map.put("value", patTestResultRs.getString(3));
									// map.put("range", patTestResultRs.getString(4));
									if (null != patTestResultRs.getString(5) && !StringUtils.contains(patTestResultRs.getString(4), patTestResultRs.getString(5))) {
										map.put("range", patTestResultRs.getString(4) + patTestResultRs.getString(5));
									} else {
										map.put("range", patTestResultRs.getString(4));
									}
									bDetails.add(map);
								}

								if (StringUtils.contains(labId, "S")) {
									Map<String, String> map = new HashMap<>();
									map.put("testname", patTestResultRs.getString(2));
									map.put("value", patTestResultRs.getString(3));
									if (null != patTestResultRs.getString(5) && !StringUtils.contains(patTestResultRs.getString(4), patTestResultRs.getString(5))) {
										map.put("range", patTestResultRs.getString(4) + patTestResultRs.getString(5));
									} else {
										map.put("range", patTestResultRs.getString(4));
									}
									sDetails.add(map);
								}
							}

							if(labId!=null && labId.length()>0)
								labId = labId.substring(0, labId.length() - 1);
								details.setLabId(labId);
								
						} while (patTestResultRs.next());
					}

					details.setHaematologyDetails(hDetails);
					details.setBioDetails(bDetails);
					details.setSerologyDetails(sDetails);
					String doctorsMasterStr = "select doctor_name from tbl_med_mcc_doctors where doctor_code=?";

					doctorPreSt = con.prepareStatement(doctorsMasterStr);
					doctorPreSt.setInt(1, Integer.valueOf(status));
					docotrRs = doctorPreSt.executeQuery();
					if (docotrRs.next() == false) {
						System.out.println("ResultSet is empty");
					} else {

						do {
							details.setReferredBy(docotrRs.getString(1));
						} while (docotrRs.next());
					}

					String unitMasterStr = "select MCCUnitName from tbl_med_mcc_unit_master where id=?";
					unitMasterSt = con.prepareStatement(unitMasterStr);
					unitMasterSt.setInt(1, Integer.valueOf(mccCode));
					unitRs = unitMasterSt.executeQuery();

					if (unitRs.next() == false) {
						System.out.println("ResultSet is empty");
					} else {

						do {
							details.setSamplecollectedAt(unitRs.getString(1));
						} while (unitRs.next());
					}

					System.out.println(details);

				} while (rs.next());
			}

			return ResponseEntity.ok(details);
		} catch (Exception e) {
			logger.error("error while getting lab details " + e);
		} finally {
			try {
				if (con != null)

					con.close();
				if (preparedStatement != null)
					preparedStatement.close();
				if (rs != null)
					rs.close();
				if (patTestResultSt != null)
					patTestResultSt.close();
				if (doctorPreSt != null)
					doctorPreSt.close();
				if (docotrRs != null)
					docotrRs.close();
				if (unitMasterSt != null)
					unitMasterSt.close();
				if (unitRs != null)
					unitRs.close();

			} catch (SQLException e) {
				logger.error("error while closing" + e);
			}
		}
		
		
		return null;
		
	}
	
	@RequestMapping(path = "/labdetailsbyid", method = RequestMethod.GET)
	public ModelAndView labDetailsById(ModelAndView modelAndView, ModelMap model,@RequestParam String labId,@RequestParam String regNum) {
		String type="pdf";
		JasperReportsMultiFormatView view = new JasperReportsMultiFormatView();
		view.setUrl("classpath:reports/LabDetailsbyId.jrxml");
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
			Connection con = null;
			PreparedStatement preparedStatement = null;
			ResultSet rs = null;
			PreparedStatement patTestResultSt = null;
			PreparedStatement doctorPreSt = null;
			ResultSet docotrRs = null;
			PreparedStatement unitMasterSt = null;
			ResultSet unitRs = null;

			ResultSet patTestResultRs = null;
			AllLabDetails details = new AllLabDetails();
			try {
				con = MSSqlUtil.getConnection();

				String patienMaster = " select Top 1 id,mcc_code,name,age,gender,sample_date,sample_time,status,mobile_number from tbl_med_mcc_patient_master where name like ? order by sample_date desc";

				preparedStatement = con.prepareStatement(patienMaster);
				preparedStatement.setString(1, "%" + regNum);
				
				rs = preparedStatement.executeQuery();
				if (rs.next() == false) {
					System.out.println("ResultSet is empty");
				} else {

					do {
						details = new AllLabDetails();
						details.setLabId(rs.getString(1));
						details.setAge(rs.getString(4));
						details.setGender(rs.getString(5));
						details.setSubjectInitials(rs.getString(9));
						details.setRegNum(rs.getString(3));
						details.setSampleReceivedOn(rs.getTimestamp(6));
						details.setSamplecollectedOn(rs.getTimestamp(7));

						Long id = rs.getLong(1);
						String mccCode = rs.getString(2);
						String status = rs.getString(8);

						String patTestResultStr = "select vailid,testname,value,testnormal_range,testunit from tbl_med_mcc_patient_test_result where vailid like ?";
						patTestResultSt = con.prepareStatement(patTestResultStr);
						patTestResultSt.setString(1, "%"+labId+"%");

						patTestResultRs = patTestResultSt.executeQuery();
						List<Map<String, String>> hDetails = new ArrayList<>();
						List<Map<String, String>> bDetails = new ArrayList<>();
						List<Map<String, String>> sDetails = new ArrayList<>();
						if (patTestResultRs.next() == false) {
							System.out.println("patTestResultRs is empty");
						} else {

							do {
								labId = patTestResultRs.getString(1);
								if (null != patTestResultRs.getString(3) && !(patTestResultRs.getString(3).isEmpty())) {
									if (StringUtils.contains(labId, "W")) {
										Map<String, String> map = new HashMap<>();
										map.put("testname", patTestResultRs.getString(2));
										// map.put("range", patTestResultRs.getString(4));
										map.put("value", patTestResultRs.getString(3));
										if (null != patTestResultRs.getString(5) && !StringUtils.contains(patTestResultRs.getString(4), patTestResultRs.getString(5))) {
											map.put("range", patTestResultRs.getString(4) + patTestResultRs.getString(5));
										} else {
											map.put("range", patTestResultRs.getString(4));
										}
										hDetails.add(map);
									}

									if (StringUtils.contains(labId, "B") || StringUtils.contains(labId, "P")) {
										Map<String, String> map = new HashMap<>();
										map.put("testname", patTestResultRs.getString(2));
										map.put("value", patTestResultRs.getString(3));
										// map.put("range", patTestResultRs.getString(4));
										if (null != patTestResultRs.getString(5) && !StringUtils.contains(patTestResultRs.getString(4), patTestResultRs.getString(5))) {
											map.put("range", patTestResultRs.getString(4) + patTestResultRs.getString(5));
										} else {
											map.put("range", patTestResultRs.getString(4));
										}
										bDetails.add(map);
									}

									if (StringUtils.contains(labId, "S")) {
										Map<String, String> map = new HashMap<>();
										map.put("testname", patTestResultRs.getString(2));
										map.put("value", patTestResultRs.getString(3));
										if (null != patTestResultRs.getString(5) && !StringUtils.contains(patTestResultRs.getString(4), patTestResultRs.getString(5))) {
											map.put("range", patTestResultRs.getString(4) + patTestResultRs.getString(5));
										} else {
											map.put("range", patTestResultRs.getString(4));
										}
										sDetails.add(map);
									}
								}

								if(labId!=null && labId.length()>0)
									labId = labId.substring(0, labId.length() - 1);
									details.setLabId(labId);
									
							} while (patTestResultRs.next());
						}

						details.setHaematologyDetails(hDetails);
						details.setBioDetails(bDetails);
						details.setSerologyDetails(sDetails);
						String doctorsMasterStr = "select doctor_name from tbl_med_mcc_doctors where doctor_code=?";

						doctorPreSt = con.prepareStatement(doctorsMasterStr);
						doctorPreSt.setInt(1, Integer.valueOf(status));
						docotrRs = doctorPreSt.executeQuery();
						if (docotrRs.next() == false) {
							System.out.println("ResultSet is empty");
						} else {

							do {
								details.setReferredBy(docotrRs.getString(1));
							} while (docotrRs.next());
						}

						String unitMasterStr = "select MCCUnitName from tbl_med_mcc_unit_master where id=?";
						unitMasterSt = con.prepareStatement(unitMasterStr);
						unitMasterSt.setInt(1, Integer.valueOf(mccCode));
						unitRs = unitMasterSt.executeQuery();

						if (unitRs.next() == false) {
							System.out.println("ResultSet is empty");
						} else {

							do {
								details.setSamplecollectedAt(unitRs.getString(1));
							} while (unitRs.next());
						}

						System.out.println(details);

					} while (rs.next());
				}

			
			} catch (Exception e) {
				logger.error("error while getting lab details " + e);
			} finally {
				try {
					if (con != null)

						con.close();
					if (preparedStatement != null)
						preparedStatement.close();
					if (rs != null)
						rs.close();
					if (patTestResultSt != null)
						patTestResultSt.close();
					if (doctorPreSt != null)
						doctorPreSt.close();
					if (docotrRs != null)
						docotrRs.close();
					if (unitMasterSt != null)
						unitMasterSt.close();
					if (unitRs != null)
						unitRs.close();

				} catch (SQLException e) {
					logger.error("error while closing" + e);
				}
			}
			
			List<AllLabDetails> labHistory = new ArrayList<AllLabDetails>(); 
			labHistory.add(details);
			model.addAttribute("datasource", labHistory);
			model.addAttribute("format", type);
			return new ModelAndView(view, model);

		} else {

			return null;

		}
	}
}
