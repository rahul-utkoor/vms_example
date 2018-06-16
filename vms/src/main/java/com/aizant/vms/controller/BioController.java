/*package com.aizant.vms.controller;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Base64;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aizant.vms.dto.BioDTO;
import com.aizant.vms.model.Volunteer;
import com.aizant.vms.model.VolunteerFingerDtls;
import com.aizant.vms.repo.VolunteerFingerDtlsRepo;
import com.aizant.vms.repo.VolunteerRepo;
import com.neurotec.biometrics.NBiometricOperation;
import com.neurotec.biometrics.NBiometricStatus;
import com.neurotec.biometrics.NBiometricTask;
import com.neurotec.biometrics.NFinger;
import com.neurotec.biometrics.NMatchingResult;
import com.neurotec.biometrics.NSubject;
import com.neurotec.biometrics.NTemplateSize;
import com.neurotec.biometrics.client.NBiometricClient;
import com.neurotec.devices.NDevice;
import com.neurotec.devices.NDeviceManager;
import com.neurotec.devices.NDeviceManager.DeviceCollection;
import com.neurotec.devices.NDeviceType;
import com.neurotec.devices.NFScanner;
import com.neurotec.images.NImage;
import com.neurotec.images.NImageFormat;
import com.neurotec.io.NBuffer;
import com.neurotec.licensing.NLicense;
import com.neurotec.samples.FingersTools;

@Controller
public class BioController {

	@Autowired
	VolunteerFingerDtlsRepo volunteerFingerDtlsRepo;

	@Autowired
	VolunteerRepo volunteerRepo;

	final String components = "Biometrics.FingerExtraction,Devices.FingerScanners,Biometrics.FingerMatching";
	Map<String, String> errorMap = new HashMap();

	@GetMapping("/bio/capture")
	@ResponseBody
	public ResponseEntity<?> captureFinger() {
		byte[] bytes = null;
		NBiometricClient biometricClient = null;
		NSubject subject = null;
		NFinger finger = null;
		NImage image = null;
		BioDTO bioDTO = null;
		String imageAsBase64String = null;
		try {
			if (!NLicense.obtainComponents("/local", 5000, components)) {
				System.err.format("Could not obtain licenses for components: %s%n", components);
				System.exit(-1);
			}
			List<String> deviceList = new ArrayList<>();
			biometricClient = new NBiometricClient();
			subject = new NSubject();
			finger = new NFinger();
			biometricClient.setUseDeviceManager(true);
			NDeviceManager deviceManager = biometricClient.getDeviceManager();
			deviceManager.setDeviceTypes(EnumSet.of(NDeviceType.FINGER_SCANNER));
			deviceManager.initialize();
			DeviceCollection devices = deviceManager.getDevices();
			if (devices.size() > 0) {
				System.out.format("Found %d fingerprint scanner\n", devices.size());
			} else {
				System.out.format("No scanners found\n");
			}
			Iterator<NDevice> iterator = devices.iterator();
			while (iterator.hasNext()) {
				NDevice deviced = iterator.next();
				deviceList.add(deviced.getDisplayName());
			}
			int selection = 0;
			biometricClient.setFingerScanner((NFScanner) devices.get(selection));
			subject.getFingers().add(finger);
			System.out.println("Capturing....");
			NBiometricStatus status = biometricClient.capture(subject);
			biometricClient.setFingersTemplateSize(NTemplateSize.LARGE);
			status = biometricClient.createTemplate(subject);
			if (status == NBiometricStatus.OK) {
				System.out.println("Template extracted");
			} else {
				System.out.format("Extraction failed: %s\n", status);
				System.exit(-1);
			}
			ByteBuffer jpegImageByteBuffer;
			byte[] jpgImage;
			jpegImageByteBuffer = subject.getFingers().get(0).getImage().save(NImageFormat.getJPEG()).asByteBuffer();
			jpgImage = new byte[jpegImageByteBuffer.capacity()];
			jpegImageByteBuffer.get(jpgImage, 0, jpgImage.length);
			imageAsBase64String = Base64.getEncoder().encodeToString(jpgImage);

			bioDTO = new BioDTO();
			bioDTO.setBase64(imageAsBase64String);
			bioDTO.setBytes(subject.getTemplate().save().toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (finger != null)
				finger.dispose();
			if (subject != null)
				subject.dispose();
			if (biometricClient != null)
				biometricClient.dispose();
		}
		return ResponseEntity.ok(bioDTO);
	}

	@PostMapping("/bio/save")
	public ResponseEntity<?> saveFPTemplate(@RequestBody BioDTO bioDTO) {

		try {
			if (!NLicense.obtainComponents("/local", 5000, components)) {
				System.err.format("Could not obtain licenses for components: %s%n", components);
				System.exit(-1);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String regNum = bioDTO.getRegNum();
		byte[] bytes = bioDTO.getBytes();

		Volunteer volunteer = volunteerRepo.findByRegistrationNumber(regNum);
		if (volunteer == null)
			return null;

		NBuffer buffer = NBuffer.fromArray(bytes);
		NSubject subject = NSubject.fromMemory(buffer);

		VolunteerFingerDtls fingerDtls = new VolunteerFingerDtls();
		fingerDtls.setDbid(subject.getId());
		fingerDtls.setTemplate(bytes);

		fingerDtls = volunteerFingerDtlsRepo.save(fingerDtls);

		volunteer.setFingerId(fingerDtls.getId());
		volunteerRepo.save(volunteer);

		return ResponseEntity.ok(volunteer);
	}

	@GetMapping("/bio/scanner/list")
	@ResponseBody
	public ResponseEntity<?> getScannersList() {
		NBiometricClient biometricClient = null;
		NSubject subject = null;
		NFinger finger = null;
		try {
			if (!NLicense.obtainComponents("/local", 5000, components)) {
				System.err.format("Could not obtain licenses for components: %s%n", components);
				System.exit(-1);
			}
			List<String> deviceList = new ArrayList<>();
			biometricClient = new NBiometricClient();
			subject = new NSubject();
			finger = new NFinger();
			biometricClient.setUseDeviceManager(true);
			NDeviceManager deviceManager = biometricClient.getDeviceManager();
			deviceManager.setDeviceTypes(EnumSet.of(NDeviceType.FINGER_SCANNER));
			deviceManager.initialize();
			DeviceCollection devices = deviceManager.getDevices();
			if (devices.size() > 0) {
				System.out.format("Found %d fingerprint scanner\n", devices.size());
			} else {
				System.out.format("No scanners found\n");
			}
			Iterator<NDevice> iterator = devices.iterator();
			while (iterator.hasNext()) {
				NDevice deviced = iterator.next();
				deviceList.add(deviced.getDisplayName());
			}
			return ResponseEntity.ok(deviceList);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (finger != null)
				finger.dispose();
			if (subject != null)
				subject.dispose();
			if (biometricClient != null)
				biometricClient.dispose();
		}
		return null;
	}

	@PostMapping("/bio/compare")
	public ResponseEntity<?> compareBio(@RequestBody BioDTO bioDTO) {
		NBiometricClient biometricClient = null;
		NSubject subject = null;
		NFinger finger = null;
		NBiometricStatus status = null;
		try {
			if (!NLicense.obtainComponents("/local", 5000, components)) {
				System.err.format("Could not obtain licenses for components: %s%n", components);
				System.exit(-1);
			}
			subject = new NSubject();
			NBuffer nBuffer = NBuffer.fromArray(bioDTO.getBytes());
			subject = NSubject.fromMemory(nBuffer);
			FingersTools.getInstance().getClient().clear();
			// Create enrollment task.
			NBiometricTask enrollmentTask = new NBiometricTask(EnumSet.of(NBiometricOperation.ENROLL));

			List<NSubject> subjects = new ArrayList<>();

			List<VolunteerFingerDtls> dtls = volunteerFingerDtlsRepo.findAll();
			if (dtls == null)
				return null;

			for (VolunteerFingerDtls dtls2 : dtls) {
				NBuffer buffer = NBuffer.fromArray(dtls2.getTemplate());
				NSubject subj = NSubject.fromMemory(buffer);
				subj.setId(dtls2.getDbid());
				subjects.add(subj);
			}

			// Add subjects to be enrolled.
			for (NSubject s1 : subjects) {
				enrollmentTask.getSubjects().add(s1);
			}

			subject.setId("abc");
			// Enroll subjects.
			FingersTools.getInstance().getClient().performTask(enrollmentTask);

			// FingersTools.getInstance().getClient().enroll(subject);
			status = FingersTools.getInstance().getClient().identify(subject);

			Long dbId = null;
			if ((status == NBiometricStatus.OK) || (status == NBiometricStatus.MATCH_NOT_FOUND)) {
				boolean match = false;
				for (NMatchingResult result : subject.getMatchingResults()) {
					match = true;
					dbId = Long.valueOf(result.getId());
					break;
				}

				if (!match) {
					Map<String, String> map = new HashMap<>();
					map.put("message", "NO VOLUNTEER FOUND");
					return ResponseEntity.badRequest().body(map);
				}
			}

			if (dbId == null)
				return null;
			VolunteerFingerDtls dtls2 = volunteerFingerDtlsRepo.findByDbid(dbId.toString());
			if (dtls2 == null)
				return null;

			Volunteer volunteer = volunteerRepo.findByFingerId(dtls2.getId());

			return ResponseEntity.ok(volunteer);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (finger != null)
				finger.dispose();
			if (subject != null)
				subject.dispose();
			if (biometricClient != null)
				biometricClient.dispose();
			
		}

		return null;
	}

}*/