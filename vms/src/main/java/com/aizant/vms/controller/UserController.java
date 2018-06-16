package com.aizant.vms.controller;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aizant.vms.model.StudyCenter;
import com.aizant.vms.model.User;
import com.aizant.vms.model.UserCreds;
import com.aizant.vms.model.UserLoginLogoutAudit;
import com.aizant.vms.repo.StudyCenterRepo;
import com.aizant.vms.repo.UserLoginLogoutAuditRepo;
import com.aizant.vms.repo.UserRepository;
import com.aizant.vms.security.JwtTokenUtil;
import com.aizant.vms.security.JwtUser;
import com.aizant.vms.security.JwtUserFactory;

@Controller
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserRepository userRepo;

	@Autowired
	StudyCenterRepo studyCenterRepo;

	@Autowired
	UserLoginLogoutAuditRepo userLoginLogoutAuditRepo;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Value("${jwt.header}")
	private String tokenHeader;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@GetMapping("/currentuser")
	@ResponseBody
	public JwtUser getAuthenticatedUser(HttpServletRequest request) {
		String token = request.getHeader(tokenHeader).substring(7);
		String username = jwtTokenUtil.getUsernameFromToken(token);
		JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(username);

		List<UserLoginLogoutAudit> audits = userLoginLogoutAuditRepo.findbyUser(username);
		UserLoginLogoutAudit audit = null;
		if (audits != null && !audits.isEmpty()) {
			audit = audits.get(0);
			if (audit != null && audit.getCenterId() != null) {
				StudyCenter studyCenter = studyCenterRepo.findOne(audit.getCenterId());
				jwtUser.setCenterId(audit.getCenterId());
				jwtUser.setCenterName(studyCenter.getCenterName());
			}
		}

		return jwtUser;
	}

	@GetMapping("/users")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public ResponseEntity<Set<JwtUser>> getAllUsers() {
		List<User> users = userRepo.findAll();
		Set<JwtUser> jwtUsers = new HashSet();
		for (User user : users) {
			jwtUsers.add(JwtUserFactory.create(user));
		}
		return ResponseEntity.ok(jwtUsers);
	}

	@GetMapping("/users/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public ResponseEntity<JwtUser> getAuthenticatedUserById(@PathVariable Long id) {
		User user = userRepo.findOne(id);
		return ResponseEntity.ok(JwtUserFactory.create(user));
	}

	@PostMapping("/users")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public ResponseEntity<?> createUser(@RequestBody User user) {
		try {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			user.setPin(bCryptPasswordEncoder.encode(user.getPin()));
			userRepo.save(user);
			return ResponseEntity.ok(Boolean.TRUE);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@PostMapping("/users/{id}")
//	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public ResponseEntity<?> editUser(@PathVariable Long id, @RequestBody User user) {
		try {
			User user2 = userRepo.findOne(id);
			if (user2 == null)
				return null;

			// BeanUtils.copyProperties(user, user2);
			if (user.getPassword() != null) {
				user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			} else {
				user.setPassword(user2.getPassword());
			}
			if (user.getPin().equals(user2.getPin())) {	
				user.setPin(user2.getPin());
			} else {
				user.setPin(bCryptPasswordEncoder.encode(user.getPin()));
			}

			user.setId(id);

			userRepo.save(user);
			return ResponseEntity.ok(Boolean.TRUE);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/users/changepassword/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public ResponseEntity<?> changePassword(@PathVariable Long id, @RequestBody User user) {
		try {
			User user2 = userRepo.findOne(id);
			if (user2 == null)
				return null;

			if (user.getPassword() != null) {
				user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			}
			if (user.getPin() != null) {
				user.setPin(bCryptPasswordEncoder.encode(user.getPin()));
			}

			BeanUtils.copyProperties(user, user2);
			user2.setId(id);

			userRepo.save(user2);
			return ResponseEntity.ok(Boolean.TRUE);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/currentuser/changepassword/{id}")
	@ResponseBody
	public ResponseEntity<?> changeCurrentUserPassword(@PathVariable Long id, @RequestBody UserCreds userCreds) {
		try {
			User user1 = userRepo.findOne(id);
			if (userCreds.getOldPassword() != null) {
				Boolean currPassword = bCryptPasswordEncoder.matches(userCreds.getOldPassword(), user1.getPassword());
				if (currPassword) {
					user1.setPassword(bCryptPasswordEncoder.encode(userCreds.getNewPassword()));
					userRepo.save(user1);
					return ResponseEntity.ok(Boolean.TRUE);
				} else {
					return ResponseEntity.badRequest().body("old password is not matched with current password");
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.badRequest().body("Password change failed");
	}

	@PostMapping("/currentuser/changepin/{id}")
	@ResponseBody
	public ResponseEntity<?> changeCurrentUserPin(@PathVariable Long id, @RequestBody UserCreds userCreds) {
		try {
			User user1 = userRepo.findOne(id);
			if (userCreds.getOldPin() != null) {
				Boolean currPin = bCryptPasswordEncoder.matches(userCreds.getOldPin(), user1.getPin());
				if (currPin) {
					user1.setPin(bCryptPasswordEncoder.encode(userCreds.getNewPin()));
					userRepo.save(user1);
					return ResponseEntity.ok(Boolean.TRUE);
				} else {
					return ResponseEntity.badRequest().body("old pin is not matched with current pin");
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.badRequest().body("Pin changing failed");
	}

	@GetMapping("/user/check/{username}")
	@ResponseBody
	public ResponseEntity<?> checkUserExists(@PathVariable String username) {
		User user = userRepo.findByUsername(username);
		if (user != null) {
			return ResponseEntity.ok(Boolean.TRUE);
		} else {
			return ResponseEntity.ok(Boolean.FALSE);
		}

	}
	@GetMapping("/user/pincheck/{username}/{pin}")
	@ResponseBody
	public ResponseEntity<?> checkPin(@PathVariable String username,@PathVariable String pin) {
		User user = userRepo.findByUsername(username);
		
		if (bCryptPasswordEncoder.matches(pin, user.getPin())) {
			return ResponseEntity.ok(Boolean.TRUE);
		} else {
			return ResponseEntity.ok(Boolean.FALSE);
		}

	}
	@GetMapping("/user/getbyid/{id}")
	@ResponseBody
	public User getVolunteerBySingleId(@PathVariable("id") Long id) {
		logger.info("Get one volunteer data");
		User user = null;
		try {
			user = userRepo.findOne(id);
		
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("failed to get the data"+e);
		}
		return user;
	}
	@GetMapping("/user/delete/{id}")
	@ResponseBody
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		try {
			User user = userRepo.findOne(id);
			user.setEnabled(false);
			userRepo.save(user);
			return ResponseEntity.ok(Boolean.TRUE);
		} catch (Exception e) {

		}
		return ResponseEntity.badRequest().body(null);
	}

	@GetMapping("/user/logout/{username}")
	@ResponseBody
	public ResponseEntity<?> userLogout(@PathVariable String username) {
		try {

			List<UserLoginLogoutAudit> audits = userLoginLogoutAuditRepo.findbyUser(username);
			UserLoginLogoutAudit audit = null;
			if (audits != null && !audits.isEmpty()) {
				audit = audits.get(0);

				Timestamp timestamp = new Timestamp(new java.util.Date().getTime());
				audit.setLastLogout(timestamp);

				userLoginLogoutAuditRepo.save(audit);
				return ResponseEntity.ok(Boolean.TRUE);
			}

		} catch (Exception e) {

		}
		return ResponseEntity.badRequest().body(null);
	}

	@GetMapping("/centers")
	@ResponseBody
	public ResponseEntity<?> getAllCenteres() {
		try {

			List<StudyCenter> centers = studyCenterRepo.findAll();
			return ResponseEntity.ok(centers);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return ResponseEntity.badRequest().body(null);
	}
	@GetMapping("/userdata/{username}")
	@ResponseBody
	public ResponseEntity<?> getUserData(@PathVariable String username) {
		User user = userRepo.findByUsername(username);
		if (user != null) {
			return ResponseEntity.ok(user);
		} else {
			return null;
		}

	}
}
