package com.group.InternMap.User;

import com.group.InternMap.Admin.Admin;
import com.group.InternMap.FilePaths;
import com.group.InternMap.Job.JobRepo;
import com.group.InternMap.Recruiter.Recruiter;
import com.group.InternMap.Recruiter.RecruiterRepo;
import com.group.InternMap.Student.Student;
import com.group.InternMap.Student.StudentRepo;
import com.group.InternMap.cv.CV;
import com.group.InternMap.cv.CVRepo;
import groovy.util.ResourceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//CRUD operations
//Create, Read, Update, Delete
@Service
public class UserService implements FilePaths {

    private static final int MIN_PASSWORD_LENGTH = 12;

    AuthenticationManager authenticationManager;

    private PasswordEncoder passwordEncoder;
    private UserRepo userRepo;
    private StudentRepo studentRepo;
    private RecruiterRepo recruiterRepo;

    CVRepo cvRepo;
    JobRepo jobRepo;

    public UserService() {
    }

    @Autowired
    public UserService(UserRepo userRepo, StudentRepo studentRepo, CVRepo cvRepo, JobRepo jobRepo, RecruiterRepo recruiterRepo, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.studentRepo = studentRepo;
        this.recruiterRepo = recruiterRepo;
        this.cvRepo = cvRepo;
        this.jobRepo = jobRepo;
    }

    public void register(Users u, HttpServletRequest request) throws ServletException {
        String plainPassword = u.getPassword();
        register(u);
        request.logout();
        request.login(u.getEmail(), plainPassword);
    }

    public void register(Users u) {
        try {
                if (isEmailValid(u.getEmail())) {
                if (isPasswordValid(u.getPassword())) {
                    u.setPassword(passwordEncoder.encode(u.getPassword()));
                }
                userRepo.save(u);
            }
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("could")) {
                throw new DataIntegrityViolationException("User with this email already exists.");
            } else {
                throw new DataIntegrityViolationException("Required Data Missing");
            }
        }
    }

    public static boolean isPasswordValid(String password) {
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new IllegalArgumentException("Password is too short. It must be at least " + MIN_PASSWORD_LENGTH + " characters long.");
        }

        boolean hasUpperCase = false;
        boolean hasSpecialChar = false;
        boolean hasDigit = false;
        boolean hasLowerCase = false;

        for (int i = 0; i < password.length(); i++) {

            if (Character.isUpperCase(password.charAt(i))) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(password.charAt(i))) {
                hasLowerCase = true;
            } else if (Character.isDigit(password.charAt(i))) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(password.charAt(i)) && (!Character.isSpaceChar(password.charAt(i)) || !Character.isEmoji(password.charAt(i)))) {
                hasSpecialChar = true;
            }
        }

        String badPasswordMessage = "Password is too weak. ";

        if (!hasUpperCase) {
            throw new IllegalArgumentException(badPasswordMessage + "Password must contain at least one uppercase letter");
        } else if (!hasLowerCase) {
            throw new IllegalArgumentException(badPasswordMessage + "Password must contain at least one lowercase letter");
        } else if (!hasDigit) {
            throw new IllegalArgumentException(badPasswordMessage + "Password must contain at least one digit");
        } else if (!hasSpecialChar) {
            throw new IllegalArgumentException(badPasswordMessage + "Password must contain at least one special character followed by a letter or digit");
        }

        return true;
    }

    public static boolean isEmailValid(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        int atSymbolCount = 0;
        int dotCount = 0;

        if (!Character.isLetterOrDigit(email.charAt(0))) {
            throw new IllegalArgumentException("Email must start with a letter or digit");
        }

        for (int i = 0; i < email.length(); i++) {
                if (!Character.isDigit(email.charAt(i))  && ((Character.isSpaceChar(email.charAt(i))) || Character.isEmoji(email.charAt(i)))) {
                    throw new IllegalArgumentException("Email cannot contain spaces or emojis");
                } else if (email.charAt(i) == '.' || email.charAt(i) == '@') {
                    atSymbolCount += (email.charAt(i) == '@') ? 1 : 0;
                    dotCount += (email.charAt(i) == '.') ? 1 : 0;

                    try {
                        if (!Character.isLetterOrDigit(email.charAt(i + 1))) {
                            if (email.charAt(i) == '@') {
                                throw new IllegalArgumentException("Email must contain at least one letter or digit after the '@'");
                            }
                        }
                    } catch (IndexOutOfBoundsException e) {
                        throw new IllegalArgumentException("Email must contain at least one letter or digit after the '.'");
                    }

                    if (atSymbolCount > 1 || dotCount > 1) {
                        throw new IllegalArgumentException("Email cannot contain more than one '@' or '.'");
                    }
                } else if (!Character.isLetterOrDigit(email.charAt(i)) && email.charAt(i) != '-' && email.charAt(i) != '_') {
                    throw new IllegalArgumentException("Email cannot contain any special characters");
                }
        }
        return true;
    }

    public Users login(String email, String password) throws Exception {
        if (email == null || password == null) {
            throw new IllegalArgumentException("Neither the email nor the password are allowed to be empty.");
        }

        Optional<Users> optionalUser = userRepo.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new ResourceException("No user found with that email.");
        }

        Users user = optionalUser.get();
        if (user.getPassword().equals(password)) {
            return user;
        } else {
            throw new Exception("Provided password is incorrect.");
        }
    }

    public void updateUser(Users userToUpdate, Users userToUpdateWith) {

        if (userToUpdate.getRole() == UserRole.STUDENT) {
            Student student = studentRepo.findByEmail(userToUpdate.getEmail());

            student.setFName(userToUpdateWith.getFName());
            student.setLName(userToUpdateWith.getLName());

            if (isEmailValid(userToUpdateWith.getEmail())) {
                userToUpdate.setEmail(userToUpdateWith.getEmail());
            }

            if (userToUpdateWith instanceof Student studentToUpdateWith) {
                student.setStudentMajor(studentToUpdateWith.getStudentMajor());
                student.setFaculty(studentToUpdateWith.getFaculty());
                student.setUniName(studentToUpdateWith.getUniName());
                student.setGraduatingYear(studentToUpdateWith.getGraduatingYear());
            }

            studentRepo.save(student);
        } else if (userToUpdate.getRole() == UserRole.RECRUITER) {
            Recruiter recruiter = recruiterRepo.findByEmail(userToUpdate.getEmail());

            recruiter.setFName(userToUpdateWith.getFName());
            recruiter.setLName(userToUpdateWith.getLName());

            if (isEmailValid(userToUpdateWith.getEmail())) {
                userToUpdate.setEmail(userToUpdateWith.getEmail());
            }

            if (userToUpdateWith instanceof Recruiter recruiterToUpdateWith) {
                recruiter.setTitle(recruiterToUpdateWith.getTitle());
            }

            recruiterRepo.save(recruiter);
        } else if (userToUpdate.getRole() == UserRole.ADMIN) {
            userToUpdate.setFName(userToUpdateWith.getFName());
            userToUpdate.setLName(userToUpdateWith.getLName());

            if (isEmailValid(userToUpdateWith.getEmail())) {
                userToUpdate.setEmail(userToUpdateWith.getEmail());
            }
        } else {
            throw new IllegalArgumentException("Unkown user type??? How did that happen?");
        }
    }

    public Optional<Users> searchByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public Optional<Users> searchByID(long id) {
        return userRepo.findById(id);
    }

    public List<Users> findall() {
        return userRepo.findAll();
    }

    public void deleteByEmail(String email) {
        Optional<Users> user = userRepo.findByEmail(email);
        if (user.isPresent()) {
            if (user.get().getRole() == UserRole.STUDENT) {
                CV cv = studentRepo.findByEmail(email).getCv();
                if (cv != null) {
                    cvRepo.delete(cv);
               }

               studentRepo.delete(studentRepo.findByEmail(email));
            } else if (user.get().getRole() == UserRole.RECRUITER) {
                jobRepo.deleteAll(recruiterRepo.findByEmail(email).getJobPosting());
                recruiterRepo.delete(recruiterRepo.findByEmail(email));
            }

            userRepo.delete(user.get());
        } else {
            throw new IllegalArgumentException("No user found with that email.");
        }
    }
}
